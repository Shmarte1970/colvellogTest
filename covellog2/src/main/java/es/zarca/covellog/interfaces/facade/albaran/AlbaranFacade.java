package es.zarca.covellog.interfaces.facade.albaran;

import es.zarca.covellog.application.albaranes.form.AlbaranLineaForm;
import es.zarca.covellog.application.albaranes.form.ModificarAlbaranInfoForm;
import es.zarca.covellog.application.stock.form.AsignacionStockForm;
import es.zarca.covellog.domain.model.app.Ordre;
import es.zarca.covellog.interfaces.facade.albaran.dto.AlbaranDto;
import es.zarca.covellog.interfaces.facade.albaran.dto.AlbaranSmallDto;
import es.zarca.covellog.interfaces.facade.contrato.dto.ContratoLineaDto;
import es.zarca.covellog.interfaces.facade.empresas.facade.dto.DireccionEnvioDto;
import es.zarca.covellog.interfaces.web.app.controller.BusquedaFacade;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author francisco
 */
public interface AlbaranFacade extends BusquedaFacade<AlbaranSmallDto>  {
    //Consultas
    AlbaranDto findById(Integer albaranId);
    @Override
    List<AlbaranSmallDto> find(int first, int pageSize, Ordre sortOrdre, Map<String, Object> filters);
    @Override
    int findTotalCount(Map<String, Object> filters);
    int findTotalCount();
    //Insercion y modificaciones
    AlbaranDto nuevo(ModificarAlbaranInfoForm form);
    void modificarInfoGeneral(Integer albaranId, ModificarAlbaranInfoForm form);
    void cambiarTransporte(Integer albaranId, Integer transporteId);
    void cambiarOrigen(Integer albaranId, Integer ubicacionId);
    void cambiarDestino(Integer albaranId, Integer ubicacionId);
    //control estados
    void anular(Integer albaranId);
    void cancelar(Integer albaranId);
    void activar(Integer albaranId);
    void finalizar(Integer albaranId);
    //Control global Salida/Llegada
    void marcarSalida(Integer albaranId, Date fecha);
    void desmarcarSalida(Integer albaranId);
    void marcarLlegada(Integer albaranId, Date fecha);
    void desmarcarLlegada(Integer albaranId);
    //Gestion lineas
    void lineasAnadir(AlbaranLineaForm form);
    void lineasEliminar(Integer albaranId, List<String> referencias);
    void lineasBajar(Integer albaranId, List<String> referencias);
    void lineasSubir(Integer albaranId, List<String> referencias);
    //Control linea individual Salida/Llegada
    void lineasMarcarLlegada(Integer albaranId, List<String> referencias, Date fecha);
    void lineasDesmarcarLlegada(Integer albaranId, List<String> referencias);
    void lineasMarcarSalida(Integer albaranId, List<String> referencias, Date fecha);
    void lineasDesmarcarSalida(Integer albaranId, List<String> referencias);

    public List<DireccionEnvioDto> findDireccionesEnvioPosibles(Integer albaranId);
    public List<ContratoLineaDto> getLineasContratoQueSePuedenAnadir(Integer albaranId);
    public void anadirLineasContrato(Integer contratoId, List<Integer> lineasContratoIds, Integer albaranId);

    public Integer crearAdmitase(
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
    public Integer crearEntreguese(
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
    
    public Integer generarMovimiento(
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