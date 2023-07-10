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
import es.zarca.covellog.domain.model.email.ServidorEmail;
import es.zarca.covellog.domain.model.email.ServidorEmailRepository;
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
public class JpaServidorEmailRepository implements ServidorEmailRepository, Serializable {
    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(JpaServidorEmailRepository.class.getName());

    @PersistenceContext(unitName = "covellog2-pu")
    private EntityManager entityManager;

    @Override
    public ServidorEmail find(String id) {
        ServidorEmail servidorEmail;
        try {
            servidorEmail = entityManager.createNamedQuery("ServidorEmail.findById", ServidorEmail.class).setParameter("id", id).getSingleResult();
        } catch (NoResultException e) {
            logger.log(Level.FINE, "Find called on non-existant tracking ID.", e);
            servidorEmail = null;
        }
        return servidorEmail;
    }

    @Override
    public void store(ServidorEmail servidorEmail) {
        try {
            entityManager.persist(servidorEmail);
        } catch (ConstraintViolationException ex) {
            throw new PersistenceException(ex);
        }
    }
        
    @Override
    public void remove(ServidorEmail servidorEmail) {
        try {
            entityManager.remove(entityManager.merge(servidorEmail));
        } catch (ConstraintViolationException ex) {
            throw new PersistenceException(ex);
        }
    }
    
    @Override
    public List<ServidorEmail> findAll(int start, int size){      
        Query query = entityManager.createNamedQuery("ServidorEmail.findAll", ServidorEmail.class);
        query.setFirstResult(start);
        query.setMaxResults(size);
        List <ServidorEmail> list = query.getResultList();
        logger.log(Level.INFO, "{0}.findAll(start: {1}, size: {2}) => {3}", new Object[]{getClass().getName(), String.valueOf(start), String.valueOf(size), String.valueOf(list.size())});
        return list;
    }

    @Override
    public int findAllTotalCount() {
        logger.log(Level.INFO, "{0}.findAllTotalCount()", getClass().getName());
        Query query = entityManager.createQuery("Select count(o.id) From ServidorEmail o");
        return ((Long)query.getSingleResult()).intValue();
    }
    
    private List<Predicate> processFilters(CriteriaBuilder cb, Root<ServidorEmail> root, Map<String, Object> filters) {
        List<Predicate> predicates = new ArrayList<>();
        for (Map.Entry<String, Object> entry : filters.entrySet()) {
            String field = entry.getKey();
            Object value = entry.getValue();
            if (value == null) {
                continue;
            }
            Expression<String> expr, expr2;
            Predicate p;
            switch (field) {
                case "comercial.nom":
                    Join<ServidorEmail, Comercial> comercial = root.join("comercial");
                    expr = comercial.<String>get("nom").as(String.class);
                    expr2 = comercial.<String>get("cognoms").as(String.class);
                    p = cb.like(cb.concat(cb.concat(cb.lower(expr)," "), cb.lower(expr2)), "%" + value.toString().toLowerCase() + "%");
                    break;
                case "potencial.nom":
                    Join<ServidorEmail, Potencial2> potencial = root.join("potencial");
                    expr = potencial.<String>get("nom").as(String.class);
                    p = cb.like(cb.lower(expr), "%" + value.toString().toLowerCase() + "%");
                        
                    break;
                case "data":
                    expr = root.get("dataCreacio").as(String.class);
                    p = cb.like(cb.lower(expr), "%" + value.toString().toLowerCase() + "%");
                    break;
                default:
                    expr = root.get(field).as(String.class);
                    p = cb.like(cb.lower(expr), "%" + value.toString().toLowerCase() + "%");
                    break;
            }
            predicates.add(p); 
        }            
        
        return predicates;
    }
    
    private Order getOrderItem(CriteriaBuilder cb, Ordre ordre, Expression<String> path) {
        if (ordre.getTipusOrdre().equals(TipusOrdre.ASCENDENT)) {
            return cb.asc(path);
        }
        return cb.desc(path);
    }

    private List<Order> processOrder(CriteriaBuilder cb, Root<ServidorEmail> root, Ordre ordre) {
        Path<String> path;
        List<Order> orderList = new ArrayList();
        switch (ordre.getId()) {
            case "comercial.nom":
                Join<ServidorEmail, Comercial> comercial = root.join("comercial");
                orderList.add(getOrderItem(cb, ordre, comercial.get("nom")));
                orderList.add(getOrderItem(cb, ordre, comercial.get("cognoms")));
                break;
            case "potencial.nom":
                Join<ServidorEmail, Potencial2> potencial = root.join("potencial");
                orderList.add(getOrderItem(cb, ordre, potencial.get("nom")));
                break;
            case "data":
                orderList.add(getOrderItem(cb, ordre, root.get("dataCreacio")));
                break;
            default:
                orderList.add(getOrderItem(cb, ordre, root.get(ordre.getId())));
                break;
        }
        return orderList;
    }

    @Override
    public List<ServidorEmail> findAll(int start, int size, Ordre ordre, Map<String, Object> filters) {
        //logger.log(Level.INFO, getClass().getName() + ".findAll(" + String.valueOf(start) + "," + String.valueOf(size) + ")");
        logger.log(Level.INFO, "{0}.findAll(start: {1}, size: {2}, filters: {3})", new Object[]{getClass().getName(), String.valueOf(start), String.valueOf(size), filters.toString()});
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<ServidorEmail> criteriaQuery = cb.createQuery(ServidorEmail.class);
        Root<ServidorEmail> root = criteriaQuery.from(ServidorEmail.class);
        CriteriaQuery<ServidorEmail> select = criteriaQuery.select(root);
        
        if (filters != null && filters.size() > 0) {
            List<Predicate> predicates = processFilters(cb, root, filters);
            if (predicates.size() > 0) {
                criteriaQuery.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
            }           
        }
        if (ordre != null) {
            criteriaQuery.orderBy(processOrder(cb, root, ordre));
        }
            
        TypedQuery<ServidorEmail> query = entityManager.createQuery(select);
        query.setFirstResult(start);
        query.setMaxResults(size);
        List<ServidorEmail> list = query.getResultList();
        logger.log(Level.INFO, "OJITO: {0}", query.unwrap(JpaQuery.class).getDatabaseQuery().getJPQLString());
        logger.log(Level.INFO, "OJITO: {0}", query.unwrap(JpaQuery.class).getDatabaseQuery().getSQLString());
        return list;
    }

    @Override
    public int findAllTotalCount(Map<String, Object> filters) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = cb.createQuery(Long.class);
        Root<ServidorEmail> root = criteriaQuery.from(ServidorEmail.class);
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
