/*
 * Proyecto Covellog2
 * 
 * Este archivo ha sido creado para ZARCA y no se puede usar sin su consentimiento.
 */
package es.zarca.covellog.application.adreces.internal;

import es.zarca.covellog.domain.model.adreces.poblacio.Poblacio;
import es.zarca.covellog.domain.model.adreces.poblacio.PoblacioRepository;
import javax.ejb.Stateless;
import javax.inject.Inject;
import es.zarca.covellog.application.adreces.PoblacioService;
import es.zarca.covellog.application.adreces.exception.PoblacioNotExistException;
import es.zarca.covellog.application.adreces.exception.ProvinciaNotExistException;
import es.zarca.covellog.application.adreces.form.AltaPoblacioForm;
import es.zarca.covellog.application.adreces.form.BaixaPoblacioForm;
import es.zarca.covellog.application.adreces.form.EditarPoblacioForm;
import es.zarca.covellog.domain.model.adreces.provincia.Provincia;
import es.zarca.covellog.domain.model.adreces.provincia.ProvinciaRepository;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
@Stateless
public class DefaultPoblacioService implements PoblacioService {

    @Inject
    private PoblacioRepository poblacioRepository;
    @Inject
    private ProvinciaRepository provinciaRepository;
    
    @Override
    public void altaPoblacio(AltaPoblacioForm formulari) {
        System.out.println("*************** ALTA POBLACIO *****************************");
        Poblacio poblacio = new Poblacio();
        poblacio.setNom(formulari.getNom());
        Provincia provincia = provinciaRepository.find(formulari.getIdProvincia());
        if (provincia == null) {
            throw new ProvinciaNotExistException(formulari.getIdProvincia());
        }
        poblacio.setProvincia(provincia);
        poblacioRepository.store(poblacio);
    }

    @Override
    public void editarPoblacio(EditarPoblacioForm formulari) {
        Poblacio poblacio = poblacioRepository.find(formulari.getId());
        if (poblacio == null) {
            throw new PoblacioNotExistException(formulari.getId());
        }
        poblacio.setNom(formulari.getNom());
        Provincia provincia = provinciaRepository.find(formulari.getIdProvincia());
        if (provincia == null) {
            throw new ProvinciaNotExistException(formulari.getIdProvincia());
        }
        poblacio.setProvincia(provincia);
        poblacioRepository.store(poblacio);
    }

    @Override
    public void baixaPoblacio(BaixaPoblacioForm formulari) {
        Poblacio poblacio = poblacioRepository.find(formulari.getId());
        if (poblacio == null) {
            throw new PoblacioNotExistException(formulari.getId());
        }
        poblacioRepository.remove(poblacio);
    }

}
