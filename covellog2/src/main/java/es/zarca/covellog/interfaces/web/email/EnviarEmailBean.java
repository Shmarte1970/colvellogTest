package es.zarca.covellog.interfaces.web.email;

import es.zarca.covellog.application.email.form.EmailAttachmentForm;
import es.zarca.covellog.application.email.form.EmailForm;
import es.zarca.covellog.infrastructure.log.Capa;
import es.zarca.covellog.infrastructure.log.FunctionLog;
import es.zarca.covellog.interfaces.facade.stock.dto.DocumentoDto;
import es.zarca.covellog.interfaces.web.app.controller.GuardarCancelarBean;
import es.zarca.covellog.interfaces.web.contratos.controller.ContratoDocumentacionBean;
import es.zarca.covellog.interfaces.web.exception.PresentationException;
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
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
/**
 *
 * @author francisco
 */
@Named("enviarEmailBean")
@ViewScoped
public class EnviarEmailBean extends GuardarCancelarBean implements Serializable {
    private static final Logger LOGGER = Logger.getLogger(EnviarEmailBean.class.getName());
    private String to;
    private String cc;
    private String bcc;
    private String subject;
    private String body;    
    private List<EmailAttachmentForm> adjuntos = new ArrayList();
    private List<EmailAttachmentForm> adjuntosSeleccionados = new ArrayList();
    
    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getBcc() {
        return bcc;
    }

    public void setBcc(String bcc) {
        this.bcc = bcc;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public List<EmailAttachmentForm> getAdjuntos() {
        return adjuntos;
    }

    public void setAdjuntos(List<EmailAttachmentForm> adjuntos) {
        this.adjuntos = adjuntos;
    }

    public List<EmailAttachmentForm> getAdjuntosSeleccionados() {
        return adjuntosSeleccionados;
    }

    public void setAdjuntosSeleccionados(List<EmailAttachmentForm> adjuntosSeleccionados) {
        this.adjuntosSeleccionados = adjuntosSeleccionados;
    }
    
    public EmailForm getForm() {
        EmailForm form = new EmailForm();
        form.setTo(to);
        form.setCc(cc);
        form.setBcc(bcc);
        form.setSubject(subject);
        form.setBody(body);
        form.setAttachments(adjuntos);
        return form;
    }
    
    public void prepare() {
        to = "";
        cc = "";
        bcc = "";
        subject = "";
        body = "";
        adjuntos = new ArrayList();
    }

    public void upload(FileUploadEvent event) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            UploadedFile uploadedFile = event.getFile();
            String fileName = uploadedFile.getFileName();
            String contentType = uploadedFile.getContentType();
            byte[] contents = uploadedFile.getContents(); // Or getInputStream()
            EmailAttachmentForm attachment = new EmailAttachmentForm();
            attachment.setNombre(fileName);
            attachment.setContentType(contentType);
            attachment.setContent(contents);
            adjuntos.add(attachment);
          //  setContrato(facade.addDocumento(getContrato().getId(), fileName, contents));
            //prepare();
            JsfUtil.addSuccessMessage(fileName);
            PrimeFaces.current().ajax().update(":growl, EnviarEmailForm");
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    private void assertSomeItemSelected() {
        if ((adjuntosSeleccionados == null) || (adjuntosSeleccionados.isEmpty())) {
            throw new PresentationException("Seleccione algun fichero adjunto.");
        }
    }    
    
    public void adjuntoEliminar() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try { 
            assertSomeItemSelected();
            adjuntos.removeAll(adjuntosSeleccionados);
        } catch (Exception e) {
            JsfUtil.addException(e);
        } finally {
            log.finish();
        }
    }
    
    public void adjuntoVer() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try { 
            assertSomeItemSelected();
            for (EmailAttachmentForm adjunto : adjuntosSeleccionados) {
                byte[] content = adjunto.getContent();
                JsfUtil.showFileBufferInNewTab(adjunto.getNombre(), content);                
            }
        } catch (Exception e) {
            JsfUtil.addException(e);
        } finally {
            log.finish();
        }
    }
    
    @FacesConverter(value="emailAdjuntoConverter")
    public static class EmailAdjuntoConverter implements Converter {
        
        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            EnviarEmailBean bean = (EnviarEmailBean) facesContext.getApplication().getELResolver().
                        getValue(facesContext.getELContext(), null, "enviarEmailBean");
            for (EmailAttachmentForm adjunto : bean.getAdjuntos()) {
                if (adjunto.getNombre().equals(value)) {
                    return adjunto;
                }
            }
            return null;
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            
            if (object == null) {
                return null;
            }
            if (object instanceof EmailAttachmentForm) {
                EmailAttachmentForm o = (EmailAttachmentForm) object;
                return o.getNombre();
            } else {
                return null;
            }
        }
        
    }
}