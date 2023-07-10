/*
 * Proyecto Covellog2
 * 
 * Este archivo ha sido creado para ZARCA y no se puede usar sin su consentimiento.
 */
package es.zarca.covellog.interfaces.facade.clients.facade;

import es.zarca.covellog.domain.model.app.Ordre;
import es.zarca.covellog.interfaces.facade.clients.facade.dto.ClientDTO;
import es.zarca.covellog.interfaces.facade.clients.facade.form.ClientForm;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public interface ClientServiceFacade {

    void altaClient(ClientForm client);
    
    void modificarClient(ClientForm client);
    
    void baixaClient(ClientForm client);
    
    ClientForm buscarPerId(Integer id);
    
    List<ClientDTO> llistarClients();
    
    List<ClientDTO> llistarClients(int start, int size);

    List<ClientDTO> llistarClients(Map<String, Object> filters);
    
    List<ClientDTO> llistarClients(int start, int size, Ordre ordre, Map<String, Object> filters);
    
    int llistarClientsTotalCount();
    
    int llistarClientsTotalCount(Map<String, Object> filters);

}
