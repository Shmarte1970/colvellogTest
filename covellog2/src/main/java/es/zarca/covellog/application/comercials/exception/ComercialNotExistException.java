package es.zarca.covellog.application.comercials.exception;

import es.zarca.covellog.application.exception.BusinessException;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public class ComercialNotExistException extends BusinessException {

    public ComercialNotExistException(Integer comercialId) {
        super(crearMessageIdEmpresa(comercialId));
    }
       
    private static String crearMessageIdEmpresa(Integer comercialId) {
        if (comercialId == null) {
            return "El comercial con Id \"null\" no existe.";
        }
        return "El comercial con Id \"" + comercialId.toString() + "\" no existe.";
    }
}
