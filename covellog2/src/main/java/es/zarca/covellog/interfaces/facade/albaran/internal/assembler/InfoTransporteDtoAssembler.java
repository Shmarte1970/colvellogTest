package es.zarca.covellog.interfaces.facade.albaran.internal.assembler;

import es.zarca.covellog.domain.model.albaran.InfoTransporte;
import es.zarca.covellog.interfaces.facade.albaran.dto.InfoTransporteDto;
import es.zarca.covellog.interfaces.facade.empresas.facade.internal.assembler.DobleObservacionAssembler;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author francisco
 */
public class InfoTransporteDtoAssembler {
    
    static public List<InfoTransporteDto> toDto(List<InfoTransporte> lista) {
        List<InfoTransporteDto> listaDto = new ArrayList<>();
        if (lista != null) {
            lista.forEach(model -> {
                listaDto.add(toDto(model));
            });
        }
        return listaDto;
    }
    
    static public InfoTransporteDto toDto(InfoTransporte infoTransporte) {
        if (infoTransporte == null) {
            return null;
        }
        InfoTransporteDto dto = new InfoTransporteDto();
        dto.setTransportistaNombre(infoTransporte.getTransportistaNombre());
        dto.setNombre(infoTransporte.getNombre());
        dto.setCapacidad(infoTransporte.getCapacidad());
        dto.setObservaciones(DobleObservacionAssembler.toDto(infoTransporte.getObservaciones()));
        return dto;
    }
    
}