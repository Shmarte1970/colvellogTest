package es.zarca.covellog.interfaces.web.contratos.controller;
import es.zarca.covellog.infrastructure.log.Capa;
import es.zarca.covellog.infrastructure.log.FunctionLog;
import es.zarca.covellog.interfaces.facade.contrato.ContratoFacade;
import es.zarca.covellog.interfaces.facade.contrato.dto.ContratoDto;
import es.zarca.covellog.interfaces.facade.stock.dto.DocumentoDto;
import es.zarca.covellog.interfaces.web.exception.PresentationException;
import es.zarca.covellog.interfaces.web.helpers.ExceptionHandler;
import es.zarca.covellog.interfaces.web.helpers.JsfUtil;
import es.zarca.covellog.interfaces.web.app.controller.GuardarCancelarBean;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.eclipse.persistence.jpa.jpql.Assert;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
/**
 *
 * @author francisco
 */
@Named("contratoDocumentacionBean")
@ViewScoped
public class ContratoDocumentacionBean extends GuardarCancelarBean implements Serializable {
    private static final Logger LOGGER = Logger.getLogger(ContratoDocumentacionBean.class.getName());
    @Inject
    private ContratoFacade facade;
    @Inject
    private ModificarContratoBean modificarContratoBean;
    private List<DocumentoDto> documentos = new ArrayList<>();
    private List<DocumentoDto> documentosSeleccionados = new ArrayList<>();
    private Integer numNewDocs = 0;

    public ContratoDto getContrato() {
        return modificarContratoBean.getContrato();
    }
    
    public void setContrato(ContratoDto contrato) {
        modificarContratoBean.setContrato(contrato);
    }
    
    public List<DocumentoDto> getDocumentos() {
        return documentos;
    }

    public void setDocumentos(List<DocumentoDto> documentos) {
        this.documentos = documentos;
    }

    public List<DocumentoDto> getDocumentosSeleccionados() {
        return documentosSeleccionados;
    }

    public void setDocumentosSeleccionados(List<DocumentoDto> documentosSeleccionados) {
        this.documentosSeleccionados = documentosSeleccionados;
    }

    public Integer getNumNewDocs() {
        return numNewDocs;
    }

    public void setNumNewDocs(Integer numNewDocs) {
        this.numNewDocs = numNewDocs;
    }
    
    public void prepare() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            Assert.isNotNull(getContrato(), "El contrato es null.");
            ContratoDto contrato = getContrato();
            if (contrato == null) {
                JsfUtil.showErrorDialog("El contrato es null");
            } else {
                documentos = contrato.getDocumentos();
            }
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
   /* @Override
    public void guardar(ActionEvent event) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            //modificarContratoBean.setContrato(facade.modificarDocumentacion(modificarContratoBean.getContrato().getId(), form));
            JsfUtil.addSuccessMessage("Contrato modificado correctamente");
            notificarGuardar(event);
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }*/
    
    public void upload(FileUploadEvent event) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            UploadedFile uploadedFile = event.getFile();
            String fileName = uploadedFile.getFileName();
            String contentType = uploadedFile.getContentType();
            byte[] contents = uploadedFile.getContents(); // Or getInputStream()
            setContrato(facade.addDocumento(getContrato().getId(), fileName, contents));
            prepare();
            JsfUtil.addSuccessMessage(fileName);
            PrimeFaces.current().ajax().update(":growl, Pestanas:ContratoDocumentacionForm");
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    private void assertSomeItemSelected() {
        if ((documentosSeleccionados == null) || (documentosSeleccionados.isEmpty())) {
            throw new PresentationException("Seleccione algun documento");
        }
    }
    
    public void documentacionSubir() {        
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try { 
            assertSomeItemSelected();
            List<Integer> ids = new ArrayList();
            for (DocumentoDto documento : documentosSeleccionados) {
                ids.add(documento.getId());
            }
            setContrato(facade.subirDocumentos(getContrato().getId(), ids));
            prepare();
        } catch (Exception e) {
            JsfUtil.addException(e);
        } finally {
            log.finish();
        }
    } 
     
    public void documentacionBajar() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try { 
            assertSomeItemSelected();
            List<Integer> ids = new ArrayList();
            for (DocumentoDto documento : documentosSeleccionados) {
                ids.add(documento.getId());
            }
            setContrato(facade.bajarDocumentos(getContrato().getId(), ids));
            prepare();
        } catch (Exception e) {
            JsfUtil.addException(e);
        } finally {
            log.finish();
        }
    }
    
    public void documentacionEliminar() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try { 
            assertSomeItemSelected();
            List<Integer> ids = new ArrayList();
            for (DocumentoDto documento : documentosSeleccionados) {
                ids.add(documento.getId());
            }
            setContrato(facade.removeDocumentos(getContrato().getId(), ids));
            prepare();
        } catch (Exception e) {
            JsfUtil.addException(e);
        } finally {
            log.finish();
        }
    }
    
    public void documentacionVer() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try { 
            assertSomeItemSelected();
            for (DocumentoDto documento : documentosSeleccionados) {
                byte[] datos = facade.getDatosDocumento(getContrato().getId(), documento.getId());
                JsfUtil.showFileBufferInNewTab(documento.getNombre(), datos);                
            }
        } catch (Exception e) {
            JsfUtil.addException(e);
        } finally {
            log.finish();
        }
    }
    
    @FacesConverter(value="contratoDocumentoDtoConverter")
    public static class SelectorListaDocumentosConverter implements Converter {
        
        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ContratoDocumentacionBean contratoDocumentacionBean = (ContratoDocumentacionBean) facesContext.getApplication().getELResolver().
                        getValue(facesContext.getELContext(), null, "contratoDocumentacionBean");
            for (DocumentoDto documento : contratoDocumentacionBean.getDocumentos()) {
                if (documento.getId().equals(Integer.parseInt(value))) {
                    return documento;
                }
            }
            return null;
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            
            if (object == null) {
                return null;
            }
            if (object instanceof DocumentoDto) {
                DocumentoDto o = (DocumentoDto) object;
                return o.getId().toString();
            } else {
                return null;
            }
        }
    }
}