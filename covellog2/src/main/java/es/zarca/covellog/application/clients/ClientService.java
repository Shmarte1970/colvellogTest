/*
 * Proyecto Covellog2
 * 
 * Este archivo ha sido creado para ZARCA y no se puede usar sin su consentimiento.
 */
package es.zarca.covellog.application.clients;

import es.zarca.covellog.interfaces.facade.clients.facade.form.ClientForm;

/**
 *
 * @author Francisco Torralbo
 */
public interface ClientService {
    void altaClient(ClientForm client);
    void modificarClient(ClientForm client);
    void baixaClient(Integer id);   
}
