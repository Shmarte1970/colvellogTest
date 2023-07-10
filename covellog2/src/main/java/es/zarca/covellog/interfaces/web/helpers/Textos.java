package es.zarca.covellog.interfaces.web.helpers;

import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Textos {
    private static final Logger LOGGER = Logger.getLogger(Textos.class.getName());
    private static final String PREFIX_MODUL = "es.zarca.covellog.interfaces.web";
    private static final String SUFIX_MODUL = "Bundle";
    private ResourceBundle bundle;
    private String modul;
    
    public static Textos getTextos(String modul) {
        if (modul == null) {
            LOGGER.log(Level.INFO, "No s'ha indicat cap mòdul per obtenir els textos d'idioma.");
            throw new NullPointerException("No s'ha indicat cap mòdul per obtenir els textos d'idioma.");
        }
        try {
            Textos textos = new Textos();
            textos.setModul(modul);
            return textos;
        } catch (Exception ex) {
            LOGGER.log(Level.INFO, "No es troben el textes del mòdul {0} => {1}", new Object[]{modul, ex.getMessage()});
            throw new RuntimeException("No es troben el textes del mòdul " + modul + " => " + ex.getMessage());
        }
    }

    private Textos() {
        modul = null;
    }   
    
    private void setModul(String modul) {
        if (modul == null) {
            LOGGER.log(Level.INFO, "No s'ha indicat cap mòdul per obtenir els textos d'idioma.");
            throw new NullPointerException("No s'ha indicat cap mòdul per obtenir els textos d'idioma.");
        }
        if (!modul.equals(this.modul)) {
            try {
                bundle = ResourceBundle.getBundle(PREFIX_MODUL + "." + modul + SUFIX_MODUL);
                this.modul = modul;
            } catch (Exception ex) {
                LOGGER.log(Level.INFO, "No es troben el textes del mòdul {0} => {1}", new Object[]{modul, ex.getMessage()});
                throw new RuntimeException("No es troba el repositori de textos del mòdul \"" + modul + "\" => " + ex.getMessage());
            }
        }
    }
            
    public String getText(String modul, String text) {
        setModul(modul);
        return getText(text);
    }
    
    public String getText(String text) {
        if (text == null) {
            LOGGER.log(Level.INFO, "No s'ha indicat cap text per trobar als textes.", new Object[]{modul});
            return "ERROR";
        }
        try {
            return bundle.getString(text);
        } catch (Exception ex) {
            LOGGER.log(Level.INFO, "No es troba el text \"{0}\" del mòdul \"{1}\" => {2}", new Object[]{text, modul, ex.getMessage()});
            return text;
        }  
    }
    
}
