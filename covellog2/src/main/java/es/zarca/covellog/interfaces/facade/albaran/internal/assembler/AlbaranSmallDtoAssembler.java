package es.zarca.covellog.interfaces.facade.albaran.internal.assembler;

import es.zarca.covellog.domain.model.albaran.Albaran;
import es.zarca.covellog.domain.model.albaran.IAlbaranLinea;
import es.zarca.covellog.interfaces.facade.albaran.dto.AlbaranSmallDto;
import es.zarca.covellog.interfaces.facade.base.internal.assembler.EstadoDtoAssembler;
import es.zarca.covellog.interfaces.facade.empresas.facade.internal.assembler.DobleObservacionAssembler;
import es.zarca.covellog.interfaces.facade.ubicaciones.internal.assembler.UbicacionAssembler;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author francisco
 */
public class AlbaranSmallDtoAssembler {
    
    static public List<AlbaranSmallDto> toDto(List<? extends Albaran> lista) {
        List<AlbaranSmallDto> listaDto = new ArrayList<>();
        if (lista != null) {
            lista.forEach(model -> {
                listaDto.add(toDto(model));
            });
        }
        return listaDto;
    }
    
    static public AlbaranSmallDto toDto(Albaran albaran) {
        if (albaran == null) {
            return null;
        }
        AlbaranSmallDto dto = new AlbaranSmallDto();
        dto.setId(albaran.getId());
        dto.setCodigoAlbaran(albaran.getFriendlyId());
        dto.setBooking(albaran.getBooking());
        dto.setFecha(albaran.getFecha());
        dto.setTipo(AlbaranTipoDtoAssembler.toDto(albaran.getTipo()));                
        dto.setClienteId(albaran.getCliente().getId());
        dto.setClienteNombre(albaran.getClienteNombre());
        dto.setClienteCif(albaran.getClienteCif());
        dto.setTransporte(InfoTransporteDtoAssembler.toDto(albaran.getInfoTransporte()));
        dto.setOrigen(UbicacionAssembler.toDto(albaran.getOrigen()));
        dto.setDestino(UbicacionAssembler.toDto(albaran.getDestino()));
        dto.setObservaciones(DobleObservacionAssembler.toDto(albaran.getObservaciones()));
        dto.setEstado(EstadoDtoAssembler.toDto(albaran.getEstado()));
        dto.setEstadoFecha(albaran.getEstadoFecha());
        String productos = "";
        for (IAlbaranLinea linea : albaran.getLineas()) {
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