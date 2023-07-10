package es.zarca.covellog.interfaces.web.app.controller;

import java.io.Serializable;
import java.util.logging.Logger;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author francisco
 */
@Named("navigationController")
@ViewScoped
public class NavigationController implements Serializable {
    private static final Logger LOGGER = Logger.getLogger(NavigationController.class.getName());

    private String content;
    
    public NavigationController() {
        content = "lista";
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}