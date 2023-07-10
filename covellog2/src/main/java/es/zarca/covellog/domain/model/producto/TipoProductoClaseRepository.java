package es.zarca.covellog.domain.model.producto;

import es.zarca.covellog.domain.model.app.Ordre;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public interface TipoProductoClaseRepository {

    TipoProductoClase find(String id);
    List<TipoProductoClase> find(int first, int pageSize, Ordre sortOrder, Map<String, Object> filters);
    List<TipoProductoClase> findAll();
    int findAllTotalCount();
    int findTotalCount(Map<String, Object> filters);
    void store(TipoProductoClase tipoProductoClase);
    void remove(TipoProductoClase tipoProductoClase);
    
}
