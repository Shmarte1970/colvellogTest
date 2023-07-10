package es.zarca.covellog.application.stock;

import es.zarca.covellog.application.stock.form.ModificarStockForm;
import es.zarca.covellog.application.stock.form.NuevoStockForm;
import es.zarca.covellog.domain.model.app.Tags;
import java.util.Date;

/**
 *
 * @author francisco
 */
public interface GestionStockService {
    
    void alta(NuevoStockForm form);
    void modificar(Integer stockId, ModificarStockForm form);
    
    void traslado(Integer stockId, Date fecha, String observaciones, Integer ubicacionId);
    void deshacerUltimoTraslado(Integer stockId);
    
    void cambiarFlota(Integer stockId, String observaciones, String flotaId);
    void deshacerUltimoCambioFlota(Integer stockId);
    
    void cambiarPropietario(Integer stockId, Date fecha, String observaciones, Integer propietarioId, Tags tags);
    void deshacerUltimoCambioPropietario(Integer stockId);
    
    void cambiarCondicion(Integer stockId, Date fecha, String observaciones, String condicionId);
    
    void bloquear(Integer stockId, Date fecha, String observaciones);
    void desbloquear(Integer stockId, Date fecha, String observaciones);
    void deshacerUltimoBloqueo(Integer stockId);
    
    void baja(Integer stockId, Date fecha, String observaciones);
    void reincorporarBaja(Integer stockId, Date fecha, String observaciones);
    
}