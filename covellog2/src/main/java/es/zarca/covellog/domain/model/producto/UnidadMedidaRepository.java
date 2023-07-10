package es.zarca.covellog.domain.model.producto;

import es.zarca.covellog.domain.model.app.Ordre;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public interface UnidadMedidaRepository {

    UnidadMedida find(String id);
    List<UnidadMedida> find(int first, int pageSize, Ordre sortOrder, Map<String, Object> filters);
    int findAllTotalCount();
    int findTotalCount(Map<String, Object> filters);
    void store(UnidadMedida unidadMedida);
    void remove(UnidadMedida unidadMedida);
    List<UnidadMedida> findAll();
    
}
