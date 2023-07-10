package es.zarca.covellog.interfaces.facade.empresas.facade.internal.assembler;

import es.zarca.covellog.domain.model.empresa.formapago.FormaPagoLinea;
import es.zarca.covellog.interfaces.facade.empresas.facade.dto.FormaPagoLineaDto;

/**
 *
 * @author francisco
 */
public class FormaPagoLineaAssembler {
    
    public FormaPagoLineaDto toDto(FormaPagoLinea lineaFormaPago) {
        if (lineaFormaPago == null) {
            return null;
        }   
        FormaPagoLineaDto lineaFormaPagoDto = new FormaPagoLineaDto();
        lineaFormaPagoDto.setNumLinea(lineaFormaPago.getNumLinea());
        lineaFormaPagoDto.setPorcentaje(lineaFormaPago.getPorcentaje());
        lineaFormaPagoDto.setTipoPago(lineaFormaPago.getTipoPago());
        lineaFormaPagoDto.setTipoVencimiento(lineaFormaPago.getTipoVencimiento());
        lineaFormaPagoDto.setDiaPago(lineaFormaPago.getDiaPago());
        lineaFormaPagoDto.setCuenta(lineaFormaPago.getCuenta());
        return lineaFormaPagoDto;
    }
    
    public FormaPagoLineaDto toDto(FormaPagoLineaDto lineaFormaPago) {
        if (lineaFormaPago == null) {
            return null;
        }   
        FormaPagoLineaDto lineaFormaPagoDto = new FormaPagoLineaDto();
        lineaFormaPagoDto.setNumLinea(lineaFormaPago.getNumLinea());
        lineaFormaPagoDto.setPorcentaje(lineaFormaPago.getPorcentaje());
        lineaFormaPagoDto.setTipoPago(lineaFormaPago.getTipoPago());
        lineaFormaPagoDto.setTipoVencimiento(lineaFormaPago.getTipoVencimiento());
        lineaFormaPagoDto.setDiaPago(lineaFormaPago.getDiaPago());
        lineaFormaPagoDto.setCuenta(lineaFormaPago.getCuenta());
        return lineaFormaPagoDto;
    }
    
    public FormaPagoLinea toModel(FormaPagoLineaDto lineaFormaPagoDto) {
        if (lineaFormaPagoDto == null) {
            return null;
        }   
        FormaPagoLinea lineaFormaPago = new FormaPagoLinea();
        lineaFormaPago.setNumLinea(lineaFormaPagoDto.getNumLinea());
        lineaFormaPago.setPorcentaje(lineaFormaPagoDto.getPorcentaje());
        lineaFormaPago.setTipoPago(lineaFormaPagoDto.getTipoPago());
        lineaFormaPago.setTipoVencimiento(lineaFormaPagoDto.getTipoVencimiento());
        lineaFormaPago.setDiaPago(lineaFormaPagoDto.getDiaPago());
        lineaFormaPago.setCuenta(lineaFormaPagoDto.getCuenta());
        return lineaFormaPago;
    }
    
    
}
