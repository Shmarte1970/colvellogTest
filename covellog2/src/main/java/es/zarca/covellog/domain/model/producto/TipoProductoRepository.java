package es.zarca.covellog.domain.model.producto;

import es.zarca.covellog.domain.model.app.Ordre;
import es.zarca.covellog.interfaces.web.app.model.Filtro;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public interface TipoProductoRepository {

    TipoProducto find(String id);
    TipoProducto findOrFail(String id);
    List<TipoProducto> find(int first, int pageSize, Ordre sortOrder, Map<String, Object> filters);
    List<TipoProducto> findAll();
    int findTotalCount();
    int findTotalCount(Map<String, Object> filters);
    void store(TipoProducto tipoProducto);
    void remove(TipoProducto tipoProducto);
    List<Filtro> getFiltrosPosibles();
    
}
