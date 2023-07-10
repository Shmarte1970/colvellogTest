/*
 * Proyecto Covellog2
 * 
 * Este archivo ha sido creado para ZARCA y no se puede usar sin su consentimiento.
 */
package es.zarca.covellog.infrastructure.persistence.jpa;

import es.zarca.covellog.domain.model.comercials.divisiocomercial.DivisioComercial;
import es.zarca.covellog.domain.model.comercials.divisiocomercial.DivisioComercialRepository;

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
public class JpaDivisioComercialRepository implements DivisioComercialRepository, Serializable {
    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(JpaDivisioComercialRepository.class.getName());

    @PersistenceContext(unitName = "covellog2-pu")
    private EntityManager entityManager;

    @Override
    public DivisioComercial find(String id) {
        DivisioComercial divisioComercial;
        try {
            divisioComercial = entityManager.createNamedQuery("DivisioComercial.findById", DivisioComercial.class).setParameter("id", id).getSingleResult();
        } catch (NoResultException e) {
            logger.log(Level.FINE, "Find called on non-existant tracking ID.", e);
            divisioComercial = null;
        }
        return divisioComercial;
    }
   
    @Override
    public List<DivisioComercial> findAll(){      
        Query query = entityManager.createNamedQuery("DivisioComercial.findAll", DivisioComercial.class);
        List <DivisioComercial> list = query.getResultList();
        return list;
    }

    @Override
    public int findAllTotalCount() {
        logger.log(Level.INFO, "{0}.findAllTotalCount()", getClass().getName());
        Query query = entityManager.createQuery("Select count(o.id) From DivisioComercial o");
        return ((Long)query.getSingleResult()).intValue();
    }

}
