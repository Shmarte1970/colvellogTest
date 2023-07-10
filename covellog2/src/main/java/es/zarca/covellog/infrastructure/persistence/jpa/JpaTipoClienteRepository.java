package es.zarca.covellog.infrastructure.persistence.jpa;

import es.zarca.covellog.domain.model.empresa.cliente.TipoCliente;
import es.zarca.covellog.domain.model.empresa.cliente.TipoClienteRepository;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
@ApplicationScoped
public class JpaTipoClienteRepository implements TipoClienteRepository, Serializable {
    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(JpaTipoClienteRepository.class.getName());

    @PersistenceContext(unitName = "covellog2-pu")
    private EntityManager entityManager;

    @Override
    public TipoCliente find(String id) {
        TipoCliente tipoCliente;
        try {
            tipoCliente = entityManager.createNamedQuery("TipoCliente.findById", TipoCliente.class).setParameter("id", id).getSingleResult();
        } catch (NoResultException e) {
            tipoCliente = null;
        }
        return tipoCliente;
    }
    
    @Override
    public List<TipoCliente> findAll() {      
        Query query = entityManager.createNamedQuery("TipoCliente.findAll", TipoCliente.class);
        List <TipoCliente> list = query.getResultList();
        return list;
    }

    @Override
    public int findAllTotalCount() {
        Query query = entityManager.createQuery("Select count(o.id) From TipoCliente o");
        return ((Long)query.getSingleResult()).intValue();
    }

}