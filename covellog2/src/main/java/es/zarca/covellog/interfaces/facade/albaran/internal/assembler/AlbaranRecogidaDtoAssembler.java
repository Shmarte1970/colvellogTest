package es.zarca.covellog.interfaces.facade.albaran.internal.assembler;

import es.zarca.covellog.domain.model.albaran.Albaran;
import es.zarca.covellog.interfaces.facade.albaran.dto.AlbaranDto;
import es.zarca.covellog.interfaces.facade.empresas.facade.internal.assembler.DobleObservacionAssembler;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author francisco
 */
public class AlbaranRecogidaDtoAssembler {
    
    static public List<AlbaranDto> toDto(List<? extends Albaran> lista) {
        List<AlbaranDto> listaDto = new ArrayList<>();
        if (lista != null) {
            lista.forEach(model -> {
                listaDto.add(toDto(model));
            });
        }
        return listaDto;
    }
    
    static public AlbaranDto toDto(Albaran albaran) {
        if (albaran == null) {
            return null;
        }
        AlbaranDto dto = new AlbaranDto();
        dto.setId(albaran.getId());
        dto.setCodigoAlbaran(albaran.getFriendlyId());
        dto.setFecha(albaran.getFecha());
        dto.setTransporte(InfoTransporteDtoAssembler.toDto(albaran.getInfoTransporte()));
       //dto.setOrigen(UbicacionAssembler.toDto(albaran.getOrigen()));
        //dto.setDestino(UbicacionAssembler.toDto(albaran.getDestino()));
        dto.setTextoAviso(albaran.getTextoAviso());
        dto.setObservaciones(DobleObservacionAssembler.toDto(albaran.getObservaciones()));
        dto.setLineas(AlbaranLineaDtoAssembler.toDto(albaran.getLineas()));
        return dto;
    }
    
}