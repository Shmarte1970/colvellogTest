package es.zarca.covellog.interfaces.facade.stock.internal.assembler.listadohistorico;

import es.zarca.covellog.interfaces.facade.stock.internal.assembler.historico.StockHistoricoPropietarioAssembler;
import es.zarca.covellog.domain.model.stock.Stock;
import es.zarca.covellog.interfaces.facade.empresas.facade.internal.assembler.EmpresaMiniDtoAssembler;
import es.zarca.covellog.interfaces.facade.stock.dto.historico.StockHistoricoPropietarioDto;
import es.zarca.covellog.interfaces.facade.stock.dto.listadohistorico.StockListadoHistoricoPropietariosDto;
import es.zarca.covellog.interfaces.facade.stock.internal.assembler.StockAssembler;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author francisco
 */
public class StockListadoHistoricoPropiedadesAssembler {
    
    static public List<StockListadoHistoricoPropietariosDto> toDto(List<Stock> lista) {
        List<StockListadoHistoricoPropietariosDto> listaDto = new ArrayList<>();
        if (lista != null) {
            lista.forEach(model -> {
                listaDto.add(toDto(model));
            });
        }
        return listaDto;
    }
    
    static public StockListadoHistoricoPropietariosDto toDto(Stock value) {
        if (value == null) {
            return null;
        }
        StockListadoHistoricoPropietariosDto dto = new StockListadoHistoricoPropietariosDto();
        dto.setStock(StockAssembler.toDto(value));
        dto.setHistorico(StockHistoricoPropietarioAssembler.toDto(value.getHistoricoPropietarios()));
        StockHistoricoPropietarioDto actual = new StockHistoricoPropietarioDto();        
        actual.setFechaInicio(value.getPropietarioFecha());
        actual.setObservaciones(value.getPropietarioObservaciones());
        if (value.getPropietarioTags() != null) {
            actual.setTags(value.getPropietarioTags().getString());
        }
        actual.setPropietario(EmpresaMiniDtoAssembler.toDto(value.getPropietario()));
        dto.getHistorico().add(actual);
        return dto;
    }
    
}