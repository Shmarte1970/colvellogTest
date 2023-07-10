package es.zarca.covellog.domain.model.empresa.formapago;

import es.zarca.covellog.domain.model.app.Ordre;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public interface TipoPagoRepository {

    TipoPago find(Integer id);
    List<TipoPago> findAll();
    List<TipoPago> findAll(int start, int size);
    int findAllTotalCount();
    List<TipoPago> findAll(Ordre ordre, Map<String, Object> filters);
    List<TipoPago> findAll(int start, int size, Ordre ordre, Map<String, Object> filters);
    int findAllTotalCount(Map<String, Object> filters);
    void store(TipoPago tipoPago);
    void remove(TipoPago tipoPago);
    
}
