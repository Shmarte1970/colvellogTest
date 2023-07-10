package es.zarca.covellog.infrastructure.persistence.jpa;

import es.zarca.covellog.application.exception.UndefinedBussinesException;
import es.zarca.covellog.application.productos.familias.exception.TipoProductoNotExistException;
import es.zarca.covellog.domain.model.app.Ordre;
import es.zarca.covellog.domain.model.app.TipusOrdre;
import es.zarca.covellog.domain.model.producto.FamiliaProducto;
import es.zarca.covellog.domain.model.producto.TipoProducto;
import es.zarca.covellog.domain.model.producto.TipoProductoClase;
import es.zarca.covellog.domain.model.producto.TipoProductoRepository;
import es.zarca.covellog.domain.model.producto.UnidadMedida;
import es.zarca.covellog.infrastructure.log.Capa;
import es.zarca.covellog.infrastructure.log.FunctionLog;
import es.zarca.covellog.infrastructure.persistence.exception.PersistenceException;
import es.zarca.covellog.infrastructure.util.crono.Crono;
import es.zarca.covellog.interfaces.web.app.model.Filtro;
import es.zarca.covellog.interfaces.web.app.model.Operacion;
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
public class JpaTipoProductoRepository implements TipoProductoRepository, Serializable {
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(JpaTipoProductoRepository.class.getName());

    @PersistenceContext(unitName = "covellog2-pu")
    private EntityManager entityManager;

    @Override
    public TipoProducto find(String id) {
        TipoProducto tipoProducto;
        try {
            tipoProducto = entityManager.createNamedQuery("TipoProducto.findById", TipoProducto.class).setParameter("id", id).getSingleResult();
        } catch (NoResultException e) {
            tipoProducto = null;
        }
        return tipoProducto;
    }

    @Override
    public TipoProducto findOrFail(String id) {
        TipoProducto tipoProducto = find(id);
        if (tipoProducto == null) {
            throw new TipoProductoNotExistException(id);
        }
        return tipoProducto;
    }
    
    private Order getOrderItem(CriteriaBuilder cb, Ordre ordre, Expression<String> path) {
        if (ordre.getTipusOrdre().equals(TipusOrdre.ASCENDENT)) {
            return cb.asc(path);
        }
        return cb.desc(path);
    }
    
    private List<Order> processOrder(CriteriaBuilder cb, Root<TipoProducto> root, Ordre ordre) {
        Path<String> path;
        List<Order> orderList = new ArrayList();
        switch (ordre.getId()) {
            /*case "unidadMedida.nombre":
                Join<UnidadMedida, FamiliaProducto> unidadMedida = root.join("unidadMedida");
                orderList.add(getOrderItem(cb, ordre, unidadMedida.<String>get("nombre").as(String.class)));
                break;*/
            case "familia.nombre":
                Join<TipoProducto, FamiliaProducto> familia = root.join("familia");
                orderList.add(getOrderItem(cb, ordre, familia.<String>get("nombre").as(String.class)));
                break;
            case "clase.nombre":
                Join<TipoProducto, TipoProductoClase> clase = root.join("clase");
                orderList.add(getOrderItem(cb, ordre, clase.<String>get("nombre").as(String.class)));
                break;
            default:
                orderList.add(getOrderItem(cb, ordre, root.get(ordre.getId())));
                break;
        }
        return orderList;
    }
    
    private List<Predicate> processFilters(CriteriaQuery query, CriteriaBuilder cb, Root<TipoProducto> root, Map<String, Object> filters) {
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
                case "familia.nombre":
                case "familia:CONTAINS":
                    Join<TipoProducto, FamiliaProducto> familia = root.join("familia");
                    expr = familia.<String>get("nombre").as(String.class);
                    p = cb.like(cb.lower(expr), "%" + value.toString().toLowerCase() + "%");
                    break;
                case "clase.nombre":
                case "clase:CONTAINS":
                    Join<TipoProducto, TipoProductoClase> clase = root.join("clase");
                    expr = clase.<String>get("nombre").as(String.class);
                    p = cb.like(cb.lower(expr), "%" + value.toString().toLowerCase() + "%");
                    break;
                case "clase:EQUALS":
                    clase = root.join("clase");
                    expr = clase.<String>get("id").as(String.class);
                    p = cb.equal(cb.lower(expr), value.toString().toLowerCase());
                    break;
                case "unidadMedida":
                case "unidadMedida:CONTAINS":
                    Join<TipoProducto, UnidadMedida> unidadMedida = root.join("unidadMedida");
                    expr = unidadMedida.<String>get("nombre").as(String.class);
                    p = cb.like(cb.lower(expr), "%" + value.toString().toLowerCase() + "%");
                    break;
                case "id":
                case "id:CONTAINS":
                    expr = root.get("id").as(String.class);
                    p = cb.like(expr, "%" + value.toString() + "%");
                    break;
                case "descripcion":
                case "descripcion:CONTAINS":
                    expr = root.get("descripcion").as(String.class);
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
    
    @Override
    public List<TipoProducto> find(int first, int pageSize, Ordre sortOrder, Map<String, Object> filters) {
        FunctionLog log = new FunctionLog(Capa.REPOSITORY);
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<TipoProducto> criteriaQuery = cb.createQuery(TipoProducto.class);
            Root<TipoProducto> root = criteriaQuery.from(TipoProducto.class);
            CriteriaQuery<TipoProducto> select = criteriaQuery.select(root);

            if (filters != null && filters.size() > 0) {
                List<Predicate> predicates = processFilters(criteriaQuery, cb, root, filters);
                if (predicates.size() > 0) {
                    criteriaQuery.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
                }           
            }
            if (sortOrder != null) {
                criteriaQuery.orderBy(processOrder(cb, root, sortOrder));
            }

            TypedQuery<TipoProducto> query = entityManager.createQuery(select);
            query.setFirstResult(first);
            query.setMaxResults(pageSize);
            List<TipoProducto> list = query.getResultList();
            return list;
        } catch (Exception ex) {
            throw new UndefinedBussinesException(ex);
        } finally {
            log.finish();
        }
    }
    
    @Override
    public List<TipoProducto> findAll(){      
        return entityManager.createNamedQuery("TipoProducto.findAll", TipoProducto.class).getResultList();
    }

    @Override
    public int findTotalCount() {
        Crono crono = new Crono();               
        Query query = entityManager.createQuery("Select count(f.id) From TipoProducto f");
        int count = ((Long)query.getSingleResult()).intValue();
        LOGGER.log(Level.FINE, "{0}.findAllTotalCount(): count = {1} ({2} ms)." ,new Object[]{getClass().getName(), String.valueOf(count), String.valueOf(crono.getMiliSegons())});
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
        Root<TipoProducto> root = criteriaQuery.from(TipoProducto.class);
        CriteriaQuery<Long> select = criteriaQuery.select(cb.count(root));

        if (filters.size() > 0) {
            List<Predicate> predicates = processFilters(criteriaQuery, cb, root, filters);
            if (predicates.size() > 0) {
                criteriaQuery.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
            }           
        }
        
        Long count = entityManager.createQuery(select).getSingleResult();
        LOGGER.log(Level.FINE, "{0}.findAllTotalCount(Map<String, Object> filters)({1}): count = {2} ({3} ms)." ,new Object[]{getClass().getName(), filters.toString(), count.toString(), String.valueOf(crono.getMiliSegons())});
        return count.intValue();
    }

    @Override
    public void store(TipoProducto tipoProducto) {
        try {
            entityManager.persist(tipoProducto);
        } catch (ConstraintViolationException ex) {
            throw new PersistenceException(ex);
        }
    }

    @Override
    public void remove(TipoProducto tipoProducto) {
        try {
            entityManager.remove(entityManager.merge(tipoProducto));
        } catch (ConstraintViolationException ex) {
            throw new PersistenceException(ex);
        }
    }
    
    @Override
    public List<Filtro> getFiltrosPosibles() {
        FunctionLog log = new FunctionLog(Capa.REPOSITORY);
        List<Filtro> lista = new ArrayList<>();
        lista.add(new Filtro("id", Operacion.CONTAINS));
        lista.add(new Filtro("descripcion", Operacion.CONTAINS));
        lista.add(new Filtro("familia", Operacion.CONTAINS));
        lista.add(new Filtro("clase", Operacion.CONTAINS));
        lista.add(new Filtro("unidadMedida", Operacion.CONTAINS));                
        lista.forEach(filtro -> {
            LOGGER.log(Level.INFO, "Filtro: {0}", filtro);
        });
        log.finish();
        return lista;
    }
    /*

    
    @Override
    public TipoProducto findByUsername(String username) {
        TipoProducto tipoProducto;
        try {
            tipoProducto = entityManager.createNamedQuery("TipoProducto.findByUsername", TipoProducto.class).setParameter("username", username).getSingleResult();
        } catch (NoResultException e) {
            tipoProducto = null;
        }
        return tipoProducto;
    }
    @Override
    public List<TipoProducto> findAll(){      
        Query query = entityManager.createNamedQuery("TipoProducto.findAll", TipoProducto.class);
        List <TipoProducto> list = query.getResultList();
        return list;
    }
    
    @Override
    public List<TipoProducto> findAll(int start, int size){      
        Query query = entityManager.createNamedQuery("TipoProducto.findAll", TipoProducto.class);
        query.setFirstResult(start);
        query.setMaxResults(size);
        List <TipoProducto> list = query.getResultList();
        return list;
    }

    @Override
    public int findAllTotalCount() {
        Query query = entityManager.createQuery("Select count(o.id) From TipoProducto o");
        return ((Long)query.getSingleResult()).intValue();
    }
       
    private List<Predicate> processFilters(CriteriaQuery query, CriteriaBuilder cb, Root<TipoProducto> root, Map<String, Object> filters) {
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

    private List<Order> processOrder(CriteriaBuilder cb, Root<TipoProducto> root, Ordre ordre) {
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
    public List<TipoProducto> findAll( Ordre ordre, Map<String, Object> filters) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<TipoProducto> criteriaQuery = cb.createQuery(TipoProducto.class);
        Root<TipoProducto> root = criteriaQuery.from(TipoProducto.class);
        CriteriaQuery<TipoProducto> select = criteriaQuery.select(root);
        
        if (filters != null && filters.size() > 0) {
            List<Predicate> predicates = processFilters(criteriaQuery, cb, root, filters);
            if (predicates.size() > 0) {
                criteriaQuery.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
            }           
        }
        TypedQuery<TipoProducto> query = entityManager.createQuery(select);
        List<TipoProducto> list = query.getResultList();
        return list; 
    }

    @Override
    public List<TipoProducto> findAll(int start, int size, Ordre ordre, Map<String, Object> filters) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<TipoProducto> criteriaQuery = cb.createQuery(TipoProducto.class);
        Root<TipoProducto> root = criteriaQuery.from(TipoProducto.class);
        CriteriaQuery<TipoProducto> select = criteriaQuery.select(root);
        
        if (filters != null && filters.size() > 0) {
            List<Predicate> predicates = processFilters(criteriaQuery, cb, root, filters);
            if (predicates.size() > 0) {
                criteriaQuery.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
            }           
        }
        if (ordre != null) {
            criteriaQuery.orderBy(processOrder(cb, root, ordre));
        }
            
        TypedQuery<TipoProducto> query = entityManager.createQuery(select);
        query.setFirstResult(start);
        query.setMaxResults(size);
        List<TipoProducto> list = query.getResultList();
        
        return list;
    }

    @Override
    public int findAllTotalCount(Map<String, Object> filters) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = cb.createQuery(Long.class);
        Root<TipoProducto> root = criteriaQuery.from(TipoProducto.class);
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
