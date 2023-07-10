package es.zarca.covellog.domain.model.albaran.exception;

import es.zarca.covellog.application.exception.BusinessException;
import es.zarca.covellog.domain.model.albaran.Albaran;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public class AsignarDestinoEnEstadoNoBorradorException extends BusinessException {

    public AsignarDestinoEnEstadoNoBorradorException(Albaran albaran) {
        super("No puede cambiar el origen del albaran \"" + albaran.getFriendlyId()  + "\" en estado \"" + albaran.getEstado() + "\". Solo se permite el cambio en estado BORRADOR.");
    }

}