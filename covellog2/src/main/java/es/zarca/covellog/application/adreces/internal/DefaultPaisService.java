/*
 * Proyecto Covellog2
 * 
 * Este archivo ha sido creado para ZARCA y no se puede usar sin su consentimiento.
 */
package es.zarca.covellog.application.adreces.internal;

import es.zarca.covellog.domain.model.adreces.pais.Pais;
import es.zarca.covellog.domain.model.adreces.pais.PaisRepository;
import javax.ejb.Stateless;
import javax.inject.Inject;
import es.zarca.covellog.application.adreces.PaisService;
import es.zarca.covellog.application.adreces.exception.PaisCodiNotUniqueException;
import es.zarca.covellog.application.adreces.exception.PaisNomNotUniqueException;
import es.zarca.covellog.application.adreces.exception.PaisNotExistException;
import es.zarca.covellog.application.adreces.exception.ProvinciaNotExistException;
import es.zarca.covellog.application.adreces.form.AltaPaisForm;
import es.zarca.covellog.application.adreces.form.BaixaPaisForm;
import es.zarca.covellog.application.adreces.form.EditarPaisForm;
import es.zarca.covellog.domain.model.adreces.pais.CodiIsoPais2;
import java.util.Collection;
import javax.annotation.security.DeclareRoles;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
@DeclareRoles({"ADMIN"})
@Stateless
public class DefaultPaisService implements PaisService {

    @Inject
    private PaisRepository paisRepository;
    
    private void comprovarRestriccionsIntegritat(Pais pais) throws PaisNomNotUniqueException, PaisCodiNotUniqueException {
        Collection<Pais> paisos = paisRepository.findAll();
        //Fa un recorregut per tots el països per veure si la modificació crearà 
        for(Pais paisDB : paisos) {
            if (!paisDB.getId().equals(pais.getId())) {           
                if (paisDB.getNom().equals(pais.getNom())) {
                    throw new PaisNomNotUniqueException(pais, paisDB);
                }
                if (paisDB.getCodiIso2().equals(pais.getCodiIso2())) {
                    throw new PaisCodiNotUniqueException(pais, paisDB);
                }
            }
        }
    }
    
    @Override
    public void altaPais(AltaPaisForm form) {
        Pais pais = new Pais(new CodiIsoPais2(form.getCodiIso2()), form.getNom());
        comprovarRestriccionsIntegritat(pais);
        paisRepository.store(pais);
    }

    @Override
    public void modificarPais(EditarPaisForm form) {
        Pais pais = paisRepository.find(form.getId());
        if (pais == null) {
            throw new PaisNotExistException(form.getId());
        }
        comprovarRestriccionsIntegritat(pais);
        pais.setCodiIso2(new CodiIsoPais2(form.getCodiIso2()));
        pais.setNom(form.getNom());
        paisRepository.store(pais);
    }

    @Override
    public void baixaPais(BaixaPaisForm form) {
        Pais pais = paisRepository.find(form.getId());
        if (pais == null) {
            throw new PaisNotExistException(form.getId());
        }
        paisRepository.remove(pais);
    }

}
