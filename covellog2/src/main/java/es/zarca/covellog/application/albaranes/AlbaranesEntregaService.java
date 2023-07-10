package es.zarca.covellog.application.albaranes;

import es.zarca.covellog.application.albaranes.form.AlbaranLineaForm;
import es.zarca.covellog.application.albaranes.form.ModificarAlbaranInfoForm;
import es.zarca.covellog.application.stock.form.AsignacionStockForm;
import es.zarca.covellog.domain.model.albaran.Albaran;
import es.zarca.covellog.domain.model.stock.reservas.Reserva;
import java.util.Date;
import java.util.List;


/**
 *
 * @author francisco
 */
public interface AlbaranesEntregaService {
    
    Albaran nuevo(ModificarAlbaranInfoForm form);
    void modificarInfoGeneral(Integer albaranId, ModificarAlbaranInfoForm form);
    void cambiarTransporte(Integer albaranId, Integer transporteId);
    void cambiarOrigen(Integer albaranId, Integer ubicacionId);
    void cambiarDestino(Integer albaranId, Integer ubicacionId);
    //control estados
    void anular(Integer albaranId);
    void reabrir(Integer albaranId);
    void activar(Integer albaranId);
    void finalizar(Integer albaranId);
    //Control global Salida/Llegada
    void marcarSalida(Integer albaranId, Date fecha);
    void desmarcarSalida(Integer albaranId);
    void marcarLlegada(Integer albaranId, Date fecha);
    void desmarcarLlegada(Integer albaranId);
    //Gestion lineas
    void lineasAnadir(Integer albaranId, AlbaranLineaForm form);
    void lineasEliminar(Integer albaranId, List<String> referencias);
    void lineasBajar(Integer albaranId, List<String> referencias);
    void lineasSubir(Integer albaranId, List<String> referencias);
    //Control linea individual Salida/Llegada
    void lineasMarcarLlegada(Integer albaranId, List<String> referencias, Date fecha);
    void lineasDesmarcarLlegada(Integer albaranId, List<String> referencias);
    void lineasMarcarSalida(Integer albaranId, List<String> referencias, Date fecha);
    void lineasDesmarcarSalida(Integer albaranId, List<String> referencias);

    public Reserva crearEntreguese(
        Integer albaranId, 
        Boolean activar,
        Date fecha,
        List<AsignacionStockForm> asignaciones, 
        Integer transportistaId, 
        String transportistaNombre, 
        String chofer, 
        String matricula, 
        String observaciones
    );

    public Reserva crearAdmitase(
        Integer albaranId, 
        Boolean activar,
        Date fecha,
        List<AsignacionStockForm> asignaciones, 
        Integer transportistaId, 
        String transportistaNombre, 
        String chofer, 
        String matricula, 
        String observaciones
    );

    public Reserva generarMovimiento(
        Integer albaranId, 
        Date fecha, 
        List<AsignacionStockForm> asignaciones, 
        Integer transportistaId, 
        String transportistaNombre, 
        String chofer, 
        String matricula, 
        String observaciones
    );
}