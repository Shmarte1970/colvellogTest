package es.zarca.covellog.infrastructure.persistence.jpa;

import es.zarca.covellog.domain.model.adreces.pais.Pais;
import es.zarca.covellog.domain.model.app.Ordre;
import es.zarca.covellog.domain.model.app.TipusOrdre;
import es.zarca.covellog.domain.model.producto.FamiliaProducto;
import es.zarca.covellog.domain.model.producto.FamiliaProductoRepository;
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

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
@ApplicationScoped
public class JpaFamiliaProductoRepository implements FamiliaProductoRepository, Serializable {
    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(JpaFamiliaProductoRepository.class.getName());

    @PersistenceContext(unitName = "covellog2-pu")
    private EntityManager entityManager;

    @Override
    public FamiliaProducto find(Integer id) {
        FamiliaProducto familiaProducto;
        try {
            familiaProducto = entityManager.createNamedQuery("FamiliaProducto.findById", FamiliaProducto.class).setParameter("id", id).getSingleResult();
        } catch (NoResultException e) {
            familiaProducto = null;
        }
        return familiaProducto;
    }

    private Order getOrderItem(CriteriaBuilder cb, Ordre ordre, Expression<String> path) {
        if (ordre.getTipusOrdre().equals(TipusOrdre.ASCENDENT)) {
            return cb.asc(path);
        }
        return cb.desc(path);
    }
    
    private List<Order> processOrder(CriteriaBuilder cb, Root<FamiliaProducto> root, Ordre ordre) {
        Path<String> path;
        List<Order> orderList = new ArrayList();
        switch (ordre.getId()) {
            default:
                orderList.add(getOrderItem(cb, ordre, root.get(ordre.getId())));
                break;
        }
        return orderList;
    }
    
    private List<Predicate> processFilters(CriteriaQuery query, CriteriaBuilder cb, Root<FamiliaProducto> root, Map<String, Object> filters) {
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
    public List<FamiliaProducto> findAll() {
        List<FamiliaProducto> list = entityManager.createNamedQuery("FamiliaProducto.findAll", FamiliaProducto.class).getResultList();
        return list;
    }
    
    @Override
    public List<FamiliaProducto> find(int first, int pageSize, Ordre sortOrder, Map<String, Object> filters) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<FamiliaProducto> criteriaQuery = cb.createQuery(FamiliaProducto.class);
        Root<FamiliaProducto> root = criteriaQuery.from(FamiliaProducto.class);
        CriteriaQuery<FamiliaProducto> select = criteriaQuery.select(root);
        if (filters != null && filters.size() > 0) {
            List<Predicate> predicates = processFilters(criteriaQuery, cb, root, filters);
            if (predicates.size() > 0) {
                criteriaQuery.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
            }           
        }
        if (sortOrder != null) {
            criteriaQuery.orderBy(processOrder(cb, root, sortOrder));
        }
            
        TypedQuery<FamiliaProducto> query = entityManager.createQuery(select);
        query.setFirstResult(first);
        query.setMaxResults(pageSize);
        List<FamiliaProducto> list = query.getResultList();
        return list;
    }

    @Override
    public int findAllTotalCount() {
        Crono crono = new Crono();               
        Query query = entityManager.createQuery("Select count(f.id) From FamiliaProducto f");
        int count = ((Long)query.getSingleResult()).intValue();
        logger.log(Level.FINE, "{0}.findAllTotalCount(): count = {1} ({2} ms)." ,new Object[]{getClass().getName(), String.valueOf(count), String.valueOf(crono.getMiliSegons())});
        return count;
    }
    
    
    @Override
    public int findTotalCount(Map<String, Object> filters) {
        if (filters == null) {
            return findAllTotalCount();
        }
        Crono crono = new Crono();
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = cb.createQuery(Long.class);
        Root<FamiliaProducto> root = criteriaQuery.from(FamiliaProducto.class);
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
    public void store(FamiliaProducto familiaProducto) {
        try {
            entityManager.persist(familiaProducto);
        } catch (ConstraintViolationException ex) {
            throw new PersistenceException(ex);
        }
    }

    @Override
    public void remove(FamiliaProducto familiaProducto) {
        try {
            entityManager.remove(entityManager.merge(familiaProducto));
        } catch (ConstraintViolationException ex) {
            throw new PersistenceException(ex);
        }
    }
    
    /*

    
    @Override
    public FamiliaProducto findByUsername(String username) {
        FamiliaProducto familiaProducto;
        try {
            familiaProducto = entityManager.createNamedQuery("FamiliaProducto.findByUsername", FamiliaProducto.class).setParameter("username", username).getSingleResult();
        } catch (NoResultException e) {
            familiaProducto = null;
        }
        return familiaProducto;
    }
    @Override
    public List<FamiliaProducto> findAll(){      
        Query query = entityManager.createNamedQuery("FamiliaProducto.findAll", FamiliaProducto.class);
        List <FamiliaProducto> list = query.getResultList();
        return list;
    }
    
    @Override
    public List<FamiliaProducto> findAll(int start, int size){      
        Query query = entityManager.createNamedQuery("FamiliaProducto.findAll", FamiliaProducto.class);
        query.setFirstResult(start);
        query.setMaxResults(size);
        List <FamiliaProducto> list = query.getResultList();
        return list;
    }

    @Override
    public int findAllTotalCount() {
        Query query = entityManager.createQuery("Select count(o.id) From FamiliaProducto o");
        return ((Long)query.getSingleResult()).intValue();
    }
       
    private List<Predicate> processFilters(CriteriaQuery query, CriteriaBuilder cb, Root<FamiliaProducto> root, Map<String, Object> filters) {
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

    private List<Order> processOrder(CriteriaBuilder cb, Root<FamiliaProducto> root, Ordre ordre) {
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
    public List<FamiliaProducto> findAll( Ordre ordre, Map<String, Object> filters) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<FamiliaProducto> criteriaQuery = cb.createQuery(FamiliaProducto.class);
        Root<FamiliaProducto> root = criteriaQuery.from(FamiliaProducto.class);
        CriteriaQuery<FamiliaProducto> select = criteriaQuery.select(root);
        
        if (filters != null && filters.size() > 0) {
            List<Predicate> predicates = processFilters(criteriaQuery, cb, root, filters);
            if (predicates.size() > 0) {
                criteriaQuery.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
            }           
        }
        TypedQuery<FamiliaProducto> query = entityManager.createQuery(select);
        List<FamiliaProducto> list = query.getResultList();
        return list; 
    }

    @Override
    public List<FamiliaProducto> findAll(int start, int size, Ordre ordre, Map<String, Object> filters) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<FamiliaProducto> criteriaQuery = cb.createQuery(FamiliaProducto.class);
        Root<FamiliaProducto> root = criteriaQuery.from(FamiliaProducto.class);
        CriteriaQuery<FamiliaProducto> select = criteriaQuery.select(root);
        
        if (filters != null && filters.size() > 0) {
            List<Predicate> predicates = processFilters(criteriaQuery, cb, root, filters);
            if (predicates.size() > 0) {
                criteriaQuery.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
            }           
        }
        if (ordre != null) {
            criteriaQuery.orderBy(processOrder(cb, root, ordre));
        }
            
        TypedQuery<FamiliaProducto> query = entityManager.createQuery(select);
        query.setFirstResult(start);
        query.setMaxResults(size);
        List<FamiliaProducto> list = query.getResultList();
        
        return list;
    }

    @Override
    public int findAllTotalCount(Map<String, Object> filters) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = cb.createQuery(Long.class);
        Root<FamiliaProducto> root = criteriaQuery.from(FamiliaProducto.class);
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

    */

}
