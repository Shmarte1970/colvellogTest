package es.zarca.covellog.interfaces.facade.transporte.internal;

import es.zarca.covellog.application.exception.BusinessException;
import es.zarca.covellog.application.exception.UndefinedBussinesException;
import es.zarca.covellog.application.transporte.GestionTransportesService;
import es.zarca.covellog.domain.model.app.Ordre;
import es.zarca.covellog.domain.model.empresa.EmpresaRepository;
import es.zarca.covellog.domain.model.transporte.TransporteRepository;
import es.zarca.covellog.infrastructure.log.Capa;
import es.zarca.covellog.infrastructure.log.FunctionLog;
import es.zarca.covellog.interfaces.facade.empresas.facade.dto.EmpresaDto;
import es.zarca.covellog.interfaces.facade.exception.FacadeExceptionHandler;
import es.zarca.covellog.interfaces.facade.transporte.TransportesFacade;
import es.zarca.covellog.interfaces.facade.transporte.dto.TransporteDto;
import es.zarca.covellog.interfaces.facade.transporte.internal.assembler.TransporteDtoAssembler;
import es.zarca.covellog.interfaces.web.app.model.Filtro;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

/**
 *
 * @author francisco
 */
@ApplicationScoped
public class DefaultTransporteFacade implements TransportesFacade {
    @Inject
    private GestionTransportesService service;
    @Inject
    private TransporteRepository transporteRepository;

    @Override
    public TransporteDto findById(Integer id) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try { 
            return TransporteDtoAssembler.toDto(transporteRepository.find(id));
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
        return null;
    }
    
    @Override
    public List<TransporteDto> find(int first, int pageSize, Ordre sortOrdre, Map<String, Object> filters) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try { 
            return TransporteDtoAssembler.toDto(transporteRepository.find(first, pageSize, sortOrdre, filters));
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
        return null;
    }

    @Override
    public int findTotalCount() {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try { 
            return transporteRepository.findTotalCount();
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
        return 0;
    }
    
    @Override
    public int findTotalCount(Map<String, Object> filters) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try { 
            return transporteRepository.findTotalCount(filters);
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
        return 0;
    }
    
    @Override
    public TransporteDto nuevo(Integer empresaId, String nombre, Integer capacidad, String obsInternas, String obsPublicas) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try { 
            return TransporteDtoAssembler.toDto(service.nuevo(empresaId, nombre, capacidad, obsInternas, obsPublicas));
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
        return null;
    }

    @Override
    public TransporteDto modificar(Integer transporteId, Integer empresaId, String nombre, Integer capacidad, String obsInternas, String obsPublicas) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try { 
            return TransporteDtoAssembler.toDto(service.modificar(transporteId, empresaId, nombre, capacidad, obsInternas, obsPublicas));
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
        return null;
    }

    @Override
    public TransporteDto baja(Integer transporteId) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try { 
            return TransporteDtoAssembler.toDto(service.baja(transporteId));
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
        return null;
    }

    @Override
    public TransporteDto anularBaja(Integer transporteId) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try { 
            return TransporteDtoAssembler.toDto(service.anularBaja(transporteId));
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
        return null;
    }

    @Override
    public List<Filtro> getFiltrosPosibles() {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try {
            return transporteRepository.getFiltrosPosibles();
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
        return null;
    }

    @Override
    public List<EmpresaDto> getProveedoresPosibles() {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try {
            return null;
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
        return null;
    }

}
