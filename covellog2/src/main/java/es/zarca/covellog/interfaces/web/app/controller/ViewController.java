package es.zarca.covellog.interfaces.web.app.controller;

import java.io.Serializable;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author francisco
 */
@Named("viewController")
@SessionScoped
public class ViewController implements Serializable {
    private static final Logger LOGGER = Logger.getLogger(ViewController.class.getName());

    private Integer width = 0;
    private Integer height = 0;
    private Integer contentWidth = 0;
    private Integer contentHeight = 0;
    private Date fechaActualizacion = new Date();
    
    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
        fechaActualizacion = new Date();
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
        fechaActualizacion = new Date();
    }

    public Integer getContentWidth() {
        return contentWidth;
    }

    public void setContentWidth(Integer contentWidth) {
        this.contentWidth = contentWidth;
        fechaActualizacion = new Date();
    }

    public Integer getContentHeight() {
        return contentHeight;
    }

    public void setContentHeight(Integer contentHeight) {
        this.contentHeight = contentHeight;
        fechaActualizacion = new Date();
    }

    public Date getFechaActualizacion() {
        return fechaActualizacion;
    }
    
    public void updateSize() {
        //PrimeFaces.current().ajax().update("@(.view-controller-panel)");
         LOGGER.log(Level.SEVERE, "update size");
    }

}