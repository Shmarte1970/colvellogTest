package es.zarca.covellog.domain.services.notificacions.internal;

import es.zarca.covellog.domain.services.notificacions.NotificacionsService;
import es.zarca.covellog.domain.model.notificacions.Notificacio;
import es.zarca.covellog.domain.model.notificacions.NotificacioRepository;
import es.zarca.covellog.domain.model.usuarios.Usuario;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.inject.Inject;
import es.zarca.covellog.domain.model.usuarios.UsuarioRepository;
import es.zarca.covellog.application.email.EmailService;

/**
 *
 * @author francisco
 */
@Stateless
//Lock(LockType.READ) //allows timers to execute in parallel
public class DefaultNotificacioService implements NotificacionsService {
    private static final Logger LOGGER = Logger.getLogger(DefaultNotificacioService.class.getName());
    
    @Inject
    NotificacioRepository notificacioRepository;
    @Inject
    UsuarioRepository usuariRepository;
    @Inject
    EmailService notificacionsEmailService;
    
 /*   @Schedules({
         @Schedule(hour = "*", minute = "*" , second = "10")
    })*/
   // @Lock(LockType.READ)

    /**
     *
     */
    @Lock(LockType.READ)
    @Schedule(minute = "*/10", hour = "*", persistent=false)
    @Override
    public void enviarNotificacions() {
        LOGGER.log(Level.INFO, "{0}DefaultNotificacioService: Enviando notificaciones pendientes", new Date().toString());
        try {
            List<Notificacio> notificacionsPendents = notificacioRepository.findPendents();
            LOGGER.log(Level.INFO, "DefaultNotificacioService: Encontradas {0} notificaciones pendientes de enviar.", String.valueOf(notificacionsPendents.size()));
            for (Notificacio notificacioPendent : notificacionsPendents) {
                notificacionsEmailService.enviarEmail(notificacioPendent.getDestinatari().getCanalesContacto().getEmail(), notificacioPendent.getTitol(), notificacioPendent.getDetall());
                notificacioPendent.marcarEnviada();
                LOGGER.log(Level.INFO, "DefaultNotificacioService: Enviada notificaci\u00f3n a: {0}", notificacioPendent.getDestinatari().getNombreCompleto());
            }
        } catch (Exception e) {
            LOGGER.log(Level.INFO, "DefaultNotificacioService: No se ha podido enviar las notificaciones pendientes. {0}", e.getMessage());
        }
        
    }

    @Override
    public void afegirNotificacio(Usuario destinatari, String titol, String detall) {
        if ((titol == null) || (titol.isEmpty())) {
            titol = "SIN TITULO";
        }  
        if ((detall == null) || (detall.isEmpty())) {
            detall = "SIN DETALLE";
        }
        Notificacio notificacio = new Notificacio(destinatari, titol, detall);
        notificacioRepository.store(notificacio);
    }
    
}
