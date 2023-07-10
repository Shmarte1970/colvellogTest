package es.zarca.covellog.interfaces.web.empresas.controller;

import es.zarca.covellog.domain.model.contactos.Contacto;
import es.zarca.covellog.infrastructure.log.Capa;
import es.zarca.covellog.infrastructure.log.FunctionLog;
import es.zarca.covellog.interfaces.facade.empresas.facade.EmpresaServiceFacade;
import es.zarca.covellog.interfaces.web.empresas.model.ListadoEmpresasModel;
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
@Named("empresasListadoController")
@ViewScoped
public class ListadoEmpresasController implements Serializable {
    private static final Logger LOGGER = Logger.getLogger(ListadoEmpresasController.class.getName());
    @Inject
    private EmpresaServiceFacade facade;
    @Inject
    private ModificarEmpresaController modificarEmpresaController;
    
    private ListadoEmpresasModel model;

    public ListadoEmpresasController() {
    }

    public ListadoEmpresasModel getModel() {
        if (model == null) {
            model = new ListadoEmpresasModel(facade);
        }
        return model;
    }

    public void setModel(ListadoEmpresasModel model) {
        this.model = model;
    }

    public Contacto getContacto(Integer id) {
        return model.getSelected().getContacto(id);
    }
    
    public void prepareCreate() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            modificarEmpresaController.prepareCreate();
        } catch (Exception e) {
            JsfUtil.addException(e);
        } finally {
            log.finish();
        }
    }
    
    public void prepareEdit() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            if (model == null) {
                model = new ListadoEmpresasModel();
            }
            modificarEmpresaController.prepareUpdate(getModel().getSelected().getId());
        } catch (Exception e) {
            JsfUtil.addException(e);
        } finally {
            log.finish();
        }
    }
    
}