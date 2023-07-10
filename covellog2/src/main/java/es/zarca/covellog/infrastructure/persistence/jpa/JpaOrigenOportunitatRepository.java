/*
 * Proyecto Covellog2
 * 
 * Este archivo ha sido creado para ZARCA y no se puede usar sin su consentimiento.
 */
package es.zarca.covellog.infrastructure.persistence.jpa;

import es.zarca.covellog.domain.model.crm.oportunitat.OrigenOportunitat;
import es.zarca.covellog.domain.model.crm.oportunitat.OrigenOportunitatRepository;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
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
public class JpaOrigenOportunitatRepository implements OrigenOportunitatRepository, Serializable {
    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(JpaOrigenOportunitatRepository.class.getName());

    @PersistenceContext(unitName = "covellog2-pu")
    private EntityManager entityManager;

    @Override
    public OrigenOportunitat find(String id) {
        OrigenOportunitat origenOportunitat;
        try {
            origenOportunitat = entityManager.createNamedQuery("OrigenOportunitat.findById", OrigenOportunitat.class).setParameter("id", id).getSingleResult();
        } catch (NoResultException e) {
            logger.log(Level.FINE, "Find called on non-existant tracking ID.", e);
            origenOportunitat = null;
        }
        return origenOportunitat;
    }
   
    @Override
    public List<OrigenOportunitat> findAll(){      
        Query query = entityManager.createNamedQuery("OrigenOportunitat.findAll", OrigenOportunitat.class);
        List <OrigenOportunitat> list = query.getResultList();
        return list;
    }

    @Override
    public int findAllTotalCount() {
        logger.log(Level.INFO, "{0}.findAllTotalCount()", getClass().getName());
        Query query = entityManager.createQuery("Select count(o.id) From OrigenOportunitat o");
        return ((Long)query.getSingleResult()).intValue();
    }

}
