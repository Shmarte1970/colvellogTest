package es.zarca.covellog.infrastructure.persistence.jpa;

import es.zarca.covellog.application.exception.BusinessException;
import es.zarca.covellog.application.exception.UndefinedBussinesException;
import es.zarca.covellog.application.stock.exception.MovimientoNotExistException;
import es.zarca.covellog.domain.model.app.Ordre;
import es.zarca.covellog.domain.model.app.TipusOrdre;
import es.zarca.covellog.domain.model.producto.TipoProducto;
import es.zarca.covellog.domain.model.stock.movimientos.Movimiento;
import es.zarca.covellog.domain.model.stock.movimientos.MovimientoEstado;
import es.zarca.covellog.domain.model.stock.movimientos.MovimientoRepository;
import es.zarca.covellog.infrastructure.log.Capa;
import es.zarca.covellog.infrastructure.log.FunctionLog;
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
public class JpaMovimientoRepository implements MovimientoRepository, Serializable {
    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(JpaMovimientoRepository.class.getName());

    @PersistenceContext(unitName = "covellog2-pu")
    private EntityManager entityManager;

    @Override
    public Movimiento find(int id) {
        Movimiento movimiento;
        try {
            movimiento = entityManager.createNamedQuery("Movimiento.findById", Movimiento.class).setParameter("id", id).getSingleResult();
        } catch (NoResultException e) {
            movimiento = null;
        }
        return movimiento;
    }

    @Override
    public Movimiento findOrFail(int id) {
        Movimiento movimiento = find(id);
        if (movimiento == null) {
            throw new MovimientoNotExistException(id);
        }
        return movimiento;
    }
    

    private Order getOrderItem(CriteriaBuilder cb, Ordre ordre, Expression<String> path) {
        if (ordre.getTipusOrdre().equals(TipusOrdre.ASCENDENT)) {
            return cb.asc(path);
        }
        return cb.desc(path);
    }
    
    private List<Order> processOrder(CriteriaBuilder cb, Root<Movimiento> root, Ordre ordre) {
        Path<String> path;
        List<Order> orderList = new ArrayList();
        switch (ordre.getId()) {
            case "tipoProducto.id":
                Join<Movimiento, TipoProducto> tipoProducto = root.join("tipoProducto");
                path = tipoProducto.get("id");
                break;
            case "tipoProducto.descripcion":
                Join<Movimiento, TipoProducto> tipoProducto2 = root.join("tipoProducto");
                path = tipoProducto2.get("descripcion");
                break;
            default:
                orderList.add(getOrderItem(cb, ordre, root.get(ordre.getId())));
                break;
        }
        return orderList;
    }
    
    private List<Predicate> processFilters(CriteriaQuery query, CriteriaBuilder cb, Root<Movimiento> root, Map<String, Object> filters) {
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
                case "tipoProducto.id":
                    System.err.println("XXXXXXX tipoProducto.id");
                    Join<Movimiento, TipoProducto> tipoProducto = root.join("tipoProducto");
                    expr = tipoProducto.<String>get("id").as(String.class);
                    p = cb.like(cb.lower(expr), "%" + value.toString().toLowerCase() + "%");
                    expr = tipoProducto.<String>get("descripcion").as(String.class);
                    Predicate p2 = cb.like(cb.lower(expr), "%" + value.toString().toLowerCase() + "%");
                    p = cb.or(p,p2);
                    break;
                case "condicion":
                case "condicion.id":
                    //Join<Movimiento, MovimientoHistoricoCondicion> detalleCondicion = root.join("detalleCondicion");
                    //expr = detalleCondicion.<String>get("condicion").as(String.class);
                    expr = root.<String>get("condicion").as(String.class);
                    p = cb.like(cb.lower(expr), "%" + value.toString().toLowerCase() + "%");
                    break;
                case "ocultarAnulados":
                    //Join<Movimiento, MovimientoHistoricoEstado> detalleEstado = root.join("detalleEstado");
                    expr = root.<String>get("estado").as(String.class);
                    p = cb.notEqual(expr, MovimientoEstado.ANULADO);
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
    public List<Movimiento> find(int first, int pageSize, Ordre sortOrder, Map<String, Object> filters) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Movimiento> criteriaQuery = cb.createQuery(Movimiento.class);
        Root<Movimiento> root = criteriaQuery.from(Movimiento.class);
        CriteriaQuery<Movimiento> select = criteriaQuery.select(root);
        if (filters != null && filters.size() > 0) {
            List<Predicate> predicates = processFilters(criteriaQuery, cb, root, filters);
            if (predicates.size() > 0) {
                criteriaQuery.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
            }           
        }
        if (sortOrder != null) {
            criteriaQuery.orderBy(processOrder(cb, root, sortOrder));
        }
        TypedQuery<Movimiento> query = entityManager.createQuery(select);
        query.setFirstResult(first);
        query.setMaxResults(pageSize);
        List<Movimiento> list = query.getResultList();
        return list;
    }
    
    @Override
    public List<Movimiento> findAll() {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try { 
            Query query = entityManager.createNamedQuery("Movimiento.findAll", Movimiento.class);
            List <Movimiento> list = query.getResultList();
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
        Query query = entityManager.createQuery("Select count(f.id) From Movimiento f");
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
        Root<Movimiento> root = criteriaQuery.from(Movimiento.class);
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
    public void store(Movimiento movimiento) {
        try {
            entityManager.persist(movimiento);
        } catch (ConstraintViolationException ex) {
            throw new PersistenceException(ex);
        }
    }

    @Override
    public void remove(Movimiento movimiento) {
        try {
            entityManager.remove(entityManager.merge(movimiento));
        } catch (ConstraintViolationException ex) {
            throw new PersistenceException(ex);
        }
    }

}