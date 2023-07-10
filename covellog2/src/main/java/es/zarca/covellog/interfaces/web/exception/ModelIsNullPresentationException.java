package es.zarca.covellog.interfaces.web.exception;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */

public class ModelIsNullPresentationException extends PresentationException { 
    
    public ModelIsNullPresentationException() {
        super(crearMessage(null));
    }
    
    public ModelIsNullPresentationException(String model) {
        super(crearMessage(model));
    }
    
    private static String crearMessage(String model) {
        if (model == null) {
            return "El modelo no está cargado";
        }
        return "El modelo \"" + model + "\" no está cargado.";
    }
    
}