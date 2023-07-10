package es.zarca.covellog.interfaces.facade.stock;

import es.zarca.covellog.application.stock.form.MovimientoForm;
import es.zarca.covellog.domain.model.app.Ordre;
import es.zarca.covellog.interfaces.facade.stock.dto.MovimientoDto;
import es.zarca.covellog.interfaces.web.app.controller.BusquedaFacade;
import java.util.List;
import java.util.Map;

/**
 *
 * @author francisco
 */
public interface MovimientoFacade extends BusquedaFacade<MovimientoDto>{
    
    @Override
    List<MovimientoDto> find(int first, int pageSize, Ordre sortOrdre, Map<String, Object> filters);
    List<MovimientoDto> findAll();
    MovimientoDto findById(int id);
    
    @Override
    int findTotalCount(Map<String, Object> filters);
    int findTotalCount(); 
    public void nuevo(MovimientoForm form);
    public void modificar(Integer movimientoId, MovimientoForm form);
    public void anular(Integer movimientoId);
    public void lanzar(Integer movimientoId);
    public void eliminar(Integer movimientoId);

    public MovimientoDto getEntradaAutocompleteFromStockId(Integer stockId);

    public MovimientoDto entrada(MovimientoForm form);
    public MovimientoDto salida(MovimientoForm form);
        
}