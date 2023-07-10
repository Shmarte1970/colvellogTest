package es.zarca.covellog.interfaces.facade.empresas.facade.internal.assembler;

import es.zarca.covellog.domain.model.empresa.cliente.DetalleContratacion;
import es.zarca.covellog.interfaces.facade.empresas.facade.dto.DetalleContratacionDto;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author francisco
 */
public class DetalleContratacionAssembler {
    
    static public DetalleContratacionDto toDto(DetalleContratacion detalleContratacion) {
        if (detalleContratacion == null) {
            return null;
        }
        DetalleContratacionDto dto = new DetalleContratacionDto();
        dto.setTransporteEntregaAdelantado(detalleContratacion.getTransporteEntregaAdelantado());
        dto.setTransporteRecogidaAdelantado(detalleContratacion.getTransporteRecogidaAdelantado());
        dto.setMontajeAdelantado(detalleContratacion.getMontajeAdelantado());
        dto.setDesmontajeAdelantado(detalleContratacion.getDesmontajeAdelantado());
        dto.setMesesFianza(detalleContratacion.getMesesFianza());
        dto.setTipoFacturacion(TipoFacturacionAssembler.toDto(detalleContratacion.getTipoFacturacion()));
        dto.setFacturarPor(FacturarPorAssembler.toDto(detalleContratacion.getFacturarPor()));
        dto.setDescuento(detalleContratacion.getDescuento());
        return dto;
    }
    
    static public List<DetalleContratacionDto> toDto(List<DetalleContratacion> detalleContratacions) {
        if (detalleContratacions == null) {
            return null;
        }
        List<DetalleContratacionDto> dtos = new ArrayList<>();
        detalleContratacions.forEach((detalleContratacion) -> {
            dtos.add(toDto(detalleContratacion));
        });
        return dtos;
    }
    
    static public DetalleContratacion toModel(DetalleContratacionDto detalleContratacion) {
        if (detalleContratacion == null) {
            return null;
        }
        return new DetalleContratacion(
            detalleContratacion.isTransporteEntregaAdelantado(), 
            detalleContratacion.isTransporteRecogidaAdelantado(), 
            detalleContratacion.isMontajeAdelantado(), 
            detalleContratacion.isDesmontajeAdelantado(),  
            detalleContratacion.getMesesFianza(), 
            TipoFacturacionAssembler.toModel(detalleContratacion.getTipoFacturacion()),
            FacturarPorAssembler.toModel(detalleContratacion.getFacturarPor()),
            detalleContratacion.getDescuento()
        );
    }
    
}
