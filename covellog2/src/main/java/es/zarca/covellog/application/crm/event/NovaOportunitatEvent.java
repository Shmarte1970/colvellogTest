/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.zarca.covellog.application.crm.event;

import es.zarca.covellog.domain.model.crm.oportunitat.Oportunitat;

/**
 *
 * @author francisco
 */
public class NovaOportunitatEvent {

    private Oportunitat oportunitat;
    
    public NovaOportunitatEvent(Oportunitat oportunitat) {
        this.oportunitat = oportunitat;
    }

    public Oportunitat getOportunitat() {
        return oportunitat;
    }

    public void setOportunitat(Oportunitat oportunitat) {
        this.oportunitat = oportunitat;
    }
    
    
}
