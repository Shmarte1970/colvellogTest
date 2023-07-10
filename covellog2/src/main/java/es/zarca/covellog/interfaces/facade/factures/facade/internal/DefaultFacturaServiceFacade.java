/*
 * Proyecto Covellog2
 * 
 * Este archivo ha sido creado para ZARCA y no se puede usar sin su consentimiento.
 */
package es.zarca.covellog.interfaces.facade.factures.facade.internal;

import es.zarca.covellog.application.factures.FacturaService;
import es.zarca.covellog.domain.model.factures.factura.FacturaRepository;
import es.zarca.covellog.interfaces.facade.factures.facade.FacturaServiceFacade;
import es.zarca.covellog.interfaces.facade.factures.facade.dto.FacturaDTO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
@ApplicationScoped
public class DefaultFacturaServiceFacade implements FacturaServiceFacade, Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private FacturaService facturaService;
    @Inject
    private FacturaRepository facturaRepository;

    @Override
    public void crearFactura(FacturaDTO pais) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void editarFactura(FacturaDTO pais) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void eliminarFactura(FacturaDTO pais) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public FacturaDTO buscarPerId(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<FacturaDTO> llistarFactures() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<FacturaDTO> llistarFactures(int start, int size) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<FacturaDTO> llistarFactures(Map<String, Object> filters) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<FacturaDTO> llistarFactures(int start, int size, Map<String, Object> filters) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int llistarFacturesTotalCount() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int llistarFacturesTotalCount(Map<String, Object> filters) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
      
    

}
