package es.zarca.covellog.interfaces.facade.stock;

import es.zarca.covellog.application.stock.form.ModificarStockForm;
import es.zarca.covellog.application.stock.form.MovimientoForm;
import es.zarca.covellog.application.stock.form.NuevoStockForm;
import es.zarca.covellog.domain.model.app.Ordre;
import es.zarca.covellog.interfaces.facade.stock.dto.CondicionDto;
import es.zarca.covellog.interfaces.facade.stock.dto.EstadoDto;
import es.zarca.covellog.interfaces.facade.stock.dto.FlotaDto;
import es.zarca.covellog.interfaces.facade.stock.dto.IdCantidadDto;
import es.zarca.covellog.interfaces.facade.stock.dto.listadohistorico.StockListadoHistoricoPropietariosDto;
import es.zarca.covellog.interfaces.facade.stock.dto.StockDto;
import es.zarca.covellog.interfaces.facade.stock.dto.listadohistorico.StockListadoHistoricoCondicionesDto;
import es.zarca.covellog.interfaces.facade.stock.dto.listadohistorico.StockListadoHistoricoContratosDto;
import es.zarca.covellog.interfaces.facade.stock.dto.listadohistorico.StockListadoHistoricoEstadosDto;
import es.zarca.covellog.interfaces.facade.stock.dto.listadohistorico.StockListadoHistoricoFlotasDto;
import es.zarca.covellog.interfaces.facade.stock.dto.listadohistorico.StockListadoHistoricoLotesDto;
import es.zarca.covellog.interfaces.facade.stock.dto.listadohistorico.StockListadoHistoricoNumerosSerieDto;
import es.zarca.covellog.interfaces.facade.stock.dto.listadohistorico.StockListadoHistoricoTiposProductoDto;
import es.zarca.covellog.interfaces.facade.stock.dto.listadohistorico.StockListadoHistoricoUbicacionesDto;
import es.zarca.covellog.interfaces.facade.stock.dto.StockPorAlmacenDto;
import es.zarca.covellog.interfaces.facade.stock.dto.StockPorLoteDto;
import es.zarca.covellog.interfaces.facade.stock.dto.TipoProductoDto;
import es.zarca.covellog.interfaces.facade.ubicaciones.dto.UbicacionDto;
import es.zarca.covellog.interfaces.web.app.controller.BusquedaFacade;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author francisco
 */
public interface StockFacade extends BusquedaFacade<StockDto>{
    @Override
    List<StockDto> find(int first, int pageSize, Ordre sortOrdre, Map<String, Object> filters);
    List<StockDto> findAll();
    StockDto findById(int id);
    @Override
    int findTotalCount(Map<String, Object> filters);
    int findTotalCount(); 
    List<StockPorLoteDto> findPorLote(int first, int pageSize, Ordre sortOrdre, Map<String, Object> filters);
    List<StockPorLoteDto> findPorLoteAll();
    StockPorLoteDto findPorLoteById(String id);
    int findPorLoteTotalCount(Map<String, Object> filters);
    int findPorLoteTotalCount();    
    List<StockPorAlmacenDto> findPorAlmacen(int first, int pageSize, Ordre sortOrdre, Map<String, Object> filters);
    List<StockPorAlmacenDto> findPorAlmacenAll();
    StockPorAlmacenDto findPorAlmacenById(String id);
    int findPorAlmacenTotalCount(Map<String, Object> filters);
    int findPorAlmacenTotalCount();
    
    List<TipoProductoDto> getTiposProdutoPosibles();
    List<UbicacionDto> getAlmacenesPosibles();
    List<FlotaDto> getFlotasPosibles();
    List<EstadoDto> getEstadosPosibles();
    List<CondicionDto> getCondicionesPosibles();
    List<IdCantidadDto> getLotesPosibles(String filtro);
    
    void alta(NuevoStockForm form);
    void modificar(int id, ModificarStockForm form);
    
    void baja(List<Integer> stockIds, Date fecha, String observaciones);
    void reincorporarBaja(List<Integer> stockIds, Date fecha, String observaciones);
    
    void bloquear(List<Integer> stockIds, Date fecha, String observaciones);
    void desbloquear(List<Integer> stockIds, Date fecha, String observaciones);
    
    void cambiarCondicion(List<Integer> stockIds, Date fecha, String observaciones, String condicionId);
    void cambiarCondicion(Integer stockId, Date fecha, String observaciones, String condicionId);
    
    public void traslado(List<Integer> stockIds, Date fecha, String observaciones, Integer ubicacionId);
    public void traslado(Integer stockId, Date fecha, String observaciones, Integer ubicacionId);
    public void deshacerUltimoTraslado(List<Integer> stockIds);
    public void deshacerUltimoTraslado(Integer stockId);
    
    public void cambiarPropietario(List<Integer> stockIds, Date fecha, String observaciones, Integer propietarioId, String tags);
    public void cambiarPropietario(Integer stockId, Date fecha, String observaciones, Integer propietarioId, String tags);
    public void deshacerUltimoCambioPropietario(List<Integer> stockIds);
    public void deshacerUltimoCambioPropietario(Integer stockId);
    
    public void cambiarFlota(List<Integer> stockIds, String observaciones, String flotaId);
    public void cambiarFlota(Integer stockId, String observaciones, String flotaId);
    public void deshacerUltimoCambioFlota(List<Integer> stockIds);
    public void deshacerUltimoCambioFlota(Integer stockId);
    
    public StockListadoHistoricoCondicionesDto findHistoricoCondiciones(Integer stockId);
    public StockListadoHistoricoEstadosDto findHistoricoEstados(Integer stockId);
    public StockListadoHistoricoFlotasDto findHistoricoFlotas(Integer stockId);
    public StockListadoHistoricoLotesDto findHistoricoLotes(Integer stockId);
    public StockListadoHistoricoPropietariosDto findHistoricoPropietarios(Integer stockId);
    public StockListadoHistoricoTiposProductoDto findHistoricoTiposProducto(Integer stockId);
    public StockListadoHistoricoNumerosSerieDto findHistoricoNumerosSerie(Integer stockId);
    public StockListadoHistoricoUbicacionesDto findHistoricoUbicaciones(Integer stockId);
    public StockListadoHistoricoContratosDto findHistoricoContratos(Integer stockId);

}