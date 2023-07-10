package es.zarca.covellog.domain.model.contrato;

import es.zarca.covellog.domain.model.app.Ordre;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public interface ContratoRepository {
    Contrato find(int id);
    Contrato findOrFail(int id);
    List<Contrato> find(int first, int pageSize, Ordre sortOrder, Map<String, Object> filters);
    List<Contrato> findAll();
    int findTotalCount();
    int findTotalCount(Map<String, Object> filters);
    void store(Contrato contrato);
    void remove(Contrato contrato);
    
}