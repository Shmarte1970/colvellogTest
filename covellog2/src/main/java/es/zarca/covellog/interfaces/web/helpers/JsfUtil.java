package es.zarca.covellog.interfaces.web.helpers;

import es.zarca.covellog.application.exception.BusinessException;
import es.zarca.covellog.application.exception.UndefinedBussinesException;
import es.zarca.covellog.interfaces.web.exception.PresentationException;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.model.SelectItem;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.PrimeFaces;
import org.primefaces.shaded.commons.io.FilenameUtils;

public class JsfUtil {
    private static final Logger LOGGER = Logger.getLogger(JsfUtil.class.getName());
    
    public static SelectItem[] getSelectItems(List<?> entities, boolean selectOne) {
        int size = selectOne ? entities.size() + 1 : entities.size();
        SelectItem[] items = new SelectItem[size];
        int i = 0;
        if (selectOne) {
            items[0] = new SelectItem("", "---");
            i++;
        }
        for (Object x : entities) {
            items[i++] = new SelectItem(x, x.toString());
        }
        return items;
    }

    public static boolean isValidationFailed() {
        return FacesContext.getCurrentInstance().isValidationFailed();
    }
    
    public static void validationFailed() {
        FacesContext.getCurrentInstance().validationFailed();
    }
    
    public static void addErrorMessage(Exception ex, String defaultMsg) {
        String msg = ex.getLocalizedMessage();
        if (msg != null && msg.length() > 0) {
            addErrorMessage(msg);
        } else {
            addErrorMessage(defaultMsg);
        }
    }
    
    public static void showErrorDialog(String message) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", message);
        PrimeFaces.current().dialog().showMessageDynamic(facesMsg);
    }
    
    public static void showErrorDialog(Exception ex) {
        String location = "";
        LOGGER.log(Level.SEVERE, "FINAL EXCEPTION: {0}", ex.getMessage());
        try {
            /*location =  "<ul class=\"exception\"><li>Class: <ul class=\"classname\"><li>" + ex.getStackTrace()[0].getClassName().replace("es.zarca.covellog.", "").replace(".", "</li><li>") + "</li></ul>" +
                "<li> Method: " + ex.getStackTrace()[0].getMethodName() + "</li>" +
                "<li> Line: " + String.valueOf(ex.getStackTrace()[0].getLineNumber()) + "</li></ul>";*/
            location =  "\nClass: " + ex.getStackTrace()[0].getClassName().replace("es.zarca.covellog.", "") + "\n" +
                "Method: " + ex.getStackTrace()[0].getMethodName() + "\n" +
                "Line: " + String.valueOf(ex.getStackTrace()[0].getLineNumber()) + "\n";
        } catch (Exception e) {
            location ="No se puede crear location";
        }
        if (ex instanceof PresentationException) {
            showErrorDialog(ex.getMessage() + location);
        } else if (ex instanceof UndefinedBussinesException) {
            showErrorDialog(ex.getMessage() + location);
        } else if (ex instanceof BusinessException) {
            showErrorDialog(ex.getMessage());
          //   ex.printStackTrace();
        }else if (ex != null) {
            showErrorDialog(ex.getMessage()  + location);
            //ex.printStackTrace();
        } else {
            showErrorDialog("Excepción nula");
            //ex.printStackTrace();
        }
    }
    

    public static void addException(Exception ex) {
        String location = "";
        LOGGER.log(Level.SEVERE, "FINAL EXCEPTION: {0}", ex.getMessage());
        try {
            location =  "<ul class=\"exception\"><li>Class: <ul class=\"classname\"><li>" + ex.getStackTrace()[0].getClassName().replace("es.zarca.covellog.", "").replace(".", "</li><li>") + "</li></ul>" +
                "<li> Method: " + ex.getStackTrace()[0].getMethodName() + "</li>" +
                "<li> Line: " + String.valueOf(ex.getStackTrace()[0].getLineNumber()) + "</li></ul>";
        } catch (Exception e) {
            location ="No se puede crear location";
        }
        if (ex instanceof PresentationException) {
            addErrorMessage("growl", "PresentationException", ex.getMessage() + location);
        } else if (ex instanceof UndefinedBussinesException) {
            addErrorMessage("growl", "UndefinedBussinesException", ex.getMessage() + location);
        } else if (ex instanceof BusinessException) {
            addErrorMessage("growl", "BusinessException", ex.getMessage());
          //   ex.printStackTrace();
        }else if (ex != null) {
            addErrorMessage("growl", "Exception", ex.getMessage() + "<br/>" + location);
            //ex.printStackTrace();
        } else {
            addErrorMessage("growl", "Exception", "Excepción nula");
            //ex.printStackTrace();
        }
       
    }

    public static void addErrorMessages(List<String> messages) {
        messages.forEach((message) -> {
            addErrorMessage(message);
        });
    }
    
    public static void addValidationErrorMessage(String id, String msg) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", msg);
        FacesContext.getCurrentInstance().addMessage(id, facesMsg);
        FacesContext.getCurrentInstance().validationFailed();
    }

    public static void addErrorMessage(String msg) {
        addErrorMessage("growl", "Error", msg);
        //addErrorMessage("messages", "Error", msg);
    }

    public static void addErrorMessage(String id, String title, String msg) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, title, msg);
        FacesContext.getCurrentInstance().addMessage(id, facesMsg);
    }
    
    public static void addErrorMessage(UIComponent component, String msg) {
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", msg  + " " + component.getClientId(context));
        
        context.addMessage(component.getClientId(context), facesMsg);
    }
    
    public static void addSuccessMessage(String msg) {
        addSuccessMessage("growl", "Operación finalizada con éxito", msg);
        //FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Operación finalizada con éxito", msg);
       // FacesContext.getCurrentInstance().addMessage("successInfo", facesMsg);
    }
    
    public static void addWarningMessage(String msg) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", msg);
        FacesContext.getCurrentInstance().addMessage("successInfo", facesMsg);
    }
    
    public static void addSuccessMessage(String id, String title, String msg) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, title, msg);
        FacesContext.getCurrentInstance().addMessage(id, facesMsg);
    }
    
    public static void addSuccessMessage(String id, String msg) {
        addSuccessMessage(id, "Correcto", msg);
    }

    public static String getRequestParameter(String key) {
        return FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(key);
    }

    public static Object getObjectFromRequestParameter(String requestParameterName, Converter converter, UIComponent component) {
        String theId = JsfUtil.getRequestParameter(requestParameterName);
        return converter.getAsObject(FacesContext.getCurrentInstance(), component, theId);
    }    

    public static enum PersistAction {
        CREATE,
        DELETE,
        UPDATE
    }
    
    public static void closeSession() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getSessionMap().clear();
        //context.getExternalContext().getSessionMap().remove("#{vtBbBean}");
        LOGGER.log(Level.INFO, "Sesion cerrada");
    }
    
    private static HttpServletRequest getRequest() {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        return (HttpServletRequest) externalContext.getRequest();
    }
    
    public static void logout() {
        try {
            getRequest().logout();
            LOGGER.log(Level.INFO, "LOGOUT");
        } catch (ServletException ex) {
            LOGGER.log(Level.SEVERE, "Error al hacer LOGOUT: {0}", ex.getMessage());
        }
    }
    
    public static void assertIsAdmin() {
        if (isUserInRole("ADMIN")) throw new BusinessException("Solo puede entrar un ADMIN") {};
    }
    
    public static Boolean isUserInRole(String role) {
        return getRequest().isUserInRole(role);
    }
    
    private static String getContentType(String extension) {
        switch(extension.toUpperCase()) {
            case "PDF":
                return "application/pdf"; 
            case "PNG":
                return "application/image"; 
            case "JPG":
                return "application/image"; 
            case "GIF":
                return "application/image"; 
            default:
                return "text/plain";
        }
    }

    public static void printPdfFromHtml(String fileName, String url, String params) throws IOException {
        HtmlToPdf htmlToPdf = new HtmlToPdf();
        System.out.println("Starting conversion to PDF...");
        System.out.println(url + "?" + params);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        htmlToPdf.appUrlToPdf(url, params, outputStream);                 
        JsfUtil.showFileBufferInNewTab(fileName, outputStream.toByteArray());    
    }
    
    public static void showFileBufferInNewTab(String filename, byte[] content) throws IOException {
        String extension = FilenameUtils.getExtension(filename);
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();
        ec.responseReset(); 
        ec.setResponseContentType(getContentType(extension)); 
        ec.setResponseContentLength((int)content.length); 
        ec.setResponseHeader("Content-Disposition", "inline; filename=\"" + filename + "\""); 
        OutputStream output = ec.getResponseOutputStream();
        output.write(content);
        fc.responseComplete();
    }
    
    public static void showFileInNewTab(String filename) throws FileNotFoundException, IOException {
        File outputPdf = new File(filename);
        byte[] arr = new byte[(int)outputPdf.length()];
        try (FileInputStream fis = new FileInputStream(outputPdf)) {                
            fis.read(arr);            
        }
        showFileBufferInNewTab(filename, arr);
    }
    
    public static void downloadFileBuffer(String filename, byte[] content) throws IOException {
        String extension = FilenameUtils.getExtension(filename);
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();
        ec.responseReset(); 
        ec.setResponseContentType(getContentType(extension)); 
        ec.setResponseContentLength((int)content.length); 
        ec.setResponseHeader("Content-Disposition", "attachment; filename=\"" + filename + "\""); 
        OutputStream output = ec.getResponseOutputStream();
        output.write(content);
        fc.responseComplete();
    }
    
    public static void downloadFile(String filename) throws FileNotFoundException, IOException {
        File outputPdf = new File(filename);
        byte[] arr = new byte[(int)outputPdf.length()];
        try (FileInputStream fis = new FileInputStream(outputPdf)) {                
            fis.read(arr);            
        }
        downloadFileBuffer(filename, arr);
    }
    
}
