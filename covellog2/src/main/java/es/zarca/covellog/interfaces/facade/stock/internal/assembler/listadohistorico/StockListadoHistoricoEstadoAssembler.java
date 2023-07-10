package es.zarca.covellog.interfaces.facade.stock.internal.assembler.listadohistorico;

import es.zarca.covellog.interfaces.facade.stock.internal.assembler.historico.StockHistoricoEstadoAssembler;
import es.zarca.covellog.domain.model.stock.Stock;
import es.zarca.covellog.interfaces.facade.stock.dto.historico.StockHistoricoEstadoDto;
import es.zarca.covellog.interfaces.facade.stock.dto.listadohistorico.StockListadoHistoricoEstadosDto;
import es.zarca.covellog.interfaces.facade.stock.internal.assembler.EstadoAssembler;
import es.zarca.covellog.interfaces.facade.stock.internal.assembler.StockAssembler;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author francisco
 */
public class StockListadoHistoricoEstadoAssembler {
    
    static public List<StockListadoHistoricoEstadosDto> toDto(List<Stock> lista) {
        List<StockListadoHistoricoEstadosDto> listaDto = new ArrayList<>();
        if (lista != null) {
            lista.forEach(model -> {
                listaDto.add(toDto(model));
            });
        }
        return listaDto;
    }
    
    static public StockListadoHistoricoEstadosDto toDto(Stock value) {
        if (value == null) {
            return null;
        }
        StockListadoHistoricoEstadosDto dto = new StockListadoHistoricoEstadosDto();
        dto.setStock(StockAssembler.toDto(value));
        dto.setHistorico(StockHistoricoEstadoAssembler.toDto(value.getHistoricoEstados()));
        StockHistoricoEstadoDto actual = new StockHistoricoEstadoDto();        
        actual.setFechaInicio(value.getEstadoFecha());
        actual.setObservaciones(value.getEstadoObservaciones());
        actual.setEstado(EstadoAssembler.toDto(value.getEstado()));
        dto.getHistorico().add(actual);
        return dto;
    }
    
}