package es.zarca.covellog.infrastructure.persistence.jpa;

import es.zarca.covellog.application.exception.BusinessException;
import es.zarca.covellog.application.exception.UndefinedBussinesException;
import es.zarca.covellog.domain.model.app.Ordre;
import es.zarca.covellog.domain.model.app.TipusOrdre;
import es.zarca.covellog.domain.model.stock.StockEstado;
import es.zarca.covellog.domain.model.stock.StockPorAlmacen;
import es.zarca.covellog.domain.model.stock.StockPorAlmacenRepository;
import es.zarca.covellog.domain.model.stock.exception.StockPorAlmacenNotExistException;
import es.zarca.covellog.infrastructure.log.Capa;
import es.zarca.covellog.infrastructure.log.FunctionLog;
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

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
@ApplicationScoped
public class JpaStockPorAlmacenRepository implements StockPorAlmacenRepository, Serializable {
    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(JpaStockPorAlmacenRepository.class.getName());

    @PersistenceContext(unitName = "covellog2-pu")
    private EntityManager entityManager;

    @Override
    public StockPorAlmacen find(String id) {
        StockPorAlmacen stockPorLote;
        try {
            stockPorLote = entityManager.createNamedQuery("StockPorAlmacen.findById", StockPorAlmacen.class).setParameter("id", id).getSingleResult();
        } catch (NoResultException e) {
            stockPorLote = null;
        }
        return stockPorLote;
    }

    @Override
    public StockPorAlmacen findOrFail(String id) {
        StockPorAlmacen stockPorLote = find(id);
        if (stockPorLote == null) {
            throw new StockPorAlmacenNotExistException(id);
        }
        return stockPorLote;
    }

    private Order getOrderItem(CriteriaBuilder cb, Ordre ordre, Expression<String> path) {
        if (ordre.getTipusOrdre().equals(TipusOrdre.ASCENDENT)) {
            return cb.asc(path);
        }
        return cb.desc(path);
    }
    
    private List<Order> processOrder(CriteriaBuilder cb, Root<StockPorAlmacen> root, Ordre ordre) {
        Path<String> path;
        List<Order> orderList = new ArrayList();
        switch (ordre.getId()) {
            default:
                orderList.add(getOrderItem(cb, ordre, root.get(ordre.getId())));
                break;
        }
        return orderList;
    }
    
    private List<Predicate> processFilters(CriteriaQuery query, CriteriaBuilder cb, Root<StockPorAlmacen> root, Map<String, Object> filters) {
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
                case "ocultarBajas":
                    System.err.println("ocultarBajas por donde toca");
                    expr = root.get("estado").as(String.class);
                    p = cb.notEqual(expr, StockEstado.BAJA);
                    break;
                    case "tipoProducto.familia.nombre":
                    expr = root.get("tipoProducto").get("familia").get("nombre").as(String.class);
                    p = cb.like(cb.lower(expr), "%" + value.toString().toLowerCase() + "%");
                    break;
                case "tipoProducto":
                    expr = root.get("tipoProducto").get("id").as(String.class);
                    Expression<String> expr2 = root.get("tipoProducto").get("descripcion").as(String.class);
                    p = cb.or(
                        cb.like(cb.lower(expr), "%" + value.toString().toLowerCase() + "%"),
                        cb.like(cb.lower(expr2), "%" + value.toString().toLowerCase() + "%")
                    );
                    break;
                case "tipoProducto.id":
                    expr = root.get("tipoProducto").get("id").as(String.class);
                    p = cb.like(cb.lower(expr), "%" + value.toString().toLowerCase() + "%");
                    break;
                case "tipoProducto.descripcion":
                    expr = root.get("tipoProducto").get("descripcion").as(String.class);
                    p = cb.like(cb.lower(expr), "%" + value.toString().toLowerCase() + "%");
                    break;
                default:
                    System.err.println("defaut: " + field);
                    expr = root.get(field).as(String.class);
                    p = cb.like(cb.lower(expr), "%" + value.toString().toLowerCase() + "%");
                    break;
            }
            predicates.add(p); 
        }            
        return predicates;
    }
    
    @Override
    public List<StockPorAlmacen> find(int first, int pageSize, Ordre sortOrder, Map<String, Object> filters) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<StockPorAlmacen> criteriaQuery = cb.createQuery(StockPorAlmacen.class);
        Root<StockPorAlmacen> root = criteriaQuery.from(StockPorAlmacen.class);
        CriteriaQuery<StockPorAlmacen> select = criteriaQuery.select(root);
        if (filters != null && filters.size() > 0) {
            List<Predicate> predicates = processFilters(criteriaQuery, cb, root, filters);
            if (predicates.size() > 0) {
                criteriaQuery.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
            }           
        }
        if (sortOrder != null) {
            criteriaQuery.orderBy(processOrder(cb, root, sortOrder));
        }
            
        TypedQuery<StockPorAlmacen> query = entityManager.createQuery(select);
        query.setFirstResult(first);
        query.setMaxResults(pageSize);
        List<StockPorAlmacen> list = query.getResultList();
        return list;
    }
    
    @Override
    public List<StockPorAlmacen> findAll() {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try { 
            Query query = entityManager.createNamedQuery("StockPorAlmacen.findAll", StockPorAlmacen.class);
            List <StockPorAlmacen> list = query.getResultList();
            return list;
        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new UndefinedBussinesException(ex);
        } finally {
            log.finish();
        }
    }

    @Override
    public int findTotalCount() {
        Crono crono = new Crono();               
        Query query = entityManager.createQuery("Select count(f.id) From StockPorAlmacen f");
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
        Root<StockPorAlmacen> root = criteriaQuery.from(StockPorAlmacen.class);
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

}