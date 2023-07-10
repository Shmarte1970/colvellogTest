package es.zarca.covellog.infrastructure.persistence.jpa;

import es.zarca.covellog.application.exception.UndefinedBussinesException;
import es.zarca.covellog.domain.model.adreces.poblacio.Poblacio;
import es.zarca.covellog.domain.model.albaran.Albaran;
import es.zarca.covellog.domain.model.albaran.AlbaranEstado;
import es.zarca.covellog.domain.model.albaran.AlbaranLinea;
import es.zarca.covellog.domain.model.albaran.AlbaranRepository;
import es.zarca.covellog.domain.model.albaran.exception.AlbaranNotExistException;
import es.zarca.covellog.domain.model.app.Ordre;
import es.zarca.covellog.domain.model.app.TipusOrdre;
import es.zarca.covellog.domain.model.empresa.Empresa;
import es.zarca.covellog.domain.model.stock.reservas.Reserva;
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
import javax.persistence.criteria.Subquery;
import javax.validation.ConstraintViolationException;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
@ApplicationScoped
public class JpaAlbaranRepository implements AlbaranRepository, Serializable {
    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = Logger.getLogger(JpaAlbaranRepository.class.getName());

    @PersistenceContext(unitName = "covellog2-pu")
    private EntityManager entityManager;

    @Override
    public Albaran find(Integer id) {
        Albaran almacen;
        try {
            almacen = entityManager.createNamedQuery("Albaran.findById", Albaran.class).setParameter("id", id).getSingleResult();
        } catch (NoResultException e) {
            almacen = null;
        }
        return almacen;
    }

    @Override
    public Albaran findOrFail(Integer id) {
        Albaran almacen = find(id);
        if (almacen == null) {
            throw new AlbaranNotExistException(id);
        }
        return almacen;
    }
    
    private Order getOrderItem(CriteriaBuilder cb, Ordre ordre, Expression<String> path) {
        if (ordre.getTipusOrdre().equals(TipusOrdre.ASCENDENT)) {
            return cb.asc(path);
        }
        return cb.desc(path);
    }
    
    private List<Order> processOrder(CriteriaBuilder cb, Root<Albaran> root, Ordre ordre) {
        Path<String> path;
        List<Order> orderList = new ArrayList();
        switch (ordre.getId()) {
            case "empresa.nombre":
                Join<Albaran, Empresa> empresa = root.join("empresa");
                orderList.add(getOrderItem(cb, ordre, empresa.<String>get("nombre").as(String.class)));
                break;
            case "direccion.poblacion.nombre":
            case "direccion.poblacion.nom":
                Join<Albaran, Poblacio> poblacio = root.join("direccion").join("poblacion");
                orderList.add(getOrderItem(cb, ordre, poblacio.<String>get("nom").as(String.class)));
                break;
            default:
                orderList.add(getOrderItem(cb, ordre, root.get(ordre.getId())));
                break;
        }
        return orderList;
    }
    
    private List<Predicate> processFilters(CriteriaQuery query, CriteriaBuilder cb, Root<Albaran> root, Map<String, Object> filters) {
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
                case "id":
                case "id:CONTAINS":
                    expr = root.get("id").as(String.class);
                    p = cb.like(expr, "%" + value.toString() + "%");
                    break;
                case "fechaBajaIsNotNull":
                    expr = root.get("fechaBaja").as(String.class);
                    p = cb.isNotNull(cb.lower(expr));
                    break;
                case "fechaBajaIsNull":
                    expr = root.get("fechaBaja").as(String.class);
                    p = cb.isNull(cb.lower(expr));
                    break;
                case "empresa.nombre":
                case "empresa:CONTAINS":
                    Join<Albaran, Empresa> empresa = root.join("empresa");
                    expr = empresa.<String>get("nombre").as(String.class);
                    p = cb.like(cb.lower(expr), "%" + value.toString().toLowerCase() + "%");
                    break;
                case "direccion.poblacion.nombre":
                case "direccion.poblacion.nom":
                case "poblacion:CONTAINS":
                    Join<Albaran, Poblacio> poblacio = root.join("direccion").join("poblacion");
                    expr = poblacio.<String>get("nom").as(String.class);
                    p = cb.like(cb.lower(expr), "%" + value.toString().toLowerCase() + "%");
                    break;
                case "activo":
                    expr = root.<String>get("estado").as(String.class);
                    p = cb.equal(expr, AlbaranEstado.ACTIVO);
                    break;
                case "sinReserva":
                    Subquery<AlbaranLinea> subQuery = query.subquery(AlbaranLinea.class);
                    Root<AlbaranLinea> linea = subQuery.from(AlbaranLinea.class);
                    subQuery.where(cb.isNull(linea.get("reserva")));
                    p = cb.exists(subQuery);
                    break;
                case "numSerie":
                    Subquery<AlbaranLinea> subQuery2 = query.subquery(AlbaranLinea.class);
                    Root<AlbaranLinea> linea2 = subQuery2.from(AlbaranLinea.class);
                    subQuery2.where(cb.like(cb.lower(linea2.get("numSerie")), "%" + value.toString().toLowerCase() + "%"));
                    p = cb.exists(subQuery2);
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
    public List<Albaran> find(int first, int pageSize, Ordre sortOrder, Map<String, Object> filters) {
        FunctionLog log = new FunctionLog(Capa.REPOSITORY);
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Albaran> criteriaQuery = cb.createQuery(Albaran.class);
            Root<Albaran> root = criteriaQuery.from(Albaran.class);
            CriteriaQuery<Albaran> select = criteriaQuery.select(root);

            if (filters != null && filters.size() > 0) {
                List<Predicate> predicates = processFilters(criteriaQuery, cb, root, filters);
                if (predicates.size() > 0) {
                    criteriaQuery.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
                }           
            }
            if (sortOrder != null) {
                criteriaQuery.orderBy(processOrder(cb, root, sortOrder));
            }

            TypedQuery<Albaran> query = entityManager.createQuery(select);
            query.setFirstResult(first);
            query.setMaxResults(pageSize);
            List<Albaran> list = query.getResultList();
            return list;
        } catch (Exception ex) {
            throw new UndefinedBussinesException(ex);
        } finally {
            log.finish();
        }
    }
    
    @Override
    public List<Albaran> findAll(){      
        return entityManager.createNamedQuery("Albaran.findAll", Albaran.class).getResultList();
    }

    @Override
    public int findTotalCount() {
        Crono crono = new Crono();               
        Query query = entityManager.createQuery("Select count(f.id) From Albaran f");
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
        Root<Albaran> root = criteriaQuery.from(Albaran.class);
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
    public List<Filtro> getFiltrosPosibles() {
        FunctionLog log = new FunctionLog(Capa.REPOSITORY);
        List<Filtro> lista = new ArrayList<>();
        lista.add(new Filtro("id", Operacion.CONTAINS));
        lista.add(new Filtro("nombre", Operacion.CONTAINS));
        lista.add(new Filtro("empresa", Operacion.CONTAINS));
        lista.add(new Filtro("poblacion", Operacion.CONTAINS));
        lista.forEach(filtro -> {
            LOGGER.log(Level.INFO, "Filtro: {0}", filtro);
        });
        log.finish();
        return lista;
    }
    
    @Override
    public void store(Albaran almacen) {
        try {
            entityManager.persist(almacen);
        } catch (ConstraintViolationException ex) {
            throw new PersistenceException(ex);
        }
    }

    @Override
    public void remove(Albaran almacen) {
        try {
            entityManager.remove(entityManager.merge(almacen));
        } catch (ConstraintViolationException ex) {
            throw new PersistenceException(ex);
        }
    }
    
    /*

    
    @Override
    public Albaran findByUsername(String username) {
        Albaran almacen;
        try {
            almacen = entityManager.createNamedQuery("Albaran.findByUsername", Albaran.class).setParameter("username", username).getSingleResult();
        } catch (NoResultException e) {
            almacen = null;
        }
        return almacen;
    }
    @Override
    public List<Albaran> findAll(){      
        Query query = entityManager.createNamedQuery("Albaran.findAll", Albaran.class);
        List <Albaran> list = query.getResultList();
        return list;
    }
    
    @Override
    public List<Albaran> findAll(int start, int size){      
        Query query = entityManager.createNamedQuery("Albaran.findAll", Albaran.class);
        query.setFirstResult(start);
        query.setMaxResults(size);
        List <Albaran> list = query.getResultList();
        return list;
    }

    @Override
    public int findAllTotalCount() {
        Query query = entityManager.createQuery("Select count(o.id) From Albaran o");
        return ((Long)query.getSingleResult()).intValue();
    }
       
    private List<Predicate> processFilters(CriteriaQuery query, CriteriaBuilder cb, Root<Albaran> root, Map<String, Object> filters) {
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

    private List<Order> processOrder(CriteriaBuilder cb, Root<Albaran> root, Ordre ordre) {
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
    public List<Albaran> findAll( Ordre ordre, Map<String, Object> filters) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Albaran> criteriaQuery = cb.createQuery(Albaran.class);
        Root<Albaran> root = criteriaQuery.from(Albaran.class);
        CriteriaQuery<Albaran> select = criteriaQuery.select(root);
        
        if (filters != null && filters.size() > 0) {
            List<Predicate> predicates = processFilters(criteriaQuery, cb, root, filters);
            if (predicates.size() > 0) {
                criteriaQuery.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
            }           
        }
        TypedQuery<Albaran> query = entityManager.createQuery(select);
        List<Albaran> list = query.getResultList();
        return list; 
    }

    @Override
    public List<Albaran> findAll(int start, int size, Ordre ordre, Map<String, Object> filters) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Albaran> criteriaQuery = cb.createQuery(Albaran.class);
        Root<Albaran> root = criteriaQuery.from(Albaran.class);
        CriteriaQuery<Albaran> select = criteriaQuery.select(root);
        
        if (filters != null && filters.size() > 0) {
            List<Predicate> predicates = processFilters(criteriaQuery, cb, root, filters);
            if (predicates.size() > 0) {
                criteriaQuery.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
            }           
        }
        if (ordre != null) {
            criteriaQuery.orderBy(processOrder(cb, root, ordre));
        }
            
        TypedQuery<Albaran> query = entityManager.createQuery(select);
        query.setFirstResult(start);
        query.setMaxResults(size);
        List<Albaran> list = query.getResultList();
        
        return list;
    }

    @Override
    public int findAllTotalCount(Map<String, Object> filters) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = cb.createQuery(Long.class);
        Root<Albaran> root = criteriaQuery.from(Albaran.class);
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
