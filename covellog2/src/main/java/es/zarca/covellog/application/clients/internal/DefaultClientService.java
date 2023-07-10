/*
 * Proyecto Covellog2
 * 
 * Este archivo ha sido creado para ZARCA y no se puede usar sin su consentimiento.
 */
package es.zarca.covellog.application.clients.internal;

import es.zarca.covellog.application.clients.ClientService;
import es.zarca.covellog.application.clients.exception.ClientNotExistException;
import es.zarca.covellog.application.exception.BusinessException;
import es.zarca.covellog.domain.model.adreces.adreça.Adreça;
import es.zarca.covellog.domain.model.adreces.adreça.CodigoPostal;
import es.zarca.covellog.domain.model.adreces.poblacio.Poblacio;
import es.zarca.covellog.domain.model.adreces.poblacio.PoblacioRepository;
import es.zarca.covellog.domain.model.empresa.Cif;
import es.zarca.covellog.domain.model.clients.client.Client;
import es.zarca.covellog.domain.model.clients.client.ClientRepository;
import es.zarca.covellog.domain.model.clients.client.Email;
import es.zarca.covellog.domain.model.clients.client.PersonaContacte;
import es.zarca.covellog.domain.model.idiomas.idioma.CodigoIdioma;
import es.zarca.covellog.domain.model.idiomas.idioma.Idioma;
import es.zarca.covellog.domain.model.idiomas.idioma.IdiomaRepository;
import es.zarca.covellog.interfaces.facade.clients.facade.form.ClientForm;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
@Stateless
public class DefaultClientService implements ClientService {

    

    @Inject
    private ClientRepository clientRepository;
    @Inject
    private PoblacioRepository poblacioRepository;
    @Inject
    private IdiomaRepository idiomaRepository;
    
    
    @Override
    public void altaClient(ClientForm client) {
        Client domainClient = new Client();
        domainClient.setNom(client.getNom());
        domainClient.setAlias(client.getAlias());
        Poblacio poblacio = poblacioRepository.find(10);
       /* CodiPostal codiPostal = new CodiPostal(client.getAdreçaFiscal().getCodiPostal());
        Adreça adreça = new Adreça(client.getDadesFiscals().getAdreça().getAdreça(), codiPostal, poblacio);
        DadesFiscalsClient dadesFiscals = new DadesFiscalsClient(
                    new Cif(client.getDadesFiscals().getCif()), 
                    client.getDadesFiscals().getExentIva(), 
                    client.getDadesFiscals().getIban(), 
                    adreça
        );
        domainClient.setDadesFiscals(dadesFiscals);*/
        clientRepository.store(domainClient);
    }

    @Override
    public void modificarClient(ClientForm client) {
        Client domainClient = clientRepository.find(client.getId());
        domainClient.setNom(client.getNom());
        domainClient.setAlias(client.getAlias());
        domainClient.setCif(new Cif(client.getCif()));
        domainClient.setExentIva(client.getExentIva());
        domainClient.setIban(client.getIban());
        
        Poblacio poblacio = poblacioRepository.find(10);
        domainClient.setAdreçaFiscal(new Adreça(client.getAdreçaFiscal().getAdreça(), new CodigoPostal(client.getAdreçaFiscal().getCodiPostal()), poblacio));
        
        domainClient.setHorari(client.getHorari());
        Idioma idioma = idiomaRepository.find(new CodigoIdioma("es_ES"));
        if (idioma == null) {
            throw new BusinessException("El idioma no existe") {};
        }
        domainClient.setIdioma(idioma);
        domainClient.setTelefon(client.getTelefon());
        domainClient.setMovil(client.getMovil());
        domainClient.setFax(client.getFax());
        domainClient.setEmail(new Email(client.getEmail()));
        domainClient.setWeb(client.getWeb());
        domainClient.setAdreçaContacte(new Adreça(client.getAdreçaContacte().getAdreça(), new CodigoPostal(client.getAdreçaContacte().getCodiPostal()), poblacio));
        
        domainClient.setPersonaContactePrincipal(new PersonaContacte());
        /*CodiPostal codiPostal = new CodiPostal(client.getDadesFiscals().getAdreça().getCodiPostal());
        Adreça adreça = new Adreça(client.getDadesFiscals().getAdreça().getAdreça(), codiPostal, poblacio);
        DadesFiscalsClient dadesFiscals = new DadesFiscalsClient(
                    new Cif(client.getDadesFiscals().getCif()), 
                    client.getDadesFiscals().getExentIva(), 
                    client.getDadesFiscals().getIban(), 
                    adreça
        );
        domainClient.setDadesFiscals(dadesFiscals);*/
        clientRepository.store(domainClient);
    }

    @Override
    public void baixaClient(Integer id) {
        Client client = clientRepository.find(id);
        if (client == null) {
            throw new ClientNotExistException(id);
        }
        clientRepository.remove(client);
    }

}
