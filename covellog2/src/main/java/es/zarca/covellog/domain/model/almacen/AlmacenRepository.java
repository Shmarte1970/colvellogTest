package es.zarca.covellog.domain.model.almacen;

import es.zarca.covellog.domain.model.app.Ordre;
import es.zarca.covellog.interfaces.web.app.model.Filtro;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public interface AlmacenRepository {

    Almacen find(Integer id);
    Almacen findOrFail(Integer id);
    List<Almacen> find(int first, int pageSize, Ordre sortOrder, Map<String, Object> filters);
    List<Almacen> findAll();
    int findTotalCount();
    int findTotalCount(Map<String, Object> filters);
    void store(Almacen almacen);
    void remove(Almacen almacen);
    List<Filtro> getFiltrosPosibles();
}
