/*
 * Proyecto Covellog2
 * 
 * Este archivo ha sido creado para ZARCA y no se puede usar sin su consentimiento.
 */
package es.zarca.covellog.infrastructure.persistence.jpa;

import es.zarca.covellog.domain.model.crm.oportunitat.EstatOportunitat;
import es.zarca.covellog.domain.model.crm.oportunitat.EstatOportunitatRepository;

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
public class JpaEstatOportunitatRepository implements EstatOportunitatRepository, Serializable {
    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(JpaEstatOportunitatRepository.class.getName());

    @PersistenceContext(unitName = "covellog2-pu")
    private EntityManager entityManager;

    @Override
    public EstatOportunitat find(String id) {
        EstatOportunitat estatOportunitat;
        try {
            estatOportunitat = entityManager.createNamedQuery("EstatOportunitat.findById", EstatOportunitat.class).setParameter("id", id).getSingleResult();
        } catch (NoResultException e) {
            logger.log(Level.FINE, "Find called on non-existant tracking ID.", e);
            estatOportunitat = null;
        }
        return estatOportunitat;
    }
   
    @Override
    public List<EstatOportunitat> findAll(){      
        Query query = entityManager.createNamedQuery("EstatOportunitat.findAll", EstatOportunitat.class);
        List <EstatOportunitat> list = query.getResultList();
        return list;
    }

    @Override
    public int findAllTotalCount() {
        logger.log(Level.INFO, "{0}.findAllTotalCount()", getClass().getName());
        Query query = entityManager.createQuery("Select count(o.id) From EstatOportunitat o");
        return ((Long)query.getSingleResult()).intValue();
    }

}
