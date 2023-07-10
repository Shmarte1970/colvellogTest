package es.zarca.covellog.interfaces.web.proves.controller;


import java.io.Serializable;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import es.zarca.covellog.interfaces.facade.crm.peticionscontacte.facade.ImportarPeticionsContacteServiceFacade;
import es.zarca.covellog.interfaces.web.helpers.Textos;

@Named("provesController")
@SessionScoped
public class ProvesController implements Serializable {
    private static final Logger LOGGER = Logger.getLogger(ProvesController.class.getName());
    private static final Textos TEXTOS = Textos.getTextos("Clients");
    
    //@EJB
    @Inject
    private ImportarPeticionsContacteServiceFacade importarPeticionsContacteServiceFacade;

   
    
    public ProvesController() {
    }

    public void boto1() {
        System.out.println("boto 1");
        importarPeticionsContacteServiceFacade.importarPeticionsContactePendents();
    }

}