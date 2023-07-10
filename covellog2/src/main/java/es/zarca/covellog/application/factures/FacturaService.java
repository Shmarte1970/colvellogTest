/*
 * Proyecto Covellog2
 * 
 * Este archivo ha sido creado para ZARCA y no se puede usar sin su consentimiento.
 */
package es.zarca.covellog.application.factures;

import es.zarca.covellog.domain.model.factures.factura.Factura;

/**
 *
 * @author Francisco Torralbo
 */
public interface FacturaService {
    void altaFactura(Factura factura);
    void modificarFactura(Factura factura);
    void baixaFactura(Integer id);   
}
