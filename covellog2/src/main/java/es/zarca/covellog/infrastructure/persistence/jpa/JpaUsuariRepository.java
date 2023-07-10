package es.zarca.covellog.infrastructure.persistence.jpa;

import es.zarca.covellog.domain.model.app.Ordre;
import es.zarca.covellog.domain.model.app.TipusOrdre;
import es.zarca.covellog.domain.model.usuarios.Usuario;
import es.zarca.covellog.infrastructure.persistence.exception.PersistenceException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
import javax.validation.ConstraintViolationException;
import es.zarca.covellog.domain.model.usuarios.UsuarioRepository;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
@ApplicationScoped
public class JpaUsuariRepository implements UsuarioRepository, Serializable {
    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(JpaUsuariRepository.class.getName());

    @PersistenceContext(unitName = "covellog2-pu")
    private EntityManager entityManager;

    @Override
    public Usuario find(Integer id) {
        Usuario usuario;
        try {
            usuario = entityManager.createNamedQuery("Usuario.findById", Usuario.class).setParameter("id", id).getSingleResult();
        } catch (NoResultException e) {
            usuario = null;
        }
        return usuario;
    }
    
    @Override
    public Usuario findByUsername(String username) {
        Usuario usuario;
        try {
            usuario = entityManager.createNamedQuery("Usuario.findByUsername", Usuario.class).setParameter("username", username).getSingleResult();
        } catch (NoResultException e) {
            usuario = null;
        }
        return usuario;
    }

    @Override
    public void store(Usuario usuario) {
        try {
            entityManager.persist(usuario);
        } catch (ConstraintViolationException ex) {
            throw new PersistenceException(ex);
        }
    }
        
    @Override
    public void remove(Usuario usuario) {
        try {
            entityManager.remove(entityManager.merge(usuario));
        } catch (ConstraintViolationException ex) {
            throw new PersistenceException(ex);
        }
    }
    
    @Override
    public List<Usuario> findAll(){      
        Query query = entityManager.createNamedQuery("Usuario.findAll", Usuario.class);
        List <Usuario> list = query.getResultList();
        return list;
    }
    
    @Override
    public List<Usuario> findAll(int start, int size){      
        Query query = entityManager.createNamedQuery("Usuario.findAll", Usuario.class);
        query.setFirstResult(start);
        query.setMaxResults(size);
        List <Usuario> list = query.getResultList();
        return list;
    }

    @Override
    public int findAllTotalCount() {
        Query query = entityManager.createQuery("Select count(o.id) From Usuario o");
        return ((Long)query.getSingleResult()).intValue();
    }
       
    private List<Predicate> processFilters(CriteriaQuery query, CriteriaBuilder cb, Root<Usuario> root, Map<String, Object> filters) {
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
                case "grupo.nombre":
                  /*  Subquery<Grupo> grupoSubquery = query.subquery(Grupo.class);
                    Root<Grupo> grupo = grupoSubquery.from(Grupo.class);
                    expr = grupo.<String>get("nombre").as(String.class);
                    ListJoin<Grupo, Usuario> subGrupos = grupo.join(Grupo_.usuarios);
                    grupoSubquery.select(grupo)//subquery selection
                                .where(
                                    cb.and(
                                        cb.like(cb.lower(expr), "%" + value.toString().toLowerCase() + "%"), 
                                        cb.equal(root.get(Usuario_.id), subGrupos.get(Usuario_.id))
                                    )
                                );
                    //main query selection
                    p = cb.exists(grupoSubquery);*/
                    expr = root.get(field).as(String.class);
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
    
    private Order getOrderItem(CriteriaBuilder cb, Ordre ordre, Expression<String> path) {
        if (ordre.getTipusOrdre().equals(TipusOrdre.ASCENDENT)) {
            return cb.asc(path);
        }
        return cb.desc(path);
    }

    private List<Order> processOrder(CriteriaBuilder cb, Root<Usuario> root, Ordre ordre) {
        Path<String> path;
        List<Order> orderList = new ArrayList();
        switch (ordre.getId()) {
            default:
                orderList.add(getOrderItem(cb, ordre, root.get(ordre.getId())));
                break;
        }
        return orderList;
    }
    
    @Override
    public List<Usuario> findAll( Ordre ordre, Map<String, Object> filters) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Usuario> criteriaQuery = cb.createQuery(Usuario.class);
        Root<Usuario> root = criteriaQuery.from(Usuario.class);
        CriteriaQuery<Usuario> select = criteriaQuery.select(root);
        
        if (filters != null && filters.size() > 0) {
            List<Predicate> predicates = processFilters(criteriaQuery, cb, root, filters);
            if (predicates.size() > 0) {
                criteriaQuery.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
            }           
        }
        TypedQuery<Usuario> query = entityManager.createQuery(select);
        List<Usuario> list = query.getResultList();
        return list; 
    }

    @Override
    public List<Usuario> findAll(int start, int size, Ordre ordre, Map<String, Object> filters) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Usuario> criteriaQuery = cb.createQuery(Usuario.class);
        Root<Usuario> root = criteriaQuery.from(Usuario.class);
        CriteriaQuery<Usuario> select = criteriaQuery.select(root);
        
        if (filters != null && filters.size() > 0) {
            List<Predicate> predicates = processFilters(criteriaQuery, cb, root, filters);
            if (predicates.size() > 0) {
                criteriaQuery.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
            }           
        }
        if (ordre != null) {
            criteriaQuery.orderBy(processOrder(cb, root, ordre));
        }
            
        TypedQuery<Usuario> query = entityManager.createQuery(select);
        query.setFirstResult(start);
        query.setMaxResults(size);
        List<Usuario> list = query.getResultList();
        
        return list;
    }

    @Override
    public int findAllTotalCount(Map<String, Object> filters) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = cb.createQuery(Long.class);
        Root<Usuario> root = criteriaQuery.from(Usuario.class);
        CriteriaQuery<Long> select = criteriaQuery.select(cb.count(root));

        if (filters != null && filters.size() > 0) {
            List<Predicate> predicates = processFilters(criteriaQuery, cb, root, filters);
            if (predicates.size() > 0) {
                criteriaQuery.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
            }
        }
        Long count = entityManager.createQuery(select).getSingleResult();
        return count.intValue();
    }

    

}
