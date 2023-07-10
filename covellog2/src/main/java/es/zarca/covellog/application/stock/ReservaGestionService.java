package es.zarca.covellog.application.stock;

import es.zarca.covellog.application.stock.form.AsignacionStockForm;
import es.zarca.covellog.application.stock.form.ReservaForm;
import es.zarca.covellog.domain.model.stock.reservas.Reserva;
import es.zarca.covellog.interfaces.facade.stock.dto.StockAsignacionDto;
import es.zarca.covellog.interfaces.facade.stock.dto.reservas.ReservaDto;
import java.util.Date;
import java.util.List;

/**
 *
 * @author francisco
 */
public interface ReservaGestionService {
    
    public void modificar(Integer reservaId, ReservaForm form);
    public void anular(Integer reservaId);
    public void reabrir(Integer reservaId);
    public void activar(Integer reservaId);
    public void eliminar(Integer reservaId);
    public ReservaDto getEntradaAutocompleteFromStockId(Integer StockId);
    public Reserva nuevoAdmitase(ReservaForm form);
    public Reserva nuevoEntreguese(ReservaForm form);
    public void generarMovimientos(Integer reservaId, Date fecha, List<AsignacionStockForm> asignaciones);
    public void generarMovimientos(
        Integer reservaId, 
        Date fecha, 
        List<AsignacionStockForm> asignaciones, 
        Integer transportistaId, 
        String transportistaNombre, 
        String chofer, 
        String matricula, 
        String observaciones
    );

}