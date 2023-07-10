package es.zarca.covellog.comercial.interfaces.facade.oferta.internal.assembler;

import es.zarca.covellog.domain.model.contrato.ContratoLineaGastoAdicionalRO;
import es.zarca.covellog.interfaces.facade.contrato.dto.ContratoLineaGastoAdicionalDto;
import es.zarca.covellog.interfaces.facade.stock.internal.assembler.TipoProductoAssembler;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author francisco
 */
public class OfertaLineaGastoAdicionalDtoAssembler {
    
    static public List<ContratoLineaGastoAdicionalDto> toDto(List<ContratoLineaGastoAdicionalRO> lista) {
        List<ContratoLineaGastoAdicionalDto> listaDto = new ArrayList<>();
        if (lista != null) {
            lista.forEach(model -> {
                listaDto.add(toDto(model));
            });
        }
        return listaDto;
    }
    
    static public ContratoLineaGastoAdicionalDto toDto(ContratoLineaGastoAdicionalRO gastoAdicional) {
        if (gastoAdicional == null) {
            return null;
        }
        ContratoLineaGastoAdicionalDto dto = new ContratoLineaGastoAdicionalDto();
        dto.setId(gastoAdicional.getId());
        dto.setGastoAdicional(TipoProductoAssembler.toDto(gastoAdicional.getTipoProducto()));
        dto.setConcepto(gastoAdicional.getConcepto());
        dto.setCantidad(gastoAdicional.getCantidad());
        dto.setImporte(gastoAdicional.getImporte());
        return dto;
    }
    
}