/*
 * Proyecto Covellog2
 * 
 * Este archivo ha sido creado para ZARCA y no se puede usar sin su consentimiento.
 */
package es.zarca.covellog.interfaces.facade.clients.facade.internal.assembler;

import es.zarca.covellog.domain.model.clients.client.PersonaContacte;
import es.zarca.covellog.interfaces.facade.clients.facade.form.DadesContacteForm;
import es.zarca.covellog.interfaces.facade.clients.facade.form.PersonaContacteForm;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public class PersonaContacteFormAssembler {
    private static final Logger LOGGER = Logger.getLogger(PersonaContacteFormAssembler.class.getName());
    
    public PersonaContacteForm toForm(PersonaContacte personaContacte) {
        if (personaContacte == null) {
            LOGGER.log(Level.SEVERE, "PersonaContacteFormAssembler: Cliente NULL");
            return null;
        }               
        return new PersonaContacteForm(personaContacte.getNom(), personaContacte.getCarrec(), personaContacte.getTelefon(), personaContacte.getMovil(), personaContacte.getFax(), personaContacte.getEmail(), personaContacte.getObservacions());
    }
}
