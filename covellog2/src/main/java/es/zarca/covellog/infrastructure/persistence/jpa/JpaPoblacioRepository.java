/*
 * Proyecto Covellog2
 * 
 * Este archivo ha sido creado para ZARCA y no se puede usar sin su consentimiento.
 */
package es.zarca.covellog.infrastructure.persistence.jpa;

import es.zarca.covellog.domain.model.adreces.pais.Pais;
import es.zarca.covellog.domain.model.adreces.poblacio.Poblacio;
import es.zarca.covellog.domain.model.adreces.poblacio.PoblacioRepository;
import es.zarca.covellog.domain.model.adreces.provincia.Provincia;
import es.zarca.covellog.domain.model.app.Ordre;
import es.zarca.covellog.domain.model.app.TipusOrdre;
import es.zarca.covellog.infrastructure.util.crono.Crono;
import es.zarca.covellog.infrastructure.persistence.exception.PersistenceException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.eclipse.persistence.jpa.JpaQuery;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
@ApplicationScoped
public class JpaPoblacioRepository implements PoblacioRepository, Serializable {
    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = Logger.getLogger(JpaPoblacioRepository.class.getName());

    @PersistenceContext(unitName = "covellog2-pu")
    private EntityManager entityManager;

    @Override
    public Poblacio find(Integer id) {
        if (id == null) {
            return null;
        }
        Poblacio poblacio;
        try {
            poblacio = entityManager.createNamedQuery("Poblacio.findById", Poblacio.class).setParameter("id", id).getSingleResult();
        } catch (NoResultException e) {
            LOGGER.log(Level.FINE, "Find called on non-existant tracking ID.", e);
            poblacio = null;
        }
        return poblacio;
    }

    @Override
    public void store(Poblacio poblacio) {
        try {
            entityManager.persist(poblacio);
        } catch (ConstraintViolationException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
            for (ConstraintViolation actual : ex.getConstraintViolations()) {
                LOGGER.log(Level.SEVERE, actual.toString());
            }
            throw new PersistenceException(ex);
        }
    }
        
    @Override
    public void remove(Poblacio poblacio) {
        entityManager.remove(entityManager.merge(poblacio));
    }
    
    @Override
    public List<Poblacio> findAll() {
        LOGGER.log(Level.INFO, "{0}.findAll()", getClass().getName());
        return entityManager.createNamedQuery("Poblacio.findAll", Poblacio.class).getResultList();
    }
    
    @Override
    public List<Poblacio> findAll(int start, int size){      
        Query query = entityManager.createNamedQuery("Poblacio.findAll", Poblacio.class);
        query.setFirstResult(start);
        query.setMaxResults(size);
        List <Poblacio> list = query.getResultList();
        LOGGER.log(Level.INFO, "{0}.findAll(start: {1}, size: {2}) => {3}", new Object[]{getClass().getName(), String.valueOf(start), String.valueOf(size), String.valueOf(list.size())});
        return list;
    }

    @Override
    public int findAllTotalCount() {
        LOGGER.log(Level.INFO, "{0}.findAllTotalCount()", getClass().getName());
        Query query = entityManager.createQuery("Select count(p.id) From Poblacio p");
        return ((Long)query.getSingleResult()).intValue();
    }

    @Override
    public List<Poblacio> findAll(Map<String, Object> filters) {
        LOGGER.log(Level.INFO, "{0}.findFiltered()", getClass().getName());
        return entityManager.createNamedQuery("Poblacio.findByFilter", Poblacio.class).getResultList();
    }
    
    private List<Predicate> processFilters(CriteriaBuilder cb, Root<Poblacio> root, Map<String, Object> filters) {
        List<Predicate> predicates = new ArrayList<>();
        for (Map.Entry<String, Object> entry : filters.entrySet()) {
            String field = entry.getKey();
            Object value = entry.getValue();
            if (value == null) {
                continue;
            }
            Expression<String> expr;
            switch (field) {
                case "provincia.nom":
                    {
                        Join<Poblacio, Provincia> provincia = root.join("provincia");
                        expr = provincia.<String>get("nom").as(String.class);
                        break;
                    }
                case "provincia.pais.nom":
                    {
                        Join<Poblacio, Provincia> provincia = root.join("provincia");
                        Join<Provincia, Pais> pais = provincia.join("pais");
                        expr = pais.<String>get("nom").as(String.class);
                        break;
                    }
                default:
                    expr = root.get(field).as(String.class);
                    break;
            }
            Predicate p = cb.like(cb.lower(expr), "%" + value.toString().toLowerCase() + "%");
            predicates.add(p);
        }            
        return predicates;
    }

    private Order processOrder(CriteriaBuilder cb, Root<Poblacio> root, Ordre ordre) {
        Path<String> path;
        switch (ordre.getId()) {
            case "provincia.nom":
                {
                    Join<Poblacio, Provincia> provincia = root.join("provincia");
                    path = provincia.get("nom");
                    break;
                }
            case "provincia.pais.nom":
                {
                    Join<Poblacio, Provincia> provincia = root.join("provincia");
                    Join<Provincia, Pais> pais = provincia.join("pais");
                    path = pais.get("nom");               
                    break;
                }
            default:
                path = root.get(ordre.getId());
                break;
        }
        if (ordre.getTipusOrdre().equals(TipusOrdre.ASCENDENT)) {
            return cb.asc(path);
        }
        return cb.desc(path);
    }

    @Override
    public List<Poblacio> findAll(int start, int size, Ordre ordre, Map<String, Object> filters) {
        //logger.log(Level.INFO, getClass().getName() + ".findAll(" + String.valueOf(start) + "," + String.valueOf(size) + ")");
        LOGGER.log(Level.INFO, "{0}.findAll(start: {1}, size: {2}, filters: {3})", new Object[]{getClass().getName(), String.valueOf(start), String.valueOf(size), filters.toString()});
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Poblacio> criteriaQuery = cb.createQuery(Poblacio.class);
        Root<Poblacio> root = criteriaQuery.from(Poblacio.class);
        CriteriaQuery<Poblacio> select = criteriaQuery.select(root);
        
        if (filters != null && filters.size() > 0) {
            List<Predicate> predicates = processFilters(cb, root, filters);
            if (predicates.size() > 0) {
                criteriaQuery.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
            }           
        }
        if (ordre != null) {
            criteriaQuery.orderBy(processOrder(cb, root, ordre));
        }
            
        TypedQuery<Poblacio> query = entityManager.createQuery(select);
        query.setFirstResult(start);
        query.setMaxResults(size);
        List<Poblacio> list = query.getResultList();
        LOGGER.log(Level.INFO, "OJITO: {0}", query.unwrap(JpaQuery.class).getDatabaseQuery().getJPQLString());
        LOGGER.log(Level.INFO, "OJITO: {0}", query.unwrap(JpaQuery.class).getDatabaseQuery().getSQLString());
        return list;
    }

    @Override
    public int findAllTotalCount(Map<String, Object> filters) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = cb.createQuery(Long.class);
        Root<Poblacio> root = criteriaQuery.from(Poblacio.class);
        CriteriaQuery<Long> select = criteriaQuery.select(cb.count(root));

        if (filters != null && filters.size() > 0) {
            List<Predicate> predicates = processFilters(cb, root, filters);
            if (predicates.size() > 0) {
                criteriaQuery.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
            }
        }
        //entityManager.createQuery(select).(JpaQuery.class) .getDatabaseQuery().getSQLString();
        
        Long count = entityManager.createQuery(select).getSingleResult();
        LOGGER.log(Level.INFO, "{0}.findFilteredTotalCount() => {1} ", new Object[]{getClass().getName(), count.toString()});
        return count.intValue();
    }

}
