package es.zarca.covellog.comercial.domain.oferta;

import es.zarca.covellog.domain.model.app.Ordre;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public interface OfertaRepository {
    Oferta find(int id);
    Oferta findOrFail(int id);
    List<Oferta> find(int first, int pageSize, Ordre sortOrder, Map<String, Object> filters);
    List<Oferta> findAll();
    int findTotalCount();
    int findTotalCount(Map<String, Object> filters);
    void store(Oferta oferta);
    void remove(Oferta oferta);
    
}