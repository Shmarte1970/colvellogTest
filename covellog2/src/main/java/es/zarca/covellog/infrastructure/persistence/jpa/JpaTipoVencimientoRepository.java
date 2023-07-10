package es.zarca.covellog.infrastructure.persistence.jpa;

import es.zarca.covellog.domain.model.app.Ordre;
import es.zarca.covellog.domain.model.app.TipusOrdre;
import es.zarca.covellog.domain.model.empresa.formapago.TipoVencimiento;
import es.zarca.covellog.domain.model.empresa.formapago.TipoVencimientoRepository;
import es.zarca.covellog.infrastructure.persistence.exception.PersistenceException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
@ApplicationScoped
public class JpaTipoVencimientoRepository implements TipoVencimientoRepository, Serializable {
    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(JpaTipoVencimientoRepository.class.getName());

    @PersistenceContext(unitName = "covellog2-pu")
    private EntityManager entityManager;

    @Override
    public TipoVencimiento find(Integer id) {
        TipoVencimiento usuario;
        try {
            usuario = entityManager.createNamedQuery("TipoVencimiento.findById", TipoVencimiento.class).setParameter("id", id).getSingleResult();
        } catch (NoResultException e) {
            usuario = null;
        }
        return usuario;
    }

    @Override
    public void store(TipoVencimiento tipoVencimiento) {
        try {
            entityManager.persist(tipoVencimiento);
        } catch (ConstraintViolationException ex) {
            throw new PersistenceException(ex);
        }
    }
        
    @Override
    public void remove(TipoVencimiento tipoVencimiento) {
        try {
            entityManager.remove(entityManager.merge(tipoVencimiento));
        } catch (ConstraintViolationException ex) {
            throw new PersistenceException(ex);
        }
    }
    
    @Override
    public List<TipoVencimiento> findAll(){      
        Query query = entityManager.createNamedQuery("TipoVencimiento.findAll", TipoVencimiento.class);
        List <TipoVencimiento> list = query.getResultList();
        return list;
    }
    
    @Override
    public List<TipoVencimiento> findAll(int start, int size){      
        Query query = entityManager.createNamedQuery("TipoVencimiento.findAll", TipoVencimiento.class);
        query.setFirstResult(start);
        query.setMaxResults(size);
        List <TipoVencimiento> list = query.getResultList();
        return list;
    }

    @Override
    public int findAllTotalCount() {
        Query query = entityManager.createQuery("Select count(o.id) From TipoVencimiento o");
        return ((Long)query.getSingleResult()).intValue();
    }
       
    private List<Predicate> processFilters(CriteriaQuery query, CriteriaBuilder cb, Root<TipoVencimiento> root, Map<String, Object> filters) {
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
                case "grupo.nombre":
                    expr = root.get(field).as(String.class);
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

    private List<Order> processOrder(CriteriaBuilder cb, Root<TipoVencimiento> root, Ordre ordre) {
        Path<String> path;
        List<Order> orderList = new ArrayList();
        switch (ordre.getId()) {
            default:
                orderList.add(getOrderItem(cb, ordre, root.get(ordre.getId())));
                break;
        }
        return orderList;
    }
    
    @Override
    public List<TipoVencimiento> findAll( Ordre ordre, Map<String, Object> filters) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<TipoVencimiento> criteriaQuery = cb.createQuery(TipoVencimiento.class);
        Root<TipoVencimiento> root = criteriaQuery.from(TipoVencimiento.class);
        CriteriaQuery<TipoVencimiento> select = criteriaQuery.select(root);
        
        if (filters != null && filters.size() > 0) {
            List<Predicate> predicates = processFilters(criteriaQuery, cb, root, filters);
            if (predicates.size() > 0) {
                criteriaQuery.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
            }           
        }
        TypedQuery<TipoVencimiento> query = entityManager.createQuery(select);
        List<TipoVencimiento> list = query.getResultList();
        return list; 
    }

    @Override
    public List<TipoVencimiento> findAll(int start, int size, Ordre ordre, Map<String, Object> filters) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<TipoVencimiento> criteriaQuery = cb.createQuery(TipoVencimiento.class);
        Root<TipoVencimiento> root = criteriaQuery.from(TipoVencimiento.class);
        CriteriaQuery<TipoVencimiento> select = criteriaQuery.select(root);
        
        if (filters != null && filters.size() > 0) {
            List<Predicate> predicates = processFilters(criteriaQuery, cb, root, filters);
            if (predicates.size() > 0) {
                criteriaQuery.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
            }           
        }
        if (ordre != null) {
            criteriaQuery.orderBy(processOrder(cb, root, ordre));
        }
            
        TypedQuery<TipoVencimiento> query = entityManager.createQuery(select);
        query.setFirstResult(start);
        query.setMaxResults(size);
        List<TipoVencimiento> list = query.getResultList();
        
        return list;
    }

    @Override
    public int findAllTotalCount(Map<String, Object> filters) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = cb.createQuery(Long.class);
        Root<TipoVencimiento> root = criteriaQuery.from(TipoVencimiento.class);
        CriteriaQuery<Long> select = criteriaQuery.select(cb.count(root));

        if (filters != null && filters.size() > 0) {
            List<Predicate> predicates = processFilters(criteriaQuery, cb, root, filters);
            if (predicates.size() > 0) {
                criteriaQuery.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
            }
        }
        Long count = entityManager.createQuery(select).getSingleResult();
        return count.intValue();
    }

}
