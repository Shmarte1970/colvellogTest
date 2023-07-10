/*
 * Proyecto Covellog2
 * 
 * Este archivo ha sido creado para ZARCA y no se puede usar sin su consentimiento.
 */
package es.zarca.covellog.infrastructure.persistence.jpa;

import es.zarca.covellog.domain.model.adreces.pais.Pais;
import es.zarca.covellog.domain.model.adreces.provincia.Provincia;
import es.zarca.covellog.domain.model.adreces.provincia.ProvinciaRepository;
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
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.eclipse.persistence.jpa.JpaQuery;


/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
@ApplicationScoped
public class JpaProvinciaRepository implements ProvinciaRepository, Serializable {
    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(JpaProvinciaRepository.class.getName());

    @PersistenceContext(unitName = "covellog2-pu")
    private EntityManager entityManager;

    @Override
    public Provincia find(Integer id) {
        Provincia provincia;
        try {
            provincia = entityManager.createNamedQuery("Provincia.findById", Provincia.class).setParameter("id", id).getSingleResult();
        } catch (NoResultException e) {
            logger.log(Level.FINE, "Find called on non-existant tracking ID.", e);
            provincia = null;
        }
        return provincia;
    }

    @Override
    public Provincia findByNom(String nom) {
        Provincia provincia;
        try {
            provincia = entityManager.createNamedQuery("Provincia.findByNom", Provincia.class).setParameter("nom", nom).getSingleResult();
        } catch (NoResultException e) {
            logger.log(Level.FINE, "Find called on non-existant tracking ID.", e);
            provincia = null;
        }
        return provincia;
    }
    
    @Override
    public Provincia findByCodi(String codi) {
        Provincia provincia;
        try {
            provincia = entityManager.createNamedQuery("Provincia.findByCodi", Provincia.class).setParameter("codigo", codi).getSingleResult();
        } catch (NoResultException e) {
            logger.log(Level.FINE, "Find called on non-existant tracking ID.", e);
            provincia = null;
        }
        return provincia;
    }

    @Override
    public Provincia findByCodiPostal(String codiPostal) {
        Provincia provincia;
        try {
            provincia = entityManager.createNamedQuery("Provincia.findByCodiPostal", Provincia.class).setParameter("codiPostal", codiPostal).getSingleResult();
        } catch (NoResultException e) {
            logger.log(Level.FINE, "Find called on non-existant tracking ID.", e);
            provincia = null;
        }
        return provincia;
    }
    
    @Override
    public void store(Provincia provincia) {
        try {
        entityManager.persist(provincia);
        } catch (Exception e) {
            logger.log(Level.INFO, "JPA PROVINCIA " + e.getMessage());
        }
    }
        
    @Override
    public void remove(Provincia provincia) {
        entityManager.remove(entityManager.merge(provincia));
    }
    
    @Override
    public List<Provincia> findAll() {
        logger.log(Level.INFO, "{0}.findAll()", getClass().getName());
        return entityManager.createNamedQuery("Provincia.findAll", Provincia.class).getResultList();
    }
    
    @Override
    public List<Provincia> findAll(int start, int size){      
        Query query = entityManager.createNamedQuery("Provincia.findAll", Provincia.class);
        query.setFirstResult(start);
        query.setMaxResults(size);
        List <Provincia> list = query.getResultList();
        logger.log(Level.INFO, "{0}.findAll(start: {1}, size: {2}) => {3}", new Object[]{getClass().getName(), String.valueOf(start), String.valueOf(size), String.valueOf(list.size())});
        return list;
    }

    @Override
    public int findAllTotalCount() {
        logger.log(Level.INFO, "{0}.findAllTotalCount()", getClass().getName());
        Query query = entityManager.createQuery("Select count(p.id) From Provincia p");
        return ((Long)query.getSingleResult()).intValue();
    }

    @Override
    public List<Provincia> findAll(Map<String, Object> filters) {
        logger.log(Level.INFO, "{0}.findFiltered()", getClass().getName());
        return entityManager.createNamedQuery("Provincia.findByFilter", Provincia.class).getResultList();
    }
    
    private List<Predicate> processFilters(CriteriaBuilder cb, Root<Provincia> root, Map<String, Object> filters) {
        List<Predicate> predicates = new ArrayList<>();
        for (Map.Entry<String, Object> entry : filters.entrySet()) {
            String field = entry.getKey();
            Object value = entry.getValue();
            if (value == null) {
                continue;
            }
            Expression<String> expr;
            if (field.equals("pais.nom")) {
                Join<Provincia, Pais> pais = root.join("pais");
                expr = pais.<String>get("nom").as(String.class);
            } else {
                expr = root.get(field).as(String.class);
            }
            Predicate p = cb.like(cb.lower(expr), "%" + value.toString().toLowerCase() + "%");
            predicates.add(p);
        }            
        return predicates;
    }

    @Override
    public List<Provincia> findAll(int start, int size, Map<String, Object> filters) {
        //logger.log(Level.INFO, getClass().getName() + ".findAll(" + String.valueOf(start) + "," + String.valueOf(size) + ")");
        logger.log(Level.INFO, "{0}.findAll(start: {1}, size: {2}, filters: {3})", new Object[]{getClass().getName(), String.valueOf(start), String.valueOf(size), filters.toString()});
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Provincia> criteriaQuery = cb.createQuery(Provincia.class);
        Root<Provincia> root = criteriaQuery.from(Provincia.class);
        CriteriaQuery<Provincia> select = criteriaQuery.select(root);

        if (filters != null && filters.size() > 0) {
            List<Predicate> predicates = processFilters(cb, root, filters);
            if (predicates.size() > 0) {
                criteriaQuery.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
            }           
        }
        
        criteriaQuery.orderBy(cb.asc(root.get("nom")));
        TypedQuery<Provincia> query = entityManager.createQuery(select);

        query.setFirstResult(start);
        query.setMaxResults(size);
        List<Provincia> list = query.getResultList();
        logger.log(Level.INFO, "OJITO: {0}", query.unwrap(JpaQuery.class).getDatabaseQuery().getJPQLString());
        logger.log(Level.INFO, "OJITO: {0}", query.unwrap(JpaQuery.class).getDatabaseQuery().getSQLString());
        return list;
    }

    @Override
    public int findAllTotalCount(Map<String, Object> filters) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = cb.createQuery(Long.class);
        Root<Provincia> root = criteriaQuery.from(Provincia.class);
        CriteriaQuery<Long> select = criteriaQuery.select(cb.count(root));

        if (filters != null && filters.size() > 0) {
            List<Predicate> predicates = processFilters(cb, root, filters);
            if (predicates.size() > 0) {
                criteriaQuery.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
            }
        }
        //entityManager.createQuery(select).(JpaQuery.class) .getDatabaseQuery().getSQLString();
        
        Long count = entityManager.createQuery(select).getSingleResult();
        logger.log(Level.INFO, "{0}.findFilteredTotalCount() => {1} ", new Object[]{getClass().getName(), count.toString()});
        return count.intValue();
    }

}
