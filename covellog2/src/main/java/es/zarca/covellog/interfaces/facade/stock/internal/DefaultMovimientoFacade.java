package es.zarca.covellog.interfaces.facade.stock.internal;

import es.zarca.covellog.application.stock.MovimientoGestionService;
import es.zarca.covellog.application.stock.form.MovimientoForm;
import es.zarca.covellog.domain.model.app.Ordre;
import es.zarca.covellog.domain.model.stock.movimientos.Movimiento;
import es.zarca.covellog.domain.model.stock.movimientos.MovimientoRepository;
import es.zarca.covellog.domain.model.stock.Stock;
import es.zarca.covellog.domain.model.stock.StockRepository;
import es.zarca.covellog.infrastructure.assertions.ArgumentValidator;
import es.zarca.covellog.infrastructure.log.Capa;
import es.zarca.covellog.infrastructure.log.FunctionLog;
import es.zarca.covellog.interfaces.facade.empresas.facade.internal.assembler.EmpresaMiniDtoAssembler;
import es.zarca.covellog.interfaces.facade.exception.FacadeExceptionHandler;
import es.zarca.covellog.interfaces.facade.stock.MovimientoFacade;
import es.zarca.covellog.interfaces.facade.stock.dto.MovimientoDto;
import es.zarca.covellog.interfaces.facade.stock.internal.assembler.MovimientoAssembler;
import es.zarca.covellog.interfaces.facade.ubicaciones.internal.assembler.UbicacionAssembler;
import es.zarca.covellog.interfaces.web.app.model.Filtro;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

/**
 *
 * @author francisco
 */
@ApplicationScoped
public class DefaultMovimientoFacade implements MovimientoFacade {
    @Inject
    private MovimientoGestionService service;
    @Inject
    private MovimientoRepository movimientoRepository;
    @Inject
    private StockRepository stockRepository;
    
    
    @Override
    public List<MovimientoDto> find(int first, int pageSize, Ordre sortOrdre, Map<String, Object> filters) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try { 
            List<Movimiento> movimientos = movimientoRepository.find(first, pageSize, sortOrdre, filters);
            List<MovimientoDto> result = MovimientoAssembler.toDto(movimientos);
            return result;
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);            
        } finally {
            log.finish();
        }
        return null;
    }
    
    @Override
    public List<MovimientoDto> findAll() {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try { 
            return MovimientoAssembler.toDto(movimientoRepository.findAll());
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
        return null;
    }
    
    @Override
    public MovimientoDto findById(int id) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try { 
            return MovimientoAssembler.toDto(movimientoRepository.find(id));
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
            return movimientoRepository.findTotalCount(filters);
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
            return movimientoRepository.findTotalCount();
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
        return -1;
    }

    @Override
    public void nuevo(MovimientoForm form) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try { 
            service.eliminar(movimientoId);
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }

    @Override
    public MovimientoDto entrada(MovimientoForm form) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try { 
            return MovimientoAssembler.toDto(service.entrada(form));
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
        return null;
    }
    
    @Override
    public MovimientoDto salida(MovimientoForm form) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try { 
            return MovimientoAssembler.toDto(service.salida(form));
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
        return null;
    }

    
    @Override
    public List<Filtro> getFiltrosPosibles() {
        return new ArrayList();
    }

    @Override
    public MovimientoDto getEntradaAutocompleteFromStockId(Integer stockId) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try { 
            MovimientoDto dto = new MovimientoDto();
            Stock stock = stockRepository.findOrFail(stockId);
            System.err.println("COJONES YOYO: " + stock.getNumeroSerie());
            System.err.println("COJONES YOYO: " + (stock.getPropietario() == null ? "null de mierda" : stock.getPropietario().getAliasNombre()));
            dto.setCliente(EmpresaMiniDtoAssembler.toDto(stock.getPropietario()));
            if (stock.getAlbaranRecogida() != null) {
                dto.setTransportistaId(stock.getAlbaranRecogida().getTransportista().getId());
                dto.setTransportistaNombre(stock.getAlbaranRecogida().getTransportista().getNombre());                
            }
            dto.setUbicacion(UbicacionAssembler.toDto(stock.getUbicacion()));
            return dto;//service.getEntradaAutocompleteFromStockId(stockId);
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
        return null;
    }
    
    
    
    
    
}