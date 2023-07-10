package es.zarca.covellog.interfaces.web.empresas.cliente.controller;

import es.zarca.covellog.infrastructure.log.Capa;
import es.zarca.covellog.infrastructure.log.FunctionLog;
import es.zarca.covellog.interfaces.facade.empresas.facade.EmpresaServiceFacade;
import es.zarca.covellog.interfaces.facade.empresas.facade.dto.EmpresaDto;
import es.zarca.covellog.interfaces.web.adreces.controller.DireccionPostalEditController;
import es.zarca.covellog.interfaces.web.comerciales.controller.SelectorComercialesController;
import es.zarca.covellog.interfaces.web.empresas.cliente.exception.EmpresaNoTieneRolClientePresentationException;
import es.zarca.covellog.interfaces.web.empresas.cliente.model.ClienteContratacionModel;
import es.zarca.covellog.interfaces.facade.empresas.facade.dto.DetalleContratacionDto;
import es.zarca.covellog.interfaces.web.empresas.contactos.controller.SelectorContactosController;
import es.zarca.covellog.interfaces.web.empresas.controller.ModificarEmpresaController;
import es.zarca.covellog.interfaces.web.empresas.controller.ModificarEmpresaEstado;
import es.zarca.covellog.interfaces.web.helpers.JsfUtil;
import java.io.Serializable;
import java.util.logging.Logger;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author francisco
 */
@Named("clienteContratacionController")
@ViewScoped
public class ClienteContratacionController implements Serializable {
    private static final Logger LOGGER = Logger.getLogger(ClienteContratacionController.class.getName());
    
    @Inject
    EmpresaServiceFacade facade;
    @Inject
    SelectorComercialesController selectorComercialesController;
    @Inject
    SelectorContactosController selectorContactosController;
    @Inject
    DireccionPostalEditController direccionPostalEditController;
    @Inject
    ModificarEmpresaController modificarEmpresaController;
    
    private ClienteContratacionModel model;
    
    public ClienteContratacionController() {
    }
    
    public ClienteContratacionModel getModel() {
        return model;
    }

    public void setModel(ClienteContratacionModel model) {
        this.model = model;
    }
    
    public void guardar() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            facade.modificarInfoClienteContratacion(model.getEmpresaId(), model.getDetalleContratacion());
            modificarEmpresaController.setEstado(ModificarEmpresaEstado.CLIENTE_CONTRATACION);
            JsfUtil.addSuccessMessage("growl", "Condiciones cliente modificada.");
        } catch (Exception ex) {
            JsfUtil.addException(ex);
        } finally {
            log.finish();
        }
    }

    public void actualizarEmpresa(EmpresaDto empresa) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            if ((empresa == null) || (empresa.getCliente() == null)) {
                throw new EmpresaNoTieneRolClientePresentationException(empresa);
            }
            model = new ClienteContratacionModel();
            model.setEmpresaId(empresa.getId());
            if (empresa.getCliente().getDetalleFacturacion() == null) {
                model.setDetalleContratacion(new DetalleContratacionDto());
            } else {
                model.setDetalleContratacion(empresa.getCliente().getDetalleContratacion());
            }
            model.setTiposFacturacionPosibles(facade.geTiposFacturacionPosibles());
            model.setFacturarPorPosibles(facade.getFacturarPorPosibles());
        } catch (Exception e) {
            JsfUtil.addException(e);
        } finally {
            log.finish();
        }
    }

}