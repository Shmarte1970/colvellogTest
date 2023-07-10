package es.zarca.covellog.interfaces.facade.almacen.internal;

import es.zarca.covellog.application.almacenes.internal.AlmacenesService;
import es.zarca.covellog.application.almacenes.form.AlmacenForm;
import es.zarca.covellog.application.almacenes.form.EntradaAlmacenForm;
import es.zarca.covellog.application.almacenes.form.SalidaAlmacenForm;
import es.zarca.covellog.application.exception.BusinessException;
import es.zarca.covellog.application.exception.UndefinedBussinesException;
import es.zarca.covellog.domain.model.almacen.AlmacenRepository;
import es.zarca.covellog.domain.model.app.Ordre;
import es.zarca.covellog.domain.model.empresa.EmpresaRepository;
import es.zarca.covellog.infrastructure.log.Capa;
import es.zarca.covellog.infrastructure.log.FunctionLog;
import es.zarca.covellog.interfaces.facade.adreces.facade.dto.DireccionDto;
import es.zarca.covellog.interfaces.facade.adreces.facade.internal.assembler.DireccionAssembler;
import es.zarca.covellog.interfaces.facade.almacen.AlmacenesFacade;
import es.zarca.covellog.interfaces.facade.almacen.dto.AlmacenDto;
import es.zarca.covellog.interfaces.facade.almacen.internal.assembler.AlmacenDtoAssembler;
import es.zarca.covellog.interfaces.facade.contactos.dto.ContactoDto;
import es.zarca.covellog.interfaces.facade.contactos.internal.assembler.ContactoAssembler;
import es.zarca.covellog.interfaces.facade.exception.FacadeExceptionHandler;
import es.zarca.covellog.interfaces.web.app.model.Filtro;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

/**
 *
 * @author francisco
 */
@ApplicationScoped
public class DefaultAlmacenesFacade implements AlmacenesFacade {
    private static final Logger LOGGER = Logger.getLogger(DefaultAlmacenesFacade.class.getName());
    @Inject
    private AlmacenRepository almacenRepository;
    @Inject
    private EmpresaRepository empresaRepository;
    @Inject
    private AlmacenesService service;
    
    @Override
    public AlmacenDto findById(Integer id) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try { 
            return AlmacenDtoAssembler.toDto(almacenRepository.find(id));
        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new UndefinedBussinesException(ex);
        } finally {
            log.finish();
        }
    }

    @Override
    public List<AlmacenDto> find(int first, int pageSize, Ordre sortOrdre, Map<String, Object> filters) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try { 
            return AlmacenDtoAssembler.toDto(almacenRepository.find(first, pageSize, sortOrdre, filters));
        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new UndefinedBussinesException(ex);
        } finally {
            log.finish();
        }
    }

    @Override
    public int findTotalCount() {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try { 
            return almacenRepository.findTotalCount();
        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new UndefinedBussinesException(ex);
        } finally {
            log.finish();
        }
    }
    
    @Override
    public int findTotalCount(Map<String, Object> filters) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try { 
            return almacenRepository.findTotalCount(filters);
        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new UndefinedBussinesException(ex);
        } finally {
            log.finish();
        }
    }
    
    @Override
    public List<Filtro> getFiltrosPosibles() {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try {
            return almacenRepository.getFiltrosPosibles();
        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new UndefinedBussinesException(ex);
        } finally {
            log.finish();
        }
    }
    
    @Override
    public AlmacenDto nuevo(AlmacenForm form) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try { 
            return AlmacenDtoAssembler.toDto(service.nuevo(form));
        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new UndefinedBussinesException(ex);
        } finally {
            log.finish();
        }
    }

    @Override
    public AlmacenDto modificar(Integer id, AlmacenForm form) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try { 
            return AlmacenDtoAssembler.toDto(
                service.modificar(id, form)
            );
        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new UndefinedBussinesException(ex);
        } finally {
            log.finish();
        }
    }

    @Override
    public AlmacenDto baja(Integer id) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try { 
            return AlmacenDtoAssembler.toDto(service.baja(id));
        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new UndefinedBussinesException(ex);
        } finally {
            log.finish();
        }
    }

    @Override
    public AlmacenDto anularBaja(Integer id) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try { 
            return AlmacenDtoAssembler.toDto(service.anularBaja(id));
        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new UndefinedBussinesException(ex);
        } finally {
            log.finish();
        }
    }

    @Override
    public List<ContactoDto> getContactosPosibles(Integer empresaId) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try { 
            System.err.println("size:empresaRepository.findOrFail(empresaId).getContactos()" + empresaRepository.findOrFail(empresaId).getContactos().size());
            return ContactoAssembler.toDto(empresaRepository.findOrFail(empresaId).getContactos());
        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
        return null;
    }

    @Override
    public DireccionDto getDireccionFiscalEmpresa(Integer empresaId) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try { 
            return DireccionAssembler.toDto(empresaRepository.findOrFail(empresaId).getDireccionFiscal());
        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new UndefinedBussinesException(ex);
        } finally {
            log.finish();
        }
    }

    @Override
    public void salida(Integer almacenId, SalidaAlmacenForm form) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void entrada(Integer almacenId, EntradaAlmacenForm form) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
