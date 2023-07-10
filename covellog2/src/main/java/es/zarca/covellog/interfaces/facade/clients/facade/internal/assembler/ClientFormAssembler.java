/*
 * Proyecto Covellog2
 * 
 * Este archivo ha sido creado para ZARCA y no se puede usar sin su consentimiento.
 */
package es.zarca.covellog.interfaces.facade.clients.facade.internal.assembler;

import es.zarca.covellog.domain.model.clients.client.Client;
import es.zarca.covellog.domain.model.idiomas.idioma.Idioma;
import es.zarca.covellog.interfaces.facade.adreces.facade.internal.assembler.AdreçaFormAssembler;
import es.zarca.covellog.interfaces.facade.clients.facade.form.ClientForm;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public class ClientFormAssembler {
    private static final Logger LOGGER = Logger.getLogger(ClientFormAssembler.class.getName());
    
    public ClientForm toForm(Client client) {
        if (client == null) {
            LOGGER.log(Level.SEVERE, "ClientFormAssembler: Cliente NULL");
            return null;
        }   
        
        PersonaContacteFormAssembler personaContacteFormAssembler = new PersonaContacteFormAssembler();
        AdreçaFormAssembler adreçaFormAssembler = new AdreçaFormAssembler();
        String cif;
        if (client.getCif() == null) {
            cif = "";
        } else {
            cif = client.getCif().getCifString();
        }
        String email;
        if (client.getEmail() == null) {
            email = "";
        } else {
            email = client.getEmail().getEmailString();
        }
     
        ClientForm form = new ClientForm(
                client.getId(), 
                client.getNom(), 
                client.getAlias(),
                cif, 
                adreçaFormAssembler.toForm(client.getAdreçaFiscal()), 
                client.getExentIva(), 
                client.getIban(), 
                personaContacteFormAssembler.toForm(client.getPersonaContactePrincipal()),
                adreçaFormAssembler.toForm(client.getAdreçaContacte()), 
                client.getHorari(), 
                email,               
                client.getWeb(), 
                client.getTelefon(), 
                client.getMovil(), 
                client.getFax(), 
                0,"sin idioma");
                /*
                client.getIdioma().getId(), 
                client.getIdioma().getNom()
        );    */
        return form;
    }
}
