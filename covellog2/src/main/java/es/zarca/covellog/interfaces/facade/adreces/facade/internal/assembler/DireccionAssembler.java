package es.zarca.covellog.interfaces.facade.adreces.facade.internal.assembler;

import es.zarca.covellog.application.adreces.exception.PoblacioNotExistException;
import es.zarca.covellog.application.adreces.form.DireccionForm;
import es.zarca.covellog.domain.model.adreces.adreça.CodigoPostal;
import es.zarca.covellog.domain.model.adreces.adreça.Direccion;
import es.zarca.covellog.domain.model.adreces.poblacio.Poblacio;
import es.zarca.covellog.domain.model.adreces.poblacio.PoblacioRepository;
import es.zarca.covellog.infrastructure.assertions.ArgumentValidator;
import es.zarca.covellog.interfaces.facade.adreces.facade.dto.DireccionDto;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public class DireccionAssembler {
    
    static public DireccionDto toDto(Direccion direccion) {
        if (direccion == null) {
            return null;
        }
        return new DireccionDto(
            direccion.getDireccion(),
            direccion.getDireccion2(),
            direccion.getCodigoPostal() == null ? null : direccion.getCodigoPostal().getString(),
            PoblacioDtoAssembler.toDto(direccion.getPoblacion())
        );
    }
    
    static public DireccionDto toDto(DireccionDto direccion) {
        if (direccion == null) {
            return null;
        }
        return new DireccionDto(
            direccion.getDireccion(),
            direccion.getDireccion2(),
            direccion.getCodigoPostal(),
            direccion.getPoblacion()
        );
    }
    
    static public Direccion toModel(DireccionDto direccion, PoblacioRepository poblacionRepository) {
        ArgumentValidator.required(direccion.getPoblacion(), "direccion-poblacion");
        Poblacio poblacion = poblacionRepository.find(direccion.getPoblacion().getId());
        if (poblacion == null) {
            throw new PoblacioNotExistException(direccion.getPoblacion().getId());
        }
        return new Direccion(
            direccion.getDireccion(),
            direccion.getDireccion2(),
            direccion.getCodigoPostal() == null ? null : new CodigoPostal(direccion.getCodigoPostal()),
            poblacion
        );
    }
    
    static public Direccion toModel(DireccionForm direccion, PoblacioRepository poblacionRepository) {
        ArgumentValidator.required(direccion.getPoblacionId(), "direccion-poblacion = " + direccion.getPoblacionId());
        Poblacio poblacion = poblacionRepository.find(direccion.getPoblacionId());
        if (poblacion == null) {
            throw new PoblacioNotExistException(direccion.getPoblacionId());
        }
        return new Direccion(
            direccion.getDireccion(),
            direccion.getDireccion2() == null ? "" : direccion.getDireccion2(),
            direccion.getCodigoPostal() == null ? null : new CodigoPostal(direccion.getCodigoPostal()),
            poblacion
        );
    }
}
