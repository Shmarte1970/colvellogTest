package es.zarca.covellog.interfaces.facade.empresas.facade.internal.assembler;

import es.zarca.covellog.domain.model.empresa.formapago.FormaPago;
import es.zarca.covellog.domain.model.empresa.formapago.FormaPagoLinea;
import es.zarca.covellog.interfaces.facade.empresas.facade.dto.FormaPagoDto;
import es.zarca.covellog.interfaces.facade.empresas.facade.dto.FormaPagoLineaDto;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author francisco
 */
public class FormaPagoAssembler {
    
    public static FormaPagoDto toDto(FormaPago formaPago) {
        if (formaPago == null) {
            return null;
        }
        FormaPagoLineaAssembler assembler = new FormaPagoLineaAssembler();
        List<FormaPagoLineaDto> formaPagoLineasDto = new ArrayList<>();
        formaPago.getLineas().forEach(lineaDto -> {
            formaPagoLineasDto.add(assembler.toDto(lineaDto));
        });
        return new FormaPagoDto(formaPagoLineasDto);
    }
    
    public static FormaPago toModel(FormaPagoDto formaPagoDto) {
        if (formaPagoDto == null) {
            return null;
        }
        FormaPagoLineaAssembler assembler = new FormaPagoLineaAssembler();
        List<FormaPagoLinea> formaPagoLineas = new ArrayList<>();
        formaPagoDto.getLineas().forEach(linea -> {
            formaPagoLineas.add(assembler.toModel(linea));
        });
        return new FormaPago(formaPagoLineas);
    }
}
