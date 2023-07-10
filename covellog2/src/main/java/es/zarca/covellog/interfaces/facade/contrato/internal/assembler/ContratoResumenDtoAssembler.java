package es.zarca.covellog.interfaces.facade.contrato.internal.assembler;

import es.zarca.covellog.domain.model.contrato.Contrato;
import es.zarca.covellog.domain.model.contrato.ContratoPagoEstado;
import es.zarca.covellog.interfaces.facade.contrato.dto.ContratoPagoEstadoDto;
import es.zarca.covellog.interfaces.facade.contrato.dto.ContratoResumenDto;
import es.zarca.covellog.interfaces.facade.contrato.dto.ContratoResumenParcialDto;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author francisco
 */
public class ContratoResumenDtoAssembler {
    
    static public List<ContratoResumenDto> toDto(List<Contrato> lista) {
        List<ContratoResumenDto> listaDto = new ArrayList<>();
        if (lista != null) {
            lista.forEach(model -> {
                listaDto.add(toDto(model));
            });
        }
        return listaDto;
    }
    
    static public ContratoResumenDto toDto(Contrato contrato) {
        ContratoResumenDto dto = new ContratoResumenDto();
        ContratoResumenParcialDto parcial = new ContratoResumenParcialDto();
        parcial.setTotal(contrato.getTotalAlquiler());
        parcial.setTotalBase(contrato.getTotalBaseAlquiler());
        parcial.setTotalComplementos(contrato.getTotalComplementosAlquiler());
        parcial.setTotalGastosAdicionales(contrato.getTotalGastosAdicionalesAlquiler());
        parcial.setTotalTransporte(contrato.getTotalTransporteAlquiler());        
        dto.setResumenAlquiler(parcial);
        
        parcial = new ContratoResumenParcialDto();
        parcial.setTotal(contrato.getTotalVenta());
        parcial.setTotalBase(contrato.getTotalBaseVenta());
        parcial.setTotalComplementos(contrato.getTotalComplementosVenta());
        parcial.setTotalGastosAdicionales(contrato.getTotalGastosAdicionalesVenta());
        parcial.setTotalTransporte(contrato.getTotalTransporteVenta());        
        dto.setResumenVenta(parcial);
        
        parcial = new ContratoResumenParcialDto();
        parcial.setTotal(contrato.getTotal());
        parcial.setTotalBase(contrato.getTotalBase());
        parcial.setTotalComplementos(contrato.getTotalComplementos());
        parcial.setTotalGastosAdicionales(contrato.getTotalGastosAdicionales());
        parcial.setTotalTransporte(contrato.getTotalTransporte());  
        dto.setResumenTotal(parcial);
        dto.setEstimacionMesesAlquiler(contrato.getPrevisionMesesAlquiler());
        
        return dto;
    }
    
}