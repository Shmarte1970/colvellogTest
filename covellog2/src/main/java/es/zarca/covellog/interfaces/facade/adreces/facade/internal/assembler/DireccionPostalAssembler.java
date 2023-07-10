package es.zarca.covellog.interfaces.facade.adreces.facade.internal.assembler;

import es.zarca.covellog.application.adreces.exception.PoblacioNotExistException;
import es.zarca.covellog.application.adreces.form.DireccionPostalForm;
import es.zarca.covellog.domain.model.adreces.adreça.CodigoPostal;
import es.zarca.covellog.domain.model.adreces.adreça.Direccion;
import es.zarca.covellog.domain.model.adreces.adreça.DireccionPostal;
import es.zarca.covellog.domain.model.adreces.poblacio.Poblacio;
import es.zarca.covellog.domain.model.adreces.poblacio.PoblacioRepository;
import es.zarca.covellog.infrastructure.assertions.ArgumentValidator;
import es.zarca.covellog.interfaces.facade.adreces.facade.dto.DireccionDto;
import es.zarca.covellog.interfaces.facade.adreces.facade.dto.DireccionPostalDto;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public class DireccionPostalAssembler {
    
    static public DireccionPostalDto toDto(DireccionPostalDto direccionPostal) {
        if (direccionPostal == null) {
            return null;
        }
        return new DireccionPostalDto(
            direccionPostal.getAtt(),
            direccionPostal.getDireccion(),
            direccionPostal.getDireccion2(),
            direccionPostal.getCodigoPostal(),
            direccionPostal.getPoblacion()
        );
    }
    
    static public DireccionPostalDto toDto(DireccionPostal direccionPostal) {
        if (direccionPostal == null) {
            return null;
        }
        return new DireccionPostalDto(
            direccionPostal.getAtt(),
            direccionPostal.getDireccion(),
            direccionPostal.getDireccion2(),
            direccionPostal.getCodigoPostal() == null ? null : direccionPostal.getCodigoPostal().getString(),
            PoblacioDtoAssembler.toDto(direccionPostal.getPoblacion())        
        );
    }
    
    static public DireccionPostalForm toForm(DireccionPostalDto direccionPostal) {
        if (direccionPostal == null) {
            return null;
        }
        return new DireccionPostalForm(
            direccionPostal.getAtt(),
            direccionPostal.getDireccion(),
            direccionPostal.getDireccion2(),
            direccionPostal.getCodigoPostal(),
            direccionPostal.getPoblacion().getId()        
        );
    }
    
    static public DireccionPostal toModel(DireccionPostalDto direccion, PoblacioRepository poblacionRepository) {
        if (direccion == null) {
            return null;
        }
        ArgumentValidator.required(direccion.getPoblacion(), "direccion-poblacion");
        Poblacio poblacion = poblacionRepository.find(direccion.getPoblacion().getId());
        if (poblacion == null) {
            throw new PoblacioNotExistException(direccion.getPoblacion().getId());
        }
        return new DireccionPostal(
            direccion.getAtt(),
            direccion.getDireccion(),
            direccion.getDireccion2(),
            direccion.getCodigoPostal() == null ? null : new CodigoPostal(direccion.getCodigoPostal()),
            poblacion
        );
    }
    
    static public DireccionPostal toModel(DireccionPostalForm direccion, PoblacioRepository poblacionRepository) {
        if (direccion == null) {
            return null;
        }
        ArgumentValidator.required(direccion.getPoblacionId(), "direccion-poblacion");
        Poblacio poblacion = poblacionRepository.find(direccion.getPoblacionId());
        if (poblacion == null) {
            throw new PoblacioNotExistException(direccion.getPoblacionId());
        }
        return new DireccionPostal(
            direccion.getAtt(),
            direccion.getDireccion(),
            direccion.getDireccion2(),
            direccion.getCodigoPostal() == null ? null : new CodigoPostal(direccion.getCodigoPostal()),
            poblacion
        );
    }
}
