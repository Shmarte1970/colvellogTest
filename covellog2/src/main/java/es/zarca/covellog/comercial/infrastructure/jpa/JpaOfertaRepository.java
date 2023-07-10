package es.zarca.covellog.comercial.infrastructure.jpa;

import es.zarca.covellog.application.exception.BusinessException;
import es.zarca.covellog.application.exception.UndefinedBussinesException;
import es.zarca.covellog.application.ofertas.exception.OfertaNotExistException;
import es.zarca.covellog.application.stock.exception.ContratoNotExistException;
import es.zarca.covellog.domain.model.app.Ordre;
import es.zarca.covellog.domain.model.app.TipusOrdre;
import es.zarca.covellog.comercial.domain.oferta.Oferta;
import es.zarca.covellog.comercial.domain.oferta.OfertaLinea;
import es.zarca.covellog.comercial.domain.oferta.OfertaRepository;
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
public class JpaOfertaRepository implements OfertaRepository, Serializable {
    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(JpaOfertaRepository.class.getName());

    @PersistenceContext(unitName = "covellog2-pu")
    private EntityManager entityManager;

    @Override
    public Oferta find(int id) {
        Oferta stock;
        try {
            stock = entityManager.createNamedQuery("Oferta.findById", Oferta.class).setParameter("id", id).getSingleResult();
        } catch (NoResultException e) {
            stock = null;
        }
        return stock;
    }

    @Override
    public Oferta findOrFail(int id) {
        Oferta stock = find(id);
        if (stock == null) {
            throw new OfertaNotExistException(id);
        }
        return stock;
    }
    

    private Order getOrderItem(CriteriaBuilder cb, Ordre ordre, Expression<String> path) {
        if (ordre.getTipusOrdre().equals(TipusOrdre.ASCENDENT)) {
            return cb.asc(path);
        }
        return cb.desc(path);
    }
    
    private List<Order> processOrder(CriteriaBuilder cb, Root<Oferta> root, Ordre ordre) {
        Path<String> path;
        List<Order> orderList = new ArrayList();
        switch (ordre.getId()) {
            case "id":            
            case "fechaOferta":            
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
    
    private List<Predicate> processFilters(CriteriaQuery query, CriteriaBuilder cb, Root<Oferta> root, Map<String, Object> filters) {
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
                    p = cb.notEqual(expr, EstadoOferta.BAJA);
                    break;*/                
                case "friendlyId":
                    String aux = (String)value;
                    aux = aux.replace("C", "");
                    Integer id = Integer.parseInt(aux);
                    expr = root.get("id").as(String.class);
                    p = cb.like(expr, "%" + aux + "%");
                    break;
                case "clienteNombre":
                    Join<Oferta, Empresa> empresa = root.join("cliente");
                    expr = empresa.get("nombre").as(String.class);
                    p = cb.like(cb.lower(expr), "%" + value.toString().toLowerCase() + "%");
                    break;
                case "productos":
                    System.err.println("cojones: camosssss " + field);
                    Subquery<OfertaLinea> subQuery = query.subquery(OfertaLinea.class);
                    Root<OfertaLinea> linea = subQuery.from(OfertaLinea.class);
                    subQuery.select(linea.get("numeroSerie"));
                    expr = linea.get("numeroSerie").as(String.class);
                    Predicate subPredictate = cb.and(
                        cb.equal(root, linea.get("oferta")),
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
    public List<Oferta> find(int first, int pageSize, Ordre sortOrder, Map<String, Object> filters) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Oferta> criteriaQuery = cb.createQuery(Oferta.class);
        Root<Oferta> root = criteriaQuery.from(Oferta.class);
        CriteriaQuery<Oferta> select = criteriaQuery.select(root);
        if (filters != null && filters.size() > 0) {
            List<Predicate> predicates = processFilters(criteriaQuery, cb, root, filters);
            if (predicates.size() > 0) {
                criteriaQuery.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
            }           
        }
        if (sortOrder != null) {
            criteriaQuery.orderBy(processOrder(cb, root, sortOrder));
        }
            
        TypedQuery<Oferta> query = entityManager.createQuery(select);
        query.setFirstResult(first);
        query.setMaxResults(pageSize);
        List<Oferta> list = query.getResultList();
        return list;
    }
    
    @Override
    public List<Oferta> findAll() {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try { 
            Query query = entityManager.createNamedQuery("Oferta.findAll", Oferta.class);
            List <Oferta> list = query.getResultList();
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
        Query query = entityManager.createQuery("Select count(f.id) From Oferta f");
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
        Root<Oferta> root = criteriaQuery.from(Oferta.class);
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
    public void store(Oferta oferta) {
        try {
            entityManager.persist(oferta);
        } catch (ConstraintViolationException ex) {
            throw new PersistenceException(ex);
        }
    }

    @Override
    public void remove(Oferta oferta) {
        try {
            entityManager.remove(entityManager.merge(oferta));
        } catch (ConstraintViolationException ex) {
            throw new PersistenceException(ex);
        }
    }

}
