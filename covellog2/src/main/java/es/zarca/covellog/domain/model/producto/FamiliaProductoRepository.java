package es.zarca.covellog.domain.model.producto;

import es.zarca.covellog.domain.model.app.Ordre;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public interface FamiliaProductoRepository {

    FamiliaProducto find(Integer id);
    List<FamiliaProducto> find(int first, int pageSize, Ordre sortOrder, Map<String, Object> filters);
    List<FamiliaProducto> findAll();
    int findAllTotalCount();
    int findTotalCount(Map<String, Object> filters);
    void store(FamiliaProducto familiaProducto);
    void remove(FamiliaProducto familiaProducto);
    
}
