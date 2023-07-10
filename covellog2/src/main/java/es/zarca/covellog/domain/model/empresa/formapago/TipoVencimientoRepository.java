package es.zarca.covellog.domain.model.empresa.formapago;

import es.zarca.covellog.domain.model.app.Ordre;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public interface TipoVencimientoRepository {

    TipoVencimiento find(Integer id);
    List<TipoVencimiento> findAll();
    List<TipoVencimiento> findAll(int start, int size);
    int findAllTotalCount();
    List<TipoVencimiento> findAll(Ordre ordre, Map<String, Object> filters);
    List<TipoVencimiento> findAll(int start, int size, Ordre ordre, Map<String, Object> filters);
    int findAllTotalCount(Map<String, Object> filters);
    void store(TipoVencimiento tipoVencimiento);
    void remove(TipoVencimiento tipoVencimiento);
    
}
