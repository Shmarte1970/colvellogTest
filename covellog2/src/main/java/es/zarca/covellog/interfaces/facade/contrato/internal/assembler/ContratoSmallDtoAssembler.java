package es.zarca.covellog.interfaces.facade.contrato.internal.assembler;

import es.zarca.covellog.domain.model.contrato.Contrato;
import es.zarca.covellog.domain.model.contrato.ContratoLineaRO;
import es.zarca.covellog.infrastructure.util.DateUtil.DateUtil;
import es.zarca.covellog.interfaces.facade.base.internal.assembler.EstadoDtoAssembler;
import es.zarca.covellog.interfaces.facade.empresas.facade.internal.assembler.DobleObservacionAssembler;
import es.zarca.covellog.interfaces.facade.contrato.dto.ContratoSmallDto;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author francisco
 */
public class ContratoSmallDtoAssembler {
    
    static public List<ContratoSmallDto> toDto(List<Contrato> lista) {
        List<ContratoSmallDto> listaDto = new ArrayList<>();
        if (lista != null) {
            lista.forEach(model -> {
                listaDto.add(toDto(model));
            });
        }
        return listaDto;
    }
    
    static public ContratoSmallDto toDto(Contrato contrato) {
        if (contrato == null) {
            return null;
        }
        ContratoSmallDto dto = new ContratoSmallDto();
        dto.setId(contrato.getId());
        dto.setFriendlyId(contrato.getFriendlyId());
        dto.setFechaContrato(DateUtil.dateTimeToString(contrato.getFechaContrato()));
        dto.setObservaciones(DobleObservacionAssembler.toDto(contrato.getObservaciones()));
        dto.setClienteId(contrato.getCliente().getId());
        dto.setClienteNombre(contrato.getCliente().getAliasNombre());
        dto.setClienteCif(contrato.getCliente().getCif().getCifString());
        dto.setEstado(EstadoDtoAssembler.toDto(contrato.getEstado()));
        dto.setEstadoPago(EstadoDtoAssembler.toDto(contrato.getPagoEstado()));
        String productos = "";
        for (ContratoLineaRO linea : contrato.getLineas()) {
            if (!productos.isEmpty()) {
                productos += ", ";
            }
            if (linea.getNumSerie() == null) {
                productos += "S/A";
            } else {
                productos += linea.getNumSerie();
            }
            productos += "(" + linea.getTipoProducto().getId() + ")";
        }
        dto.setProductos(productos);
        return dto;
    }

}