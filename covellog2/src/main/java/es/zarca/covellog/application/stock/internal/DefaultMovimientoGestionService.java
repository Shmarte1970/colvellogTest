package es.zarca.covellog.application.stock.internal;

import es.zarca.covellog.application.exception.AppExceptionHandler;
import es.zarca.covellog.application.stock.MovimientoGestionService;
import es.zarca.covellog.application.stock.exception.MovimientoNotExistException;
import es.zarca.covellog.application.stock.form.MovimientoForm;
import es.zarca.covellog.domain.model.almacen.AlmacenRepository;
import es.zarca.covellog.domain.model.empresa.Empresa;
import es.zarca.covellog.domain.model.empresa.EmpresaRepository;
import es.zarca.covellog.domain.model.stock.movimientos.Movimiento;
import es.zarca.covellog.domain.model.stock.movimientos.MovimientoRepository;
import es.zarca.covellog.domain.model.stock.movimientos.MovimientoTipo;
import es.zarca.covellog.domain.model.stock.Stock;
import es.zarca.covellog.domain.model.stock.StockRepository;
import es.zarca.covellog.infrastructure.assertions.ArgumentValidator;
import es.zarca.covellog.infrastructure.log.Capa;
import es.zarca.covellog.infrastructure.log.FunctionLog;
import es.zarca.covellog.interfaces.facade.stock.dto.MovimientoDto;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author francisco
 */
@Stateless
public class DefaultMovimientoGestionService implements MovimientoGestionService {

    @Inject
    private MovimientoRepository movimientoRepository;
    @Inject
    private EmpresaRepository empresaRepository;
    @Inject
    private StockRepository stockRepository;
    @Inject
    private AlmacenRepository almacenRepository;
    
    private Movimiento findMovimiento(Integer movimientoId) {
        Movimiento movimiento = movimientoRepository.findOrFail(movimientoId);
        if (movimiento == null) {
            throw new MovimientoNotExistException(movimientoId);
        }
        return movimiento;
    }

    @Override
    public Movimiento nuevo(MovimientoForm form) {
        FunctionLog log = new FunctionLog(Capa.APP_SERVICE);
        try {
            Empresa transportista = empresaRepository.findOrFail(form.getTransportistaId());
            Movimiento movimiento;
            if (transportista != null) {
                movimiento = new Movimiento(
                    MovimientoTipo.fromId(form.getTipoId()), 
                    form.getBooking(),
                    form.getFecha(), 
                    almacenRepository.findOrFail(form.getAlmacenId()),
                    empresaRepository.findOrFail(form.getClienteId()), 
                    empresaRepository.findOrFail(form.getTransportistaId()), 
                    form.getChofer(), 
                    form.getMatricula(), 
                    form.getObservaciones(),
                    null,
                    null
                );    
            } else {
                movimiento = new Movimiento(
                    MovimientoTipo.fromId(form.getTipoId()), 
                    form.getBooking(),
                    form.getFecha(), 
                    almacenRepository.findOrFail(form.getAlmacenId()),
                    empresaRepository.findOrFail(form.getClienteId()), 
                    empresaRepository.findOrFail(form.getTransportistaId()), 
                    form.getChofer(), 
                    form.getMatricula(), 
                    form.getObservaciones(),
                    null,
                    null
                );
            }
            movimientoRepository.store(movimiento);
            return movimiento;
        } catch (Exception ex) {
            AppExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
        return null;
    }

    @Override
    public void modificar(Integer movimientoId, MovimientoForm form) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void anular(Integer movimientoId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void lanzar(Integer movimientoId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void eliminar(Integer movimientoId) {
        FunctionLog log = new FunctionLog(Capa.APP_SERVICE);
        try {
            Movimiento movimiento = movimientoRepository.findOrFail(movimientoId);
            ArgumentValidator.isNotNull(movimiento.getUbicacion(), "El movimiento no tiene ubicacion.d");
            movimiento.getUbicacion().anularMovimiento(movimiento);
            //movimientoRepository.remove(movimiento);
        } catch (Exception ex) {
            AppExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }

    @Override
    public MovimientoDto getEntradaAutocompleteFromStockId(Integer StockId) {
        return new MovimientoDto();
    }

    @Override
    public Movimiento entrada(MovimientoForm form) {
        FunctionLog log = new FunctionLog(Capa.APP_SERVICE);
        try {
            List<Stock> stocks = new ArrayList();
            for (Integer stockId : form.getStockIds()) {
                stocks.add(stockRepository.findOrFail(stockId));
            }
            Movimiento movimiento = new Movimiento(
                MovimientoTipo.ENTRADA, 
                form.getBooking(),
                form.getFecha(),
                almacenRepository.findOrFail(form.getAlmacenId()),
                empresaRepository.findOrFail(form.getClienteId()), 
                empresaRepository.findOrFail(form.getTransportistaId()),
                form.getChofer(), 
                form.getMatricula(), 
                form.getObservaciones(),
                null,
                    null
            );
            movimientoRepository.store(movimiento);
            return movimiento;
        } catch (Exception ex) {
            AppExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
        return null;
    }

    @Override
    public Movimiento salida(MovimientoForm form) {
        FunctionLog log = new FunctionLog(Capa.APP_SERVICE);
        try {
            List<Stock> stocks = new ArrayList();
            for (Integer stockId : form.getStockIds()) {
                stocks.add(stockRepository.findOrFail(stockId));
            }
            Movimiento movimiento = new Movimiento(
                MovimientoTipo.SALIDA,
                form.getBooking(),
                form.getFecha(),
                almacenRepository.findOrFail(form.getAlmacenId()),
                empresaRepository.findOrFail(form.getClienteId()), 
                empresaRepository.findOrFail(form.getTransportistaId()),
                form.getChofer(), 
                form.getMatricula(), 
                form.getObservaciones(),
                null,
                    null
                
            );
            movimientoRepository.store(movimiento);
            return movimiento;
        } catch (Exception ex) {
            AppExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
        return null;
    }
    
    
    
}