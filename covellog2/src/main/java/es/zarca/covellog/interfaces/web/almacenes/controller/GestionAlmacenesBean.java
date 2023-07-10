package es.zarca.covellog.interfaces.web.almacenes.controller;

import java.io.Serializable;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
/**
 *
 * @author francisco
 */
@Named("gestionAlmacenesBean")
@ViewScoped
public class GestionAlmacenesBean implements Serializable {
    private Boolean mostrarListado = true;
    private String view = "lista";
    
    public GestionAlmacenesBean() {    
    }

    public Boolean getMostrarListado() {
        return mostrarListado;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

}