package es.zarca.covellog.infrastructure.communications.email;

import es.zarca.covellog.application.email.form.EmailAttachmentForm;
import es.zarca.covellog.application.email.form.EmailForm;
import es.zarca.covellog.domain.model.email.EmailClient;
import es.zarca.covellog.domain.model.exception.DomainException;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author francisco
 */
public class EmailClientJava implements EmailClient {

    private static final Logger LOG = Logger.getLogger(EmailClientJava.class.getName());
    private String servidor;
    private Integer port;
    private String user;
    private String password;
    
    @Override
    public void send(EmailForm email) {
        Properties props = new Properties();
        props.put("mail.smtp.host", servidor); //SMTP Host
        /*props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.socketFactory.port", String.valueOf(port));
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); //SSL Factory Class
*/
        props.put("mail.smtp.auth", "true"); //Enabling SMTP Authentication
        props.put("mail.smtp.port", String.valueOf(587));

        Authenticator auth = new Authenticator() {
            //override the getPasswordAuthentication method
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, password);
            }
        };
        LOG.log(Level.INFO, "Connecting to email server:");
        LOG.log(Level.INFO, "User: " + user);
        LOG.log(Level.INFO, "Password: " + password);
        LOG.log(Level.INFO, props.toString());
        //Session session = Session.getDefaultInstance(props, auth);
        try {
            Session session = Session.getInstance(props, auth);
            sendMail(session, email);
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, ex.getMessage());
        }
        
    }
    
    public void sendMail(Session session, EmailForm email) {
        try {
            MimeMessage msg = new MimeMessage(session);
            //set message headers
            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            msg.addHeader("format", "flowed");
            msg.addHeader("Content-Transfer-Encoding", "8bit");
            msg.setFrom(new InternetAddress(email.getFrom(), ""));
            msg.setReplyTo(InternetAddress.parse(email.getFrom(), false));
            //List<String> recipients = parseRecipients(to);
            //msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));
            //msg.setRecipients(Message.RecipientType.TO, to);
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email.getTo(), false));
            //msg.setRecipients(Message.RecipientType.CC, cc);
            //msg.setRecipients(Message.RecipientType.BCC, bcc);
            msg.setSubject(email.getSubject(), "UTF-8");
            msg.setSentDate(new Date());
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(email.getBody());
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            if (email.getAttachments() != null) {
                for (EmailAttachmentForm attachment : email.getAttachments()) {
                    MimeBodyPart attachmentPart = new MimeBodyPart();
                    attachmentPart.attachFile(attachment.getNombre());
                    multipart.addBodyPart(attachmentPart);
                }
            }
            msg.setContent(multipart);
            System.out.println("Message is ready");
            Transport.send(msg);  

	      System.out.println("EMail Sent Successfully!!");
        } catch (Exception e) {
            new DomainException(e.getMessage());
        }
    }

    @Override
    public List<EmailForm> receive(Integer start, Integer limit) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
