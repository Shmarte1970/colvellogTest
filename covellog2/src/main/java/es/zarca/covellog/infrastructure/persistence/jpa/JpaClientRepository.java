/*
 * Proyecto Covellog2
 * 
 * Este archivo ha sido creado para ZARCA y no se puede usar sin su consentimiento.
 */
package es.zarca.covellog.infrastructure.persistence.jpa;

import es.zarca.covellog.domain.model.app.Ordre;
import es.zarca.covellog.domain.model.app.TipusOrdre;
import es.zarca.covellog.domain.model.clients.client.Client;
import es.zarca.covellog.domain.model.clients.client.ClientRepository;
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
public class JpaClientRepository implements ClientRepository, Serializable {
    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(JpaClientRepository.class.getName());

    @PersistenceContext(unitName = "covellog2-pu")
    private EntityManager entityManager;

    @Override
    public Client find(Integer id) {
        Crono crono = new Crono();
        Client client;
        try {
            client = entityManager.createNamedQuery("Client.findById", Client.class).setParameter("id", id).getSingleResult();
            logger.log(Level.FINE, "{0}.find(Integer id): Trovat país amb id={1} nom={2} ({3} ms).", new Object[]{getClass().getName(), id.toString(), client.getNom(), String.valueOf(crono.getMiliSegons())});
        } catch (NoResultException e) {
            logger.log(Level.FINE, "{0}.find(Integer id): No existeix el país amb id={1} ({2} ms).", new Object[]{getClass().getName(), id.toString(), String.valueOf(crono.getMiliSegons())});
            client = null;
        }
        return client;
    }
    
    @Override
    public void store(Client client) {
        Crono crono = new Crono();
        entityManager.persist(client);
        //logger.log(Level.FINE, "{0}.store(Client client): País guardat codi={0} nom={1} ({2} ms).", new Object[]{getClass().getName(), client.getCodiIso2(), client.getNom(), String.valueOf(crono.getMiliSegons())});
    }
        
    @Override
    public void remove(Client client) {
        Crono crono = new Crono();
        entityManager.remove(entityManager.merge(client));
       // logger.log(Level.FINE, "{0}.remove(Client client): País el·liminat codi={0} nom={1} ({2} ms).", new Object[]{getClass().getName(), client.getCodiIso2(), client.getNom(), String.valueOf(crono.getMiliSegons())});
    }
    
    @Override
    public List<Client> findAll() {
        Crono crono = new Crono();
        List<Client> list = entityManager.createNamedQuery("Client.findAll", Client.class).getResultList();
        logger.log(Level.FINE, "{0}.findAll(): Trovats {1} clients  ({2} ms)." ,new Object[]{getClass().getName(), String.valueOf(list.size()), String.valueOf(crono.getMiliSegons())});
        return list;
    }
    
    @Override
    public List<Client> findAll(int start, int size) {    
        Crono crono = new Crono();
        Query query = entityManager.createNamedQuery("Client.findAll", Client.class);
        query.setFirstResult(start);
        query.setMaxResults(size);
        List <Client> list = query.getResultList();
        logger.log(Level.FINE, "{0}.findAll({1}, {2}): Trovats {3} clients  ({4} ms)." ,new Object[]{getClass().getName(), String.valueOf(start), String.valueOf(size), String.valueOf(list.size()), String.valueOf(crono.getMiliSegons())});
        return list;
    }

    @Override
    public int findAllTotalCount() {
        Crono crono = new Crono();               
        Query query = entityManager.createQuery("Select count(c.id) From Client c");
        int count = ((Long)query.getSingleResult()).intValue();
        logger.log(Level.FINE, "{0}.findAllTotalCount(): count = {1} ({2} ms)." ,new Object[]{getClass().getName(), String.valueOf(count), String.valueOf(crono.getMiliSegons())});
        return count;
    }

    @Override
    public List<Client> findAll(Map<String, Object> filters) {
        Crono crono = new Crono();
        List<Client> list = entityManager.createNamedQuery("Client.findByFilter", Client.class).getResultList();
        logger.log(Level.FINE, "{0}.findAll(Map<String, Object> filters)({1}): Trovats {2} clients  ({3} ms)." ,new Object[]{getClass().getName(), filters.toString(), String.valueOf(list.size()), String.valueOf(crono.getMiliSegons())});
        return list;
    }

    @Override
    public List<Client> findAll(int start, int size, Ordre ordre, Map<String, Object> filters) {
        if (filters == null) {
            return findAll(start, size);
        }
        Crono crono = new Crono();
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Client> criteriaQuery = cb.createQuery(Client.class);
        Root<Client> root = criteriaQuery.from(Client.class);
        CriteriaQuery<Client> select = criteriaQuery.select(root);

        if (filters.size() > 0) {
            List<Predicate> predicates = new ArrayList<>();
            for (Map.Entry<String, Object> entry : filters.entrySet()) {
                String field = entry.getKey();
                Object value = entry.getValue();
                if (value == null) {
                    continue;
                }
                Expression<String> expr;
                expr = root.get(field).as(String.class);
                Predicate p = cb.like( cb.lower(expr), "%" + value.toString().toLowerCase() + "%");
                predicates.add(p);
            }
            if (predicates.size() > 0) {
                criteriaQuery.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
            }           
        }
        if (ordre != null) {
            if (ordre.getTipusOrdre().equals(TipusOrdre.ASCENDENT)) {
                criteriaQuery.orderBy(cb.asc(root.get(ordre.getId())));            
            } else if (ordre.getTipusOrdre().equals(TipusOrdre.DESCENDENT)) {
                criteriaQuery.orderBy(cb.desc(root.get(ordre.getId())));
            }
        }
        TypedQuery<Client> query = entityManager.createQuery(select);
        query.setFirstResult(start);
        query.setMaxResults(size);
        List<Client> list = query.getResultList();
        logger.log(Level.FINE, "{0}.findAll(int start, int size, Map<String, Object> filters)({1}, {2}, {3}): Trovats {4} clients ({5} ms)." ,
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
        Root<Client> root = criteriaQuery.from(Client.class);
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
                expr = root.get(field).as(String.class);
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
