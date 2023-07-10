package es.zarca.covellog.interfaces.facade.empresas.facade.internal.assembler;

import es.zarca.covellog.application.empresas.form.DobleObservacionForm;
import es.zarca.covellog.domain.model.empresa.DobleObservacion;
import es.zarca.covellog.interfaces.facade.empresas.facade.dto.DobleObservacionDto;

/**
 *
 * @author francisco
 */
public class DobleObservacionAssembler {
    
    static public DobleObservacionDto toDto(DobleObservacion dobleObservacion) {
        if (dobleObservacion == null) {
            return new DobleObservacionDto();
        }   
        DobleObservacionDto dto = new DobleObservacionDto();
        dto.setObsInternas(dobleObservacion.getObsInternas());
        dto.setObsPublicas(dobleObservacion.getObsPublicas());
        return dto;
    }
    
    static public DobleObservacion toModel(DobleObservacionDto dobleObservacion) {
        if (dobleObservacion == null) {
            return null;
        }   
        return new DobleObservacion(dobleObservacion.getObsInternas(), dobleObservacion.getObsPublicas());
    }
    
    static public DobleObservacion toModel(DobleObservacionForm dobleObservacion) {
        if (dobleObservacion == null) {
            return null;
        }   
        return new DobleObservacion(dobleObservacion.getObsInternas(), dobleObservacion.getObsPublicas());
    }
   
}
