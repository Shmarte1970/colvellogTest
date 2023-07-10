/*
 * Proyecto Covellog2
 * 
 * Este archivo ha sido creado para ZARCA y no se puede usar sin su consentimiento.
 */
package es.zarca.covellog.application.adreces.internal;

import es.zarca.covellog.domain.model.adreces.provincia.Provincia;
import es.zarca.covellog.domain.model.adreces.provincia.ProvinciaRepository;
import javax.ejb.Stateless;
import javax.inject.Inject;
import es.zarca.covellog.application.adreces.ProvinciaService;
import es.zarca.covellog.application.adreces.exception.PaisNotExistException;
import es.zarca.covellog.application.adreces.exception.ProvinciaCodiNotUniqueException;
import es.zarca.covellog.application.adreces.exception.ProvinciaCodiPostalNotUniqueException;
import es.zarca.covellog.application.adreces.exception.ProvinciaNomNotUniqueException;
import es.zarca.covellog.application.adreces.exception.ProvinciaNotExistException;
import es.zarca.covellog.application.adreces.form.AltaProvinciaForm;
import es.zarca.covellog.application.adreces.form.BaixaProvinciaForm;
import es.zarca.covellog.application.adreces.form.EditarProvinciaForm;
import es.zarca.covellog.domain.model.adreces.pais.Pais;
import es.zarca.covellog.domain.model.adreces.pais.PaisRepository;
import es.zarca.covellog.infrastructure.persistence.jpa.JpaPaisRepository;
import java.util.logging.Logger;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
@Stateless
public class DefaultProvinciaService implements ProvinciaService {
    
    @Inject
    private ProvinciaRepository provinciaRepository;
    @Inject
    private PaisRepository paisRepository;
    
    private static final Logger LOGGER = Logger.getLogger(JpaPaisRepository.class.getName());
    
    @Override
    public void altaProvincia(AltaProvinciaForm form) {
        throw new ProvinciaNotExistException(333);
      /*  Pais pais = paisRepository.find(form.getIdPais());
        if (pais == null) {
            throw new PaisNotExistException(form.getIdPais());
        }
        pais.altaProvincia(form.getNom(), form.getCodi(), form.getCodiPostal());
        paisRepository.store(pais);       */
    }

    @Override
    public void editarProvincia(EditarProvinciaForm form) {
        Pais pais = paisRepository.find(form.getIdPais());
        if (pais == null) {
            throw new PaisNotExistException(form.getIdPais());
        }
        Provincia provincia = provinciaRepository.find(form.getId());
        if (provincia == null) {
            throw new ProvinciaNotExistException(form.getId());
        }
        Provincia provinciaAmbMateixNom = provinciaRepository.findByNom(form.getNom());
        if ((provinciaAmbMateixNom != null) && (!provinciaAmbMateixNom.equals(provincia))) {
            throw new ProvinciaNomNotUniqueException(provincia, provinciaAmbMateixNom);
        }
        Provincia provinciaAmbMateixCodi = provinciaRepository.findByCodi(form.getCodi());
        if ((provinciaAmbMateixCodi != null) && (!provinciaAmbMateixCodi.equals(provincia))) {
            throw new ProvinciaCodiNotUniqueException(provincia, provinciaAmbMateixCodi);
        }
        Provincia provinciaAmbMateixCodiPostal = provinciaRepository.findByCodiPostal(form.getCodiPostal());
        if ((provinciaAmbMateixCodiPostal != null) && (!provinciaAmbMateixCodiPostal.equals(provincia))) {
            throw new ProvinciaCodiPostalNotUniqueException(provincia, provinciaAmbMateixCodiPostal);
        }            
        provincia.setNom(form.getNom());
        provincia.setCodi(form.getCodi());
        provincia.setCodiPostal(form.getCodiPostal());
        provincia.setPais(pais);
        provinciaRepository.store(provincia);
    }
        

    @Override
    public void baixaProvincia(BaixaProvinciaForm form) {
        Provincia provincia = provinciaRepository.find(form.getId());
        if (provincia == null) {
            throw new ProvinciaNotExistException(form.getId());
        }
        provinciaRepository.remove(provincia);
    }

}
