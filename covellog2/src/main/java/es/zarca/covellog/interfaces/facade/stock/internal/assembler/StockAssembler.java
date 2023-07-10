package es.zarca.covellog.interfaces.facade.stock.internal.assembler;

import es.zarca.covellog.domain.model.stock.Stock;
import es.zarca.covellog.interfaces.facade.empresas.facade.internal.assembler.DobleObservacionAssembler;
import es.zarca.covellog.interfaces.facade.stock.dto.StockDto;
import es.zarca.covellog.interfaces.facade.ubicaciones.internal.assembler.UbicacionAssembler;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author francisco
 */
public class StockAssembler {
    
    static public List<StockDto> toDto(List<Stock> lista) {
        List<StockDto> listaDto = new ArrayList<>();
        if (lista != null) {
            lista.forEach(model -> {
                listaDto.add(toDto(model));
            });
        }
        return listaDto;
    }
    
    static public StockDto toDto(Stock stock) {
        if (stock == null) {
            return null;
        }
        StockDto dto = new StockDto();
        dto.setId(stock.getId());
        dto.setNumSerie(stock.getNumeroSerie());
        dto.setTipoProducto(TipoProductoAssembler.toDto(stock.getTipoProducto()));
        dto.setLote(stock.getLote());
        dto.setFlota(FlotaAssembler.toDto(stock.getFlota()));
        //dto.setDetalleEstado(StockHistoricoEstadoAssembler.toDto(stock.getDetalleEstado()));
        dto.setEstado(EstadoAssembler.toDto(stock.getEstado()));
        dto.setEstadoFecha(stock.getEstadoFecha());
        dto.setEstadoFechaEfectiva(stock.getEstadoFechaEfectiva());
        if (stock.getEstadoContrato() != null) {
            dto.setEstadoContratoId(stock.getEstadoContrato().getId());
            dto.setEstadoContratoFriendlyId(stock.getEstadoContrato().getFriendlyId());
        }
        dto.setEstadoObservaciones(stock.getEstadoObservaciones());
        //dto.setDetalleCondicion(StockHistoricoCondicionAssembler.toDto(stock.getDetalleCondicion()));
        dto.setCondicion(CondicionAssembler.toDto(stock.getCondicion()));
        dto.setCondicionFecha(stock.getCondicionFecha());
        dto.setCondicionObservaciones(stock.getCondicionObservaciones());
        dto.setUbicacion(UbicacionAssembler.toDto(stock.getUbicacion()));
        
        //dto.setDetalleUbicacion(StockHistoricoUbicacionAssembler.toDto(stock.getDetalleUbicacion()));
        if (stock.getPropietario() != null) {
            dto.setPropietarioId(stock.getPropietario().getId());
            dto.setPropietario(stock.getPropietario().getAliasNombre());
        } else {
            dto.setPropietario("SIN PROPIETARIO");
        }
        dto.setPropietarioFecha(stock.getPropietarioFecha());
        dto.setPropietarioObservaciones(stock.getPropietarioObservaciones());
        dto.setObservaciones(DobleObservacionAssembler.toDto(stock.getObservaciones()));
        dto.setAlbaranEntregaFriendlyId(stock.getAlbaranEntrega() == null ? "Sin Albaran" : stock.getAlbaranEntrega().getFriendlyId());
        dto.setAlbaranRecogidaFriendlyId(stock.getAlbaranRecogida() == null ? "Sin Albaran" : stock.getAlbaranRecogida().getFriendlyId());
        return dto;
    }
    
}