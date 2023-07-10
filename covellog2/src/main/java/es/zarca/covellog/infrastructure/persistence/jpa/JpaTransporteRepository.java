package es.zarca.covellog.infrastructure.persistence.jpa;

import es.zarca.covellog.application.exception.UndefinedBussinesException;
import es.zarca.covellog.application.transporte.exception.TransporteNotExistException;
import es.zarca.covellog.domain.model.app.Ordre;
import es.zarca.covellog.domain.model.app.TipusOrdre;
import es.zarca.covellog.domain.model.empresa.Empresa;
import es.zarca.covellog.domain.model.producto.FamiliaProducto;
import es.zarca.covellog.domain.model.transporte.Transporte;
import es.zarca.covellog.domain.model.transporte.TransporteRepository;
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
public class JpaTransporteRepository implements TransporteRepository, Serializable {
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(JpaTransporteRepository.class.getName());

    @PersistenceContext(unitName = "covellog2-pu")
    private EntityManager entityManager;

    @Override
    public Transporte find(Integer id) {
        Transporte transporte;
        try {
            transporte = entityManager.createNamedQuery("Transporte.findById", Transporte.class).setParameter("id", id).getSingleResult();
        } catch (NoResultException e) {
            transporte = null;
        }
        return transporte;
    }

    @Override
    public Transporte findOrFail(Integer id) {
        Transporte transporte = find(id);
        if (transporte == null) {
            throw new TransporteNotExistException(id);
        }
        return transporte;
    }
    
    private Order getOrderItem(CriteriaBuilder cb, Ordre ordre, Expression<String> path) {
        if (ordre.getTipusOrdre().equals(TipusOrdre.ASCENDENT)) {
            return cb.asc(path);
        }
        return cb.desc(path);
    }
    
    private List<Order> processOrder(CriteriaBuilder cb, Root<Transporte> root, Ordre ordre) {
        Path<String> path;
        List<Order> orderList = new ArrayList();
        switch (ordre.getId()) {
            case "empresa.nombre":
                Join<Transporte, Empresa> empresa = root.join("empresa");
                orderList.add(getOrderItem(cb, ordre, empresa.<String>get("nombre").as(String.class)));
                break;
            default:
                orderList.add(getOrderItem(cb, ordre, root.get(ordre.getId())));
                break;
        }
        return orderList;
    }
    
    private List<Predicate> processFilters(CriteriaQuery query, CriteriaBuilder cb, Root<Transporte> root, Map<String, Object> filters) {
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
                case "empresa.nombre":
                case "empresa:CONTAINS":
                    Join<Transporte, FamiliaProducto> familia = root.join("familia");
                    expr = familia.<String>get("nombre").as(String.class);
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
    public List<Transporte> find(int first, int pageSize, Ordre sortOrder, Map<String, Object> filters) {
        FunctionLog log = new FunctionLog(Capa.REPOSITORY);
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Transporte> criteriaQuery = cb.createQuery(Transporte.class);
            Root<Transporte> root = criteriaQuery.from(Transporte.class);
            CriteriaQuery<Transporte> select = criteriaQuery.select(root);

            if (filters != null && filters.size() > 0) {
                List<Predicate> predicates = processFilters(criteriaQuery, cb, root, filters);
                if (predicates.size() > 0) {
                    criteriaQuery.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
                }           
            }
            if (sortOrder != null) {
                criteriaQuery.orderBy(processOrder(cb, root, sortOrder));
            }

            TypedQuery<Transporte> query = entityManager.createQuery(select);
            query.setFirstResult(first);
            query.setMaxResults(pageSize);
            List<Transporte> list = query.getResultList();
            return list;
        } catch (Exception ex) {
            throw new UndefinedBussinesException(ex);
        } finally {
            log.finish();
        }
    }
    
    @Override
    public List<Transporte> findAll(){      
        return entityManager.createNamedQuery("Transporte.findAll", Transporte.class).getResultList();
    }

    @Override
    public int findTotalCount() {
        Crono crono = new Crono();               
        Query query = entityManager.createQuery("Select count(f.id) From Transporte f");
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
        Root<Transporte> root = criteriaQuery.from(Transporte.class);
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
    public void store(Transporte transporte) {
        try {
            entityManager.persist(transporte);
        } catch (ConstraintViolationException ex) {
            throw new PersistenceException(ex);
        }
    }

    @Override
    public void remove(Transporte transporte) {
        try {
            entityManager.remove(entityManager.merge(transporte));
        } catch (ConstraintViolationException ex) {
            throw new PersistenceException(ex);
        }
    }
    
    @Override
    public List<Filtro> getFiltrosPosibles() {
        FunctionLog log = new FunctionLog(Capa.REPOSITORY);
        List<Filtro> lista = new ArrayList<>();
        lista.add(new Filtro("id", Operacion.CONTAINS));
        lista.add(new Filtro("empresa.nombre", Operacion.CONTAINS));
        lista.add(new Filtro("capacidad", Operacion.CONTAINS));
        lista.forEach(filtro -> {
            LOGGER.log(Level.INFO, "Filtro: {0}", filtro);
        });
        log.finish();
        return lista;
    }

}
