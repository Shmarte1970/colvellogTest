package es.zarca.covellog.domain.model.stock.reservas;

import es.zarca.covellog.domain.model.app.Ordre;
import es.zarca.covellog.domain.model.stock.reservas.Reserva;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public interface ReservaRepository {

    Reserva find(int id);
    Reserva findOrFail(int id);
    List<Reserva> find(int first, int pageSize, Ordre sortOrder, Map<String, Object> filters);
    List<Reserva> findAll();
    int findTotalCount();
    int findTotalCount(Map<String, Object> filters);
    
    void store(Reserva reserva);
    void remove(Reserva reserva);
    
}