/*
 * Proyecto Covellog2
 * 
 * Este archivo ha sido creado para ZARCA y no se puede usar sin su consentimiento.
 */
package es.zarca.covellog.application.factures.internal;

import es.zarca.covellog.application.factures.FacturaService;
import es.zarca.covellog.application.factures.exception.FacturaNotExistException;
import es.zarca.covellog.domain.model.factures.factura.Factura;
import es.zarca.covellog.domain.model.factures.factura.FacturaRepository;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
@Stateless
public class DefaultFacturaService implements FacturaService {

    @Inject
    private FacturaRepository facturaRepository;
    
  
    @Override
    public void altaFactura(Factura factura) {
        //Factura factura = new Factura();
        facturaRepository.store(factura);
    }

    @Override
    public void modificarFactura(Factura factura) {
        //Factura factura = facturaRepository.find(factura.getId());
        //if (factura == null) {
        //    throw new FacturaNotExistException(factura.getId());
        //}
        facturaRepository.store(factura);
    }

    @Override
    public void baixaFactura(Integer id) {
        Factura factura = facturaRepository.find(id);
        if (factura == null) {
            throw new FacturaNotExistException(id);
        }
        facturaRepository.remove(factura);
    }

}
