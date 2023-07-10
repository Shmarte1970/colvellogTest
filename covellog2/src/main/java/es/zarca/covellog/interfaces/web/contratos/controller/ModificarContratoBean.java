package es.zarca.covellog.interfaces.web.contratos.controller;
import es.zarca.covellog.application.email.form.EmailAttachmentForm;
import es.zarca.covellog.application.email.form.EmailForm;
import es.zarca.covellog.infrastructure.assertions.ArgumentValidator;
import es.zarca.covellog.infrastructure.log.Capa;
import es.zarca.covellog.infrastructure.log.FunctionLog;
import es.zarca.covellog.interfaces.facade.contrato.ContratoFacade;
import es.zarca.covellog.interfaces.facade.contrato.dto.ContratoDto;
import es.zarca.covellog.interfaces.facade.contrato.dto.ContratoLineaDto;
import es.zarca.covellog.interfaces.facade.contrato.dto.ContratoSmallDto;
import es.zarca.covellog.interfaces.facade.empresas.facade.dto.EmpresaDto;
import es.zarca.covellog.interfaces.facade.stock.dto.DocumentoDto;
import es.zarca.covellog.interfaces.facade.stock.dto.StockDto;
import es.zarca.covellog.interfaces.web.email.EnviarEmailBean;
import es.zarca.covellog.interfaces.web.empresas.controller.SelectorEmpresaController;
import es.zarca.covellog.interfaces.web.helpers.ExceptionHandler;
import es.zarca.covellog.interfaces.web.helpers.HtmlToPdf;
import es.zarca.covellog.interfaces.web.helpers.JsfUtil;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.PrimeFaces;
import org.primefaces.event.TabChangeEvent;
/**
 *
 * @author francisco
 */
@Named("modificarContratoBean")
@ViewScoped
public class ModificarContratoBean implements Serializable {
    private static final Logger LOGGER = Logger.getLogger(ModificarContratoBean.class.getName());
    @Inject
    private ContratoFacade facade;
    @Inject
    private ModificarContratoGeneralBean modificarContratoGeneralBean;
    @Inject
    private CondicionesContratoBean condicionesContratoBean;
    @Inject
    private ContratoFacturacionBean contratoFacturacionBean;
    @Inject
    private ContratoDocumentacionBean contratoDocumentacionBean;
    @Inject
    private PersonasContratoBean personasContratoBean;
    @Inject
    private ContratoLineasBean contratoLineasBean;
    @Inject
    private ContratoAlbaranesBean contratoAlbaranesBean;
    @Inject
    private SelectorEmpresaController selectorEmpresaController;
    @Inject
    private ContratoTraspasarBean contratoTraspasarBean;
    @Inject
    private EnviarEmailBean enviarEmailBean;
    private Integer tabIndex = 0;
    private Integer productoId;
    private List<ContratoSmallDto> contratosSeleccionados;
    private ContratoDto contrato;
    private List<StockDto> selecteds = new ArrayList();
    private Integer id=0;
    private Integer tabId=0;
    private Integer index=0;
    private AccionPanel accionActual = AccionPanel.PESTANAS;
    

    public enum AccionPanel {
        MODIFICAR_LINEA("lineas-editar", "Modificar Linea"),
        PESTANAS("modificar-pestanas", "Modificar Contrato"),
        MODIFICAR_ALBARAN_ENT("../../albaranes/modificar/ajax/modificar", "Modificar Albaran Entrega");
        
        private final String id;
        private final String titulo;

        private AccionPanel(String id, String titulo) {
            this.id = id;
            this.titulo = titulo;
        }
        
        public String getId() {
            return id;
        }               
        
        public String getTitulo() {
            return titulo;
        }
    }
    
    public ModificarContratoBean() {
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTabId() {
        return tabId;
    }

    public void setTabId(Integer tabId) {
        this.tabId = tabId;
    }
    
    public List<ContratoSmallDto> getContratosSeleccionados() {
        return contratosSeleccionados;
    }

    public void setContratosSeleccionados(List<ContratoSmallDto> contratosSeleccionados) {
        this.contratosSeleccionados = contratosSeleccionados;
    }

    public ContratoDto getContrato() {
        try {
            if (contrato == null) {
                System.err.println("COJONES ES NULL CARGO CONTRATO: " + String.valueOf(id));
                ArgumentValidator.isNotNullOrEmpty(id, "No se puede cargar el contrato sin especificar el id de contrato.");
                contrato = facade.findById(id);
            }
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        }
        return contrato;
    }

    public void setContrato(ContratoDto contrato) {
        this.contrato = contrato;
    }

    public List<StockDto> getSelecteds() {
        return selecteds;
    }

    public void setSelecteds(List<StockDto> selecteds) {
        this.selecteds = selecteds;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Integer getTabIndex() {
        return tabIndex;
    }

    public void setTabIndex(Integer tabIndex) {
        this.tabIndex = tabIndex;
    }

    public Integer getProductoId() {
        return productoId;
    }

    public void setProductoId(Integer productoId) {
        this.productoId = productoId;
    }

    public String getAccionActual() {
        return accionActual.getId();
    }

    public void setAccionActual(AccionPanel accionActual) {
        this.accionActual = accionActual;
    }

    public void prepare() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            index = 0;
            contratosSeleccionados = new ArrayList();            
            /*modificarContratoGeneralBean.prepare();
            List<ContratoLineaDto> selectedLines = new ArrayList();
            selectedLines.add(contrato.getLineas().get(0));
            contratoLineasBean.setSelecteds(selectedLines);
            contratoLineasBean.prepareModificar();*/
            //PrimeFaces.current().ajax().update(":Pestanas:LineasTab");
            cambiarEstadoSegunPestanaSeleccionada(indexToTabId(tabIndex), false);
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void prepareSelectEmpresa(javax.faces.event.ActionEvent event) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            selectorEmpresaController.prepare();
            selectorEmpresaController.setOnSeleccionarListener((event1) -> {
                cambiarCliente(selectorEmpresaController.getSelected());
            });
            selectorEmpresaController.setOnCancelarListener((event1) -> {
            });
            selectorEmpresaController.setOnSeleccionarUpdate("panel-interior");
            selectorEmpresaController.setOnCancelarUpdate("");
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void prepareTraspasar() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {            
            ArgumentValidator.isNotNull(getContrato(), "El contrato es null.");
            contratoTraspasarBean.prepare(getContrato());
            contratoTraspasarBean.setTitulo("Fecha Entrega Prevista");
            contratoTraspasarBean.setOnGuardarListener( (event) -> {
                List<Integer> ids = new ArrayList();
                selecteds.forEach(selected -> {
                    ids.add(selected.getId());
                });
                //facade.tra(getContrato().getId(), ids, contratoAsignarFechaBean.getFecha());
                JsfUtil.addSuccessMessage("Fecha de entrega cambiada correctamente.");
                setContrato(null);
            });
            contratoTraspasarBean.setOnCancelarListener( (event) -> {
            });
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
   /* public void prepareEnviarEmail() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            HtmlToPdf htmlToPdf = new HtmlToPdf();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            htmlToPdf.appUrlToPdf("contratos/print.xhtml", "id=" + getContrato().getId(), byteArrayOutputStream);
            try(OutputStream outputStream = new FileOutputStream(contrato.getFriendlyId() + ".pdf")) {
                byteArrayOutputStream.writeTo(outputStream);
            }
            ArgumentValidator.isNotNull(getContrato(), "El contrato es null.");
            if ((getContrato().getFacturacion() != null) && (getContrato().getFacturacion().getContacto() != null)) {
                enviarEmailBean.setTo(getContrato().getFacturacion().getContacto().getEmail());
            }
            enviarEmailBean.setSubject("ZARCA: Contrato " + getContrato().getFriendlyId());
            contratoTraspasarBean.setTitulo("Fecha Entrega Prevista");
            contratoTraspasarBean.setOnGuardarListener( (event) -> {
                List<Integer> ids = new ArrayList();
                selecteds.forEach(selected -> {
                    ids.add(selected.getId());
                });
                //facade.tra(getContrato().getId(), ids, contratoAsignarFechaBean.getFecha());
                JsfUtil.addSuccessMessage("Fecha de entrega cambiada correctamente.");
                setContrato(null);
            });
            contratoTraspasarBean.setOnCancelarListener( (event) -> {
            });
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }*/
    
    private void cambiarCliente(EmpresaDto empresa) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            ArgumentValidator.isNotNull(empresa, "Seleccione alguna empresa.");
            facade.cambiarCliente(contrato.getId(), empresa.getId());
            contrato = null;
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
        
    }
   /* public void prepare(List<ContratoSmallDto> selecteds) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            index = 0;
            contratosSeleccionados = selecteds;
            contrato = facade.findById(selecteds.get(index).getId());
            modificarContratoGeneralBean.prepare();
            //PrimeFaces.current().ajax().update(":mi-layout-contenido");
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }*/
    
    private void cambiarEstadoSegunPestanaSeleccionada(String id, Boolean ajaxUpdate) {
        LOGGER.log(Level.INFO, "Cargar Pestana: {0}", id);
        if (id != null) {
            switch(id) {
                case "GeneralTab":
                    modificarContratoGeneralBean.prepare();
                    if (ajaxUpdate) PrimeFaces.current().ajax().update(":Pestanas:GeneralTab");
                    break;
                case "LineasTab":
                    if (productoId == null) {
                        contratoLineasBean.prepare();
                    } else {
                        List<ContratoLineaDto> selecteds = new ArrayList();
                        getContrato().getLineas().forEach((linea) -> {
                            if ((linea.getStock() != null) && (linea.getStock().getId().equals(productoId))) {
                                selecteds.add(linea);                                
                            }
                        });
                        contratoLineasBean.prepare(selecteds);
                    }
                    if (ajaxUpdate) PrimeFaces.current().ajax().update(":Pestanas:LineasTab");
                    break;
                case "CondicionesTab":
                    condicionesContratoBean.prepare();
                    if (ajaxUpdate) PrimeFaces.current().ajax().update(":Pestanas:CondicionesTab");
                    break;
                case "FacturacionTab":
                    contratoFacturacionBean.prepare();
                    if (ajaxUpdate) PrimeFaces.current().ajax().update(":Pestanas:FacturacionTab");
                    break;
                case "PersonasTab":
                    personasContratoBean.prepare();
                    if (ajaxUpdate) PrimeFaces.current().ajax().update(":Pestanas:PersonasTab");
                    break;
                case "DocumentacionTab":
                    contratoDocumentacionBean.prepare();
                    if (ajaxUpdate) PrimeFaces.current().ajax().update(":Pestanas:DocumentacionTab");
                    break;
                case "AlbaranesTab":
                    contratoAlbaranesBean.prepare();
                    if (ajaxUpdate) PrimeFaces.current().ajax().update(":Pestanas:AlbaranesTab");
                    break;
            }
        }
        //PrimeFaces.current().ajax().update(":growl");
        //PrimeFaces.current().ajax().update(":mi-layout-contenido");
    }
    
    
    private Integer tabIdToIndex(String tabId) {
        
        switch(tabId) {
            case "GeneralTab":
                return 1;
            case "LineasTab":
                return 2;
            case "PersonasTab":
                return 3;
            case "CondicionesTab":
                return 4;
            case "FacturacionTab":
                return 5;
            case "DocumentacionTab":
                return 6;
            case "AlbaranesTab":
                return 7;
            case "FacturasTab":
                return 8;
        }
        return 0;
    }
    
    private String indexToTabId(Integer tabId) {
        switch(tabId) {
            case 1:
                return "GeneralTab";
            case 2:
                return "LineasTab";
            case 3:
                return "PersonasTab";
            case 4:
                return "CondicionesTab";
            case 5:
                return "FacturacionTab";
            case 6:
                return "DocumentacionTab";
            case 7:
                return "AlbaranesTab";
            case 8:
                return "FacturasTab";
        }
        return "ResumenTab";
    }
    /**
     *
     * @param event
     */
    public void onTabChange(TabChangeEvent event) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try { 
            LOGGER.log(Level.INFO, "Selected Tab: {0}", event.getTab().getId());
            tabIndex =  tabIdToIndex(event.getTab().getId());
            FacesContext context = FacesContext.getCurrentInstance();           
            context
                .getApplication()
                .getNavigationHandler()
                .handleNavigation(context, null, "/contratos/modificar?faces-redirect=true&id=" + getContrato().getId() + "&tab=" +  String.valueOf(tabIndex));
            //cambiarEstadoSegunPestanaSeleccionada(event.getTab().getId());
            
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }

    public void cancelar(ActionEvent event) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
           JsfUtil.showErrorDialog("CANCELAR");
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void modificarEstadoPago(ActionEvent event) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
           facade.modificarEstadoPago(getContrato().getId(), getContrato().getEstadoPago());
           JsfUtil.addSuccessMessage("Estado pago actualizado satisfactoriamente.");
           contrato = null;
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void imprimir(ActionEvent event) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            JsfUtil.printPdfFromHtml(contrato.getFriendlyId() + ".pdf", "contratos/print.xhtml", "id=" + getContrato().getId());
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }  
    
    public void prepareEnviarEmail(ActionEvent event) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            ArgumentValidator.isNotNull(getContrato(), "El contrato es null.");
            if ((getContrato().getFacturacion() != null) && (getContrato().getFacturacion().getContactos() != null) && (!getContrato().getFacturacion().getContactos().isEmpty())) {
                enviarEmailBean.setTo(getContrato().getFacturacion().getContactos().get(0).getEmail());
            }
            enviarEmailBean.setCc("");
            enviarEmailBean.setBcc("");
            enviarEmailBean.setSubject("ZARCA: CONTRATO " + getContrato().getFriendlyId());
            enviarEmailBean.setBody("Se le envia el contrato, bla bla, bla bla.");
            List<DocumentoDto> documentos = getContrato().getDocumentos();
            for (DocumentoDto documento : documentos) {
                EmailAttachmentForm emailAttachmentForm = new EmailAttachmentForm();
                emailAttachmentForm.setNombre(documento.getNombre());
                emailAttachmentForm.setContent(facade.getDatosDocumento(getContrato().getId(), documento.getId()));
                emailAttachmentForm.setContentType("application/octet-stream");
                enviarEmailBean.getAdjuntos().add(emailAttachmentForm);
            }
            HtmlToPdf htmlToPdf = new HtmlToPdf();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            htmlToPdf.appUrlToPdf("contratos/print.xhtml", "id=" + getContrato().getId(), byteArrayOutputStream);
            EmailAttachmentForm emailAttachmentForm = new EmailAttachmentForm();
            emailAttachmentForm.setNombre(getContrato().getFriendlyId() + " .pdf");
            emailAttachmentForm.setContent(byteArrayOutputStream.toByteArray());
            emailAttachmentForm.setContentType("application/octet-stream");
            enviarEmailBean.getAdjuntos().add(emailAttachmentForm);
            enviarEmailBean.setOnGuardarListener((ActionEvent event1) -> {
                enviarEmail(enviarEmailBean.getForm());
            });
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }  
    
    private void enviarEmail(EmailForm form) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            facade.enviarPorEmail(getContrato().getId(), form);
            JsfUtil.addSuccessMessage("Email enviado correctamente.");
         } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
}