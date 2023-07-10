package es.zarca.covellog.domain.model.ubicacion;

import es.zarca.covellog.domain.model.app.Ordre;
import es.zarca.covellog.interfaces.web.app.model.Filtro;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public interface UbicacionRepository {

    Ubicacion find(Integer id);
    Ubicacion findOrFail(Integer id);
    List<Ubicacion> find(int first, int pageSize, Ordre sortOrder, Map<String, Object> filters);
    List<Ubicacion> findAll();
    int findTotalCount();
    int findTotalCount(Map<String, Object> filters);
    List<Filtro> getFiltrosPosibles();
}
