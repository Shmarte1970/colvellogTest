/*
 * Proyecto Covellog2
 * 
 * Este archivo ha sido creado para ZARCA y no se puede usar sin su consentimiento.
 */
package es.zarca.covellog.infrastructure.persistence.jpa;

import es.zarca.covellog.domain.model.adreces.provincia.Provincia;
import es.zarca.covellog.domain.model.app.App;
import es.zarca.covellog.domain.model.app.AppRepository;
import es.zarca.covellog.domain.model.app.Ordre;
import es.zarca.covellog.domain.model.app.PropietatsApp;
import es.zarca.covellog.domain.model.app.TipusOrdre;
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
import org.eclipse.persistence.jpa.JpaQuery;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
@ApplicationScoped
public class JpaAppRepository implements AppRepository, Serializable {
    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(JpaAppRepository.class.getName());

    @PersistenceContext(unitName = "covellog2-pu")
    private EntityManager entityManager;

    @Override
    public App find(String id) {
        App app = null;
        try {
            app = entityManager.createNamedQuery("App.findById", App.class).setParameter("id", id).getSingleResult();
        } catch (NoResultException e) {
            logger.log(Level.FINE, "No se encuentra la App con id="+ id, e);
        }
        return app;
    }

    @Override
    public void store(App app) {
        entityManager.persist(app);
        for(PropietatsApp propietat:app.getPropietats()) {
            entityManager.persist(propietat);
        }
    }
        
    @Override
    public void remove(App app) {
        entityManager.remove(entityManager.merge(app));
    }
    
    @Override
    public List<App> findAll(int start, int size){      
        Query query = entityManager.createNamedQuery("App.findAll", App.class);
        query.setFirstResult(start);
        query.setMaxResults(size);
        List <App> list = query.getResultList();
        logger.log(Level.INFO, "{0}.findAll(start: {1}, size: {2}) => {3}", new Object[]{getClass().getName(), String.valueOf(start), String.valueOf(size), String.valueOf(list.size())});
        return list;
    }

    @Override
    public int findAllTotalCount() {
        logger.log(Level.INFO, "{0}.findAllTotalCount()", getClass().getName());
        Query query = entityManager.createQuery("Select count(p.id) From App p");
        return ((Long)query.getSingleResult()).intValue();
    }
    
    private List<Predicate> processFilters(CriteriaBuilder cb, Root<App> root, Map<String, Object> filters) {
        List<Predicate> predicates = new ArrayList<>();
        for (Map.Entry<String, Object> entry : filters.entrySet()) {
            String field = entry.getKey();
            Object value = entry.getValue();
            if (value == null) {
                continue;
            }
            Expression<String> expr;
            switch (field) {
                case "propietats_app.id":
                    {
                        Join<App, Provincia> provincia = root.join("propietats_app");
                        expr = provincia.<String>get("id").as(String.class);
                        break;
                    }
                default:
                    expr = root.get(field).as(String.class);
                    break;
            }
            Predicate p = cb.like(cb.lower(expr), "%" + value.toString().toLowerCase() + "%");
            predicates.add(p);
        }            
        return predicates;
    }

    private Order processOrder(CriteriaBuilder cb, Root<App> root, Ordre ordre) {
        Path<String> path;
        switch (ordre.getId()) {
            case "propietats_app.id":
                {
                    Join<App, Provincia> provincia = root.join("propietats_app");
                    path = provincia.get("id");
                    break;
                }
            default:
                path = root.get(ordre.getId());
                break;
        }
        if (ordre.getTipusOrdre().equals(TipusOrdre.ASCENDENT)) {
            return cb.asc(path);
        }
        return cb.desc(path);
    }

    @Override
    public List<App> findAll() {
        return entityManager.createNamedQuery("App.findAll", App.class).getResultList();
    }
    
    @Override
    public List<App> findAll(int start, int size, Ordre ordre, Map<String, Object> filters) {
        //logger.log(Level.INFO, getClass().getName() + ".findAll(" + String.valueOf(start) + "," + String.valueOf(size) + ")");
        logger.log(Level.INFO, "{0}.findAll(start: {1}, size: {2}, filters: {3})", new Object[]{getClass().getName(), String.valueOf(start), String.valueOf(size), filters.toString()});
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<App> criteriaQuery = cb.createQuery(App.class);
        Root<App> root = criteriaQuery.from(App.class);
        CriteriaQuery<App> select = criteriaQuery.select(root);
        
        if (filters != null && filters.size() > 0) {
            List<Predicate> predicates = processFilters(cb, root, filters);
            if (predicates.size() > 0) {
                criteriaQuery.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
            }           
        }
        if (ordre != null) {
            criteriaQuery.orderBy(processOrder(cb, root, ordre));
        }
            
        TypedQuery<App> query = entityManager.createQuery(select);
        query.setFirstResult(start);
        query.setMaxResults(size);
        List<App> list = query.getResultList();
        logger.log(Level.INFO, "OJITO: {0}", query.unwrap(JpaQuery.class).getDatabaseQuery().getJPQLString());
        logger.log(Level.INFO, "OJITO: {0}", query.unwrap(JpaQuery.class).getDatabaseQuery().getSQLString());
        return list;
    }

    @Override
    public int findAllTotalCount(Map<String, Object> filters) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = cb.createQuery(Long.class);
        Root<App> root = criteriaQuery.from(App.class);
        CriteriaQuery<Long> select = criteriaQuery.select(cb.count(root));

        if (filters != null && filters.size() > 0) {
            List<Predicate> predicates = processFilters(cb, root, filters);
            if (predicates.size() > 0) {
                criteriaQuery.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
            }
        }
        //entityManager.createQuery(select).(JpaQuery.class) .getDatabaseQuery().getSQLString();
        
        Long count = entityManager.createQuery(select).getSingleResult();
        logger.log(Level.INFO, "{0}.findFilteredTotalCount() => {1} ", new Object[]{getClass().getName(), count.toString()});
        return count.intValue();
    }

    

}
