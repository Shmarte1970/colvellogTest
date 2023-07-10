package es.zarca.covellog.application.exception;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public class ParametroNullException extends BusinessException {

    public ParametroNullException(String parametro) {
        super("El campo \"" + parametro + "\" es obligatorio.");
    }
   
}
