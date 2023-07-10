package es.zarca.covellog.interfaces.facade.stock;

import es.zarca.covellog.domain.model.app.Ordre;
import es.zarca.covellog.interfaces.facade.stock.dto.TipoProductoDto;
import es.zarca.covellog.interfaces.web.app.controller.BusquedaFacade;
import java.util.List;
import java.util.Map;

/**
 *
 * @author francisco
 */
public interface ComplementosFacade extends BusquedaFacade<TipoProductoDto> {
    @Override
    List<TipoProductoDto> find(int first, int pageSize, Ordre sortOrdre, Map<String, Object> filters);
    @Override
    int findTotalCount(Map<String, Object> filters);
    int findTotalCount();
}
