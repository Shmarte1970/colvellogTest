package es.zarca.covellog.interfaces.facade.stock.internal.assembler.listadohistorico;

import es.zarca.covellog.interfaces.facade.stock.internal.assembler.historico.StockHistoricoFlotaAssembler;
import es.zarca.covellog.domain.model.stock.Stock;
import es.zarca.covellog.interfaces.facade.stock.dto.historico.StockHistoricoFlotaDto;
import es.zarca.covellog.interfaces.facade.stock.dto.listadohistorico.StockListadoHistoricoFlotasDto;
import es.zarca.covellog.interfaces.facade.stock.internal.assembler.FlotaAssembler;
import es.zarca.covellog.interfaces.facade.stock.internal.assembler.StockAssembler;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author francisco
 */
public class StockListadoHistoricoFlotasAssembler {
    
    static public List<StockListadoHistoricoFlotasDto> toDto(List<Stock> lista) {
        List<StockListadoHistoricoFlotasDto> listaDto = new ArrayList<>();
        if (lista != null) {
            lista.forEach(model -> {
                listaDto.add(toDto(model));
            });
        }
        return listaDto;
    }
    
    static public StockListadoHistoricoFlotasDto toDto(Stock value) {
        if (value == null) {
            return null;
        }
        StockListadoHistoricoFlotasDto dto = new StockListadoHistoricoFlotasDto();
        dto.setStock(StockAssembler.toDto(value));
        dto.setHistorico(StockHistoricoFlotaAssembler.toDto(value.getHistoricoFlotas()));
        StockHistoricoFlotaDto actual = new StockHistoricoFlotaDto();        
        actual.setFechaInicio(value.getFlotaFecha());
        actual.setObservaciones(value.getFlotaObservaciones());
        actual.setFlota(FlotaAssembler.toDto(value.getFlota()));
        dto.getHistorico().add(actual);
        return dto;
    }
    
}