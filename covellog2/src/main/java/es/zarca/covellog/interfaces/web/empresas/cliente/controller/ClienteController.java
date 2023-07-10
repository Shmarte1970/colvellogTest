package es.zarca.covellog.interfaces.web.empresas.cliente.controller;

import es.zarca.covellog.infrastructure.log.Capa;
import es.zarca.covellog.infrastructure.log.FunctionLog;
import es.zarca.covellog.interfaces.facade.empresas.facade.dto.EmpresaDto;
import es.zarca.covellog.interfaces.web.empresas.cliente.model.ClienteGeneralModel;
import es.zarca.covellog.interfaces.web.empresas.controller.ModificarEmpresaController;
import es.zarca.covellog.interfaces.web.empresas.controller.ModificarEmpresaEstado;
import es.zarca.covellog.interfaces.web.helpers.JsfUtil;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.eclipse.persistence.jpa.jpql.Assert;
import org.primefaces.PrimeFaces;
import org.primefaces.event.TabChangeEvent;

/**
 *
 * @author francisco
 */
@Named("clienteController")
@ViewScoped
public class ClienteController implements Serializable {
    private static final Logger LOGGER = Logger.getLogger(ClienteController.class.getName());

    @Inject
    private ClienteGeneralController clienteGeneralController;
    @Inject
    private ClienteContratacionController clienteContratacionController;
    @Inject
    private ClienteFacturacionController clienteFacturacionController;
    @Inject
    private ModificarEmpresaController modificarEmpresaController;
    
    private ClienteGeneralModel model = new ClienteGeneralModel();
    
    public ClienteController() {
    }
    
    public ClienteGeneralModel getModel() {
        return model;
    }

    public void setModel(ClienteGeneralModel model) {
        this.model = model;
        
    }
 
    public void onTabChange(TabChangeEvent event) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            Assert.isNotNull(model, "El modelo no puede ser nulo.");
            LOGGER.log(Level.INFO, " onTabChange() => Selected Tab: {0}", event.getTab().getId());
            switch(event.getTab().getId()) {
                case "ClienteGeneralTab":
                    modificarEmpresaController.setEstado(ModificarEmpresaEstado.CLIENTE_GENERAL);
                    PrimeFaces.current().ajax().update(":Pestanas:Cliente:ClienteGeneralForm");
                    break;
                case "ClienteContratacionTab":
                    modificarEmpresaController.setEstado(ModificarEmpresaEstado.CLIENTE_CONTRATACION);
                    PrimeFaces.current().ajax().update(":Pestanas:Cliente:ClienteContratacionForm");
                    break;
                case "ClienteFacturacionTab":
                    modificarEmpresaController.setEstado(ModificarEmpresaEstado.CLIENTE_FACTURACION);
                    PrimeFaces.current().ajax().update(":Pestanas:Cliente:ClienteFacturacionForm");
                    break;
            }
            PrimeFaces.current().ajax().update(":growl");
        } catch (Exception ex) {
            JsfUtil.addException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void actualizarEmpresaxx(EmpresaDto empresa) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            model.setEmpresa(empresa);
            clienteGeneralController.actualizarEmpresa(empresa);
        } catch (Exception e) {
            JsfUtil.addException(e);
        } finally {
            log.finish();
        }
    }
    
    public Boolean getMostrarTabContratacion() {
        return modificarEmpresaController.getModel().getEstado() != ModificarEmpresaEstado.CONVERTIR_EN_CLIENTE;
    }
    
    public Boolean getMostrarTabFacturacion() {
        return modificarEmpresaController.getModel().getEstado() != ModificarEmpresaEstado.CONVERTIR_EN_CLIENTE;
    }
       
}