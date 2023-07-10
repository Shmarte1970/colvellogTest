package es.zarca.covellog.interfaces.web.almacenes.controller;

import es.zarca.covellog.application.adreces.form.DireccionForm;
import es.zarca.covellog.application.almacenes.exception.AlmacenNotExistException;
import es.zarca.covellog.application.almacenes.form.AlmacenForm;
import es.zarca.covellog.application.empresas.form.DobleObservacionForm;
import es.zarca.covellog.infrastructure.log.Capa;
import es.zarca.covellog.infrastructure.log.FunctionLog;
import es.zarca.covellog.interfaces.facade.adreces.facade.dto.DireccionDto;
import es.zarca.covellog.interfaces.facade.almacen.AlmacenesFacade;
import es.zarca.covellog.interfaces.facade.almacen.dto.AlmacenDto;
import es.zarca.covellog.interfaces.facade.contactos.dto.ContactoDto;
import es.zarca.covellog.interfaces.facade.empresas.facade.dto.EmpresaMiniDto;
import es.zarca.covellog.interfaces.web.adreces.controller.DireccionEditController;
import es.zarca.covellog.interfaces.web.app.controller.GuardarCancelarBean;
import es.zarca.covellog.interfaces.web.empresas.contactos.controller.SelectorListaContactosController;
import es.zarca.covellog.interfaces.web.empresas.controller.SelectorEmpresaController;
import es.zarca.covellog.interfaces.web.helpers.ExceptionHandler;
import es.zarca.covellog.interfaces.web.helpers.JsfUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.primefaces.PrimeFaces;
/**
 *
 * @author francisco
 */
@Named("editarAlmacenBean")
@ViewScoped
public class EditarAlmacenBean implements Serializable {
    private static final Logger LOGGER = Logger.getLogger(EditarAlmacenBean.class.getName());
    @Inject
    private AlmacenesFacade facade;
    @Inject 
    private SelectorListaContactosController selectorListaContactosController;
    @Inject
    private SelectorEmpresaController selectorEmpresaController;
    @Inject
    private DireccionEditController direccionEditController;
    private Integer almacenId;
    private boolean nuevo = true;
    private String update = "";
    private boolean mostrarSelectorEmpresa = false;
    private boolean mostrarSelectorContactos = true;
    
    private Integer id;
    @NotNull
    private List<ContactoDto> contactos;
    @NotNull
    @Size(min = 1, max = 200)
    private String nombre;
    @NotNull
    @Size(min = 1, max = 255)
    private String descripcion;
    @NotNull
    private EmpresaMiniDto empresa;
    @NotNull
    @Size(min = 1, max = 200)
    private String horario;
    @NotNull
    private DireccionDto direccion;
    @Size(max = 10000)
    private String obsInternas;
    @Size(max = 10000)
    private String obsPublicas;

    public EditarAlmacenBean() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    
    public void prepareSelectEmpresa(javax.faces.event.ActionEvent event) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            mostrarSelectorEmpresa = true;
            selectorEmpresaController.prepare();
            selectorEmpresaController.setOnSeleccionarListener((event1) -> {
                empresa = selectorEmpresaController.getSelectedMiniDto();
                mostrarSelectorEmpresa = false;
                contactos = new ArrayList<>();;
            });
            selectorEmpresaController.setOnCancelarListener((event1) -> {
                mostrarSelectorEmpresa = false;
            });
            selectorEmpresaController.setOnSeleccionarUpdate("@(.Almacen-Empresa),@(.Almacen-Contactos)");
            selectorEmpresaController.setOnCancelarUpdate("@(.Almacen-Empresa)");
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void prepareNuevo() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            nuevo = true;
            id = null;
            nombre = "";
            descripcion = "";
            empresa = null;
            horario = "";
            direccion = null;
            obsInternas = "";
            obsPublicas = "";
            contactos = new ArrayList<>();
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void prepareModificar() {
        prepareModificar(id);
    }
    
    public void prepareModificar(Integer id) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            nuevo=false;
            AlmacenDto almacen = facade.findById(id);
            if (almacen == null) {
                throw new AlmacenNotExistException(id);
            }
            this.id = almacen.getId();
            nombre = almacen.getNombre();
            descripcion = almacen.getDescripcion();
            empresa = almacen.getEmpresa();
            horario = almacen.getHorario();
            direccion = almacen.getDireccion();
            obsInternas = almacen.getObservaciones().getObsInternas();
            obsPublicas = almacen.getObservaciones().getObsPublicas();
            contactos = almacen.getContactos();
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void prepareUpdateContactos(javax.faces.event.ActionEvent event) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {            
            if (empresa == null) {
                mostrarSelectorContactos = true;
                JsfUtil.showErrorDialog("Primero debe seleccionar la empresa propietaria del almacen.");
            } else {
                selectorListaContactosController.setEmpresaId(empresa.getId());
               // List<ContactoDto> contactosPosibles = facade.findContactosAlmacen(empresa.getId());
                //EmpresaDto empresa = facade.buscarPorIdDto(empresa.getId);
                selectorListaContactosController.setContactosPosibles(facade.getContactosPosibles(empresa.getId()));
                selectorListaContactosController.setContactos(contactos);
                selectorListaContactosController.setUpdate("@(.Almacen-Contactos)");
                selectorListaContactosController.setUpdateId("Almacen-ContactosPopup");
                selectorListaContactosController.setOnGuardarListener((ActionEvent event1) -> {
                    updateContactos();
                });
                mostrarSelectorContactos = true;
            }
        } catch (Exception ex) {
            JsfUtil.addException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void updateContactos() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            contactos = selectorListaContactosController.getContactos(); 
        } catch (Exception ex) {
            JsfUtil.addException(ex);
            System.out.println(ex.getMessage());
        } finally {
            log.finish();
        }
    }
    
    public void prepareUpdateDireccion(javax.faces.event.ActionEvent event) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            //direccionTemporal = model.getDireccionEnvio().getDireccion();
            direccionEditController.setDireccion(direccion);
            direccionEditController.setListener((ActionEvent event1) -> {
                updateDireccion(event1);
            });
            direccionEditController.setUpdate("@(.Almacen-Direccion)");
        } catch (Exception ex) {
            JsfUtil.addException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void updateDireccion(javax.faces.event.ActionEvent event) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            direccion = direccionEditController.getDireccion();
        } catch (Exception ex) {
            JsfUtil.addException(ex);
        } finally {
            log.finish();
        }
    }
   
    public void copiarDireccionEmpresa(javax.faces.event.ActionEvent event) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            if (empresa == null) {
                JsfUtil.addErrorMessage("Primero seleccione una empresa.");
                PrimeFaces.current().ajax().update(":growl");
            } else {
                DireccionDto direccionPrincipal = facade.getDireccionFiscalEmpresa(empresa.getId());
                if (direccionPrincipal == null) {
                    JsfUtil.addErrorMessage("La direccion principal de la empresa esta vacia.");
                    PrimeFaces.current().ajax().update(":growl");
                } else {
                    direccion = direccionPrincipal;
                }
            }
        } catch (Exception ex) {
            JsfUtil.addException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void guardar(ActionEvent event) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            if (empresa == null) {
                JsfUtil.addValidationErrorMessage("Panel:AlmacenEditForm:empresa", "La empresa es obligatoria.");
            } else if (direccion == null) {
                JsfUtil.addValidationErrorMessage("Panel:AlmacenEditForm:direccion:direccion", "La dirección es obligatoria.");
            } else {
                if (nuevo) {
                    guardarNuevo();
                } else {
                    guardarModificar();
                }
/*                if (guardarListener != null) {
                    guardarListener.processAction(event);
                } else {
                    //FacesContext context = FacesContext.getCurrentInstance();
//                    context.getApplication().getNavigationHandler().handleNavigation(context, null, "/almacenes/gestion?faces-redirect=true");
                }*/
            }
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    private AlmacenForm createFormWithBeanValues() {
        AlmacenForm form = new AlmacenForm();        
        form.setEmpresaId(empresa.getId()); 
        form.setNombre(nombre);
        form.setDescripcion(descripcion);
        form.setDireccion(
            new DireccionForm(direccion.getDireccion(), direccion.getDireccion2(), direccion.getCodigoPostal(), direccion.getPoblacion().getId())
        );
        form.setHorario(horario);
        form.setObservaciones(new DobleObservacionForm(obsInternas, obsPublicas));
        List<Integer> contactosIds = new ArrayList<>();
        contactos.forEach(contacto -> {
            contactosIds.add(contacto.getId());
        });
        form.setContactosIds(contactosIds);
        return form;
    }
    
    private void guardarNuevo() {
        /*Set<ConstraintViolation<AlmacenForm>> errors = Validation.buildDefaultValidatorFactory().getValidator().validate(form);
        for (ConstraintViolation<AlmacenForm> error : errors) {
            JsfUtil.addValidationErrorMessage("Panel:AlmacenEditForm:" + error.getPropertyPath().toString(), "Panel:AlmacenEditForm:" + error.getPropertyPath().toString() + ": " + error.getMessage());
        }
        JsfUtil.validationFailed();*/
        facade.nuevo(createFormWithBeanValues());
        JsfUtil.addSuccessMessage("Almacen creado correctamente.");
    }
    
    private void guardarModificar() {
        facade.modificar(id, createFormWithBeanValues());
        JsfUtil.addSuccessMessage("Almacen modificado correctamente.");
    }
    
    public void cancelar(ActionEvent event) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            //cancelarListener.processAction(event);
            FacesContext context = FacesContext.getCurrentInstance();
            context.getApplication().getNavigationHandler().handleNavigation(context, null, "/almacenes/gestion?faces-redirect=true");
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    } 

    public boolean isNuevo() {
        return nuevo;
    }

    public void setNuevo(boolean nuevo) {
        this.nuevo = nuevo;
    }

    public String getUpdate() {
        return update;
    }

    public void setUpdate(String update) {
        this.update = update;
    }

    public boolean isMostrarSelectorEmpresa() {
        return mostrarSelectorEmpresa;
    }

    public void setMostrarSelectorEmpresa(boolean mostrarSelectorEmpresa) {
        this.mostrarSelectorEmpresa = mostrarSelectorEmpresa;
    }

    public boolean isMostrarSelectorContactos() {
        return mostrarSelectorContactos;
    }

  
/*    public List<ContactoDto> getContactosPosibles() {
        return contactosPosibles;
    }

    public void setContactosPosibles(List<ContactoDto> contactosPosibles) {
        this.contactosPosibles = contactosPosibles;
    }
*/
   

    public List<ContactoDto> getContactos() {
        return contactos;
    }

    public void setContactos(List<ContactoDto> contactos) {
        this.contactos = contactos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public EmpresaMiniDto getEmpresa() {
        return empresa;
    }

    public void setEmpresa(EmpresaMiniDto empresa) {
        this.empresa = empresa;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public DireccionDto getDireccion() {
        return direccion;
    }

    public void setDireccion(DireccionDto direccion) {
        this.direccion = direccion;
    }

    public String getObsInternas() {
        return obsInternas;
    }

    public void setObsInternas(String obsInternas) {
        this.obsInternas = obsInternas;
    }

    public String getObsPublicas() {
        return obsPublicas;
    }

    public void setObsPublicas(String obsPublicas) {
        this.obsPublicas = obsPublicas;
    }
}