package es.zarca.covellog.application.exception;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public class ValidationNotNull extends ValidationBusinessException {

    public ValidationNotNull(String field) {
        super("El valor de \"" + field + "\" no puede ser nulo.");
    }
   
}
