/*
 * Proyecto Covellog2
 * 
 * Este archivo ha sido creado para ZARCA y no se puede usar sin su consentimiento.
 */
package es.zarca.covellog.infrastructure.persistence.jpa;

import es.zarca.covellog.domain.model.factures.factura.Factura;
import es.zarca.covellog.domain.model.factures.factura.FacturaRepository;
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
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
@ApplicationScoped
public class JpaFacturaRepository implements FacturaRepository, Serializable {
    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(JpaFacturaRepository.class.getName());

    @PersistenceContext(unitName = "covellog2-pu")
    private EntityManager entityManager;

    @Override
    public Factura find(Integer id) {
        Crono crono = new Crono();
        Factura factura;
        try {
            factura = entityManager.createNamedQuery("Factura.findById", Factura.class).setParameter("id", id).getSingleResult();
//            logger.log(Level.FINE, "{0}.find(Integer id): Trovat país amb id={1} nom={2} ({3} ms).", new Object[]{getClass().getName(), id.toString(), factura.getNom(), String.valueOf(crono.getMiliSegons())});
        } catch (NoResultException e) {
 //           logger.log(Level.FINE, "{0}.find(Integer id): No existeix el país amb id={1} ({2} ms).", new Object[]{getClass().getName(), id.toString(), String.valueOf(crono.getMiliSegons())});
            factura = null;
        }
        return factura;
    }

    @Override
    public void store(Factura factura) {
        Crono crono = new Crono();
        entityManager.persist(factura);
//        logger.log(Level.FINE, "{0}.store(Factura factura): País guardat codi={0} nom={1} ({2} ms).", new Object[]{getClass().getName(), factura.getCodiIso2(), factura.getNom(), String.valueOf(crono.getMiliSegons())});
    }
        
    @Override
    public void remove(Factura factura) {
        Crono crono = new Crono();
        entityManager.remove(entityManager.merge(factura));
//        logger.log(Level.FINE, "{0}.remove(Factura factura): País el·liminat codi={0} nom={1} ({2} ms).", new Object[]{getClass().getName(), factura.getCodiIso2(), factura.getNom(), String.valueOf(crono.getMiliSegons())});
    }
    
    @Override
    public List<Factura> findAll() {
        Crono crono = new Crono();
        List<Factura> list = entityManager.createNamedQuery("Factura.findAll", Factura.class).getResultList();
        logger.log(Level.FINE, "{0}.findAll(): Trovats {1} països  ({2} ms)." ,new Object[]{getClass().getName(), String.valueOf(list.size()), String.valueOf(crono.getMiliSegons())});
        return list;
    }
    
    @Override
    public List<Factura> findAll(int start, int size) {    
        Crono crono = new Crono();
        Query query = entityManager.createNamedQuery("Factura.findAll", Factura.class);
        query.setFirstResult(start);
        query.setMaxResults(size);
        List <Factura> list = query.getResultList();
        logger.log(Level.FINE, "{0}.findAll({1}, {2}): Trovats {3} països  ({4} ms)." ,new Object[]{getClass().getName(), String.valueOf(start), String.valueOf(size), String.valueOf(list.size()), String.valueOf(crono.getMiliSegons())});
        return list;
    }

    @Override
    public int findAllTotalCount() {
        Crono crono = new Crono();               
        Query query = entityManager.createQuery("Select count(p.id) From Factura p");
        int count = ((Long)query.getSingleResult()).intValue();
        logger.log(Level.FINE, "{0}.findAllTotalCount(): count = {1} ({2} ms)." ,new Object[]{getClass().getName(), String.valueOf(count), String.valueOf(crono.getMiliSegons())});
        return count;
    }

    @Override
    public List<Factura> findAll(Map<String, Object> filters) {
        Crono crono = new Crono();
        List<Factura> list = entityManager.createNamedQuery("Factura.findByFilter", Factura.class).getResultList();
        logger.log(Level.FINE, "{0}.findAll(Map<String, Object> filters)({1}): Trovats {2} països  ({3} ms)." ,new Object[]{getClass().getName(), filters.toString(), String.valueOf(list.size()), String.valueOf(crono.getMiliSegons())});
        return list;
    }

    @Override
    public List<Factura> findAll(int start, int size, Map<String, Object> filters) {
        Crono crono = new Crono();
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Factura> criteriaQuery = cb.createQuery(Factura.class);
        Root<Factura> root = criteriaQuery.from(Factura.class);
        CriteriaQuery<Factura> select = criteriaQuery.select(root);

        if (filters != null && filters.size() > 0) {
            List<Predicate> predicates = new ArrayList<>();
            for (Map.Entry<String, Object> entry : filters.entrySet()) {
                String field = entry.getKey();
                Object value = entry.getValue();
                if (value == null) {
                    continue;
                }
                Expression<String> expr;
                if (field.equals("codiIso2")) {
                    expr = root.get(field).get("codigo").as(String.class);
                } else {
                    expr = root.get(field).as(String.class);
                }
                Predicate p = cb.like( cb.lower(expr), "%" + value.toString().toLowerCase() + "%");
                predicates.add(p);
            }
            if (predicates.size() > 0) {
                criteriaQuery.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
            }           
        }
        criteriaQuery.orderBy(cb.asc(root.get("nom")));
        TypedQuery<Factura> query = entityManager.createQuery(select);
        query.setFirstResult(start);
        query.setMaxResults(size);
        List<Factura> list = query.getResultList();
        logger.log(Level.FINE, "{0}.findAll(int start, int size, Map<String, Object> filters)({1}, {2}, {3}): Trovats {4} països ({5} ms)." ,
                new Object[]{getClass().getName(), String.valueOf(start), String.valueOf(size), filters.toString(), String.valueOf(list.size()), String.valueOf(crono.getMiliSegons())});
        return list;
    }

    @Override
    public int findAllTotalCount(Map<String, Object> filters) {
        if (filters == null) {
            return findAllTotalCount();
        }
        Crono crono = new Crono();
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = cb.createQuery(Long.class);
        Root<Factura> root = criteriaQuery.from(Factura.class);
        CriteriaQuery<Long> select = criteriaQuery.select(cb.count(root));

        if (filters.size() > 0) {
            List<Predicate> predicates = new ArrayList<>();
            for (Map.Entry<String, Object> entry : filters.entrySet()) {
                String field = entry.getKey();
                Object value = entry.getValue();
                if (value == null) {
                    continue;
                }
                Expression<String> expr;
                if (field.equals("codiIso2")) {
                    expr = root.get(field).get("codigo").as(String.class);
                } else {
                    expr = root.get(field).as(String.class);
                }
                Predicate p = cb.like(cb.lower(expr), "%" + value.toString().toLowerCase() + "%");
                predicates.add(p);
            }
            if (predicates.size() > 0) {
                criteriaQuery.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
            }
        }
        Long count = entityManager.createQuery(select).getSingleResult();
        logger.log(Level.FINE, "{0}.findAllTotalCount(Map<String, Object> filters)({1}): count = {2} ({3} ms)." ,new Object[]{getClass().getName(), filters.toString(), count.toString(), String.valueOf(crono.getMiliSegons())});
        return count.intValue();
    }

}
