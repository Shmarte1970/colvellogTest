package es.zarca.covellog.interfaces.facade.stock.internal;

import es.zarca.covellog.application.stock.ReservaGestionService;
import es.zarca.covellog.application.stock.form.AsignacionStockForm;
import es.zarca.covellog.application.stock.form.ReservaForm;
import es.zarca.covellog.domain.model.app.Ordre;
import es.zarca.covellog.domain.model.stock.reservas.Reserva;
import es.zarca.covellog.domain.model.stock.StockRepository;
import es.zarca.covellog.domain.model.stock.reservas.ReservaRepository;
import es.zarca.covellog.infrastructure.assertions.ArgumentValidator;
import es.zarca.covellog.infrastructure.log.Capa;
import es.zarca.covellog.infrastructure.log.FunctionLog;
import es.zarca.covellog.interfaces.facade.exception.FacadeExceptionHandler;
import es.zarca.covellog.interfaces.facade.stock.ReservaFacade;
import es.zarca.covellog.interfaces.facade.stock.dto.StockSeleccionDto;
import es.zarca.covellog.interfaces.facade.stock.dto.reservas.ReservaDto;
import es.zarca.covellog.interfaces.facade.stock.internal.assembler.reservas.ReservaAssembler;
import es.zarca.covellog.interfaces.web.app.model.Filtro;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

/**
 *
 * @author francisco
 */
@ApplicationScoped
public class DefaultReservaFacade implements ReservaFacade {
    @Inject
    private ReservaGestionService service;
    @Inject
    private ReservaRepository reservaRepository;
    @Inject
    private StockRepository stockRepository;
    
    
    @Override
    public List<ReservaDto> find(int first, int pageSize, Ordre sortOrdre, Map<String, Object> filters) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try { 
            List<Reserva> reservas = reservaRepository.find(first, pageSize, sortOrdre, filters);
            List<ReservaDto> result = ReservaAssembler.toDto(reservas);
            return result;
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);            
        } finally {
            log.finish();
        }
        return null;
    }
    
    @Override
    public List<ReservaDto> findAll() {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try { 
            return ReservaAssembler.toDto(reservaRepository.findAll());
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
        return null;
    }
    
    @Override
    public ReservaDto findById(int id) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try { 
            return ReservaAssembler.toDto(reservaRepository.find(id));
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
            return reservaRepository.findTotalCount(filters);
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
            return reservaRepository.findTotalCount();
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
        return -1;
    }


    @Override
    public void modificar(Integer reservaId, ReservaForm form) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try { 
            service.modificar(reservaId, form);
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }

    @Override
    public void anular(Integer reservaId) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try { 
            service.anular(reservaId);
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }

    @Override
    public void activar(Integer reservaId) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try {
            service.activar(reservaId);
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    @Override
    public void reabrir(Integer reservaId) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try { 
            service.reabrir(reservaId);
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }

    @Override
    public void eliminar(Integer reservaId) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try { 
            service.eliminar(reservaId);
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }

    @Override
    public ReservaDto nuevoAdmitase(ReservaForm form) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try { 
            return ReservaAssembler.toDto(service.nuevoAdmitase(form));
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
        return null;
    }
    
    @Override
    public ReservaDto nuevoEntreguese(ReservaForm form) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try { 
            return ReservaAssembler.toDto(service.nuevoEntreguese(form));
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
    public ReservaDto getAdmitaseAutocomplete(StockSeleccionDto seleccion) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        /*try { 
            ReservaDto dto = new ReservaDto();
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
        }*/
        return null;
    }
    
    @Override
    public ReservaDto getEntregueseAutocomplete(StockSeleccionDto seleccion) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        /*try { 
            ReservaDto dto = new ReservaDto();
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
        }*/
        return null;
    }

    @Override
    public void generarMovimiento(Integer reservaId, Date fecha, List<AsignacionStockForm> asignaciones) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try {
            service.generarMovimientos(reservaId, fecha, asignaciones);
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }

    @Override
    public void generarMovimiento(
        Integer reservaId, 
        Date fecha, 
        List<AsignacionStockForm> asignaciones, 
        Integer transportistaId, 
        String transportistaNombre, 
        String chofer, 
        String matricula, 
        String observaciones
    ) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try {
            service.generarMovimientos(reservaId, fecha, asignaciones, transportistaId, transportistaNombre, chofer, matricula, observaciones);
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
}