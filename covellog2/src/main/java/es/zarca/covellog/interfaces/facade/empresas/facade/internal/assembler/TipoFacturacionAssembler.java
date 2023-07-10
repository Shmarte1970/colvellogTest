package es.zarca.covellog.interfaces.facade.empresas.facade.internal.assembler;

import es.zarca.covellog.domain.model.empresa.cliente.TipoFacturacion;
import es.zarca.covellog.interfaces.facade.empresas.facade.dto.FacturarPorDto;
import es.zarca.covellog.interfaces.facade.empresas.facade.dto.TipoFacturacionDto;

/**
 *
 * @author francisco
 */
public class TipoFacturacionAssembler {
    
    static public TipoFacturacionDto toDto(TipoFacturacion tipoFacturacion) {
        if (tipoFacturacion == null) {
            return null;
        }
        TipoFacturacionDto dto = new TipoFacturacionDto();
        dto.setId(tipoFacturacion.getId());
        dto.setNombre(tipoFacturacion.getNombre());
        return dto;
    }
    
    static public TipoFacturacion toModel(TipoFacturacionDto tipoFacturacion) {
        if (tipoFacturacion == null) {
            return null;
        }
        return TipoFacturacion.parse(tipoFacturacion.getId());
    }
    
}