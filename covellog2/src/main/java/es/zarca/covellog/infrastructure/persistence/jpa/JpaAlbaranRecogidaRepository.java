package es.zarca.covellog.infrastructure.persistence.jpa;

import es.zarca.covellog.application.exception.UndefinedBussinesException;
import es.zarca.covellog.domain.model.adreces.poblacio.Poblacio;
import es.zarca.covellog.domain.model.albaran.AlbaranRecogida;
import es.zarca.covellog.domain.model.albaran.AlbaranRecogidaRepository;
import es.zarca.covellog.domain.model.albaran.exception.AlbaranRecogidaNotExistException;
import es.zarca.covellog.domain.model.app.Ordre;
import es.zarca.covellog.domain.model.app.TipusOrdre;
import es.zarca.covellog.domain.model.empresa.Empresa;
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
public class JpaAlbaranRecogidaRepository implements AlbaranRecogidaRepository, Serializable {
    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = Logger.getLogger(JpaAlbaranRecogidaRepository.class.getName());

    @PersistenceContext(unitName = "covellog2-pu")
    private EntityManager entityManager;

    @Override
    public AlbaranRecogida find(Integer id) {
        AlbaranRecogida almacen;
        try {
            almacen = entityManager.createNamedQuery("AlbaranRecogida.findById", AlbaranRecogida.class).setParameter("id", id).getSingleResult();
        } catch (NoResultException e) {
            almacen = null;
        }
        return almacen;
    }

    @Override
    public AlbaranRecogida findOrFail(Integer id) {
        AlbaranRecogida almacen = find(id);
        if (almacen == null) {
            throw new AlbaranRecogidaNotExistException(id);
        }
        return almacen;
    }
    
    private Order getOrderItem(CriteriaBuilder cb, Ordre ordre, Expression<String> path) {
        if (ordre.getTipusOrdre().equals(TipusOrdre.ASCENDENT)) {
            return cb.asc(path);
        }
        return cb.desc(path);
    }
    
    private List<Order> processOrder(CriteriaBuilder cb, Root<AlbaranRecogida> root, Ordre ordre) {
        Path<String> path;
        List<Order> orderList = new ArrayList();
        switch (ordre.getId()) {
            case "empresa.nombre":
                Join<AlbaranRecogida, Empresa> empresa = root.join("empresa");
                orderList.add(getOrderItem(cb, ordre, empresa.<String>get("nombre").as(String.class)));
                break;
            case "direccion.poblacion.nombre":
            case "direccion.poblacion.nom":
                Join<AlbaranRecogida, Poblacio> poblacio = root.join("direccion").join("poblacion");
                orderList.add(getOrderItem(cb, ordre, poblacio.<String>get("nom").as(String.class)));
                break;
            default:
                orderList.add(getOrderItem(cb, ordre, root.get(ordre.getId())));
                break;
        }
        return orderList;
    }
    
    private List<Predicate> processFilters(CriteriaQuery query, CriteriaBuilder cb, Root<AlbaranRecogida> root, Map<String, Object> filters) {
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
                    Join<AlbaranRecogida, Empresa> empresa = root.join("empresa");
                    expr = empresa.<String>get("nombre").as(String.class);
                    p = cb.like(cb.lower(expr), "%" + value.toString().toLowerCase() + "%");
                    break;
                case "direccion.poblacion.nombre":
                case "direccion.poblacion.nom":
                case "poblacion:CONTAINS":
                    Join<AlbaranRecogida, Poblacio> poblacio = root.join("direccion").join("poblacion");
                    expr = poblacio.<String>get("nom").as(String.class);
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
    public List<AlbaranRecogida> find(int first, int pageSize, Ordre sortOrder, Map<String, Object> filters) {
        FunctionLog log = new FunctionLog(Capa.REPOSITORY);
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<AlbaranRecogida> criteriaQuery = cb.createQuery(AlbaranRecogida.class);
            Root<AlbaranRecogida> root = criteriaQuery.from(AlbaranRecogida.class);
            CriteriaQuery<AlbaranRecogida> select = criteriaQuery.select(root);

            if (filters != null && filters.size() > 0) {
                List<Predicate> predicates = processFilters(criteriaQuery, cb, root, filters);
                if (predicates.size() > 0) {
                    criteriaQuery.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
                }           
            }
            if (sortOrder != null) {
                criteriaQuery.orderBy(processOrder(cb, root, sortOrder));
            }

            TypedQuery<AlbaranRecogida> query = entityManager.createQuery(select);
            query.setFirstResult(first);
            query.setMaxResults(pageSize);
            List<AlbaranRecogida> list = query.getResultList();
            return list;
        } catch (Exception ex) {
            throw new UndefinedBussinesException(ex);
        } finally {
            log.finish();
        }
    }
    
    @Override
    public List<AlbaranRecogida> findAll(){      
        return entityManager.createNamedQuery("AlbaranRecogida.findAll", AlbaranRecogida.class).getResultList();
    }

    @Override
    public int findTotalCount() {
        Crono crono = new Crono();               
        Query query = entityManager.createQuery("Select count(f.id) From AlbaranRecogida f");
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
        Root<AlbaranRecogida> root = criteriaQuery.from(AlbaranRecogida.class);
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
    public void store(AlbaranRecogida almacen) {
        try {
            entityManager.persist(almacen);
        } catch (ConstraintViolationException ex) {
            throw new PersistenceException(ex);
        }
    }

    @Override
    public void remove(AlbaranRecogida almacen) {
        try {
            entityManager.remove(entityManager.merge(almacen));
        } catch (ConstraintViolationException ex) {
            throw new PersistenceException(ex);
        }
    }
    
}
