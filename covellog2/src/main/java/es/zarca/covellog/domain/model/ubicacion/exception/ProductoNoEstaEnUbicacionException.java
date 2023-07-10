package es.zarca.covellog.domain.model.ubicacion.exception;

import es.zarca.covellog.application.exception.BusinessException;
import es.zarca.covellog.domain.model.stock.Stock;
import es.zarca.covellog.domain.model.ubicacion.Ubicacion;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public class ProductoNoEstaEnUbicacionException extends BusinessException {

    public ProductoNoEstaEnUbicacionException(Stock stock, Ubicacion ubicacion) {
        super("El producto \"" + 
            stock.getNumeroSerie() + 
            "\" no esta en el almacen \"" + 
            ubicacion.getId() + 
            "-" + 
            ubicacion.getNombre() + 
            "\". Esta en \"" + 
            stock.getUbicacion().getId() + 
            "-" + 
            stock.getUbicacion().getNombre() +
            "\"."
        );           
    }
    
}
