package es.zarca.covellog.infrastructure.persistence.jpa;

import es.zarca.covellog.application.exception.BusinessException;
import es.zarca.covellog.application.exception.UndefinedBussinesException;
import es.zarca.covellog.application.stock.exception.StockNotExistException;
import es.zarca.covellog.domain.model.app.Ordre;
import es.zarca.covellog.domain.model.app.TipusOrdre;
import es.zarca.covellog.domain.model.producto.TipoProducto;
import es.zarca.covellog.domain.model.stock.StockEstado;
import es.zarca.covellog.domain.model.stock.Stock;
import es.zarca.covellog.domain.model.stock.StockRepository;
import es.zarca.covellog.infrastructure.log.Capa;
import es.zarca.covellog.infrastructure.log.FunctionLog;
import es.zarca.covellog.infrastructure.persistence.exception.PersistenceException;
import es.zarca.covellog.infrastructure.util.crono.Crono;
import es.zarca.covellog.interfaces.facade.stock.dto.IdCantidadDto;
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

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
@ApplicationScoped
public class JpaStockRepository implements StockRepository, Serializable {
    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(JpaStockRepository.class.getName());

    @PersistenceContext(unitName = "covellog2-pu")
    private EntityManager entityManager;

    @Override
    public Stock find(int id) {
        Stock stock;
        try {
            stock = entityManager.createNamedQuery("Stock.findById", Stock.class).setParameter("id", id).getSingleResult();
        } catch (NoResultException e) {
            stock = null;
        }
        return stock;
    }

    @Override
    public Stock findOrFail(int id) {
        Stock stock = find(id);
        if (stock == null) {
            throw new StockNotExistException(id);
        }
        return stock;
    }
    
    @Override
    public Stock findOrFail(String numSerie) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private Order getOrderItem(CriteriaBuilder cb, Ordre ordre, Expression<String> path) {
        if (ordre.getTipusOrdre().equals(TipusOrdre.ASCENDENT)) {
            return cb.asc(path);
        }
        return cb.desc(path);
    }
    
    private List<Order> processOrder(CriteriaBuilder cb, Root<Stock> root, Ordre ordre) {
        Path<String> path;
        List<Order> orderList = new ArrayList();
        switch (ordre.getId()) {
            case "tipoProducto.id":
                Join<Stock, TipoProducto> tipoProducto = root.join("tipoProducto");
                path = tipoProducto.get("id");
                break;
            case "tipoProducto.descripcion":
                Join<Stock, TipoProducto> tipoProducto2 = root.join("tipoProducto");
                path = tipoProducto2.get("descripcion");
                break;
            default:
                orderList.add(getOrderItem(cb, ordre, root.get(ordre.getId())));
                break;
        }
        return orderList;
    }
    
    private List<Predicate> processFilters(CriteriaQuery query, CriteriaBuilder cb, Root<Stock> root, Map<String, Object> filters) {
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
/*Tipo Producto
Familia	Ubicacion
Matricula
Lote
Flota
Propietario
Estado
Condicion*/ 
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
                case "estado.nombre":
                    expr = root.get("estado").get("nombre").as(String.class);
                    p = cb.like(cb.lower(expr), "%" + value.toString().toLowerCase() + "%");
                    break;
                case "condicion":
                case "condicion.id":
                    //Join<Stock, StockHistoricoCondicion> detalleCondicion = root.join("detalleCondicion");
                    //expr = detalleCondicion.<String>get("condicion").as(String.class);
                    expr = root.<String>get("condicion").as(String.class);
                    p = cb.like(cb.lower(expr), "%" + value.toString().toLowerCase() + "%");
                    break;
                case "ocultarBajas":
                    //Join<Stock, StockHistoricoEstado> detalleEstado = root.join("detalleEstado");
                    expr = root.<String>get("estado").as(String.class);
                    p = cb.notEqual(expr, StockEstado.BAJA);
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
    public List<Stock> find(int first, int pageSize, Ordre sortOrder, Map<String, Object> filters) {
        for (Map.Entry<String, Object> entry : filters.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            System.err.println("COJONESJPA: " + key + " => " + String.valueOf(value));
            
        }
        
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Stock> criteriaQuery = cb.createQuery(Stock.class);
        Root<Stock> root = criteriaQuery.from(Stock.class);
        CriteriaQuery<Stock> select = criteriaQuery.select(root);
        if (filters != null && filters.size() > 0) {
            List<Predicate> predicates = processFilters(criteriaQuery, cb, root, filters);
            if (predicates.size() > 0) {
                criteriaQuery.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
            }           
        }
        if (sortOrder != null) {
            criteriaQuery.orderBy(processOrder(cb, root, sortOrder));
        }
        TypedQuery<Stock> query = entityManager.createQuery(select);
        query.setFirstResult(first);
        query.setMaxResults(pageSize);
        List<Stock> list = query.getResultList();
        return list;
    }
    
    @Override
    public List<Stock> findAll() {
        FunctionLog log = new FunctionLog(Capa.REPOSITORY);
        try { 
            Query query = entityManager.createNamedQuery("Stock.findAll", Stock.class);
            List <Stock> list = query.getResultList();
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
        Query query = entityManager.createQuery("Select count(f.id) From Stock f");
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
        Root<Stock> root = criteriaQuery.from(Stock.class);
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
    public void store(Stock stock) {
        try {
            entityManager.persist(stock);
        } catch (ConstraintViolationException ex) {
            throw new PersistenceException(ex);
        }
    }

    @Override
    public void remove(Stock stock) {
        try {
            entityManager.remove(entityManager.merge(stock));
        } catch (ConstraintViolationException ex) {
            throw new PersistenceException(ex);
        }
    }

    @Override
    public List<IdCantidadDto> findLotes(String filtro) {
        /*Crono crono = new Crono();               
        Query query = entityManager.createQuery(
            "Select s.lote as id, count(s.lote) as cantidad From Stock s WHERE s.lote like ?1 GROUP BY s.lote", IdCantidadDto.class);
        query.setParameter(1, filtro + "%");
        List<Object[]> resultList = query.getResultList();
        logger.log(Level.FINE, "{0}.findLotes(Map<String, Object> filters)({1}): count = {2} ({3} ms)." ,new Object[]{getClass().getName(), filtro, resultList.size(), String.valueOf(crono.getMiliSegons())});
        List<IdCantidadDto> result = new ArrayList();
        for (Object[] resultItem : resultList) {
            result.add(new IdCantidadDto(resultItem[0].toString(), (Long)resultItem[1]));
        }
        return result;*/
        return new ArrayList();
    }

}