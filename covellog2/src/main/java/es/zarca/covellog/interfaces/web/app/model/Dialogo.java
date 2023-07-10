package es.zarca.covellog.interfaces.web.app.model;

import es.zarca.covellog.interfaces.web.helpers.StringUtil;

/**
 *
 * @author francisco
 */
public class Dialogo {
    private String id;
    private String plantilla;
    private Object model;

    public Dialogo(String id, String plantilla, Object model) {
        this.id = id;
        this.plantilla = plantilla;
        this.model = model;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public String getPlantilla() {
        System.out.println("DIALOGO GET PLANTILLA: " + plantilla);
        return plantilla;
    }

    public void setPlantilla(String plantilla) {
        this.plantilla = plantilla;
    }

    public Object getModel() {
        return model;
    }

    public void setModel(Object model) {
        this.model = model;
    }
    
}
