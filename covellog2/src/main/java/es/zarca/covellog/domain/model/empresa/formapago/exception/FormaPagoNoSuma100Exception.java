package es.zarca.covellog.domain.model.empresa.formapago.exception;

import es.zarca.covellog.application.exception.*;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public class FormaPagoNoSuma100Exception extends BusinessException {

    public FormaPagoNoSuma100Exception() {
        super("La forma de pago es incorrecta, no suma 100%");
    }
    
}
