package es.zarca.covellog.interfaces.facade.stock.internal.assembler.historico;

import es.zarca.covellog.domain.model.stock.StockHistoricoUbicacion;
import es.zarca.covellog.interfaces.facade.stock.dto.historico.StockHistoricoUbicacionDto;
import es.zarca.covellog.interfaces.facade.ubicaciones.internal.assembler.UbicacionAssembler;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author francisco
 */
public class StockHistoricoUbicacionAssembler {
    
    static public List<StockHistoricoUbicacionDto> toDto(List<StockHistoricoUbicacion> lista) {
        List<StockHistoricoUbicacionDto> listaDto = new ArrayList<>();
        if (lista != null) {
            lista.forEach(model -> {
                listaDto.add(toDto(model));
            });
        }
        return listaDto;
    }
    
    static public StockHistoricoUbicacionDto toDto(StockHistoricoUbicacion value) {
        if (value == null) {
            return null;
        }
        StockHistoricoUbicacionDto dto = new StockHistoricoUbicacionDto();
        dto.setFechaInicio(value.getFechaInicio());
        dto.setFechaFin(value.getFechaFin());
        dto.setObservaciones(value.getObservaciones());
        dto.setUbicacion(UbicacionAssembler.toDto(value.getUbicacion()));
        return dto;
    }
    
}