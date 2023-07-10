package es.zarca.covellog.infrastructure.persistence.jpa;

import es.zarca.covellog.domain.model.empresa.EmpresaTipoRol;
import es.zarca.covellog.domain.model.empresa.EmpresaTipoRolRepository;
import es.zarca.covellog.infrastructure.log.Capa;
import es.zarca.covellog.infrastructure.log.FunctionLog;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
@ApplicationScoped
public class JpaEmpresaTipoRolRepository implements EmpresaTipoRolRepository, Serializable {
    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = Logger.getLogger(JpaEmpresaTipoRolRepository.class.getName());

    @PersistenceContext(unitName = "covellog2-pu")
    private EntityManager entityManager;

    @Override
    public EmpresaTipoRol find(Integer id) {
        EmpresaTipoRol empresaTipoRol;
        try {
            empresaTipoRol = entityManager.createNamedQuery("EmpresaTipoRol.findById", EmpresaTipoRol.class).setParameter("id", id).getSingleResult();
        } catch (NoResultException e) {
            empresaTipoRol = null;
        }
        return empresaTipoRol;
    }
    
    @Override
    public List<EmpresaTipoRol> findAll(){      
        Query query = entityManager.createNamedQuery("EmpresaTipoRol.findAll", EmpresaTipoRol.class);
        List <EmpresaTipoRol> list = query.getResultList();
        return list;
    }

    @Override
    public int findAllTotalCount() {
        FunctionLog log = new FunctionLog(Capa.REPOSITORY);
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = cb.createQuery(Long.class);
        Root<EmpresaTipoRol> root = criteriaQuery.from(EmpresaTipoRol.class);
        CriteriaQuery<Long> select = criteriaQuery.select(cb.count(root));
        Long count = entityManager.createQuery(select).getSingleResult();
        log.finish();
        return count.intValue();
    }

}
