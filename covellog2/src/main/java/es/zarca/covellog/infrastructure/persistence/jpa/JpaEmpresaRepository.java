package es.zarca.covellog.infrastructure.persistence.jpa;

import es.zarca.covellog.application.empresas.exception.EmpresaNotExistException;
import es.zarca.covellog.domain.model.app.Ordre;
import es.zarca.covellog.domain.model.app.TipusOrdre;
import es.zarca.covellog.domain.model.empresa.Empresa;
import es.zarca.covellog.domain.model.empresa.EmpresaRepository;
import es.zarca.covellog.domain.model.empresa.EmpresaRol;
import es.zarca.covellog.domain.model.empresa.EmpresaTipoRol;
import es.zarca.covellog.infrastructure.log.Capa;
import es.zarca.covellog.infrastructure.log.FunctionLog;
import es.zarca.covellog.infrastructure.persistence.exception.PersistenceException;
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
import javax.persistence.criteria.Subquery;
import javax.validation.ConstraintViolationException;
import org.eclipse.persistence.jpa.JpaQuery;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
@ApplicationScoped
public class JpaEmpresaRepository implements EmpresaRepository, Serializable {
    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = Logger.getLogger(JpaEmpresaRepository.class.getName());

    @PersistenceContext(unitName = "covellog2-pu")
    private EntityManager entityManager;

    @Override
    public Empresa find(Integer id) {
        Empresa usuario;
        try {
            usuario = entityManager.createNamedQuery("Empresa.findById", Empresa.class).setParameter("id", id).getSingleResult();
        } catch (NoResultException e) {
            usuario = null;
        }
        return usuario;
    }
    
    @Override
    public Empresa findOrFail(Integer id) {
        Empresa empresa = find(id);
        if (empresa == null) {
            throw new EmpresaNotExistException(id);
        }
        return empresa;
    }
    
    @Override
    public Empresa findByNombre(String nombre) {
        Empresa usuario;
        try {
            usuario = entityManager.createNamedQuery("Empresa.findByNombre", Empresa.class).setParameter("nombre", nombre).getSingleResult();
        } catch (NoResultException e) {
            usuario = null;
        }
        return usuario;
    }

    @Override
    public Empresa store(Empresa empresa) {
        try {
            entityManager.persist(empresa);
            return empresa;
        } catch (ConstraintViolationException ex) {
            throw new PersistenceException(ex);
        }
    }
        
    @Override
    public void remove(Empresa empresa) {
        try {
            entityManager.remove(entityManager.merge(empresa));
        } catch (ConstraintViolationException ex) {
            throw new PersistenceException(ex);
        }
    }
    
    @Override
    public List<Empresa> findAll(){      
        Query query = entityManager.createNamedQuery("Empresa.findAll", Empresa.class);
        List <Empresa> list = query.getResultList();
        return list;
    }
    
    @Override
    public List<Empresa> findAll(int start, int size){      
        Query query = entityManager.createNamedQuery("Empresa.findAll", Empresa.class);
        query.setFirstResult(start);
        query.setMaxResults(size);
        List <Empresa> list = query.getResultList();
        return list;
    }
    
    @Override
    public String findMaxCodigoProveedor() {
        Query query = entityManager.createQuery("Select max(e.proveedor.codigoProveedor.codigo) From Empresa e");
        return (String)query.getSingleResult();
    }
    
    @Override
    public String findMaxCodigoCliente() {
        Query query = entityManager.createQuery("Select max(e.cliente.codigoCliente.codigo) From Empresa e");
        return (String)query.getSingleResult();
    }

    @Override
    public int findAllTotalCount() {
        return findAllTotalCount(null);
    }
       
    private List<Predicate> processFilters(CriteriaQuery query, CriteriaBuilder cb, Root<Empresa> root, Map<String, Object> filters) {
        FunctionLog log = new FunctionLog(Capa.REPOSITORY);
        List<Predicate> predicates = new ArrayList<>();
        for (Map.Entry<String, Object> entry : filters.entrySet()) {
            String filtroId = entry.getKey();
            Object value = entry.getValue();
            if (value == null) {
                continue;
            }
            Expression<String> expr;
            Predicate p = null;
            switch (filtroId) {
                case "roles":
                case "roles:CONTAINS":
                    expr = root.get("roles").get("nombre").as(String.class);
                    p = cb.like(cb.lower(expr), "%" + value.toString().toLowerCase() + "%");
                    break;
                case "tipo":
                case "tipo:CONTAINS":  
                    expr = root.get("roles").get("nombre").as(String.class);
                    p = cb.like(expr, value.toString());
                    break;
                case "tipos":
                case "tipos:CONTAINS":
                    expr = root.get("roles").get("nombre").as(String.class);
                    String[] tiposSeleccionados = (String[]) value;
                    List<Predicate> tipoPredicates = new ArrayList<>();
                    for (String tipoSeleccionado : tiposSeleccionados) {
                        tipoPredicates.add(cb.equal(cb.lower(expr), tipoSeleccionado.toLowerCase()));
                        LOGGER.log(Level.INFO, "XXXY: Filtro: {0} Value: {1}", new Object[]{filtroId, tipoSeleccionado});
                    }
                    p = cb.or(tipoPredicates.toArray((new Predicate[] {})));
                    break;
                case "id":
                case "id:CONTAINS":
                    expr = root.get("id").as(String.class);
                    p = cb.like(expr, "%" + value.toString() + "%");
                    LOGGER.log(Level.INFO, "XXXY: Filtro: {0} Value: {1}", new Object[]{filtroId, value.toString()});
                    break;
                case "cif":
                case "cif:CONTAINS":
                    expr = root.get("cif").get("cif").as(String.class);
                    p = cb.like(cb.lower(expr), "%" + value.toString().toLowerCase() + "%");
                    LOGGER.log(Level.INFO, "XXXY: Filtro: {0} Value: {1}", new Object[]{filtroId, value.toString()});
                    break;
                case "nombre":
                case "nombre:CONTAINS":
                    expr = root.get("nombre").as(String.class);
                    p = cb.like(cb.lower(expr), "%" + value.toString().toLowerCase() + "%");
                    LOGGER.log(Level.INFO, "XXXY: Filtro: {0} Value: {1}", new Object[]{filtroId, value.toString()});
                    break;
                case "alias":
                case "alias:CONTAINS":
                    expr = root.get("alias").as(String.class);
                    p = cb.like(cb.lower(expr), "%" + value.toString().toLowerCase() + "%");
                    LOGGER.log(Level.INFO, "XXXY: Filtro: {0} Value: {1}", new Object[]{filtroId, value.toString()});
                    break;
                case "palabrasClave":
                case "palabrasClave:CONTAINS":
                    expr = root.get("palabrasClave").get("palabrasClave").as(String.class);
                    p = cb.like(cb.lower(expr), "%" + value.toString().toLowerCase() + "%");
                    LOGGER.log(Level.INFO, "XXXY: Filtro: {0} Value: {1}", new Object[]{filtroId, value.toString()});
                    break;
                case "potencial.fechaBloqueo":
                case "potencial.fechaBloqueo:CONTAINS":
                    expr = root.get("potencial").get("fechaBloqueo").as(String.class);
                    p = cb.like(cb.lower(expr), "%" + value.toString().toLowerCase() + "%");
                    LOGGER.log(Level.INFO, "XXXY: Filtro: {0} Value: {1}", new Object[]{filtroId, value.toString()});
                    break;
                case "cliente.fechaBloqueo":
                case "cliente.fechaBloqueo:CONTAINS":
                    expr = root.get("cliente").get("fechaBloqueo").as(String.class);
                    p = cb.like(cb.lower(expr), "%" + value.toString().toLowerCase() + "%");
                    LOGGER.log(Level.INFO, "XXXY: Filtro: {0} Value: {1}", new Object[]{filtroId, value.toString()});
                    break;
                case "proveedor.fechaBloqueo":
                case "proveedor.fechaBloqueo:CONTAINS":
                    expr = root.get("proveedor").get("fechaBloqueo").as(String.class);
                    p = cb.like(cb.lower(expr), "%" + value.toString().toLowerCase() + "%");
                    LOGGER.log(Level.INFO, "XXXY: Filtro: {0} Value: {1}", new Object[]{filtroId, value.toString()});
                    break;
                case "cliente.tipo":
                case "cliente.tipo:CONTAINS":
                    expr = root.get("cliente").get("tipoCliente").get("nombre").as(String.class);
                    p = cb.like(cb.lower(expr), "%" + value.toString().toLowerCase() + "%");
                    LOGGER.log(Level.INFO, "XXXY: Filtro: {0} Value: {1}", new Object[]{filtroId, value.toString()});
                    break;
                case "cliente.codigoCliente":
                case "cliente.codigoCliente:CONTAINS":
                    expr = root.get("cliente").get("codigoCliente").get("codigo").as(String.class);
                    p = cb.like(cb.lower(expr), "%" + value.toString().toLowerCase() + "%");
                    LOGGER.log(Level.INFO, "XXXY: Filtro: {0} Value: {1}", new Object[]{filtroId, value.toString()});
                    break;
                case "proveedor.codigoProveedor":
                case "proveedor.codigoProveedor:CONTAINS":
                    expr = root.get("proveedor").get("codigoProveedor").get("codigo").as(String.class);
                    p = cb.like(cb.lower(expr), "%" + value.toString().toLowerCase() + "%");
                    LOGGER.log(Level.INFO, "XXXY: Filtro: {0} Value: {1}", new Object[]{filtroId, value.toString()});
                    break;
                case "contactos":
                case "contactos:CONTAINS":
                    expr = root.get("contactos").get("nombre").as(String.class);
                    p = cb.like(cb.lower(expr), "%" + value.toString().toLowerCase() + "%");
                    LOGGER.log(Level.INFO, "XXXY: Filtro: {0} Value: {1}", new Object[]{filtroId, value.toString()});
                    break;
                case "direccionFiscal":
                case "direccionFiscal:CONTAINS":
                    List<Predicate> direccionPredicates = new ArrayList<>();
                    expr = root.get("direccionFiscal").get("direccion").as(String.class);
                    direccionPredicates.add(cb.like(cb.lower(expr), "%" + value.toString().toLowerCase() + "%"));
                    expr = root.get("direccionFiscal").get("direccion2").as(String.class);
                    direccionPredicates.add(cb.like(cb.lower(expr), "%" + value.toString().toLowerCase() + "%"));
                    expr = root.get("direccionFiscal").get("codigoPostal").get("codigo").as(String.class);
                    direccionPredicates.add(cb.like(cb.lower(expr), "%" + value.toString().toLowerCase() + "%"));
                    expr = root.get("direccionFiscal").get("poblacion").get("nom").as(String.class);
                    direccionPredicates.add(cb.like(cb.lower(expr), "%" + value.toString().toLowerCase() + "%"));
                    p = cb.or(direccionPredicates.toArray((new Predicate[] {})));
                    break;
                default:
                    LOGGER.log(Level.SEVERE, "Filtro Incorrecto: Filtro: {0} Value: {1}", new Object[]{filtroId, value.toString()});
            }
            if (p != null) predicates.add(p);
        }
        log.finish();        
        return predicates;
    }
    
    private Order getOrderItem(CriteriaBuilder cb, Ordre ordre, Expression<String> path) {
        if (ordre.getTipusOrdre().equals(TipusOrdre.ASCENDENT)) {
            return cb.asc(path);
        }
        return cb.desc(path);
    }

    private List<Order> processOrder(CriteriaBuilder cb, Root<Empresa> root, Ordre ordre) {
        Path<String> path;
        List<Order> orderList = new ArrayList();
        switch (ordre.getId()) {
            case "cliente.codigoCliente":
                orderList.add(getOrderItem(cb, ordre, root.get("cliente").get("codigoCliente")));
                break;
            case "cliente.tipo":
                orderList.add(getOrderItem(cb, ordre, root.get("cliente").get("tipoCliente").get("nombre")));
                break;
            case "cliente.fechaBloqueo":
                orderList.add(getOrderItem(cb, ordre, root.get("cliente").get("fechaBloqueo")));
                break;
            case "proveedor.codigoProveedor":
                orderList.add(getOrderItem(cb, ordre, root.get("proveedor").get("codigoProveedor")));
                break;
            case "proveedor.fechaBloqueo":
                orderList.add(getOrderItem(cb, ordre, root.get("proveedor").get("fechaBloqueo")));
                break;
            case "potencial.fechaBloqueo":
                orderList.add(getOrderItem(cb, ordre, root.get("potencial").get("fechaBloqueo")));
                break;
            default:
                orderList.add(getOrderItem(cb, ordre, root.get(ordre.getId())));
                break;
        }
        return orderList;
    }
    
    @Override
    public List<Empresa> findAll(Ordre ordre, Map<String, Object> filters) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Empresa> criteriaQuery = cb.createQuery(Empresa.class);
        Root<Empresa> root = criteriaQuery.from(Empresa.class);
        CriteriaQuery<Empresa> select = criteriaQuery.select(root);
        
        if (filters != null && filters.size() > 0) {
            List<Predicate> predicates = processFilters(criteriaQuery, cb, root, filters);
            if (predicates.size() > 0) {
                criteriaQuery.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
            }           
        }
        TypedQuery<Empresa> query = entityManager.createQuery(select);
        List<Empresa> list = query.getResultList();
        return list; 
    }

    @Override
    public List<Empresa> findAll(int start, int size, Ordre ordre, Map<String, Object> filters) {
        FunctionLog log = new FunctionLog(Capa.REPOSITORY);
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Empresa> criteriaQuery = cb.createQuery(Empresa.class);
        Root<Empresa> root = criteriaQuery.from(Empresa.class);
        CriteriaQuery<Empresa> select = criteriaQuery.select(root);
        if (filters != null && filters.size() > 0) {
            List<Predicate> predicates = processFilters(criteriaQuery, cb, root, filters);
            if (predicates.size() > 0) {
                criteriaQuery.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
            }           
        }
        if (ordre != null) {
            criteriaQuery.orderBy(processOrder(cb, root, ordre));
        }
        criteriaQuery.distinct(true);
        TypedQuery<Empresa> query = entityManager.createQuery(select);
        query.setFirstResult(start);
        query.setMaxResults(size);
        List<Empresa> list = query.getResultList();
        LOGGER.log(Level.INFO, "QUERY: {0}", query.unwrap(JpaQuery.class).getDatabaseQuery().getSQLString());
        log.finish();
        return list;
    }

    @Override
    public int findAllTotalCount(Map<String, Object> filters) {
        FunctionLog log = new FunctionLog(Capa.REPOSITORY);
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = cb.createQuery(Long.class);
        Root<Empresa> root = criteriaQuery.from(Empresa.class);
        CriteriaQuery<Long> select = criteriaQuery.select(cb.countDistinct(root));
        //Query query = entityManager.createQuery("Select count(o.id) From Empresa o");
        
        if (filters != null && filters.size() > 0) {
            List<Predicate> predicates = processFilters(criteriaQuery, cb, root, filters);
            if (predicates.size() > 0) {
                criteriaQuery.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
            }
        }
        
        Long count = entityManager.createQuery(select).getSingleResult();
        log.finish();
        return count.intValue();
    }

   /* @Override
    public Map<String, List<Empresa>> findAll(int start, int size, Ordre ordre, Map<String, Object> filters, String filter) {
        FunctionLog log = new FunctionLog(Capa.REPOSITORY);
        Map<String, List<Empresa>> map = new HashMap<>();
        map.put("nombre", findAll());
        map.put("direccion postal", findAll());
        map.put("detalle facturacion", findAll());
        log.finish();
        return map;
    }*/

    @Override
    public List<Filtro> getFiltrosPosibles() {
        FunctionLog log = new FunctionLog(Capa.REPOSITORY);
        List<Filtro> lista = new ArrayList<>();
        lista.add(new Filtro("id", Operacion.CONTAINS));
        lista.add(new Filtro("cif", Operacion.CONTAINS));
        lista.add(new Filtro("nombre", Operacion.CONTAINS));
        lista.add(new Filtro("tipo", Operacion.CONTAINS));
        lista.add(new Filtro("alias", Operacion.CONTAINS));
        lista.add(new Filtro("palabrasClave", Operacion.CONTAINS));
        lista.add(new Filtro("contactos", Operacion.CONTAINS));
        lista.add(new Filtro("direccionFiscal", Operacion.CONTAINS));
        lista.add(new Filtro("potencial.fechaBloqueo", Operacion.CONTAINS));
        lista.add(new Filtro("cliente.fechaBloqueo", Operacion.CONTAINS));
        lista.add(new Filtro("proveedor.fechaBloqueo", Operacion.CONTAINS));
        lista.add(new Filtro("cliente.tipo", Operacion.CONTAINS));
        lista.add(new Filtro("cliente.codigoCliente", Operacion.CONTAINS));
        lista.add(new Filtro("proveedor.codigoProveedor", Operacion.CONTAINS));
        lista.forEach(filtro -> {
            LOGGER.log(Level.INFO, "Filtro: {0}", filtro);
        });
        log.finish();
        return lista;
    }

    @Override
    public Map<String, List<Empresa>> findAll(int start, int size, Ordre ordre, Map<String, Object> filters, String filter) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
