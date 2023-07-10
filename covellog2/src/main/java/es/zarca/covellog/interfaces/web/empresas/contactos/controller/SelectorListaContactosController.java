package es.zarca.covellog.interfaces.web.empresas.contactos.controller;

import es.zarca.covellog.infrastructure.assertions.ArgumentValidator;
import es.zarca.covellog.infrastructure.log.Capa;
import es.zarca.covellog.infrastructure.log.FunctionLog;
import es.zarca.covellog.interfaces.facade.contactos.dto.ContactoDto;
import es.zarca.covellog.interfaces.facade.empresas.facade.EmpresaServiceFacade;
import es.zarca.covellog.interfaces.web.app.controller.GuardarCancelarBean;
import es.zarca.covellog.interfaces.web.empresas.contactos.model.SelectorContactosModel;
import es.zarca.covellog.interfaces.web.helpers.ExceptionHandler;
import es.zarca.covellog.interfaces.web.helpers.JsfUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author francisco
 */
@Named("selectorListaContactosController")
@ViewScoped
public class SelectorListaContactosController extends GuardarCancelarBean implements Serializable {
    private static final Logger LOGGER = Logger.getLogger(SelectorListaContactosController.class.getName());
    @Inject
    private SelectorContactosController selectorContactosController;
    @Inject
    private ContactoEditController contactoEditController;
    @Inject
    private EmpresaServiceFacade facade;
    private List<ContactoDto> contactos = new ArrayList<>();
    private List<ContactoDto> selecteds;
 //   private ActionListener listener;
    private String update;
    private String updateId;
    private List<ContactoDto> contactosPosibles;
    private Integer empresaId;

    public List<ContactoDto> getContactos() {
        return (List<ContactoDto>) contactos;
    }

    public void setContactos(List<ContactoDto> contactos) {
        if (contactos == null) {
            this.contactos = new ArrayList<>();
        } else {
            this.contactos = new ArrayList<>(contactos);
        }
    }

    public List<ContactoDto> getSelecteds() {
        return selecteds;
    }

    public void setSelecteds(List<ContactoDto> selecteds) {
        this.selecteds = selecteds;
    }

    public List<ContactoDto> getContactosPosibles() {
        if (contactosPosibles == null) {
            contactosPosibles = facade.findContactos(empresaId);
        }
        return contactosPosibles;
    }

    public void setContactosPosibles(List<ContactoDto> contactosPosibles) {
        this.contactosPosibles = contactosPosibles;
    }

   /* public ActionListener getListener() {
        return listener;
    }

    public void setListener(ActionListener listener) {
        this.listener = listener;
    }*/

    public String getUpdate() {
        return update;
    }

    
    public void setUpdate(String update) {
        this.update = update;
    }

    public String getUpdateId() {
        return updateId;
    }

    public void setUpdateId(String updateId) {
        this.updateId = updateId;
    }
    
    public ContactoDto getContacto(Integer id) {
        for (ContactoDto contacto : contactos) {
            if (contacto.getId().equals(id)) {
                return contacto;
            }
        }
        return null;
    }

    public Integer getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(Integer empresaId) {
        this.empresaId = empresaId;
    }
    
    public void subir() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            ArgumentValidator.isNotEmpty(selecteds, "Seleccione algun contacto.");
            ContactoDto aux;
            for (ContactoDto selected : selecteds) {
                int index = contactos.indexOf(selected);
                if (index > 0) {
                    aux = contactos.get(index - 1);
                    contactos.set(index - 1, selected);
                    contactos.set(index, aux);
                } else {
                    JsfUtil.addErrorMessage("El contacto \"" + selected.getNombre() + "\" no puede subir mas.");
                }
            }
        } catch (Exception e) {
            ExceptionHandler.handleException(e);
        } finally {
            log.finish();
        }
    }
    
    public void bajar() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            ArgumentValidator.isNotEmpty(selecteds, "Seleccione algun contacto.");
            ContactoDto aux;
            for (ContactoDto selected : selecteds) {
                int index = contactos.indexOf(selected);
                if ((index >= 0) && (index + 1 < contactos.size())) {
                    aux = contactos.get(index + 1);
                    contactos.set(index + 1, selected);
                    contactos.set(index, aux);
                } else {
                    JsfUtil.addErrorMessage("El contacto \"" + selected.getNombre() + "\" no puede bajar mas.");
                }
            }
        } catch (Exception e) {
            ExceptionHandler.handleException(e);
        } finally {
            log.finish();
        }
    }
    
    public void quitar() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            ArgumentValidator.isNotEmpty(selecteds, "Seleccione algun contacto.");
            selecteds.forEach(selected -> {
                contactos.remove(selected);
            });
            selecteds = new ArrayList<>();
        } catch (Exception e) {
            ExceptionHandler.handleException(e);
        } finally {
            log.finish();
        }
    }
        
    
    public void prepareEditar() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            ArgumentValidator.isNotEmpty(selecteds, "Seleccione algun contacto.");
            contactoEditController.prepareEdit(empresaId, getSelecteds().get(0));
            contactoEditController.setUpdate("@(.ContactoSelectorForm)");
            contactoEditController.setListener((ActionEvent event) -> {
                modificarContacto();
            });
        } catch (Exception e) {
            ExceptionHandler.handleException(e);
            //JsfUtil.validationFailed();JsfUtil.showErrorDialog(e.getMessage());
        } finally {
            log.finish();
        }
    }
    
    private void modificarContacto() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            facade.modificarContacto(empresaId, contactoEditController.getContacto());
            JsfUtil.addSuccessMessage("growl", "El contacto se ha modificado correctamente.");
        } catch (Exception e) {
            ExceptionHandler.handleException(e);
            //JsfUtil.validationFailed();JsfUtil.showErrorDialog(e.getMessage());
        } finally {
            log.finish();
        }       
    }
    
    public void prepareAnadir() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {            
            SelectorContactosModel model = new SelectorContactosModel();
            List<ContactoDto> sublistaContactosPosibles = new ArrayList<>();
            for (ContactoDto contactoPosible : getContactosPosibles()) {
                if (!contactos.contains(contactoPosible)) {
                    sublistaContactosPosibles.add(contactoPosible);
                }
            }
            model.setEmpresaId(empresaId);
            model.setItems(sublistaContactosPosibles);            
            model.setListener((ActionEvent event1) -> {
                anadir();
            });
            model.setUpdate("@(." + updateId + ")");
            model.setUpdateId("ContactosSelector-ContactosPopup");
            selectorContactosController.setModel(model);
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void anadir() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            if (selectorContactosController.getModel().getSelected() != null) {
                if (!contactos.contains(selectorContactosController.getModel().getSelected())) {
                    contactos.add(selectorContactosController.getModel().getSelected());
                }
            }
        } catch (Exception e) {
            ExceptionHandler.handleException(e);
        } finally {
            log.finish();
        }    
    }
    
    public void onAceptar() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            /*if (listener != null) {
                listener.processAction(null);
            }*/
            notificarGuardar(null);
        } catch (Exception e) {
            ExceptionHandler.handleException(e);
        } finally {
            log.finish();
        }        
    }
    
    @FacesConverter(value="selectorListaContactosConverter")
    public static class SelectorListaContactosConverter implements Converter {
        
        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            SelectorListaContactosController controller = (SelectorListaContactosController) facesContext.getApplication().getELResolver().
                        getValue(facesContext.getELContext(), null, "selectorListaContactosController");
            ContactoDto contacto = controller.getContacto(Integer.parseInt(value));
            System.err.println("Contacto: " + contacto);
            return contacto;
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof ContactoDto) {
                ContactoDto o = (ContactoDto) object;
                return o.getId().toString();
            } else {
                return null;
            }
        }
    }

}
