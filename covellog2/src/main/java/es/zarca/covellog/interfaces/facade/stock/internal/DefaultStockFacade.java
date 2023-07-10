package es.zarca.covellog.interfaces.facade.stock.internal;

import es.zarca.covellog.application.exception.PartialBussinesException;
import es.zarca.covellog.application.stock.GestionStockService;
import es.zarca.covellog.application.stock.form.ModificarStockForm;
import es.zarca.covellog.application.stock.form.NuevoStockForm;
import es.zarca.covellog.domain.model.almacen.AlmacenRepository;
import es.zarca.covellog.domain.model.app.Ordre;
import es.zarca.covellog.domain.model.app.Tags;
import es.zarca.covellog.domain.model.app.exception.MalformedTagsException;
import es.zarca.covellog.domain.model.producto.TipoProductoRepository;
import es.zarca.covellog.domain.model.producto.UnidadMedidaRepository;
import es.zarca.covellog.domain.model.stock.CondicionRepository;
import es.zarca.covellog.domain.model.stock.CondicionStock;
import es.zarca.covellog.domain.model.stock.StockEstado;
import es.zarca.covellog.domain.model.stock.FlotaRepository;
import es.zarca.covellog.domain.model.stock.Stock;
import es.zarca.covellog.domain.model.stock.StockPorAlmacenRepository;
import es.zarca.covellog.domain.model.stock.StockPorLoteRepository;
import es.zarca.covellog.domain.model.stock.StockRepository;
import es.zarca.covellog.infrastructure.assertions.ArgumentValidator;
import es.zarca.covellog.infrastructure.log.Capa;
import es.zarca.covellog.infrastructure.log.FunctionLog;
import es.zarca.covellog.interfaces.facade.exception.FacadeExceptionHandler;
import es.zarca.covellog.interfaces.facade.stock.StockFacade;
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
import es.zarca.covellog.interfaces.facade.stock.internal.assembler.CondicionAssembler;
import es.zarca.covellog.interfaces.facade.stock.internal.assembler.EstadoAssembler;
import es.zarca.covellog.interfaces.facade.stock.internal.assembler.FlotaAssembler;
import es.zarca.covellog.interfaces.facade.stock.internal.assembler.StockAssembler;
import es.zarca.covellog.interfaces.facade.stock.internal.assembler.listadohistorico.StockListadoHistoricoCondicionesAssembler;
import es.zarca.covellog.interfaces.facade.stock.internal.assembler.listadohistorico.StockListadoHistoricoEstadoAssembler;
import es.zarca.covellog.interfaces.facade.stock.internal.assembler.listadohistorico.StockListadoHistoricoFlotasAssembler;
import es.zarca.covellog.interfaces.facade.stock.internal.assembler.listadohistorico.StockListadoHistoricoLotesAssembler;
import es.zarca.covellog.interfaces.facade.stock.internal.assembler.listadohistorico.StockListadoHistoricoNumerosSerieAssembler;
import es.zarca.covellog.interfaces.facade.stock.internal.assembler.listadohistorico.StockListadoHistoricoPropiedadesAssembler;
import es.zarca.covellog.interfaces.facade.stock.internal.assembler.listadohistorico.StockListadoHistoricoTiposProductoAssembler;
import es.zarca.covellog.interfaces.facade.stock.internal.assembler.listadohistorico.StockListadoHistoricoUbicacionesAssembler;
import es.zarca.covellog.interfaces.facade.stock.internal.assembler.StockPorAlmacenAssembler;
import es.zarca.covellog.interfaces.facade.stock.internal.assembler.StockPorLoteAssembler;
import es.zarca.covellog.interfaces.facade.stock.internal.assembler.TipoProductoAssembler;
import es.zarca.covellog.interfaces.facade.stock.internal.assembler.listadohistorico.StockListadoHistoricoContratoAssembler;
import es.zarca.covellog.interfaces.facade.ubicaciones.dto.UbicacionDto;
import es.zarca.covellog.interfaces.facade.ubicaciones.internal.assembler.UbicacionAssembler;
import es.zarca.covellog.interfaces.web.app.model.Filtro;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import es.zarca.covellog.domain.model.stock.StockEstadoRepository;

/**
 *
 * @author francisco
 */
@ApplicationScoped
public class DefaultStockFacade implements StockFacade {
    @Inject
    private GestionStockService service;
    @Inject
    private StockRepository stockRepository;
    @Inject
    private StockPorLoteRepository stockPorLoteRepository;
    @Inject
    private StockPorAlmacenRepository stockPorAlmacenRepository;
    @Inject
    private TipoProductoRepository tipoProductoRepository;
    @Inject
    private UnidadMedidaRepository unidadMedidaRepository;
    @Inject
    private AlmacenRepository almacenRepository;
    @Inject
    private FlotaRepository flotaRepository;
    @Inject
    private StockEstadoRepository estadoRepository;
    @Inject
    private CondicionRepository condicionRepository;
    
    @Override
    public List<StockDto> find(int first, int pageSize, Ordre sortOrdre, Map<String, Object> filters) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try { 
            System.err.println("LLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLL");
            List<Stock> stocks = stockRepository.find(first, pageSize, sortOrdre, filters);
            System.err.println("LLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLL1111");
            List<StockDto> result = StockAssembler.toDto(stocks);
            System.err.println("LLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLL222");
            return result;
        } catch (Exception ex) {
            System.err.println("KOKOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");
            FacadeExceptionHandler.handleException(ex);            
        } finally {
            log.finish();
        }
        return null;
    }
    
    @Override
    public List<StockDto> findAll() {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try { 
            return StockAssembler.toDto(stockRepository.findAll());
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
        return null;
    }
    
    @Override
    public StockDto findById(int id) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try { 
            return StockAssembler.toDto(stockRepository.find(id));
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
        return null;
    }

    @Override
    public int findTotalCount(Map<String, Object> filters) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try { 
            return stockRepository.findTotalCount(filters);
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
        return -1;
    }
    
    @Override
    public int findTotalCount() {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try { 
            return stockRepository.findTotalCount();
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
        return -1;
    }
    
    @Override
    public List<StockPorLoteDto> findPorLote(int first, int pageSize, Ordre sortOrdre, Map<String, Object> filters) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try { 
            return StockPorLoteAssembler.toDto(stockPorLoteRepository.find(first, pageSize, sortOrdre, filters));
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
        return null;
    }
    
    @Override
    public List<StockPorLoteDto> findPorLoteAll() {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try { 
            return StockPorLoteAssembler.toDto(stockPorLoteRepository.findAll());
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
        return null;
    }
    
    @Override
    public StockPorLoteDto findPorLoteById(String id) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try { 
            return StockPorLoteAssembler.toDto(stockPorLoteRepository.find(id));
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
        return null;
    }

    @Override
    public int findPorLoteTotalCount(Map<String, Object> filters) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try { 
            return stockPorLoteRepository.findTotalCount(filters);
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
        return -1;
    }
    
    @Override
    public int findPorLoteTotalCount() {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try { 
            return stockPorLoteRepository.findTotalCount();
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
        return -1;
    }
    
    @Override
    public List<StockPorAlmacenDto> findPorAlmacen(int first, int pageSize, Ordre sortOrdre, Map<String, Object> filters) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try { 
            return StockPorAlmacenAssembler.toDto(stockPorAlmacenRepository.find(first, pageSize, sortOrdre, filters));
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
        return null;
    }
    
    @Override
    public List<StockPorAlmacenDto> findPorAlmacenAll() {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try { 
            return StockPorAlmacenAssembler.toDto(stockPorAlmacenRepository.findAll());
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
        return null;
    }
    
    @Override
    public StockPorAlmacenDto findPorAlmacenById(String id) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try { 
            return StockPorAlmacenAssembler.toDto(stockPorAlmacenRepository.find(id));
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
        return null;
    }

    @Override
    public int findPorAlmacenTotalCount(Map<String, Object> filters) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try { 
            return stockPorAlmacenRepository.findTotalCount(filters);
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
        return -1;
    }
    
    @Override
    public int findPorAlmacenTotalCount() {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try { 
            return stockPorAlmacenRepository.findTotalCount();
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
        return -1;
    }

    @Override
    public List<Filtro> getFiltrosPosibles() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public List<TipoProductoDto> getTiposProdutoPosibles() {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try { 
            return TipoProductoAssembler.toDto(tipoProductoRepository.findAll());
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
        return null;
    }

    @Override
    public List<UbicacionDto> getAlmacenesPosibles() {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try { 
            return UbicacionAssembler.toDto(almacenRepository.findAll());
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
        return null;
    }

    @Override
    public List<FlotaDto> getFlotasPosibles() {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try { 
            return FlotaAssembler.toDto(flotaRepository.findAll());
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
        return null;
    }

    @Override
    public List<EstadoDto> getEstadosPosibles() {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try { 
            return EstadoAssembler.toDto(estadoRepository.findAll());
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
        return null;
    }

    @Override
    public List<CondicionDto> getCondicionesPosibles() {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try { 
            return CondicionAssembler.toDto(Arrays.asList(CondicionStock.values()));
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
        return null;
    }

    @Override
    public List<IdCantidadDto> getLotesPosibles(String filtro) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try { 
            return stockRepository.findLotes(filtro);
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
        return null;
    }

    @Override
    public void alta(NuevoStockForm form) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try {
            service.alta(form);
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    @Override
    public void modificar(int id, ModificarStockForm form) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try {
            service.modificar(id, form);
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    @Override
    public void traslado(List<Integer> stockIds, Date fecha, String observaciones, Integer ubicacionId) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try {
            int fallos = 0;
            String message = "";
            for (Integer id : stockIds) {
                try {
                    service.traslado(id, fecha, observaciones, ubicacionId);
                } catch (Exception ex) {
                    fallos++;
                    message += "\n" + ex.getMessage();
                }
            }
            if (message.length() > 0) {
                throw new PartialBussinesException(fallos, stockIds.size(), message);
            }
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    @Override
    public void traslado(Integer stockId, Date fecha, String observaciones, Integer ubicacionId) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try {
            service.traslado(stockId, fecha, observaciones, ubicacionId);
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    @Override
    public void deshacerUltimoTraslado(List<Integer> stockIds) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try {
            ArgumentValidator.isNotNull(stockIds, "El listado de stockIds de productos no puede ser null.");
            int fallos = 0;
            String message = "";
            for (Integer id : stockIds) {
                try {
                    service.deshacerUltimoTraslado(id);
                } catch (MalformedTagsException ex) {
                    throw ex;
                } catch (Exception ex) {
                    fallos++;
                    message += "\n" + ex.getMessage();
                }
            }
            if (message.length() > 0) {
                throw new PartialBussinesException(fallos, stockIds.size(), message);
            }
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }

    @Override
    public void deshacerUltimoTraslado(Integer stockId) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try {
            service.deshacerUltimoTraslado(stockId);
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }

    @Override
    public void cambiarFlota(List<Integer> stockIds, String observaciones, String flotaId) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try {
            int fallos = 0;
            String message = "";
            for (Integer id : stockIds) {
                try {
                    service.cambiarFlota(id, observaciones, flotaId);
                } catch (Exception ex) {
                    if (stockIds.size() == 1) {
                        throw ex;
                    }
                    fallos++;
                    message += "\n" + ex.getMessage();
                }
            }
            if (message.length() > 0) {
                throw new PartialBussinesException(fallos, stockIds.size(), message);
            }
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    @Override
    public void cambiarFlota(Integer stockId, String observaciones, String flotaId) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try {
            service.cambiarFlota(stockId, observaciones, flotaId);
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }

    @Override
    public void deshacerUltimoCambioFlota(List<Integer> stockIds) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try {
            ArgumentValidator.isNotNull(stockIds, "El listado de stockIds de productos no puede ser null.");
            int fallos = 0;
            String message = "";
            for (Integer id : stockIds) {
                try {
                    service.deshacerUltimoCambioFlota(id);
                } catch (MalformedTagsException ex) {
                    throw ex;
                } catch (Exception ex) {
                    fallos++;
                    message += "\n" + ex.getMessage();
                }
            }
            if (message.length() > 0) {
                throw new PartialBussinesException(fallos, stockIds.size(), message);
            }
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }

    @Override
    public void deshacerUltimoCambioFlota(Integer stockId) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try {
            service.deshacerUltimoCambioFlota(stockId);
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }

    @Override
    public void cambiarPropietario(List<Integer> stockIds, Date fecha, String observaciones, Integer propietarioId, String tags) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try {
            int fallos = 0;
            String message = "";
            for (Integer id : stockIds) {
                try {
                    service.cambiarPropietario(id, fecha, observaciones, propietarioId, new Tags(tags));
                } catch (Exception ex) {
                    if (stockIds.size() == 1) {
                        throw ex;
                    }
                    fallos++;
                    message += "\n" + ex.getMessage();
                }
            }
            if (message.length() > 0) {
                throw new PartialBussinesException(fallos, stockIds.size(), message);
            }
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }

    @Override
    public void deshacerUltimoCambioPropietario(List<Integer> stockIds) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try {
            ArgumentValidator.isNotNull(stockIds, "El listado de stockIds de productos no puede ser null.");
            int fallos = 0;
            String message = "";
            for (Integer id : stockIds) {
                try {
                    service.deshacerUltimoCambioPropietario(id);
                } catch (MalformedTagsException ex) {
                    throw ex;
                } catch (Exception ex) {
                    fallos++;
                    message += "\n" + ex.getMessage();
                }
            }
            if (message.length() > 0) {
                throw new PartialBussinesException(fallos, stockIds.size(), message);
            }
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    @Override
    public void deshacerUltimoCambioPropietario(Integer id) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try {            
            service.deshacerUltimoCambioPropietario(id);
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    
    

    @Override
    public void baja(List<Integer> stockIds, Date fecha, String observaciones) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try {
            int fallos = 0;
            String message = "";
            for (Integer id : stockIds) {
                try {
                    service.baja(id, fecha, observaciones);
                } catch (Exception ex) {
                    fallos++;
                    message += "\n" + ex.getMessage();
                }
            }
            if (message.length() > 0) {
                throw new PartialBussinesException(fallos, stockIds.size(), message);
            }
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }

    
    @Override
    public void bloquear(List<Integer> stockIds, Date fecha, String observaciones) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try {
            int fallos = 0;
            String message = "";
            for (Integer id : stockIds) {
                try {
                    service.bloquear(id, fecha, observaciones);
                } catch (Exception ex) {
                    fallos++;
                    message += "\n" + ex.getMessage();
                }
            }
            if (message.length() > 0) {
                throw new PartialBussinesException(fallos, stockIds.size(), message);
            }
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }

    @Override
    public void desbloquear(List<Integer> stockIds, Date fecha, String observaciones) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try {
            int fallos = 0;
            String message = "";
            for (Integer id : stockIds) {
                try {
                    service.desbloquear(id, fecha, observaciones);
                } catch (Exception ex) {
                    fallos++;
                    message += "\n" + ex.getMessage();
                }
            }
            if (message.length() > 0) {
                throw new PartialBussinesException(fallos, stockIds.size(), message);
            }
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }

    @Override
    public void cambiarCondicion(List<Integer> stockIds, Date fecha, String observaciones, String condicionId) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try {
            int fallos = 0;
            String message = "";
            for (Integer id : stockIds) {
                try {
                    service.cambiarCondicion(id, fecha, observaciones, condicionId);
                } catch (Exception ex) {
                    if (stockIds.size() == 1) {
                        throw ex;
                    }
                    fallos++;
                    message += "\n" + ex.getMessage();
                }
            }
            if (message.length() > 0) {
                throw new PartialBussinesException(fallos, stockIds.size(), message);
            }
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    @Override
    public void cambiarCondicion(Integer stockId, Date fecha, String observaciones, String condicionId) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try {
            service.cambiarCondicion(stockId, fecha, observaciones, condicionId);
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }

    @Override
    public StockListadoHistoricoCondicionesDto findHistoricoCondiciones(Integer stockId) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try {
            return StockListadoHistoricoCondicionesAssembler.toDto(
                stockRepository.findOrFail(stockId)
            );
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
        return null;
    }
    
    @Override
    public StockListadoHistoricoPropietariosDto findHistoricoPropietarios(Integer stockId) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try {
            return StockListadoHistoricoPropiedadesAssembler.toDto(
                stockRepository.findOrFail(stockId)
            );
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
        return null;
    }
    
    @Override
    public StockListadoHistoricoFlotasDto findHistoricoFlotas(Integer stockId) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try {
            return StockListadoHistoricoFlotasAssembler.toDto(
                stockRepository.findOrFail(stockId)
            );
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
        return null;
    }

    @Override
    public StockListadoHistoricoEstadosDto findHistoricoEstados(Integer stockId) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try {
            return StockListadoHistoricoEstadoAssembler.toDto(
                stockRepository.findOrFail(stockId)
            );
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
        return null;
    }

    @Override
    public StockListadoHistoricoLotesDto findHistoricoLotes(Integer stockId) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try {
            return StockListadoHistoricoLotesAssembler.toDto(
                stockRepository.findOrFail(stockId)
            );
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
        return null;
    }

    @Override
    public StockListadoHistoricoTiposProductoDto findHistoricoTiposProducto(Integer stockId) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try {
            return StockListadoHistoricoTiposProductoAssembler.toDto(
                stockRepository.findOrFail(stockId)
            );
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
        return null;
    }

    @Override
    public StockListadoHistoricoNumerosSerieDto findHistoricoNumerosSerie(Integer stockId) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try {
            return StockListadoHistoricoNumerosSerieAssembler.toDto(
                stockRepository.findOrFail(stockId)
            );
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
        return null;
    }

    @Override
    public StockListadoHistoricoUbicacionesDto findHistoricoUbicaciones(Integer stockId) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try {
            return StockListadoHistoricoUbicacionesAssembler.toDto(
                stockRepository.findOrFail(stockId)
            );
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
        return null;
    }

    @Override
    public StockListadoHistoricoContratosDto findHistoricoContratos(Integer stockId) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try {
            return StockListadoHistoricoContratoAssembler.toDto(
                stockRepository.findOrFail(stockId)
            );
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
        return null;
    }

    @Override
    public void reincorporarBaja(List<Integer> stockIds, Date fecha, String observaciones) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try {
            ArgumentValidator.isNotNull(stockIds, "El listado de stockIds de productos no puede ser null.");
            int fallos = 0;
            String message = "";
            for (Integer id : stockIds) {
                try {
                    service.reincorporarBaja(id, fecha, observaciones);
                } catch (MalformedTagsException ex) {
                    throw ex;
                } catch (Exception ex) {
                    fallos++;
                    message += "\n" + ex.getMessage();
                }
            }
            if (message.length() > 0) {
                throw new PartialBussinesException(fallos, stockIds.size(), message);
            }
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }

    @Override
    public void cambiarPropietario(Integer stockId, Date fecha, String observaciones, Integer propietarioId, String tags) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    
}