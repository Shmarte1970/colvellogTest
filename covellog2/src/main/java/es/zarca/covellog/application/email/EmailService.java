package es.zarca.covellog.application.email;

import es.zarca.covellog.application.email.form.EmailAttachmentForm;
import es.zarca.covellog.application.email.form.EmailForm;
import java.util.List;


/**
 *
 * @author Francisco Torralbo
 */
public interface EmailService {
    
    public void enviarEmail(String to, String cc, String bcc, String subject, String body, List<EmailAttachmentForm> attachments);
    public void enviarEmail(String to, String subject, String body);
    public void enviarEmail(EmailForm emailForm);
}
