/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.zarca.covellog.application.app.internal;

import es.zarca.covellog.application.app.AppService;
import es.zarca.covellog.application.crm.event.NovaOportunitatEvent;
import es.zarca.covellog.domain.services.notificacions.NotificacionsService;
import es.zarca.covellog.domain.model.app.App;
import es.zarca.covellog.domain.model.crm.oportunitat.Oportunitat;
import java.util.Map;
import javax.ejb.Stateless;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

/**
 *
 * @author francisco
 */
@Stateless
public class DefaultAppService implements AppService {
    
    @Inject
    NotificacionsService notificacionsService;
            
    @Override
    public void setPropietat(App app, String id, String valor) {
        app.setPropietat("ULTIMA_PETICIO_CONTACTE_IMPORTADA", valor);
    }

    @Override
    public String getPropietat(App app, String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Map<String, String> getPropietats(App app) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void onNovaOportunitat(@Observes NovaOportunitatEvent event) {
        Oportunitat oportunitat = event.getOportunitat();
        String message = oportunitat.getConcepte() + "\n\n" +
                "Fecha Creación: " + event.getOportunitat().getDataCreacio()+ "\n" + 
                "Fecha Oportunidad: " + event.getOportunitat().getDataOportunitat() + "\n" + 
                "Tipo: " + event.getOportunitat().getDivisioComercial().getNom() + "\n" + 
                "Estado: " + event.getOportunitat().getEstat().getNom() + "\n" + 
                "Origen: " + event.getOportunitat().getOrigen().getNom() + "\n" + 
                "Potencial: " + event.getOportunitat().getPotencial().getNom() + "\n" + 
                "\n" + 
                "Detalle: " + event.getOportunitat().getDetall() + "\n";
        notificacionsService.afegirNotificacio(oportunitat.getComercial(),"Se le ha asignado una nueva oportunidad" + oportunitat.getConcepte(), message);
        //notificacionsEmailService.enviarEmail(1, "Se le ha asignado una nueva oportunidad", message);
        System.out.println("*************************************** " + event.getOportunitat().getComercial().getNombreCompleto());
    }
}
