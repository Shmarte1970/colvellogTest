/*
 * Proyecto Covellog2
 * 
 * Este archivo ha sido creado para ZARCA y no se puede usar sin su consentimiento.
 */
package es.zarca.covellog.infrastructure.persistence.jpa;

import es.zarca.covellog.domain.model.adreces.pais.CodiIsoPais2;
import es.zarca.covellog.domain.model.adreces.pais.Pais;
import es.zarca.covellog.domain.model.adreces.pais.PaisRepository;
import es.zarca.covellog.infrastructure.util.crono.Crono;
import es.zarca.covellog.infrastructure.persistence.exception.PersistenceException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
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
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */

@ApplicationScoped
public class JpaPaisRepository implements PaisRepository, Serializable {
    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = Logger.getLogger(JpaPaisRepository.class.getName());

    @PersistenceContext(unitName = "covellog2-pu")
    private EntityManager entityManager;

    @Override
    public Pais find(Integer id) {
        Crono crono = new Crono();
        Pais pais;
        try {
            pais = entityManager.createNamedQuery("Pais.findById", Pais.class).setParameter("id", id).getSingleResult();
            LOGGER.log(Level.FINE, "{0}.find(Integer id): Trovat país amb id={1} nom={2} ({3} ms).", new Object[]{getClass().getName(), id.toString(), pais.getNom(), String.valueOf(crono.getMiliSegons())});
        } catch (NoResultException e) {
            LOGGER.log(Level.FINE, "{0}.find(Integer id): No existeix el país amb id={1} ({2} ms).", new Object[]{getClass().getName(), id.toString(), String.valueOf(crono.getMiliSegons())});
            pais = null;
        }
        return pais;
    }
    
    @Override
    public Pais find(CodiIsoPais2 codi) {
        Crono crono = new Crono();
        Pais pais;
        try {
            pais = entityManager.createNamedQuery("Pais.findById", Pais.class).setParameter("CodiIsoPais", codi.getCodi()).getSingleResult();
            LOGGER.log(Level.FINE, "{0}.find(CodiIsoPais2 id): Trovat país amb codi={0} nom={1} ({2} ms).", new Object[]{getClass().getName(), codi.toString(), pais.getNom(), String.valueOf(crono.getMiliSegons())});
        } catch (NoResultException e) {
            LOGGER.log(Level.FINE, "{0}.find(Integer id): No existeix el país amb codi={0} ({1} ms).", new Object[]{getClass().getName(), codi.toString(), String.valueOf(crono.getMiliSegons())});
            pais = null;
        }
        return pais;
    }

    @Override
    public void store(Pais pais) {
        try {
            Crono crono = new Crono();
            entityManager.persist(pais);
            LOGGER.log(Level.FINE, "{0}.store(Pais pais): País guardat codi={0} nom={1} ({2} ms).", new Object[]{getClass().getName(), pais.getCodiIso2(), pais.getNom(), String.valueOf(crono.getMiliSegons())});
        } catch (ConstraintViolationException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
            for (ConstraintViolation actual : ex.getConstraintViolations()) {
                LOGGER.log(Level.SEVERE, actual.toString());
            }
            throw new PersistenceException(ex);
        }
    }
        
    @Override
    public void remove(Pais pais) {
        Crono crono = new Crono();
        entityManager.remove(entityManager.merge(pais));
        LOGGER.log(Level.FINE, "{0}.remove(Pais pais): País el·liminat codi={0} nom={1} ({2} ms).", new Object[]{getClass().getName(), pais.getCodiIso2(), pais.getNom(), String.valueOf(crono.getMiliSegons())});
    }
    
    @Override
    public List<Pais> findAll() {
        Crono crono = new Crono();
        List<Pais> list = entityManager.createNamedQuery("Pais.findAll", Pais.class).getResultList();
        LOGGER.log(Level.FINE, "{0}.findAll(): Trovats {1} països  ({2} ms)." ,new Object[]{getClass().getName(), String.valueOf(list.size()), String.valueOf(crono.getMiliSegons())});
        return list;
    }
    
    @Override
    public List<Pais> findAll(int start, int size) {    
        Crono crono = new Crono();
        Query query = entityManager.createNamedQuery("Pais.findAll", Pais.class);
        query.setFirstResult(start);
        query.setMaxResults(size);
        List <Pais> list = query.getResultList();
        LOGGER.log(Level.FINE, "{0}.findAll({1}, {2}): Trovats {3} països  ({4} ms)." ,new Object[]{getClass().getName(), String.valueOf(start), String.valueOf(size), String.valueOf(list.size()), String.valueOf(crono.getMiliSegons())});
        return list;
    }

    @Override
    public int findAllTotalCount() {
        Crono crono = new Crono();               
        Query query = entityManager.createQuery("Select count(p.id) From Pais p");
        int count = ((Long)query.getSingleResult()).intValue();
        LOGGER.log(Level.FINE, "{0}.findAllTotalCount(): count = {1} ({2} ms)." ,new Object[]{getClass().getName(), String.valueOf(count), String.valueOf(crono.getMiliSegons())});
        return count;
    }

    @Override
    public List<Pais> findAll(Map<String, Object> filters) {
        Crono crono = new Crono();
        List<Pais> list = entityManager.createNamedQuery("Pais.findByFilter", Pais.class).getResultList();
        LOGGER.log(Level.FINE, "{0}.findAll(Map<String, Object> filters)({1}): Trovats {2} països  ({3} ms)." ,new Object[]{getClass().getName(), filters.toString(), String.valueOf(list.size()), String.valueOf(crono.getMiliSegons())});
        return list;
    }

    @Override
    public List<Pais> findAll(int start, int size, Map<String, Object> filters) {
        Crono crono = new Crono();
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pais> criteriaQuery = cb.createQuery(Pais.class);
        Root<Pais> root = criteriaQuery.from(Pais.class);
        CriteriaQuery<Pais> select = criteriaQuery.select(root);

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
        TypedQuery<Pais> query = entityManager.createQuery(select);
        query.setFirstResult(start);
        query.setMaxResults(size);
        List<Pais> list = query.getResultList();
        LOGGER.log(Level.FINE, "{0}.findAll(int start, int size, Map<String, Object> filters)({1}, {2}, {3}): Trovats {4} països ({5} ms)." ,
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
        Root<Pais> root = criteriaQuery.from(Pais.class);
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
        LOGGER.log(Level.FINE, "{0}.findAllTotalCount(Map<String, Object> filters)({1}): count = {2} ({3} ms)." ,new Object[]{getClass().getName(), filters.toString(), count.toString(), String.valueOf(crono.getMiliSegons())});
        return count.intValue();
    }

}
