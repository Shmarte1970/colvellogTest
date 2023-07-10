package es.zarca.covellog.application.stock;

import es.zarca.covellog.application.stock.form.MovimientoForm;
import es.zarca.covellog.domain.model.stock.movimientos.Movimiento;
import es.zarca.covellog.interfaces.facade.stock.dto.MovimientoDto;

/**
 *
 * @author francisco
 */
public interface MovimientoGestionService {
    
    public Movimiento nuevo(MovimientoForm form);
    public void modificar(Integer movimientoId, MovimientoForm form);
    public void anular(Integer movimientoId);
    public void lanzar(Integer movimientoId);
    public void eliminar(Integer movimientoId);

    public MovimientoDto getEntradaAutocompleteFromStockId(Integer StockId);

    public Movimiento entrada(MovimientoForm form);

    public Movimiento salida(MovimientoForm form);
    
}