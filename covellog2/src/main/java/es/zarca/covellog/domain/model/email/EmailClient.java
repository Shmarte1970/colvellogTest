package es.zarca.covellog.domain.model.email;

import es.zarca.covellog.application.email.form.EmailForm;
import java.util.List;

/**
 *
 * @author francisco
 */
public interface EmailClient {
    
    void send(EmailForm email);
    List<EmailForm> receive(Integer start, Integer limit);
    
}
