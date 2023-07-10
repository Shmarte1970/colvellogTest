/*
 * Proyecto Covellog2
 * 
 * Este archivo ha sido creado para ZARCA y no se puede usar sin su consentimiento.
 */
package es.zarca.covellog.interfaces.facade.clients.facade.internal.assembler;

import es.zarca.covellog.domain.model.clients.client.DadesFiscalsClient;
import es.zarca.covellog.interfaces.facade.adreces.facade.form.AdreçaForm;
import es.zarca.covellog.interfaces.facade.clients.facade.form.DadesFiscalsClientForm;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public class DadesFiscalsClientFormAssembler {
    private static final Logger LOGGER = Logger.getLogger(DadesFiscalsClientFormAssembler.class.getName());
    
    public DadesFiscalsClientForm toForm(DadesFiscalsClient dadesFiscalsClient) {
        if (dadesFiscalsClient == null) {
            LOGGER.log(Level.SEVERE, "DadesFiscalsClientForm: dadesFiscalsClient NULL");
            return null;
        }  
        DadesFiscalsClientForm dadesFiscals = new DadesFiscalsClientForm(
                dadesFiscalsClient.getCif().getCifString(),
                dadesFiscalsClient.getExentIva(),
                dadesFiscalsClient.getIban(),                
                new AdreçaForm());
        return dadesFiscals;
    }
}
