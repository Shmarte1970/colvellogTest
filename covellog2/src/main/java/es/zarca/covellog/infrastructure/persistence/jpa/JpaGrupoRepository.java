/*
 * Proyecto Covellog2
 * 
 * Este archivo ha sido creado para ZARCA y no se puede usar sin su consentimiento.
 */
package es.zarca.covellog.infrastructure.persistence.jpa;

import es.zarca.covellog.domain.model.app.Ordre;
import es.zarca.covellog.domain.model.app.TipusOrdre;
import es.zarca.covellog.domain.model.usuarios.Grupo;
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
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import es.zarca.covellog.domain.model.usuarios.GrupoRepository;
import es.zarca.covellog.infrastructure.persistence.exception.OrdenIncorrectoException;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
@ApplicationScoped
public class JpaGrupoRepository implements GrupoRepository, Serializable {
    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(JpaGrupoRepository.class.getName());

    @PersistenceContext(unitName = "covellog2-pu")
    private EntityManager entityManager;

    @Override
    public Grupo find(Integer id) {
        Grupo grupo;
        try {
            grupo = entityManager.createNamedQuery("Grupo.findById", Grupo.class).setParameter("id", id).getSingleResult();
        } catch (NoResultException e) {
            logger.log(Level.FINE, "Find called on non-existant tracking ID.", e);
            grupo = null;
        }
        return grupo;
    }
    
    @Override
    public List<Grupo> findAll() {
        Query query = entityManager.createNamedQuery("Grupo.findAll", Grupo.class);
        List <Grupo> list = query.getResultList();
        return list;
    }
    
    @Override
    public List<Grupo> findAll(int start, int size){      
        Query query = entityManager.createNamedQuery("Grupo.findAll", Grupo.class);
        query.setFirstResult(start);
        query.setMaxResults(size);
        List <Grupo> list = query.getResultList();
        return list;
    }
    
    @Override
    public int findAllTotalCount() {
        Query query = entityManager.createQuery("Select count(o.id) From Grupo o");
        return ((Long)query.getSingleResult()).intValue();
    }
    
    private List<Predicate> processFilters(CriteriaBuilder cb, Root<Grupo> root, Map<String, Object> filters) {
        List<Predicate> predicates = new ArrayList<>();
        for (Map.Entry<String, Object> entry : filters.entrySet()) {
            String field = entry.getKey();
            Object value = entry.getValue();
            if (value == null) {
                continue;
            }
            Expression<String> expr, expr2;
            Predicate p;
            switch (field) {
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

    private List<Order> processOrder(CriteriaBuilder cb, Root<Grupo> root, Ordre ordre) {
        List<Order> orderList = new ArrayList();
        switch (ordre.getId()) {
            case "nombre":
                orderList.add(getOrderItem(cb, ordre, root.get(ordre.getId())));
                break;
            case "obs":
                orderList.add(getOrderItem(cb, ordre, root.get(ordre.getId())));
                break;    
            default:
                throw new OrdenIncorrectoException("Grupo", ordre);
        }
        return orderList;
    }

    @Override
    public List<Grupo> findAll(int start, int size, Ordre ordre, Map<String, Object> filters) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Grupo> criteriaQuery = cb.createQuery(Grupo.class);
        Root<Grupo> root = criteriaQuery.from(Grupo.class);
        CriteriaQuery<Grupo> select = criteriaQuery.select(root);
        
        if (filters != null && filters.size() > 0) {
            List<Predicate> predicates = processFilters(cb, root, filters);
            if (predicates.size() > 0) {
                criteriaQuery.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
            }           
        }
        if (ordre != null) {
            criteriaQuery.orderBy(processOrder(cb, root, ordre));
        }
            
        TypedQuery<Grupo> query = entityManager.createQuery(select);
        query.setFirstResult(start);
        query.setMaxResults(size);
        List<Grupo> list = query.getResultList();
        return list;
    }

    @Override
    public int findAllTotalCount(Map<String, Object> filters) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = cb.createQuery(Long.class);
        Root<Grupo> root = criteriaQuery.from(Grupo.class);
        CriteriaQuery<Long> select = criteriaQuery.select(cb.count(root));
        if (filters != null && filters.size() > 0) {
            List<Predicate> predicates = processFilters(cb, root, filters);
            if (predicates.size() > 0) {
                criteriaQuery.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
            }
        }    
        Long count = entityManager.createQuery(select).getSingleResult();
        return count.intValue();
    }

    @Override
    public void store(Grupo usuari) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void remove(Grupo usuari) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
