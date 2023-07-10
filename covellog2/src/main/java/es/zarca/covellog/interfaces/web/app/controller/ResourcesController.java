package es.zarca.covellog.interfaces.web.app.controller;

import es.zarca.covellog.infrastructure.log.Capa;
import es.zarca.covellog.infrastructure.log.FunctionLog;
import java.io.Serializable;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author francisco
 */
@Named("resourcesController")
@RequestScoped
public class ResourcesController implements Serializable {
    private static final Logger LOGGER = Logger.getLogger(ResourcesController.class.getName());

    public String getImage(String file) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        log.info("/covellog2/faces/resources/images/" + file);        
        log.finish();
        return "/covellog2/faces/resources/images/" + file;
    }

}