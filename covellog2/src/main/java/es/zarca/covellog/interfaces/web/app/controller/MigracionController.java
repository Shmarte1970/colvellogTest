package es.zarca.covellog.interfaces.web.app.controller;

import es.zarca.covellog.interfaces.facade.migracion.MigracionServiceFacade;
import es.zarca.covellog.interfaces.web.app.model.Dialogo;
import es.zarca.covellog.interfaces.web.helpers.StringUtil;
import java.io.Serializable;
import java.util.Stack;
import java.util.logging.Logger;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author francisco
 */
@Named("migracionController")
@ViewScoped
public class MigracionController implements Serializable {
    private static final Logger LOGGER = Logger.getLogger(MigracionController.class.getName());
    @Inject
    MigracionServiceFacade facade;

    public void migracion() {
        facade.migracion();
    }

}