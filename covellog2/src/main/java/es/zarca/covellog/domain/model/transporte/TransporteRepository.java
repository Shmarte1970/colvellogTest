package es.zarca.covellog.domain.model.transporte;

import es.zarca.covellog.domain.model.app.Ordre;
import es.zarca.covellog.interfaces.web.app.model.Filtro;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public interface TransporteRepository {

    Transporte find(Integer id);
    Transporte findOrFail(Integer id);
    List<Transporte> find(int first, int pageSize, Ordre sortOrder, Map<String, Object> filters);
    List<Transporte> findAll();
    int findTotalCount();
    int findTotalCount(Map<String, Object> filters);
    void store(Transporte transporte);
    void remove(Transporte transporte);
    List<Filtro> getFiltrosPosibles();
    
}
