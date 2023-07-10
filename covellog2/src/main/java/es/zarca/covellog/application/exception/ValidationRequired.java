package es.zarca.covellog.application.exception;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public class ValidationRequired extends ValidationBusinessException {

    public ValidationRequired(String field) {
        super("El campo \"" + field + "\" es obligatorio");
    }
   
}
