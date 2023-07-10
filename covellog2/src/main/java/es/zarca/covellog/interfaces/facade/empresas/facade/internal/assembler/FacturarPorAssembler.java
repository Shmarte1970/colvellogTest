package es.zarca.covellog.interfaces.facade.empresas.facade.internal.assembler;

import es.zarca.covellog.domain.model.empresa.cliente.FacturarPor;
import es.zarca.covellog.interfaces.facade.empresas.facade.dto.FacturarPorDto;

/**
 *
 * @author francisco
 */
public class FacturarPorAssembler {
    
    static public FacturarPorDto toDto(FacturarPor facturarPor) {
        if (facturarPor == null) {
            return null;
        }
        FacturarPorDto dto = new FacturarPorDto();
        dto.setId(facturarPor.getId());
        dto.setNombre(facturarPor.name());
        return dto;
    }
    
    static public FacturarPor toModel(FacturarPorDto facturarPor) {
        if (facturarPor == null) {
            return null;
        }
        return FacturarPor.parse(facturarPor.getId());
    }
    
}