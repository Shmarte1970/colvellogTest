package es.zarca.covellog.application.albaranes.form;

import es.zarca.covellog.application.stock.form.AsignacionStockForm;
import es.zarca.covellog.domain.model.stock.StockRepository;
import es.zarca.covellog.domain.model.stock.movimientos.AsignacionStock;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author francisco
 */
public class AsignacionStockAssembler {
    
     static public List<AsignacionStock> toDomainObject(List<? extends AsignacionStockForm> listaForm, StockRepository stockRepository) {
        List<AsignacionStock> lista = new ArrayList<>();
        if (listaForm != null) {
            listaForm.forEach(asignacionStockForm -> {
                lista.add(toDomainObject(asignacionStockForm, stockRepository));
            });
        }
        return lista;
    }
    
    static public AsignacionStock toDomainObject(AsignacionStockForm asignacionStockForm, StockRepository stockRepository) {
        if (asignacionStockForm == null) {
            return null;
        }
        return new AsignacionStock(
        asignacionStockForm.getBooking(), 
        asignacionStockForm.getStockId() != null ? stockRepository.findOrFail(asignacionStockForm.getStockId()) : null
        );
    }
}
