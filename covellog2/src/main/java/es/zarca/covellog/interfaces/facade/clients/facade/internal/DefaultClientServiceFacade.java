/*
 * Proyecto Covellog2
 * 
 * Este archivo ha sido creado para ZARCA y no se puede usar sin su consentimiento.
 */
package es.zarca.covellog.interfaces.facade.clients.facade.internal;

import es.zarca.covellog.application.clients.ClientService;
import es.zarca.covellog.domain.model.app.Ordre;
import es.zarca.covellog.domain.model.empresa.Cif;
import es.zarca.covellog.domain.model.clients.client.Client;
import es.zarca.covellog.domain.model.clients.client.ClientRepository;
import es.zarca.covellog.interfaces.facade.clients.facade.ClientServiceFacade;
import es.zarca.covellog.interfaces.facade.clients.facade.dto.ClientDTO;
import es.zarca.covellog.interfaces.facade.clients.facade.form.ClientForm;
import es.zarca.covellog.interfaces.facade.clients.facade.internal.assembler.ClientDtoAssembler;
import es.zarca.covellog.interfaces.facade.clients.facade.internal.assembler.ClientFormAssembler;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
@ApplicationScoped
public class DefaultClientServiceFacade implements ClientServiceFacade, Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private ClientService clientService;
    @Inject
    private ClientRepository clientRepository;

    @Override
    public void altaClient(ClientForm client) {
        clientService.altaClient(client);
    }

    @Override
    public void modificarClient(ClientForm client) {
        clientService.modificarClient(client);
    }

    @Override
    public void baixaClient(ClientForm client) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ClientForm buscarPerId(Integer id) {
        ClientFormAssembler assembler = new ClientFormAssembler();    
        return assembler.toForm(clientRepository.find(id));
    }

    @Override
    public List<ClientDTO> llistarClients() {
        List<Client> clients = clientRepository.findAll();
        List<ClientDTO> clientsDTO = new ArrayList<>(clients.size());

        ClientDtoAssembler assembler = new ClientDtoAssembler();  
        clients.forEach((client) -> {
            clientsDTO.add(assembler.toDto(client));
        });
        return clientsDTO;
    }

    @Override
    public List<ClientDTO> llistarClients(int start, int size) {
        List<Client> clients = clientRepository.findAll(start, size);
        List<ClientDTO> clientsDTO = new ArrayList<>(clients.size());

        ClientDtoAssembler assembler = new ClientDtoAssembler();  
        clients.forEach((client) -> {
            clientsDTO.add(assembler.toDto(client));
        });
        return clientsDTO;
    }

    @Override
    public List<ClientDTO> llistarClients(Map<String, Object> filters) {
        List<Client> clients = clientRepository.findAll(filters);
        List<ClientDTO> clientsDTO = new ArrayList<>(clients.size());

        ClientDtoAssembler assembler = new ClientDtoAssembler();  
        clients.forEach((client) -> {
            clientsDTO.add(assembler.toDto(client));
        });
        return clientsDTO;
    }

    @Override
    public List<ClientDTO> llistarClients(int start, int size, Ordre ordre, Map<String, Object> filters) {
        List<Client> clients = clientRepository.findAll(start, size, ordre, filters);
        List<ClientDTO> clientsDTO = new ArrayList<>(clients.size());

        ClientDtoAssembler assembler = new ClientDtoAssembler();  
        clients.forEach((client) -> {
            clientsDTO.add(assembler.toDto(client));
        });
        return clientsDTO;
    }

    @Override
    public int llistarClientsTotalCount() {
        return clientRepository.findAllTotalCount();
    }

    @Override
    public int llistarClientsTotalCount(Map<String, Object> filters) {
        return clientRepository.findAllTotalCount(filters);
    }
      
    

}
