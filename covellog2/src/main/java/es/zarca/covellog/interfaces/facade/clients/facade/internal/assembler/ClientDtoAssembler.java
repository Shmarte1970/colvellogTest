/*
 * Proyecto Covellog2
 * 
 * Este archivo ha sido creado para ZARCA y no se puede usar sin su consentimiento.
 */
package es.zarca.covellog.interfaces.facade.clients.facade.internal.assembler;

import es.zarca.covellog.domain.model.clients.client.Client;
import es.zarca.covellog.interfaces.facade.clients.facade.dto.ClientDTO;
import es.zarca.covellog.interfaces.web.helpers.Textos;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public class ClientDtoAssembler {
    
    public ClientDTO toDto(Client client) {
        if (client == null) {
            return null;
        }
        String poblacio = null;
        String provincia = null;
        String cif;
        
        if ( (client.getAdreçaFiscal() != null) && (client.getAdreçaFiscal().getPoblacio().getNom() != null) ) {
            poblacio = client.getAdreçaFiscal().getPoblacio().getNom();
            if (client.getAdreçaFiscal().getPoblacio().getProvincia() != null) {
                provincia = client.getAdreçaFiscal().getPoblacio().getProvincia().getNom();
            }
        }
        if (client.getCif() == null) {
            cif = "";
        } else {
            cif = client.getCif().getCifString();
        }

        if (poblacio == null) {
            poblacio = Textos.getTextos("Adreces").getText("NullValueLabel_poblacio");
        }
        if (provincia == null) {
            provincia = Textos.getTextos("Adreces").getText("NullValueLabel_provincia");
        }
        ClientDTO dto = new ClientDTO(
            client.getId(), 
            cif, 
            client.getNom(), 
            poblacio, 
            provincia
        );
        return dto;
    }
}
