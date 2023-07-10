/*
 * Proyecto Covellog2
 * 
 * Este archivo ha sido creado para ZARCA y no se puede usar sin su consentimiento.
 */
package es.zarca.covellog.interfaces.facade.factures.facade.internal.assembler;

import es.zarca.covellog.domain.model.factures.factura.Factura;
import es.zarca.covellog.interfaces.facade.factures.facade.dto.FacturaDTO;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public class FacturaDtoAssembler {
    
    public FacturaDTO toDto(Factura factura) {
        FacturaDTO dto = new FacturaDTO();
        return dto;
    }
}
