package es.zarca.covellog.domain.model.stock;

import es.zarca.covellog.domain.model.app.Ordre;
import es.zarca.covellog.interfaces.facade.stock.dto.IdCantidadDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public interface StockRepository {

    Stock find(int id);
    Stock findOrFail(int id);
    Stock findOrFail(String numSerie);
    List<Stock> find(int first, int pageSize, Ordre sortOrder, Map<String, Object> filters);
    List<Stock> findAll();
    int findTotalCount();
    int findTotalCount(Map<String, Object> filters);
    
   /* StockPorLoteDto findPorLote(int id);
    StockPorLoteDto findOrFailPorLote(int id);
    List<StockPorLoteDto> findPorLote(int first, int pageSize, Ordre sortOrder, Map<String, Object> filters);
    int findPorLoteTotalCount();
    int findPorLoteTotalCount(Map<String, Object> filters);
    
    StockPorAlmacenDto findPorAlmacen(int id);
    StockPorAlmacenDto findOrFailPorAlmacen(int id);
    List<StockPorAlmacenDto> findPorAlmacen(int first, int pageSize, Ordre sortOrder, Map<String, Object> filters);
    int findPorAlmacenTotalCount();
    int findPorAlmacenTotalCount(Map<String, Object> filters);
    */
    void store(Stock stock);
    void remove(Stock stock);
    List<IdCantidadDto> findLotes(String filtro);
    
}