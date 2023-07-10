package es.zarca.covellog.interfaces.web.albaranes.controller;
import es.zarca.covellog.application.albaranes.form.ModificarAlbaranInfoForm;
import es.zarca.covellog.application.empresas.form.DobleObservacionForm;
import es.zarca.covellog.application.stock.form.AsignacionStockForm;
import es.zarca.covellog.domain.model.albaran.exception.AlbaranNotExistException;
import es.zarca.covellog.infrastructure.assertions.ArgumentValidator;
import es.zarca.covellog.infrastructure.log.Capa;
import es.zarca.covellog.infrastructure.log.FunctionLog;
import es.zarca.covellog.interfaces.facade.albaran.dto.AlbaranDto;
import es.zarca.covellog.interfaces.facade.albaran.dto.AlbaranLineaDto;
import es.zarca.covellog.interfaces.facade.contrato.dto.ContratoLineaDto;
import es.zarca.covellog.interfaces.facade.transporte.dto.TransporteDto;
import es.zarca.covellog.interfaces.web.almacenes.controller.AlmacenBusquedaTransversalBean;
import es.zarca.covellog.interfaces.web.contratos.controller.ModificarContratoBean;
import es.zarca.covellog.interfaces.web.helpers.ExceptionHandler;
import es.zarca.covellog.interfaces.web.helpers.JsfUtil;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import es.zarca.covellog.interfaces.facade.transporte.TransportesFacade;
import es.zarca.covellog.interfaces.facade.ubicaciones.dto.UbicacionDto;
import es.zarca.covellog.interfaces.web.app.controller.BusquedaFacade;
import es.zarca.covellog.interfaces.web.app.controller.BusquedaSelector;
import es.zarca.covellog.interfaces.web.contratos.controller.ContratoLineaSelectorBean;
import es.zarca.covellog.interfaces.web.empresas.direccionesenvio.controller.DireccionEnvioSelectorBean;
import es.zarca.covellog.interfaces.web.helpers.HtmlToPdf;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import es.zarca.covellog.interfaces.facade.albaran.AlbaranFacade;
import org.primefaces.PrimeFaces;
/**
 *
 * @author francisco
 */
@Named("modificarAlbaranBean")
@ViewScoped
public class ModificarAlbaranBean implements Serializable {
    private static final Logger LOGGER = Logger.getLogger(ModificarAlbaranBean.class.getName());
    @Inject
    private AlbaranFacade facade;
    @Inject
    private ModificarContratoBean modificarContratoBean;
    @Inject
    private ContratoLineaSelectorBean contratoLineaSelectorBean;
    @Inject
    private AlmacenBusquedaTransversalBean almacenBusquedaTransversalBean;
    @Inject
    private DireccionEnvioSelectorBean direccionEnvioSelectorBean;
    @Inject 
    private AlbaranLineaReservarBean albaranLineaReservarBean;
    private AlbaranDto albaran;
    private ModificarAlbaranInfoForm editarInfoForm;
    private List<AlbaranLineaDto> lineasSelecteds;
    private List<ContratoLineaDto> lineasContratoQueSePuedenAnadirAlAlbaran;
    private Integer id;
    private TransporteDto transporteSeleccionado;
    @Inject
    private TransportesFacade transportesFacade;
    private BusquedaSelectorTransporteDto busquedaSelectorTransporte;
    
    public ModificarAlbaranBean() {
    }
    
    public class BusquedaSelectorTransporteDto extends BusquedaSelector<TransporteDto> {  

        @Override
        public Object getItemRowKey(TransporteDto item) {
            if (item == null) {
                return false;
            }
            return item.getId();
        }

        @Override
        public boolean hasRowKey(TransporteDto item, String rowKey) {
            if ( (item == null) || (item.getId() == null) ) {
                return false;
            }

            return (item.getId().toString().equals(rowKey));
        }

        @Override
        public BusquedaFacade<TransporteDto> getFacade() {
            return transportesFacade;
        }
    }  

    public BusquedaSelectorTransporteDto getBusquedaSelectorTransporte() {
        if (busquedaSelectorTransporte == null) {
            busquedaSelectorTransporte = new BusquedaSelectorTransporteDto();
        } 
        return busquedaSelectorTransporte;
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public AlbaranDto getAlbaran() {
        if (albaran == null) {
            albaran = facade.findById(id);
            if (albaran == null) {
                throw new AlbaranNotExistException(id);
            }
        }
        return albaran;
    }

    public void setAlbaran(AlbaranDto albaran) {
        this.albaran = albaran;
    }

    public String getEstadoLowerCase() {
        if ((albaran == null) || (albaran.getEstado() == null)) {
            return "";
        }
        return albaran.getEstado().toLowerCase();
    }
    
    public ModificarAlbaranInfoForm getEditarInfoForm() {
        return editarInfoForm;
    }

    public void setEditarInfoForm(ModificarAlbaranInfoForm editarInfoForm) {
        this.editarInfoForm = editarInfoForm;
    }

    
    public AlbaranFacade getFacade() {
        return facade;
    }

    public void setFacade(AlbaranFacade facade) {
        this.facade = facade;
    }

    public ModificarContratoBean getModificarContratoBean() {
        return modificarContratoBean;
    }

    public void setModificarContratoBean(ModificarContratoBean modificarContratoBean) {
        this.modificarContratoBean = modificarContratoBean;
    }

    public List<AlbaranLineaDto> getLineasSelecteds() {
        return lineasSelecteds;
    }

    public void setLineasSelecteds(List<AlbaranLineaDto> lineasSelecteds) {
        this.lineasSelecteds = lineasSelecteds;
    }

    public List<ContratoLineaDto> getLineasContratoQueSePuedenAnadirAlAlbaran() {
        return lineasContratoQueSePuedenAnadirAlAlbaran;
    }
    
    public AlbaranLineaDto getLinea(Integer id) {
        for (AlbaranLineaDto linea : albaran.getLineas()) {
            if (linea.getId().equals(id)) {
                return linea;
            }
        }
        return null;
    }

    public TransporteDto getTransporteSeleccionado() {
        return transporteSeleccionado;
    }

    public void setTransporteSeleccionado(TransporteDto transporteSeleccionado) {
        this.transporteSeleccionado = transporteSeleccionado;
    }

    public DireccionEnvioSelectorBean getDireccionEnvioSelectorBean() {
        return direccionEnvioSelectorBean;
    }

    public void prepare() {
        prepare(id);
    }
    
    public void prepare(Integer albaranId) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {            
            ArgumentValidator.isNotNull(albaranId, "Debe especificar un id de albaran.");
            lineasSelecteds = new ArrayList();
            AlbaranDto albaranAux = facade.findById(albaranId);
            if (albaranAux == null) {
                throw new AlbaranNotExistException(albaranId);
            }
            prepare(albaranAux);
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void prepareAnadirLineaContrato() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            contratoLineaSelectorBean.prepare(
                facade.getLineasContratoQueSePuedenAnadir(albaran.getId())
            );
            contratoLineaSelectorBean.setOnGuardarListener((event) -> {
                List<Integer> ids = new ArrayList();
                for (ContratoLineaDto selected : contratoLineaSelectorBean.getSelecteds()) {
                    ids.add(selected.getId());
                }
                facade.anadirLineasContrato(albaran.getContratoId(), ids, albaran.getId());
                reload();
            });
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    private void reload() {
        albaran = null;
        /* FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {            
            ArgumentValidator.isNotNull(albaranId, "Debe especificar un id de albaran."); 
            AlbaranDto albaranAux = facade.findById(albaranId);
            if (albaranAux == null) {
                throw new AlbaranNotExistException(albaranId);
            }
            prepare(albaranAux);
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }*/
    }
    
    public void prepare(AlbaranDto albaran) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            ArgumentValidator.isNotNull(albaran, "El albaran es null.");
            this.albaran = albaran;
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
  /* public void prepareUpdateDireccion(javax.faces.event.ActionEvent event) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            direccionEditController.setDireccion(albaran.get());
            direccionEditController.setListener((ActionEvent event1) -> {
                cambiarDireccion(direccionEditController.getDireccion());
            });
            direccionEditController.setUpdate(":growl,@(.ModificarAlbaran-Direccion)");
        } catch (Exception ex) {
            JsfUtil.addException(ex);
        } finally {
            log.finish();
        }
    }
    */
    
   /* private void cambiarDireccion(DireccionDto direccion) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            facade.cambiarDestino(
                id,
                new DireccionForm(
                    direccion.getDireccion(), 
                    direccion.getDireccion2(), 
                    direccion.getCodigoPostal(), 
                    direccion.getPoblacion().getId()
                )
            );
            JsfUtil.addSuccessMessage("Destino modificado correctamente.");
            reload();
            
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }*/
    
    public void prepareUpdate(javax.faces.event.ActionEvent event) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {            
            editarInfoForm = new ModificarAlbaranInfoForm();
            editarInfoForm.setFecha(albaran.getFecha());
            editarInfoForm.setTextoAviso(albaran.getTextoAviso());
            editarInfoForm.setObservaciones(
                new DobleObservacionForm(
                    albaran.getObservaciones().getObsInternas(), 
                    albaran.getObservaciones().getObsPublicas())
            );
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void onUpdate(javax.faces.event.ActionEvent event) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {            
            facade.modificarInfoGeneral(id, editarInfoForm);
            reload();
            JsfUtil.addSuccessMessage("Albaran modificado correctamente.");
        } catch (Exception ex) {
            //JsfUtil.validationFailed();
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void prepareSelectTransporte(javax.faces.event.ActionEvent event) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {            
            //transporteSelectorBean.setOnSeleccionarUpdate("@(.TransporteSeleccionable)");
            /*transporteSelectorBean.setOnSeleccionarListener((ActionEvent event1) -> {
                cambiarTransporte(transporteSelectorBean.getSelected());
                reload();
            });
            transporteSelectorBean.setOnCancelarListener((ActionEvent event1) -> {
            });*/
            
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    public Boolean getCanCrearReservaDeLineasSeleccionadas() {
        if ((lineasSelecteds == null) || (lineasSelecteds.isEmpty())) {
            return false;
        }
        for(AlbaranLineaDto linea:lineasSelecteds) {
            if (linea.getReserva() != null) {
                return false;
            }
        }
        return true;
    }
    
    public void cambiarTransporte() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            facade.cambiarTransporte(id, transporteSeleccionado.getId());
            reload();
            JsfUtil.addSuccessMessage("Transporte cambiado correctamente.");
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void onClickSusMedios(javax.faces.event.ActionEvent event) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            facade.cambiarTransporte(id, null);
            reload();
            JsfUtil.addSuccessMessage("Transporte cambiado correctamente.");
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }    
    
    public void prepareSelectOrigen(javax.faces.event.ActionEvent event) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            if ("R".equals(albaran.getTipo().getId())) {
                direccionEnvioSelectorBean.prepare(albaran.getClienteId());
                direccionEnvioSelectorBean.setOnSeleccionarUpdate(":growl,:panel-interior");
                direccionEnvioSelectorBean.setOnSeleccionarListener((ActionEvent event1) -> {
                    cambiarOrigen(direccionEnvioSelectorBean.getSelected());
                });
                direccionEnvioSelectorBean.setOnCancelarListener((ActionEvent event1) -> {
                });
                PrimeFaces.current().ajax().update("@(.ModificarAlbaran-DireccionEnvioSelector)");
                PrimeFaces.current().executeScript("PF('DireccionEnvioSelectorPopupDialog').show()");
            } else {
                almacenBusquedaTransversalBean.setOnSeleccionarUpdate(":growl,:panel-interior");
                almacenBusquedaTransversalBean.setOnCancelarUpdate(":growl,:panel-interior");
                almacenBusquedaTransversalBean.setOnSeleccionarListener((ActionEvent event1) -> {
                    cambiarOrigen(almacenBusquedaTransversalBean.getSelected());
                });
                almacenBusquedaTransversalBean.setOnCancelarListener((ActionEvent event1) -> {
                });
                PrimeFaces.current().ajax().update("@(.ModificarAlbaran-AlmacenSelector)");
                PrimeFaces.current().executeScript("PF('AlmacenSelectorPopupDialog').show()");
            }
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void prepareSelectDestino(javax.faces.event.ActionEvent event) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            if ("R".equals(albaran.getTipo().getId())) {
                almacenBusquedaTransversalBean.setOnSeleccionarUpdate(":growl,:panel-interior");
                almacenBusquedaTransversalBean.setOnCancelarUpdate(":growl,:panel-interior");
                almacenBusquedaTransversalBean.setOnSeleccionarListener((ActionEvent event1) -> {
                    cambiarDestino(almacenBusquedaTransversalBean.getSelected());
                });
                almacenBusquedaTransversalBean.setOnCancelarListener((ActionEvent event1) -> {
                });
                PrimeFaces.current().ajax().update("@(.ModificarAlbaran-AlmacenSelector)");
                PrimeFaces.current().executeScript("PF('AlmacenSelectorPopupDialog').show()");
            } else {
                direccionEnvioSelectorBean.prepare(albaran.getClienteId());
                direccionEnvioSelectorBean.setOnSeleccionarUpdate(":growl,:panel-interior");
                direccionEnvioSelectorBean.setOnSeleccionarListener((ActionEvent event1) -> {
                    cambiarDestino(direccionEnvioSelectorBean.getSelected());
                });
                direccionEnvioSelectorBean.setOnCancelarListener((ActionEvent event1) -> {
                });
                PrimeFaces.current().ajax().update("@(.ModificarAlbaran-DireccionEnvioSelector)");
                PrimeFaces.current().executeScript("PF('DireccionEnvioSelectorPopupDialog').show()");
            }
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    private void cambiarOrigen(UbicacionDto ubicacion) {  
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        ArgumentValidator.isNotNull(albaran, "El albaran no esta caragdo.");
        ArgumentValidator.isNotNull(ubicacion, "Seleccione una ubicacion.");
        facade.cambiarOrigen(albaran.getId(), ubicacion.getId());
        reload();
        JsfUtil.addSuccessMessage("Origen cambiado correctamente.");
        log.finish();
    }
    
    private void cambiarDestino(UbicacionDto ubicacion) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        ArgumentValidator.isNotNull(albaran, "El albaran no esta caragdo.");
        ArgumentValidator.isNotNull(ubicacion, "Seleccione una ubicacion.");
        facade.cambiarDestino(albaran.getId(), ubicacion.getId());
        reload();
        JsfUtil.addSuccessMessage("Destino cambiado correctamente.");
        log.finish();
    }
    
    public void modificarInfoGeneral(ActionEvent event) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            facade.modificarInfoGeneral(albaran.getId(), editarInfoForm);
            reload();            
            JsfUtil.addSuccessMessage("Albaran modificado correctamente");
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void anular() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            facade.anular(albaran.getId());
            reload();
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void reabrir() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            facade.cancelar(albaran.getId());
            reload();
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void activar() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            facade.activar(albaran.getId());
            reload();
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void finalizar() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            facade.finalizar(albaran.getId());
            reload();
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
   /* public void lineaAnadir() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            List<String> referencias = new ArrayList();
            lineasSelecteds.forEach(lineaSelected -> {
                referencias.add(lineaSelected.getReferencia());
            });
//            facade.lineasAnadir(editarInfoForm);
            reload();
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }*/
    
    public void lineasEliminar() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            List<String> referencias = new ArrayList();
            lineasSelecteds.forEach(lineaSelected -> {
                referencias.add(lineaSelected.getBooking());
            });
            facade.lineasEliminar(albaran.getId(), referencias);
            reload();
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void lineasBajar() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            List<String> referencias = new ArrayList();
            lineasSelecteds.forEach(lineaSelected -> {
                referencias.add(lineaSelected.getBooking());
            });
            facade.lineasBajar(albaran.getId(), referencias);
            reload();
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void lineasSubir() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            List<String> referencias = new ArrayList();
            lineasSelecteds.forEach(lineaSelected -> {
                referencias.add(lineaSelected.getBooking());
            });
            facade.lineasSubir(albaran.getId(), referencias);
            reload();
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void lineasMarcarSalida() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            List<String> referencias = new ArrayList();
            lineasSelecteds.forEach(lineaSelected -> {
                referencias.add(lineaSelected.getBooking());
            });
            JsfUtil.showErrorDialog("facade.marcarSalida(id, referencias);");
            facade.lineasMarcarSalida(albaran.getId(), referencias, new Date());
            reload();
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void lineasDesmarcarSalida() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            List<String> referencias = new ArrayList();
            lineasSelecteds.forEach(lineaSelected -> {
                referencias.add(lineaSelected.getBooking());
            });
            JsfUtil.showErrorDialog("facade.marcarSalida(id, referencias);");
            facade.lineasDesmarcarSalida(albaran.getId(), referencias);
            reload();
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
   /* public void reservar() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            List<String> referencias = new ArrayList();
            lineasSelecteds.forEach(lineaSelected -> {
                referencias.add(lineaSelected.getReferencia());
            });
            facade.lineasMarcarLlegada(albaran.getId(), referencias, new Date());
            reload();
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    */
    public void prepareReservar() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            ArgumentValidator.isNotEmpty(lineasSelecteds, "Seleccione alguna linea.");
            albaranLineaReservarBean.prepare();
            albaranLineaReservarBean.setTransportistaId(albaran.getTransportista() == null ? null : albaran.getTransportista().getId());
            albaranLineaReservarBean.setTransportistaNombre(albaran.getTransportista() == null ? "Sus medios" : albaran.getTransportista().getNombre());
            albaranLineaReservarBean.setOnGuardarListener((event) -> {
                List<AsignacionStockForm> asignaciones = new ArrayList();
                lineasSelecteds.forEach(lineaSelected -> {
                    asignaciones.add(
                        new AsignacionStockForm(
                            lineaSelected.getBooking(), 
                            lineaSelected.getStock() != null ? lineaSelected.getStock().getId() : null
                        )
                    );
                });
                Integer reservaId;
                if ("E".equals(albaran.getTipo().getId())) {
                    reservaId = facade.crearEntreguese(
                        albaran.getId(), 
                        false,
                        albaran.getFecha(),
                        asignaciones, 
                        albaranLineaReservarBean.getTransportistaId(), 
                        albaranLineaReservarBean.getTransportistaNombre(), 
                        albaranLineaReservarBean.getChofer(), 
                        albaranLineaReservarBean.getMatricula(), 
                        albaranLineaReservarBean.getObservaciones()
                    );
                } else {
                    reservaId = facade.crearAdmitase(
                        albaran.getId(), 
                        false,
                        albaran.getFecha(),
                        asignaciones, 
                        albaranLineaReservarBean.getTransportistaId(), 
                        albaranLineaReservarBean.getTransportistaNombre(), 
                        albaranLineaReservarBean.getChofer(), 
                        albaranLineaReservarBean.getMatricula(), 
                        albaranLineaReservarBean.getObservaciones()
                    );
                }
                
                
                FacesContext context = FacesContext.getCurrentInstance();
                context.getApplication()
                    .getNavigationHandler()
                    .handleNavigation(context, null, "/reservas/modificar.xhtml?faces-redirect=true&id=" + reservaId);
                //JsfUtil.addSuccessMessage("Reserva efectuada correctamente.");
                //reload();
            });
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void lineasDesmarcarLlegada() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            List<String> referencias = new ArrayList();
            lineasSelecteds.forEach(lineaSelected -> {
                referencias.add(lineaSelected.getBooking());
            });
            JsfUtil.showErrorDialog("facade.marcarLlegada(id, referencias);");
            facade.lineasDesmarcarLlegada(albaran.getId(), referencias);
            reload();
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void imprimir3(ActionEvent event) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            System.err.println("COJONesSSSSS IMPRIMIR");
            
           
            //JsfUtil.showFileInNewTab("temp.txt", "COJONES".getBytes());
            
            
            //JsfUtil.showErrorDialog("IMPRIMIR");
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
       
    }
    
    public void imprimir(ActionEvent event) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            HtmlToPdf htmlToPdf = new HtmlToPdf();
            //String xhtml = htmlToPdf.createWellFormedHtml(inputHTML);
            System.out.println("Starting conversion to PDF...");
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            htmlToPdf.appUrlToPdf("albaranes/print.xhtml", "id=" + albaran.getId(), outputStream);                 
            JsfUtil.showFileBufferInNewTab(albaran.getCodigoAlbaran() + ".pdf", outputStream.toByteArray());
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void printPdf() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            JsfUtil.printPdfFromHtml(getAlbaran().getCodigoAlbaran() + ".pdf", "albaranes/print.xhtml", "id=" + getAlbaran().getId().toString());
        } catch (Exception ex) {
            JsfUtil.showErrorDialog(ex);
        } finally {
            log.finish();
        }
    }
    
    @FacesConverter(value="contratoAlbaranLineaConverter")
    public static class ContratoAlbaranLineaConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ModificarAlbaranBean bean = (ModificarAlbaranBean) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "modificarAlbaranBean");
            return bean.getLinea(Integer.parseInt(value));
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof AlbaranLineaDto) {
                AlbaranLineaDto dto = (AlbaranLineaDto) object;
                return dto.getId().toString();
            } else {
                LOGGER.log(
                    Level.SEVERE, "ContratoLineaDto: object {0} is of type {1}; expected type: {2}", 
                    new Object[]{object, object.getClass().getName(), ContratoLineaDto.class.getName()}
                );
                return null;
            }
        }
    }
    
}