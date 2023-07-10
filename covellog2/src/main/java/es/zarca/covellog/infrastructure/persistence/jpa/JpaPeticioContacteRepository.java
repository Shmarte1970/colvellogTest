/*
 * Proyecto Covellog2
 * 
 * Este archivo ha sido creado para ZARCA y no se puede usar sin su consentimiento.
 */
package es.zarca.covellog.infrastructure.persistence.jpa;

import es.zarca.covellog.domain.model.adreces.pais.Pais;
import es.zarca.covellog.domain.model.adreces.poblacio.Poblacio;
import es.zarca.covellog.domain.model.adreces.provincia.Provincia;
import es.zarca.covellog.domain.model.app.Ordre;
import es.zarca.covellog.domain.model.app.TipusOrdre;
import es.zarca.covellog.domain.model.crm.peticiocontacte.AtributPeticioContacte;
import es.zarca.covellog.domain.model.crm.peticiocontacte.AtributPeticioContactePK;
import es.zarca.covellog.domain.model.crm.peticiocontacte.PeticioContacte;
import es.zarca.covellog.domain.model.crm.peticiocontacte.PeticioContacteRepository;
import es.zarca.covellog.infrastructure.util.crono.Crono;
import java.io.Serializable;
import java.math.BigDecimal;
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

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
@ApplicationScoped
public class JpaPeticioContacteRepository implements PeticioContacteRepository, Serializable {
    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(JpaPeticioContacteRepository.class.getName());

    @PersistenceContext(unitName = "webzarca-pu")
    private EntityManager entityManager;

    @Override
    public PeticioContacte find(BigDecimal submitTime) {
        Crono crono = new Crono();
        PeticioContacte peticioContacte;
        try {
            peticioContacte = entityManager.createNamedQuery("PeticioContacte.findBySubmitTime", PeticioContacte.class).setParameter("submitTime", submitTime).getSingleResult();
           // logger.log(Level.FINE, "{0}.find(Integer id): Trovat país amb id={1} nom={2} ({3} ms).", new Object[]{getClass().getName(), id.toString(), peticioContacte.getNom(), String.valueOf(crono.getMiliSegons())});
        } catch (NoResultException e) {
           // logger.log(Level.FINE, "{0}.find(Integer id): No existeix el país amb id={1} ({2} ms).", new Object[]{getClass().getName(), id.toString(), String.valueOf(crono.getMiliSegons())});
            peticioContacte = null;
        }
        return peticioContacte;
    }
    
    @Override
    public void store(PeticioContacte peticioContacte) {
        Crono crono = new Crono();
        entityManager.persist(peticioContacte);
        //logger.log(Level.FINE, "{0}.store(PeticioContacte peticioContacte): País guardat codi={0} nom={1} ({2} ms).", new Object[]{getClass().getName(), peticioContacte.getCodiIso2(), peticioContacte.getNom(), String.valueOf(crono.getMiliSegons())});
    }
        
    @Override
    public void remove(PeticioContacte peticioContacte) {
        Crono crono = new Crono();
        entityManager.remove(entityManager.merge(peticioContacte));
       // logger.log(Level.FINE, "{0}.remove(PeticioContacte peticioContacte): País el·liminat codi={0} nom={1} ({2} ms).", new Object[]{getClass().getName(), peticioContacte.getCodiIso2(), peticioContacte.getNom(), String.valueOf(crono.getMiliSegons())});
    }
    
    @Override
    public List<PeticioContacte> findAll() {
        Crono crono = new Crono();
        List<PeticioContacte> list = entityManager.createNamedQuery("PeticioContacte.findAll", PeticioContacte.class).getResultList();
        logger.log(Level.FINE, "{0}.findAll(): Trovats {1} peticioContactes  ({2} ms)." ,new Object[]{getClass().getName(), String.valueOf(list.size()), String.valueOf(crono.getMiliSegons())});
        return list;
    }
    
    @Override
    public List<PeticioContacte> findAll(int start, int size) {    
        Crono crono = new Crono();
        Query query = entityManager.createNamedQuery("PeticioContacte.findAll", PeticioContacte.class);
        query.setFirstResult(start);
        query.setMaxResults(size);
        List <PeticioContacte> list = query.getResultList();
        System.out.println("ojitooooo " + list.size());
        logger.log(Level.FINE, "{0}.findAll({1}, {2}): Trovats {3} peticioContactes  ({4} ms)." ,new Object[]{getClass().getName(), String.valueOf(start), String.valueOf(size), String.valueOf(list.size()), String.valueOf(crono.getMiliSegons())});
        return list;
    }

    @Override
    public int findAllTotalCount() {
        Crono crono = new Crono();               
        Query query = entityManager.createQuery("Select count(c.submitTime) From PeticioContacte c");
        int count = ((Long)query.getSingleResult()).intValue();
        logger.log(Level.FINE, "{0}.findAllTotalCount(): count = {1} ({2} ms)." ,new Object[]{getClass().getName(), String.valueOf(count), String.valueOf(crono.getMiliSegons())});
        return count;
    }

    @Override
    public List<PeticioContacte> findAll(Map<String, Object> filters) {
        Crono crono = new Crono();
        List<PeticioContacte> list = entityManager.createNamedQuery("PeticioContacte.findByFilter", PeticioContacte.class).getResultList();
        logger.log(Level.FINE, "{0}.findAll(Map<String, Object> filters)({1}): Trovats {2} peticioContactes  ({3} ms)." ,new Object[]{getClass().getName(), filters.toString(), String.valueOf(list.size()), String.valueOf(crono.getMiliSegons())});
        return list;
    }
    
    private String fieldMapper(String field) {
        switch(field.toLowerCase()) {
                case "nom":
                    return "your-name";
                case "email":
                    return "your-email";
                case "empresa":
                    return "empresa";
                case "telefon":
                    return "tel";
                    
                case "assumpte":
                    return "your-subject";
                    
                case "missatje":
                    return "your-message";
                case "ip":
                    return "Submitted From";
            }
        return field;
    }
    
    private List<Predicate> processFilters(CriteriaBuilder cb, Root<PeticioContacte> root, Map<String, Object> filters) {
        List<Predicate> predicates = new ArrayList<>();
        for (Map.Entry<String, Object> entry : filters.entrySet()) {
            String field = entry.getKey();
            Object value = entry.getValue();
            if (value == null) {
                continue;
            }
            Expression<String> expr;
            
            if ("form".equals(field)) {
                Join<PeticioContacte, AtributPeticioContacte> atributPeticioContacte = root.join("atributs");
                expr = atributPeticioContacte.<String>get("formName").as(String.class);
                Predicate p = cb.like(cb.lower(expr), "%" + value.toString().toLowerCase() + "%");
                predicates.add(p);
            } else if ("submitTime".equals(field)) {
                expr = root.<String>get(field).as(String.class);
                Predicate p = cb.like(cb.lower(expr), "%" + value.toString().toLowerCase() + "%");
                predicates.add(p);
            }else {
                Join<PeticioContacte, AtributPeticioContacte> atributPeticioContacte = root.join("atributs");
                Path<AtributPeticioContactePK> atributPeticioContactePK = atributPeticioContacte.get("atributPeticioContactePK");
                expr = atributPeticioContactePK.<String>get("fieldName").as(String.class);
                Predicate p = cb.equal(cb.lower(expr), fieldMapper(field));
                predicates.add(p);
                expr = atributPeticioContacte.<String>get("fieldValue").as(String.class);
                p = cb.like(cb.lower(expr), "%" + value.toString().toLowerCase() + "%");
                predicates.add(p);
            }
        }            
        return predicates;
    }

    private Order processOrder(CriteriaBuilder cb, Root<PeticioContacte> root, Ordre ordre) {
        Path<String> path;
        if ("form".equals(fieldMapper(ordre.getId()))) {
            Join<PeticioContacte, AtributPeticioContacte> atributPeticioContacte = root.join("atributs");
            Path<AtributPeticioContactePK> atributPeticioContactePK = atributPeticioContacte.get("atributPeticioContactePK");
            Expression<String> expr = atributPeticioContactePK.<String>get("fieldName").as(String.class);
            Predicate p = cb.equal(cb.lower(expr), fieldMapper("nom"));
            atributPeticioContacte = atributPeticioContacte.on(p);
            path = atributPeticioContacte.get("formName");
        } else if ("submitTime".equals(fieldMapper(ordre.getId()))) {
            path = root.get(ordre.getId());
        } else {
            
            Join<PeticioContacte, AtributPeticioContacte> atributPeticioContacte = root.join("atributs");
            Path<AtributPeticioContactePK> atributPeticioContactePK = atributPeticioContacte.get("atributPeticioContactePK");
            Expression<String> expr = atributPeticioContactePK.<String>get("fieldName").as(String.class);
            Predicate p = cb.equal(cb.lower(expr), fieldMapper(ordre.getId()));
            atributPeticioContacte = atributPeticioContacte.on(p);
            path = atributPeticioContacte.get("fieldValue");

        }

        if (ordre.getTipusOrdre().equals(TipusOrdre.ASCENDENT)) {
            return cb.asc(path);
        }
        return cb.desc(path);
    }

    @Override
    public List<PeticioContacte> findAll(int start, int size, Ordre ordre, Map<String, Object> filters) {
        if (filters == null) {
            return findAll(start, size);
        }
        Crono crono = new Crono();
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<PeticioContacte> criteriaQuery = cb.createQuery(PeticioContacte.class);
        Root<PeticioContacte> root = criteriaQuery.from(PeticioContacte.class);
        CriteriaQuery<PeticioContacte> select = criteriaQuery.select(root);

        if (filters != null && filters.size() > 0) {
            List<Predicate> predicates = processFilters(cb, root, filters);
            if (predicates.size() > 0) {
                criteriaQuery.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
            }           
        }
        
        if (ordre != null) {
            criteriaQuery.orderBy(processOrder(cb, root, ordre));
        }
        /*
        if (ordre != null) {
            if (ordre.getTipusOrdre().equals(TipusOrdre.ASCENDENT)) {
                criteriaQuery.orderBy(cb.asc(root.get(fieldMapper(ordre.getId()))));            
            } else if (ordre.getTipusOrdre().equals(TipusOrdre.DESCENDENT)) {
                criteriaQuery.orderBy(cb.desc(root.get(fieldMapper(ordre.getId()))));
            }
        }*/
        TypedQuery<PeticioContacte> query = entityManager.createQuery(select);
        query.setFirstResult(start);
        query.setMaxResults(size);
        List<PeticioContacte> list = query.getResultList();
        logger.log(Level.FINE, "{0}.findAll(int start, int size, Map<String, Object> filters)({1}, {2}, {3}): Trovats {4} peticioContactes ({5} ms)." ,
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
        Root<PeticioContacte> root = criteriaQuery.from(PeticioContacte.class);
        CriteriaQuery<Long> select = criteriaQuery.select(cb.count(root));

        if (filters != null && filters.size() > 0) {
            List<Predicate> predicates = processFilters(cb, root, filters);
            if (predicates.size() > 0) {
                criteriaQuery.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
            }           
        }
        Long count = entityManager.createQuery(select).getSingleResult();
        logger.log(Level.FINE, "{0}.findAllTotalCount(Map<String, Object> filters)({1}): count = {2} ({3} ms)." ,new Object[]{getClass().getName(), filters.toString(), count.toString(), String.valueOf(crono.getMiliSegons())});
        return count.intValue();
    }

    @Override
    public List<PeticioContacte> findBySubmitTimeMajor(BigDecimal submitTime, int start, int size) {
        Crono crono = new Crono();
        Query query = entityManager.createNamedQuery("PeticioContacte.findBySubmitTimeMajor", PeticioContacte.class).setParameter("submitTime", submitTime);
        query.setFirstResult(start);
        query.setMaxResults(size);
        List <PeticioContacte> list = query.getResultList();
        logger.log(Level.FINE, "{0}.findBySubmitTimeMajor({1}, {2}): Trovats {3} peticioContactes  ({4} ms)." ,new Object[]{getClass().getName(), String.valueOf(start), String.valueOf(size), String.valueOf(list.size()), String.valueOf(crono.getMiliSegons())});
        return list;
    }

}
