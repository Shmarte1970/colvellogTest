package es.zarca.covellog.application.stock.internal;

import es.zarca.covellog.application.exception.AppExceptionHandler;
import es.zarca.covellog.application.exception.UndefinedBussinesException;
import es.zarca.covellog.application.stock.GestionStockService;
import es.zarca.covellog.application.stock.exception.StockNotExistException;
import es.zarca.covellog.application.stock.form.ModificarStockForm;
import es.zarca.covellog.application.stock.form.NuevoStockForm;
import es.zarca.covellog.domain.model.almacen.AlmacenRepository;
import es.zarca.covellog.domain.model.app.Tags;
import es.zarca.covellog.domain.model.empresa.DobleObservacion;
import es.zarca.covellog.domain.model.empresa.Empresa;
import es.zarca.covellog.domain.model.empresa.EmpresaRepository;
import es.zarca.covellog.domain.model.producto.FamiliaProductoRepository;
import es.zarca.covellog.domain.model.producto.TipoProductoRepository;
import es.zarca.covellog.domain.model.producto.UnidadMedidaRepository;
import es.zarca.covellog.domain.model.stock.CondicionRepository;
import es.zarca.covellog.domain.model.stock.CondicionStock;
import es.zarca.covellog.domain.model.stock.Flota;
import es.zarca.covellog.domain.model.stock.FlotaRepository;
import es.zarca.covellog.domain.model.stock.Stock;
import es.zarca.covellog.domain.model.stock.StockEstado;
import es.zarca.covellog.domain.model.stock.StockRepository;
import es.zarca.covellog.domain.model.ubicacion.Ubicacion;
import es.zarca.covellog.domain.model.ubicacion.UbicacionRepository;
import es.zarca.covellog.infrastructure.assertions.ArgumentValidator;
import es.zarca.covellog.infrastructure.log.Capa;
import es.zarca.covellog.infrastructure.log.FunctionLog;
import java.util.Date;
import javax.ejb.Stateless;
import javax.inject.Inject;
import es.zarca.covellog.domain.model.stock.StockEstadoRepository;

/**
 *
 * @author francisco
 */
@Stateless
public class DefaultGestionStockService implements GestionStockService {

    @Inject
    private StockRepository stockRepository;
    @Inject
    private TipoProductoRepository tipoProductoRepository;
    @Inject
    private AlmacenRepository almacenRepository;
    @Inject
    private EmpresaRepository empresaRepository;
    @Inject
    private FlotaRepository flotaRepository;
    @Inject
    private StockEstadoRepository estadoRepository;
    @Inject
    private CondicionRepository condicionRepository;
    @Inject
    private FamiliaProductoRepository familiaProductoRepository;
    @Inject
    private UnidadMedidaRepository unidadMedidaRepository;
    @Inject
    private UbicacionRepository ubicacionRepository;
    
    private Stock findStock(Integer stockId) {
        Stock stock = stockRepository.findOrFail(stockId);
        if (stock == null) {
            throw new StockNotExistException(stockId);
        }
        return stock;
    }
    
    private Stock formToStock(NuevoStockForm form) {
        Stock stock;
        Date fechaActual = new Date();
        if (form.getEstadoId().equals("BL")) {
            stock = new Stock(StockEstado.DISPONIBLE, fechaActual, "NUEVO");
        } else {
            stock = new Stock(StockEstado.DISPONIBLE, fechaActual, "NUEVO");
        }
        stock.cambiarTipoProducto(tipoProductoRepository.findOrFail(form.getTipoProductoId()), fechaActual, "NUEVO");
        stock.cambiarUbicacion(almacenRepository.findOrFail(form.getAlmacenId()), fechaActual, "NUEVO");
        stock.cambiarLote(form.getLote(), fechaActual, "NUEVO");
        stock.cambiarPropietario(empresaRepository.findOrFail(form.getPropietarioId()), fechaActual, new Tags(""), "NUEVO");
        stock.cambiarFlota(flotaRepository.findOrFail(form.getFlotaId()), "NUEVO");
        stock.cambiarCondicion(CondicionStock.fromId(form.getCondicionId()), new Date(), "NUEVO");
        stock.setObservaciones(new DobleObservacion(form.getObservaciones().getObsInternas(), form.getObservaciones().getObsPublicas()));
        return stock;
    }
    
    @Override
    public void alta(NuevoStockForm form) {
        FunctionLog log = new FunctionLog(Capa.APP_SERVICE);
        try {
            for (String numeroSerie : form.getNumerosSerie()) {
                Stock stock = formToStock(form);
                stock.cambiarNumeroSerie(numeroSerie, new Date(), numeroSerie);
                stockRepository.store(stock);
            }
        } catch (Exception ex) {
            AppExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    @Override
    public void modificar(Integer stockId, ModificarStockForm form) {
        FunctionLog log = new FunctionLog(Capa.APP_SERVICE);
        try {
           /* Stock stock = stockRepository.findOrFail(id);
            stock.cambiarTipoProducto(tipoProductoRepository.findOrFail(form.getTipoProductoId()));
            stock.cambiarLote(form.getLote(),);
            stock.cambiarNumeroSerie(form.getNumeroSerie());
            stock.setObservaciones(new DobleObservacion(form.getObservaciones().getObsInternas(), form.getObservaciones().getObsPublicas()));*/
        } catch (Exception ex) {
            AppExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    @Override
    public void traslado(Integer stockId, Date fecha, String observaciones, Integer ubicacionId) {
        FunctionLog log = new FunctionLog(Capa.APP_SERVICE);
        try {
            Stock stock = stockRepository.findOrFail(stockId);
            Ubicacion ubicacion = ubicacionRepository.findOrFail(ubicacionId);
            stock.cambiarUbicacion(ubicacion, fecha, observaciones);
        } catch (Exception ex) {
            AppExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }

    @Override
    public void cambiarFlota(Integer stockId, String observaciones, String flotaId) {
        FunctionLog log = new FunctionLog(Capa.APP_SERVICE);
        try {
            Stock stock = stockRepository.findOrFail(stockId);
            Flota flota = flotaRepository.findOrFail(flotaId);
            stock.cambiarFlota(flota, observaciones);
        } catch (Exception ex) {
            throw new UndefinedBussinesException(ex);
        } finally {
            log.finish();
        }
    }

    @Override
    public void cambiarPropietario(Integer id, Date fecha, String observaciones, Integer propietarioId, Tags tags) {
        FunctionLog log = new FunctionLog(Capa.APP_SERVICE);
        try {
            Stock stock = stockRepository.findOrFail(id);
            Empresa empresa = empresaRepository.findOrFail(propietarioId);
            stock.cambiarPropietario(empresa, fecha, tags, observaciones);
        } catch (Exception ex) {
            AppExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }

    @Override
    public void deshacerUltimoCambioPropietario(Integer id) {
        FunctionLog log = new FunctionLog(Capa.APP_SERVICE);
        try {
            ArgumentValidator.isNotNull(id, "El id de producto no puede ser null.");
            Stock stock = stockRepository.findOrFail(id);
            stock.deshacerUltimoCambioPropietario();
        } catch (Exception ex) {
            AppExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    @Override
    public void bloquear(Integer id, Date fecha, String observaciones) {
        FunctionLog log = new FunctionLog(Capa.APP_SERVICE);
        try {
            Stock stock = stockRepository.findOrFail(id);         
            stock.bloquear(fecha);
        } catch (Exception ex) {
            AppExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }

    @Override
    public void desbloquear(Integer id, Date fecha, String observaciones) {
        FunctionLog log = new FunctionLog(Capa.APP_SERVICE);
        try {
            Stock stock = stockRepository.findOrFail(id);
            stock.desbloquear(fecha);
        } catch (Exception ex) {
            AppExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }

    @Override
    public void cambiarCondicion(Integer id, Date fecha, String condicionId, String observaciones) {
        FunctionLog log = new FunctionLog(Capa.APP_SERVICE);
        try {
            Stock stock = stockRepository.findOrFail(id);
            stock.cambiarCondicion(CondicionStock.fromId(condicionId), fecha, observaciones);
        } catch (Exception ex) {
            AppExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }

    @Override
    public void baja(Integer id, Date fecha, String observaciones) {
        FunctionLog log = new FunctionLog(Capa.APP_SERVICE);
        try {
            Stock stock = stockRepository.findOrFail(id);
            stock.baja(fecha);
        } catch (Exception ex) {
            AppExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }

    @Override
    public void reincorporarBaja(Integer id, Date fecha, String observaciones) {
        FunctionLog log = new FunctionLog(Capa.APP_SERVICE);
        try {
            Stock stock = stockRepository.findOrFail(id);
            stock.reincorporarBaja();
        } catch (Exception ex) {
            AppExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }

    @Override
    public void deshacerUltimoTraslado(Integer stockId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deshacerUltimoCambioFlota(Integer stockId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deshacerUltimoBloqueo(Integer stockId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}