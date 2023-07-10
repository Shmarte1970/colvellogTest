package es.zarca.covellog.application.email.form;

/**
 *
 * @author francisco
 */
public class EmailAttachmentForm {
    private String nombre;
    private String contentType;
    private byte[] content;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
    
}
