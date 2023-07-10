package es.zarca.covellog.infrastructure.persistence.jpa;

import es.zarca.covellog.application.exception.BusinessException;
import es.zarca.covellog.application.exception.UndefinedBussinesException;
import es.zarca.covellog.application.stock.exception.ContratoNotExistException;
import es.zarca.covellog.domain.model.app.Ordre;
import es.zarca.covellog.domain.model.app.TipusOrdre;
import es.zarca.covellog.domain.model.contrato.Contrato;
import es.zarca.covellog.domain.model.contrato.ContratoLinea;
import es.zarca.covellog.domain.model.contrato.ContratoRepository;
import es.zarca.covellog.domain.model.empresa.Empresa;
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
import javax.persistence.criteria.Subquery;
import javax.validation.ConstraintViolationException;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
@ApplicationScoped
public class JpaContratoRepository implements ContratoRepository, Serializable {
    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(JpaContratoRepository.class.getName());

    @PersistenceContext(unitName = "covellog2-pu")
    private EntityManager entityManager;

    @Override
    public Contrato find(int id) {
        Contrato stock;
        try {
            stock = entityManager.createNamedQuery("Contrato.findById", Contrato.class).setParameter("id", id).getSingleResult();
        } catch (NoResultException e) {
            stock = null;
        }
        return stock;
    }

    @Override
    public Contrato findOrFail(int id) {
        Contrato stock = find(id);
        if (stock == null) {
            throw new ContratoNotExistException(id);
        }
        return stock;
    }
    

    private Order getOrderItem(CriteriaBuilder cb, Ordre ordre, Expression<String> path) {
        if (ordre.getTipusOrdre().equals(TipusOrdre.ASCENDENT)) {
            return cb.asc(path);
        }
        return cb.desc(path);
    }
    
    private List<Order> processOrder(CriteriaBuilder cb, Root<Contrato> root, Ordre ordre) {
        Path<String> path;
        List<Order> orderList = new ArrayList();
        switch (ordre.getId()) {
            case "id":            
            case "fechaContrato":            
            case "estado":
            case "pagoEstado":
                orderList.add(getOrderItem(cb, ordre, root.get(ordre.getId())));
                break;
            case "clienteNombre":
                orderList.add(getOrderItem(cb, new Ordre(root.get("cliente").get("nombre").toString(), ordre.getTipusOrdre()), root.get("cliente").get("nombre")));
            case "friendlyId":
                orderList.add(getOrderItem(cb, new Ordre("id", ordre.getTipusOrdre()), root.get("id")));
            default:
                orderList.add(getOrderItem(cb, new Ordre("id", TipusOrdre.ASCENDENT), root.get("id")));
                break;
        }
        return orderList;
    }
    
    private List<Predicate> processFilters(CriteriaQuery query, CriteriaBuilder cb, Root<Contrato> root, Map<String, Object> filters) {
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
               /* case "ocultarBajas":
                    System.err.println("ocultarBajas por donde toca");
                    expr = root.get("estado").as(String.class);
                    p = cb.notEqual(expr, EstadoContrato.BAJA);
                    break;*/                
                case "friendlyId":
                    String aux = (String)value;
                    aux = aux.replace("C", "");
                    Integer id = Integer.parseInt(aux);
                    expr = root.get("id").as(String.class);
                    p = cb.like(expr, "%" + aux + "%");
                    break;
                case "clienteNombre":
                    Join<Contrato, Empresa> empresa = root.join("cliente");
                    expr = empresa.get("nombre").as(String.class);
                    p = cb.like(cb.lower(expr), "%" + value.toString().toLowerCase() + "%");
                    break;
                case "productos":
                    System.err.println("cojones: camosssss " + field);
                    Subquery<ContratoLinea> subQuery = query.subquery(ContratoLinea.class);
                    Root<ContratoLinea> linea = subQuery.from(ContratoLinea.class);
                    subQuery.select(linea.get("numeroSerie"));
                    expr = linea.get("numeroSerie").as(String.class);
                    Predicate subPredictate = cb.and(
                        cb.equal(root, linea.get("contrato")),
                        cb.like(cb.lower(expr), "%" + value.toString().toLowerCase() + "%")
                    );
                    subQuery.where(subPredictate);
                    p = cb.exists(subQuery);
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
    public List<Contrato> find(int first, int pageSize, Ordre sortOrder, Map<String, Object> filters) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Contrato> criteriaQuery = cb.createQuery(Contrato.class);
        Root<Contrato> root = criteriaQuery.from(Contrato.class);
        CriteriaQuery<Contrato> select = criteriaQuery.select(root);
        if (filters != null && filters.size() > 0) {
            List<Predicate> predicates = processFilters(criteriaQuery, cb, root, filters);
            if (predicates.size() > 0) {
                criteriaQuery.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
            }           
        }
        if (sortOrder != null) {
            criteriaQuery.orderBy(processOrder(cb, root, sortOrder));
        }
            
        TypedQuery<Contrato> query = entityManager.createQuery(select);
        query.setFirstResult(first);
        query.setMaxResults(pageSize);
        List<Contrato> list = query.getResultList();
        return list;
    }
    
    @Override
    public List<Contrato> findAll() {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try { 
            Query query = entityManager.createNamedQuery("Contrato.findAll", Contrato.class);
            List <Contrato> list = query.getResultList();
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
        Query query = entityManager.createQuery("Select count(f.id) From Contrato f");
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
        Root<Contrato> root = criteriaQuery.from(Contrato.class);
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
    public void store(Contrato contrato) {
        try {
            entityManager.persist(contrato);
        } catch (ConstraintViolationException ex) {
            throw new PersistenceException(ex);
        }
    }

    @Override
    public void remove(Contrato contrato) {
        try {
            entityManager.remove(entityManager.merge(contrato));
        } catch (ConstraintViolationException ex) {
            throw new PersistenceException(ex);
        }
    }

}
