package es.zarca.covellog.comercial.interfaces.facade.oferta.internal.assembler;

import es.zarca.covellog.domain.model.contrato.ContratoLineaComplementoRO;
import es.zarca.covellog.interfaces.facade.contrato.dto.ContratoLineaComplementoDto;
import es.zarca.covellog.interfaces.facade.stock.internal.assembler.TipoProductoAssembler;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author francisco
 */
public class OfertaLineaComplementoDtoAssembler {
    
    static public List<ContratoLineaComplementoDto> toDto(List<ContratoLineaComplementoRO> lista) {
        List<ContratoLineaComplementoDto> listaDto = new ArrayList<>();
        if (lista != null) {
            lista.forEach(model -> {
                listaDto.add(toDto(model));
            });
        }
        return listaDto;
    }
    
    static public ContratoLineaComplementoDto toDto(ContratoLineaComplementoRO complemento) {
        if (complemento == null) {
            return null;
        }
        ContratoLineaComplementoDto dto = new ContratoLineaComplementoDto();
        dto.setId(complemento.getId());
        dto.setComplemento(TipoProductoAssembler.toDto(complemento.getTipoProducto()));
        dto.setConcepto(complemento.getConcepto());
        dto.setCantidad(complemento.getCantidad());
        dto.setImporte(complemento.getImporte());
        return dto;
    }
    
}