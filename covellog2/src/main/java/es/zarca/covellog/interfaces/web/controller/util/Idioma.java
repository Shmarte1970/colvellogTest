package es.zarca.covellog.interfaces.web.controller.util;

import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Idioma {
    private static final Logger LOGGER = Logger.getLogger(Idioma.class.getName());
    private static final String PACKAGE_ARREL = "es.zarca.covellog.interfaces.web";
    
    public static String getText(String modul, String text) {
        try {
            text = ResourceBundle.getBundle(PACKAGE_ARREL + "." + modul + "Bundle").getString(text);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
        }
        return text;
    }
}
