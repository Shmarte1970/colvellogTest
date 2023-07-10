package es.zarca.covellog.interfaces.facade.stock.dto.historico;

import es.zarca.covellog.interfaces.facade.contrato.dto.ContratoDto;

/**
 *
 * @author francisco
 */
public class StockHistoricoContratoDto extends StockHistoricoDto {
    private ContratoDto contrato;

    public ContratoDto getContrato() {
        return contrato;
    }

    public void setContrato(ContratoDto contrato) {
        this.contrato = contrato;
    }
    
}