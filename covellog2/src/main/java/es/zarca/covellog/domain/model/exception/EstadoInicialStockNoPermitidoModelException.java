package es.zarca.covellog.domain.model.exception;

import es.zarca.covellog.application.exception.BusinessException;
import es.zarca.covellog.domain.model.stock.StockEstado;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public class EstadoInicialStockNoPermitidoModelException extends BusinessException {

    public EstadoInicialStockNoPermitidoModelException(StockEstado estado) {
        super("Los nuevos stocks no pueden tener el estado inicial " + estado.getNombre() + ". Solo se admiten los estados iniciales DISPONIBLE/BLOQUEADO");
    }
    
}
