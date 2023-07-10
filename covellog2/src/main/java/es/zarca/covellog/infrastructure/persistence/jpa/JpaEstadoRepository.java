package es.zarca.covellog.infrastructure.persistence.jpa;

import es.zarca.covellog.application.stock.exception.EstadoNotExistException;
import es.zarca.covellog.domain.model.app.Ordre;
import es.zarca.covellog.domain.model.app.TipusOrdre;
import es.zarca.covellog.domain.model.stock.StockEstado;
import es.zarca.covellog.infrastructure.persistence.exception.PersistenceException;
import es.zarca.covellog.infrastructure.util.crono.Crono;
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
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.ConstraintViolationException;
import es.zarca.covellog.domain.model.stock.StockEstadoRepository;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
@ApplicationScoped
public class JpaEstadoRepository implements StockEstadoRepository, Serializable {
    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(JpaEstadoRepository.class.getName());

    @PersistenceContext(unitName = "covellog2-pu")
    private EntityManager entityManager;

    @Override
    public StockEstado find(String id) {
        StockEstado estado;
        try {
            estado = entityManager.createNamedQuery("Estado.findById", StockEstado.class).setParameter("id", id).getSingleResult();
        } catch (NoResultException e) {
            estado = null;
        }
        return estado;
    }

    @Override
    public StockEstado findOrFail(String id) {
        StockEstado estado = find(id);
        if (estado == null) {
            throw new EstadoNotExistException(id);
        }
        return estado;
    }
    
    private Order getOrderItem(CriteriaBuilder cb, Ordre ordre, Expression<String> path) {
        if (ordre.getTipusOrdre().equals(TipusOrdre.ASCENDENT)) {
            return cb.asc(path);
        }
        return cb.desc(path);
    }
    
    private List<Order> processOrder(CriteriaBuilder cb, Root<StockEstado> root, Ordre ordre) {
        Path<String> path;
        List<Order> orderList = new ArrayList();
        switch (ordre.getId()) {
            default:
                orderList.add(getOrderItem(cb, ordre, root.get(ordre.getId())));
                break;
        }
        return orderList;
    }
    
    private List<Predicate> processFilters(CriteriaQuery query, CriteriaBuilder cb, Root<StockEstado> root, Map<String, Object> filters) {
        List<Predicate> predicates = new ArrayList<>();
        for (Map.Entry<String, Object> entry : filters.entrySet()) {
            String field = entry.getKey();
            Object value = entry.getValue();
            if (value == null) {
                continue;
            }
            Expression<String> expr;
            Predicate p;
            switch (field) {
                case "fechaBajaIsNotNull":
                    expr = root.get("fechaBaja").as(String.class);
                    p = cb.isNotNull(cb.lower(expr));
                    break;
                case "fechaBajaIsNull":
                    expr = root.get("fechaBaja").as(String.class);
                    p = cb.isNull(cb.lower(expr));
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
    
    @Override
    public List<StockEstado> findAll() {
        return entityManager.createNamedQuery("Estado.findAll", StockEstado.class).getResultList();
    }
    
    @Override
    public List<StockEstado> find(int first, int pageSize, Ordre sortOrder, Map<String, Object> filters) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<StockEstado> criteriaQuery = cb.createQuery(StockEstado.class);
        Root<StockEstado> root = criteriaQuery.from(StockEstado.class);
        CriteriaQuery<StockEstado> select = criteriaQuery.select(root);
        if (filters != null && filters.size() > 0) {
            List<Predicate> predicates = processFilters(criteriaQuery, cb, root, filters);
            if (predicates.size() > 0) {
                criteriaQuery.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
            }           
        }
        if (sortOrder != null) {
            criteriaQuery.orderBy(processOrder(cb, root, sortOrder));
        }
            
        TypedQuery<StockEstado> query = entityManager.createQuery(select);
        query.setFirstResult(first);
        query.setMaxResults(pageSize);
        List<StockEstado> list = query.getResultList();
        return list;
    }

    @Override
    public int findTotalCount() {
        Crono crono = new Crono();               
        Query query = entityManager.createQuery("Select count(f.id) From Estado f");
        int count = ((Long)query.getSingleResult()).intValue();
        logger.log(Level.FINE, "{0}.findAllTotalCount(): count = {1} ({2} ms)." ,new Object[]{getClass().getName(), String.valueOf(count), String.valueOf(crono.getMiliSegons())});
        return count;
    }
    
    
    @Override
    public int findTotalCount(Map<String, Object> filters) {
        if (filters == null) {
            return findTotalCount();
        }
        Crono crono = new Crono();
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = cb.createQuery(Long.class);
        Root<StockEstado> root = criteriaQuery.from(StockEstado.class);
        CriteriaQuery<Long> select = criteriaQuery.select(cb.count(root));

        if (filters.size() > 0) {
            List<Predicate> predicates = processFilters(criteriaQuery, cb, root, filters);
            if (predicates.size() > 0) {
                criteriaQuery.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
            }           
        }
        
        Long count = entityManager.createQuery(select).getSingleResult();
        logger.log(Level.FINE, "{0}.findAllTotalCount(Map<String, Object> filters)({1}): count = {2} ({3} ms)." ,new Object[]{getClass().getName(), filters.toString(), count.toString(), String.valueOf(crono.getMiliSegons())});
        return count.intValue();
    }

    @Override
    public void store(StockEstado estado) {
        try {
            entityManager.persist(estado);
        } catch (ConstraintViolationException ex) {
            throw new PersistenceException(ex);
        }
    }

    @Override
    public void remove(StockEstado estado) {
        try {
            entityManager.remove(entityManager.merge(estado));
        } catch (ConstraintViolationException ex) {
            throw new PersistenceException(ex);
        }
    }

}
