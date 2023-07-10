package es.zarca.covellog.domain.model.email;

import es.zarca.covellog.application.email.form.EmailAttachmentForm;
import es.zarca.covellog.domain.model.exception.DomainException;
import es.zarca.covellog.infrastructure.assertions.ArgumentValidator;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author francisco
 */
@Entity
@Table(name = "servidor_email")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ServidorEmail.findAll", query = "SELECT s FROM ServidorEmail s")
    , @NamedQuery(name = "ServidorEmail.findById", query = "SELECT s FROM ServidorEmail s WHERE s.id = :id")
    , @NamedQuery(name = "ServidorEmail.findByNom", query = "SELECT s FROM ServidorEmail s WHERE s.nom = :nom")
    , @NamedQuery(name = "ServidorEmail.findByServidor", query = "SELECT s FROM ServidorEmail s WHERE s.servidor = :servidor")
    , @NamedQuery(name = "ServidorEmail.findByPort", query = "SELECT s FROM ServidorEmail s WHERE s.port = :port")
    , @NamedQuery(name = "ServidorEmail.findBySsl", query = "SELECT s FROM ServidorEmail s WHERE s.ssl = :ssl")
    , @NamedQuery(name = "ServidorEmail.findByUsuari", query = "SELECT s FROM ServidorEmail s WHERE s.usuari = :usuari")
    , @NamedQuery(name = "ServidorEmail.findByPassword", query = "SELECT s FROM ServidorEmail s WHERE s.password = :password")})
public class ServidorEmail implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(ServidorEmail.class.getName());    
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 3, max = 10)
    @Column(name = "id")
    private String id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nom")
    private String nom;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "servidor")
    private String servidor;
    @Basic(optional = false)
    @NotNull
    @Column(name = "port")
    private int port;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ssl_encription")
    private short ssl;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 80)
    @Column(name = "usuari")
    private String usuari;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "password")
    private String password;
    @JoinColumn(name = "servidor_email_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private PoolServidorsEmail servidorEmail;

    public ServidorEmail() {
    }

    public ServidorEmail(String id) {
        this.id = id;
    }

    public ServidorEmail(String id, String nom, String servidor, int port, short ssl, String usuari, String password) {
        this.id = id;
        this.nom = nom;
        this.servidor = servidor;
        this.port = port;
        this.ssl = ssl;
        this.usuari = usuari;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getServidor() {
        return servidor;
    }

    public void setServidor(String servidor) {
        this.servidor = servidor;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public short getSsl() {
        return ssl;
    }

    public void setSsl(short ssl) {
        this.ssl = ssl;
    }

    public String getUsuari() {
        return usuari;
    }

    public void setUsuari(String usuari) {
        this.usuari = usuari;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public PoolServidorsEmail getServidorEmail() {
        return servidorEmail;
    }

    public void setServidorEmail(PoolServidorsEmail servidorEmail) {
        this.servidorEmail = servidorEmail;
    }
    
    public void enviarEmail(String to, String cc, String bcc, String subject, String body, List<EmailAttachmentForm> attachments) {
        Properties props = new Properties();
        props.put("mail.smtp.host", servidor); //SMTP Host
        /*props.put("mail.smtp.socketFactory.port", String.valueOf(port));
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); //SSL Factory Class*/
        props.put("mail.smtp.auth", "true"); //Enabling SMTP Authentication
        props.put("mail.smtp.port", String.valueOf(587));
        Authenticator auth = new Authenticator() {
            //override the getPasswordAuthentication method
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(usuari, password);
            }
        };
        LOG.log(Level.INFO, "Connecting to email server:");
        LOG.log(Level.INFO, "User: " + usuari);
        LOG.log(Level.INFO, "Password: " + password);
        LOG.log(Level.INFO, props.toString());
        try {
            //Session session = Session.getDefaultInstance(props, auth);
            Session session = Session.getInstance(props, auth);
            sendEmail(session, usuari, to, cc, bcc, subject, body, attachments);
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, ex.getMessage());
        }
    }
    
    public void enviarEmail(String toEmail, String subject, String body) {
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
                return new PasswordAuthentication(usuari, password);
            }
        };
        LOG.log(Level.INFO, "Connecting to email server:");
        LOG.log(Level.INFO, "User: " + usuari);
        LOG.log(Level.INFO, "Password: " + password);
        LOG.log(Level.INFO, props.toString());
        //Session session = Session.getDefaultInstance(props, auth);
        try {
            Session session = Session.getInstance(props, auth);
            sendEmail(session, usuari, toEmail, subject, body);
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, ex.getMessage());
        }
        
    }
    
    private InternetAddress[] parseRecipients(String recipientsString) throws AddressException {
        String recipients[] = recipientsString.split("[, ;]+");
        InternetAddress[] result = new InternetAddress[recipients.length];
        int i = 0;
        for (String recipient : recipients) {
            result[i] = InternetAddress.parse(recipient, false)[0];
            i++;
        }
        return result;
    }
    
    private void sendEmail(Session session, String from, String to, String cc, String bcc, String subject, String body, List<EmailAttachmentForm> attachments) {
        try {
            ArgumentValidator.isNotEmpty(from, "El remitente es obligatorio para enviar un email.");
            ArgumentValidator.isNotEmpty(to, "El para es obligatorio para enviar un email.");
            ArgumentValidator.isNotEmpty(subject, "El asunto es obligatorio para enviar un email.");
            MimeMessage msg = new MimeMessage(session);
            //set message headers
            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            msg.addHeader("format", "flowed");
            msg.addHeader("Content-Transfer-Encoding", "8bit");
            msg.setFrom(new InternetAddress(from, ""));
            msg.setReplyTo(InternetAddress.parse(from, false));
            msg.setRecipients(Message.RecipientType.TO, parseRecipients(to));
            if ((cc != null) && (!cc.isEmpty())) {
                msg.setRecipients(Message.RecipientType.CC, parseRecipients(cc));
            }
            if ((bcc != null) && (!bcc.isEmpty())) {
                msg.setRecipients(Message.RecipientType.BCC, parseRecipients(bcc));
            }
            msg.setSubject(subject, "UTF-8");
            msg.setSentDate(new Date());
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(body == null ? "" : body, "text/html");
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            if (attachments != null) {
                for (EmailAttachmentForm attachment : attachments) {
                    MimeBodyPart attachmentPart = new MimeBodyPart();
                    attachmentPart.setContent(attachment.getContent(), attachment.getContentType());
                    attachmentPart.setFileName(attachment.getNombre());
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
    
    private void sendEmail(Session session, String from, String to, String subject, String body) throws MessagingException, UnsupportedEncodingException{
        MimeMessage msg = new MimeMessage(session);
        //set message headers
        msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
        msg.addHeader("format", "flowed");
        msg.addHeader("Content-Transfer-Encoding", "8bit");
        msg.setFrom(new InternetAddress(from, ""));
        msg.setReplyTo(InternetAddress.parse(from, false));
        msg.setSubject(subject, "UTF-8");
        msg.setText(body, "UTF-8", "html");
        msg.setSentDate(new Date());
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));
        System.out.println("Message is ready");
        Transport.send(msg);  
        System.out.println("EMail Sent Successfully!!");
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ServidorEmail)) {
            return false;
        }
        ServidorEmail other = (ServidorEmail) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "email.ServidorEmail[ id=" + id + " ]";
    }
    
}
