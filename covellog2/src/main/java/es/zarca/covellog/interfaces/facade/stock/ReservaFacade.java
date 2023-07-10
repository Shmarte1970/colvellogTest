package es.zarca.covellog.interfaces.facade.stock;

import es.zarca.covellog.application.stock.form.AsignacionStockForm;
import es.zarca.covellog.application.stock.form.ReservaForm;
import es.zarca.covellog.domain.model.app.Ordre;
import es.zarca.covellog.interfaces.facade.stock.dto.StockSeleccionDto;
import es.zarca.covellog.interfaces.facade.stock.dto.reservas.ReservaDto;
import es.zarca.covellog.interfaces.facade.stock.dto.reservas.ReservaMiniDto;
import es.zarca.covellog.interfaces.web.app.controller.BusquedaFacade;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author francisco
 */
public interface ReservaFacade extends BusquedaFacade<ReservaDto>{
    
    @Override
    List<ReservaDto> find(int first, int pageSize, Ordre sortOrdre, Map<String, Object> filters);
    List<ReservaDto> findAll();
    ReservaDto findById(int id);
    @Override
    int findTotalCount(Map<String, Object> filters);
    int findTotalCount(); 
    
    public ReservaDto nuevoAdmitase(ReservaForm form);
    public ReservaDto nuevoEntreguese(ReservaForm form);
    public void modificar(Integer reservaId, ReservaForm form);
    public void anular(Integer reservaId);
    public void activar(Integer reservaId);
    public void reabrir(Integer reservaId);
    public void eliminar(Integer reservaId);
    public ReservaDto getAdmitaseAutocomplete(StockSeleccionDto seleccion);
    public ReservaDto getEntregueseAutocomplete(StockSeleccionDto seleccion);

    public void generarMovimiento(Integer reservaId, Date fecha, List<AsignacionStockForm> asignaciones);
    public void generarMovimiento(Integer reservaId, Date fecha, List<AsignacionStockForm> asignaciones, Integer transportistaId, String transportistaNombre, String chofer, String matricula, String observaciones);
        
}