package es.zarca.covellog.application.stock.exception;

import es.zarca.covellog.application.exception.BusinessException;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public class DocumentoNotExistException extends BusinessException {

    public DocumentoNotExistException(Integer contratoId, Integer documentoId) {
        super("No se encuentra el documento con id \"" + documentoId + "\" en el contrato con Id \"" + contratoId + "\"");
    }
    
}
