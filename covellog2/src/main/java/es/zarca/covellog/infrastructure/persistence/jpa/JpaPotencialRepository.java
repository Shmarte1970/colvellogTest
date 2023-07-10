/*
 * Proyecto Covellog2
 * 
 * Este archivo ha sido creado para ZARCA y no se puede usar sin su consentimiento.
 */
package es.zarca.covellog.infrastructure.persistence.jpa;

import es.zarca.covellog.domain.model.app.Ordre;
import es.zarca.covellog.domain.model.app.TipusOrdre;
import es.zarca.covellog.domain.model.comercials.comercial.Comercial;
import es.zarca.covellog.domain.model.clients.potencial.Potencial2;
import es.zarca.covellog.domain.model.clients.potencial.PotencialRepository;
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
import javax.validation.ConstraintViolationException;
import org.eclipse.persistence.jpa.JpaQuery;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
@ApplicationScoped
public class JpaPotencialRepository implements PotencialRepository, Serializable {
    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(JpaPotencialRepository.class.getName());

    @PersistenceContext(unitName = "covellog2-pu")
    private EntityManager entityManager;

    @Override
    public Potencial2 find(Integer id) {
        Potencial2 potencial;
        try {
            potencial = entityManager.createNamedQuery("Potencial.findById", Potencial2.class).setParameter("id", id).getSingleResult();
        } catch (NoResultException e) {
            logger.log(Level.FINE, "Find called on non-existant tracking ID.", e);
            potencial = null;
        }
        return potencial;
    }

    @Override
    public Potencial2 findByNom(String nom) {
        Potencial2 potencial;
        try {
            potencial = entityManager.createNamedQuery("Potencial.findByNom", Potencial2.class).setParameter("nom", nom).getSingleResult();
        } catch (NoResultException e) {
            logger.log(Level.FINE, "Find called on non-existant tracking ID.", e);
            potencial = null;
        }
        return potencial;
    }
    
    @Override
    public void store(Potencial2 potencial) {
        try {
            entityManager.persist(potencial);
        } catch (ConstraintViolationException ex) {
            throw new PersistenceException(ex);
        } catch (Exception ex) {
            throw new PersistenceException(ex.getMessage());
        }
    }
        
    @Override
    public void remove(Potencial2 potencial) {
        try {
            entityManager.remove(entityManager.merge(potencial));
        } catch (ConstraintViolationException ex) {
            throw new PersistenceException(ex);
        } catch (Exception ex) {
            throw new PersistenceException(ex.getMessage());
        }
    }
    
    @Override
    public List<Potencial2> findAll(int start, int size){      
        Query query = entityManager.createNamedQuery("Potencial.findAll", Potencial2.class);
        query.setFirstResult(start);
        query.setMaxResults(size);
        List <Potencial2> list = query.getResultList();
        logger.log(Level.INFO, "{0}.findAll(start: {1}, size: {2}) => {3}", new Object[]{getClass().getName(), String.valueOf(start), String.valueOf(size), String.valueOf(list.size())});
        return list;
    }

    @Override
    public int findAllTotalCount() {
        logger.log(Level.INFO, "{0}.findAllTotalCount()", getClass().getName());
        Query query = entityManager.createQuery("Select count(o.id) From Potencial o");
        return ((Long)query.getSingleResult()).intValue();
    }
    
    private List<Predicate> processFilters(CriteriaBuilder cb, Root<Potencial2> root, Map<String, Object> filters) {
        List<Predicate> predicates = new ArrayList<>();
        for (Map.Entry<String, Object> entry : filters.entrySet()) {
            String field = entry.getKey();
            Object value = entry.getValue();
            if (value == null) {
                continue;
            }
            Expression<String> expr, expr2;
            switch (field) {
                case "comercial.nom":
                    {
                        Join<Potencial2, Comercial> comercial = root.join("comercial");
                        expr = comercial.<String>get("nom").as(String.class);
                        expr2 = comercial.<String>get("cognoms").as(String.class);
                        Predicate p = cb.like(cb.concat(cb.concat(cb.lower(expr)," "), cb.lower(expr2)), "%" + value.toString().toLowerCase() + "%");
                        predicates.add(p);
                        break;
                    }
                default:
                    expr = root.get(field).as(String.class);
                    Predicate p = cb.like(cb.lower(expr), "%" + value.toString().toLowerCase() + "%");
                    predicates.add(p);
                    break;
            }
            
        }            
        return predicates;
    }

    private Order processOrder(CriteriaBuilder cb, Root<Potencial2> root, Ordre ordre) {
        Path<String> path;
        switch (ordre.getId()) {
            case "comercial.nom":
                {
                    Join<Potencial2, Comercial> comercial = root.join("comercial");
                    path = comercial.get("nom");
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
    public List<Potencial2> findAll(int start, int size, Ordre ordre, Map<String, Object> filters) {
        //logger.log(Level.INFO, getClass().getName() + ".findAll(" + String.valueOf(start) + "," + String.valueOf(size) + ")");
        logger.log(Level.INFO, "{0}.findAll(start: {1}, size: {2}, filters: {3})", new Object[]{getClass().getName(), String.valueOf(start), String.valueOf(size), filters.toString()});
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Potencial2> criteriaQuery = cb.createQuery(Potencial2.class);
        Root<Potencial2> root = criteriaQuery.from(Potencial2.class);
        CriteriaQuery<Potencial2> select = criteriaQuery.select(root);
        
        if (filters != null && filters.size() > 0) {
            List<Predicate> predicates = processFilters(cb, root, filters);
            if (predicates.size() > 0) {
                criteriaQuery.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
            }           
        }
        if (ordre != null) {
            criteriaQuery.orderBy(processOrder(cb, root, ordre));
        }
            
        TypedQuery<Potencial2> query = entityManager.createQuery(select);
        query.setFirstResult(start);
        query.setMaxResults(size);
        List<Potencial2> list = query.getResultList();
        logger.log(Level.INFO, "OJITO: {0}", query.unwrap(JpaQuery.class).getDatabaseQuery().getJPQLString());
        logger.log(Level.INFO, "OJITO: {0}", query.unwrap(JpaQuery.class).getDatabaseQuery().getSQLString());
        return list;
    }

    @Override
    public int findAllTotalCount(Map<String, Object> filters) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = cb.createQuery(Long.class);
        Root<Potencial2> root = criteriaQuery.from(Potencial2.class);
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
