package es.zarca.covellog.interfaces.facade.contrato.internal.assembler;

import es.zarca.covellog.domain.model.contrato.ContratoLineaRO;
import es.zarca.covellog.interfaces.facade.albaran.internal.assembler.AlbaranMiniDtoAssembler;
import es.zarca.covellog.interfaces.facade.base.internal.assembler.EstadoDtoAssembler;
import es.zarca.covellog.interfaces.facade.contrato.dto.ContratoLineaDto;
import es.zarca.covellog.interfaces.facade.empresas.facade.internal.assembler.DobleObservacionAssembler;
import es.zarca.covellog.interfaces.facade.stock.internal.assembler.StockAssembler;
import es.zarca.covellog.interfaces.facade.stock.internal.assembler.TipoProductoAssembler;
import es.zarca.covellog.interfaces.facade.transporte.internal.assembler.TransporteDtoAssembler;
import es.zarca.covellog.interfaces.facade.ubicaciones.internal.assembler.UbicacionAssembler;
import java.util.ArrayList;
import java.util.List;
import es.zarca.covellog.domain.model.contrato.ContratoLineaEstado;

/**
 *
 * @author francisco
 */
public class ContratoLineaDtoAssembler {
    
    static public List<ContratoLineaDto> toDto(List<ContratoLineaRO> lista) {
        List<ContratoLineaDto> listaDto = new ArrayList<>();
        if (lista != null) {
            lista.forEach(model -> {
                listaDto.add(toDto(model));
            });
        }
        return listaDto;
    }
    
    static public ContratoLineaDto toDto(ContratoLineaRO linea) {
        if (linea == null) {
            return null;
        }
        ContratoLineaDto dto = new ContratoLineaDto();
        dto.setId(linea.getId());
        dto.setTipoOperacion(ContratoTipoOperacionDtoAssembler.toDto(linea.getTipoOperacion()));
        dto.setNumSerie(linea.getNumSerie());
        dto.setTipoProducto(TipoProductoAssembler.toDto(linea.getTipoProducto()));
        dto.setUbicacion(UbicacionAssembler.toDto(linea.getUbicacion()));
        dto.setLote(linea.getLote());        
        dto.setConcepto(linea.getConcepto());
        dto.setStock(StockAssembler.toDto(linea.getProducto()));
        dto.setImporte(linea.getImporte());
        dto.setImporteTotal(linea.getImporteTotal());
        dto.setFechaEntregaPrevista(linea.getFechaEntregaPrevista());
        dto.setFechaEntrega(linea.getFechaEntrega());
        //System.err.println("COJONES xx: " + linea.getAlmacen().getNombre());
        System.err.println("COJONES YY: " + linea.getAlbaranEntrega());
        System.err.println("COJONES ZZ: " + AlbaranMiniDtoAssembler.toDto(linea.getAlbaranEntrega()));
        dto.setEntregaAlbaran(AlbaranMiniDtoAssembler.toDto(linea.getAlbaranEntrega()));
        dto.setFechaDevolucionPrevista(linea.getFechaDevolucionPrevista());
        dto.setFechaDevolucion(linea.getFechaDevolucion()); 
        dto.setRecogidaAlbaran(AlbaranMiniDtoAssembler.toDto(linea.getAlbaranRecogida()));
        dto.setEntregaTransporte(TransporteDtoAssembler.toDto(linea.getTransporteEntregaConPrecio().getTransporte()));
        dto.setEntregaImporte(linea.getTransporteEntregaConPrecio().getImporte());
        dto.setRecogidaTransporte(TransporteDtoAssembler.toDto(linea.getTransporteRecogidaConPrecio().getTransporte()));
        dto.setRecogidaImporte(linea.getTransporteRecogidaConPrecio().getImporte());
        dto.setObservaciones(DobleObservacionAssembler.toDto(linea.getObservaciones()));
        dto.setComplementos(ContratoLineaComplementoDtoAssembler.toDto(linea.getComplementos()));
        dto.setComplementosImporte(linea.getComplementosImporte());
        dto.setGastosAdicionales(ContratoLineaGastoAdicionalDtoAssembler.toDto(linea.getGastosAdicionales()));
        dto.setGastosAdicionalesImporte(linea.getGastosAdicionalesImporte());
        dto.setEstado(EstadoDtoAssembler.toDto(linea.getEstado()));
        return dto;
    }
    
}