package es.zarca.covellog.application.email.internal;

import es.zarca.covellog.application.email.exception.ImposibleEnviarEmailException;
import es.zarca.covellog.application.email.exception.ServidorEmailNotFoundException;
import es.zarca.covellog.domain.model.email.ServidorEmail;
import es.zarca.covellog.domain.model.email.ServidorEmailRepository;
import javax.ejb.Stateless;
import javax.inject.Inject;
import es.zarca.covellog.application.email.EmailService;
import es.zarca.covellog.application.email.form.EmailAttachmentForm;
import es.zarca.covellog.application.email.form.EmailForm;
import java.util.List;

/**
 *
 * @author francisco
 */
@Stateless
public class DefaultEmailService implements EmailService {
    
    @Inject
    ServidorEmailRepository servidorEmailRepository;
        
    @Override
    public void enviarEmail(String to, String cc, String bcc, String subject, String body, List<EmailAttachmentForm> attachments) {
        try {
            String id = "DEFAULT";
            ServidorEmail servidorEmail = servidorEmailRepository.find(id);
            if (servidorEmail == null) {
                throw new ServidorEmailNotFoundException(id);
            }
            servidorEmail.enviarEmail(to, cc, bcc, subject, body, attachments);
        } catch (ServidorEmailNotFoundException e) {
            throw new ImposibleEnviarEmailException(e.getMessage());
        }        
    }
    
    @Override
    public void enviarEmail(String to, String subject, String body) {
        try {
            String id = "DEFAULT";
            ServidorEmail servidorEmail = servidorEmailRepository.find(id);
            if (servidorEmail == null) {
                throw new ServidorEmailNotFoundException(id);
            }
            servidorEmail.enviarEmail(to, subject, body);
        } catch (ServidorEmailNotFoundException e) {
            throw new ImposibleEnviarEmailException(e.getMessage());
        }        
    }

    @Override
    public void enviarEmail(EmailForm form) {
        try {
            String id = "DEFAULT";
            ServidorEmail servidorEmail = servidorEmailRepository.find(id);
            if (servidorEmail == null) {
                throw new ServidorEmailNotFoundException(id);
            }
            servidorEmail.enviarEmail(form.getTo(), form.getCc(), form.getBcc(), form.getSubject(), form.getBody(), form.getAttachments());
            //servidorEmail.enviarEmail(form.getTo(), form.getSubject(), form.getBody());
        } catch (ServidorEmailNotFoundException e) {
            throw new ImposibleEnviarEmailException(e.getMessage());
        }  
    }
    
    

}
