package es.zarca.covellog.interfaces.web.helpers;

import com.lowagie.text.DocumentException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.FileSystems;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.xhtmlrenderer.layout.SharedContext;
import org.xhtmlrenderer.pdf.ITextRenderer;

public class HtmlToPdf {
    
    public String createWellFormedHtml(File inputHTML) throws IOException {
        Document document = Jsoup.parse(inputHTML, "UTF-8");
        document.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
        System.out.println("HTML parsing done...");
        return document.html();
    }
		  
    public void xhtmlToPdf(String xhtml, OutputStream outputStream) throws IOException {
        //OutputStream outputStream = null;
        try {
            //System.err.println("COJONES DE NABARONE: " + getClass().getResource("/xxx_files/xxx.html"));
            ITextRenderer renderer = new ITextRenderer();	
            SharedContext sharedContext = renderer.getSharedContext();
            sharedContext.setPrint(true);
            sharedContext.setInteractive(false);
            // Register custom ReplacedElementFactory implementation
            sharedContext.getTextRenderer().setSmoothingThreshold(0);
            // Register additional font 
            //renderer.getFontResolver().addFont("/home/DATOS-RAID1/Informatica/Proyectos/Covellog2/covellog2/src/main/resources/xxx_files/fa-regular-400.ttf", true);
            // Setting base URL to resolve the relative URLs
           /* String baseUrl = FileSystems.getDefault()
                                        .getPath("/home/DATOS-RAID1/Informatica/Proyectos/Covellog2/covellog2/src/main/resources/xxx_files/")
                                        .toUri()
                                        .toURL()
                                        .toString();*/
            FacesContext facesContext = FacesContext.getCurrentInstance();
            ExternalContext externalContext = facesContext.getExternalContext();
            String servername = externalContext.getRequestServerName();
            String port = String.valueOf(externalContext.getRequestServerPort());
            String appname = externalContext.getRequestContextPath();
            String protocol = externalContext.getRequestScheme();
            HttpSession session = (HttpSession) externalContext.getSession(true);
            String url = protocol + "://" + servername + ":" + port + appname + "/faces/albaranes/modificar/print.xhtml"+";jsessionid=" + session.getId() + "?id=30";
            renderer.setDocument(new URL(url).toString());
            //renderer.setDocumentFromString(xhtml, baseUrl);
            renderer.layout();
            
            //outputStream = new FileOutputStream(outputPdf);
            renderer.createPDF(outputStream);
            System.out.println("PDF creation completed"); 
        } catch (DocumentException ex) {
            Logger.getLogger(HtmlToPdf.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (outputStream != null) outputStream.close();
        }
  }
    
    public void appUrlToPdf(String url, String params, OutputStream outputStream) throws IOException {
        //OutputStream outputStream = null;
        try {
            //File outputPdf = new File(filename);
            //System.err.println("COJONES DE NABARONE: " + getClass().getResource("/xxx_files/xxx.html"));
            ITextRenderer renderer = new ITextRenderer();	
            SharedContext sharedContext = renderer.getSharedContext();
            sharedContext.setPrint(true);
            sharedContext.setInteractive(false);
            // Register custom ReplacedElementFactory implementation
            sharedContext.getTextRenderer().setSmoothingThreshold(0);
            // Register additional font 
            //renderer.getFontResolver().addFont("/home/DATOS-RAID1/Informatica/Proyectos/Covellog2/covellog2/src/main/resources/xxx_files/fa-regular-400.ttf", true);
            // Setting base URL to resolve the relative URLs
           /* String baseUrl = FileSystems.getDefault()
                                        .getPath("/home/DATOS-RAID1/Informatica/Proyectos/Covellog2/covellog2/src/main/resources/xxx_files/")
                                        .toUri()
                                        .toURL()
                                        .toString();*/
            FacesContext facesContext = FacesContext.getCurrentInstance();
            ExternalContext externalContext = facesContext.getExternalContext();
            String servername = externalContext.getRequestServerName();
            String port = String.valueOf(externalContext.getRequestServerPort());
            String appname = externalContext.getRequestContextPath();
            String protocol = externalContext.getRequestScheme();
            HttpSession session = (HttpSession) externalContext.getSession(true);
            url = protocol + "://" + servername + ":" + port + appname + "/faces/" + url +";jsessionid=" + session.getId() + "?" + params;
            renderer.setDocument(new URL(url).toString());            
            //renderer.setDocumentFromString(xhtml, baseUrl);
            renderer.layout();
            System.err.println("COJONES GAMA EXTRA XXX ");
            
            //outputStream = new FileOutputStream(outputPdf);
            renderer.createPDF(outputStream);
            System.err.println("COJONES GAMA EXTRA: " + renderer.getDocument());
            System.out.println("PDF creation completed"); 
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("No se a podido crear el PDF.");
        } finally {
            if (outputStream != null) outputStream.close();
        }
        
    }
}