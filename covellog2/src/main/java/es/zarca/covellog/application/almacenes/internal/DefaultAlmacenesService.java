package es.zarca.covellog.application.almacenes.internal;

import es.zarca.covellog.application.almacenes.exception.AlmacenNotExistException;
import es.zarca.covellog.application.almacenes.form.AlmacenForm;
import es.zarca.covellog.application.empresas.exception.EmpresaNotExistException;
import es.zarca.covellog.application.exception.BusinessException;
import es.zarca.covellog.application.exception.UndefinedBussinesException;
import es.zarca.covellog.domain.model.adreces.poblacio.PoblacioRepository;
import es.zarca.covellog.domain.model.almacen.Almacen;
import es.zarca.covellog.domain.model.almacen.AlmacenRepository;
import es.zarca.covellog.domain.model.contactos.Contacto;
import es.zarca.covellog.domain.model.empresa.Empresa;
import es.zarca.covellog.domain.model.empresa.EmpresaRepository;
import es.zarca.covellog.infrastructure.log.Capa;
import es.zarca.covellog.infrastructure.log.FunctionLog;
import es.zarca.covellog.interfaces.facade.adreces.facade.internal.assembler.DireccionAssembler;
import es.zarca.covellog.interfaces.facade.empresas.facade.internal.assembler.DobleObservacionAssembler;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author francisco
 */
@Stateless
public class DefaultAlmacenesService implements AlmacenesService {
    @Inject
    AlmacenRepository almacenRepository;
    @Inject
    EmpresaRepository empresaRepository;
    @Inject
    PoblacioRepository poblacionRepository;
    
    private Empresa findEmpresaOrFail(Integer empresaId) {
        Empresa empresa = empresaRepository.find(empresaId);
        if (empresa == null) {
            throw new EmpresaNotExistException(empresaId);
        }
        return empresa;
    }
    
    private Almacen formToAlmacen(Almacen almacen, AlmacenForm form) {
        almacen.setEmpresa(findEmpresaOrFail(form.getEmpresaId()));
        almacen.setNombre(form.getNombre());
        
        List<Contacto> contactos = cargarContactos(almacen.getEmpresa(), form.getContactosIds());
        almacen.setObservaciones(DobleObservacionAssembler.toModel(form.getObservaciones()));
        almacen.setContactos(contactos);
        almacen.setDescripcion(form.getDescripcion());
        almacen.setHorario(form.getHorario());
        almacen.setDireccion(DireccionAssembler.toModel(form.getDireccion(), poblacionRepository));
        
        return almacen;
    }
    
    private List<Contacto> cargarContactos(Empresa empresa, List<Integer> contactoIds) {
        List<Contacto> contactos = new ArrayList<>();
        if ((contactoIds != null) && ( empresa != null)) {
            for (Integer contactoId : contactoIds) {
                contactos.add(empresa.getContacto(contactoId));
            }
            /*contactoIds.forEach(contactoId -> {         
                contactos.add(empresa.getContacto(contactoId));
            });*/
        }
        return contactos;
    }
    
    public Almacen createAlmacen(AlmacenForm form) {
        if (form == null) {
            return null;
        }
        return formToAlmacen(new Almacen(), form);
    }
    
    
    
    private Almacen cargarAlmacen(Integer almacenId) {
        Almacen almacen = almacenRepository.find(almacenId);
        if (almacen == null) {
            throw new AlmacenNotExistException(almacenId);
        }
        return almacen;
    }
    
    @Override
    public Almacen nuevo(AlmacenForm form) {
        FunctionLog log = new FunctionLog(Capa.APP_SERVICE);
        try {
            Almacen almacen = createAlmacen(form);
            almacenRepository.store(almacen);
            return almacen;
        } catch (BusinessException ex) {
            throw ex;
        }  finally {
            log.finish();
        }
    }
    
    @Override
    public Almacen modificar(Integer almacenId, AlmacenForm form) {
        FunctionLog log = new FunctionLog(Capa.APP_SERVICE);
        try {
            if (form == null) {
                return null;
            }
            Almacen almacen = almacenRepository.find(almacenId);
            if (almacen == null) {
                throw new AlmacenNotExistException(almacenId);
            }
            almacen = formToAlmacen(almacen, form);
            System.err.println("COJONES QUE FUERTEEEEEEEEEEEEEEEEEEEE");
            return almacen;
        } catch (BusinessException ex) {
            throw ex;
        }  finally {
            log.finish();
        }
    }

    @Override
    public Almacen baja(Integer almacenId) {
        FunctionLog log = new FunctionLog(Capa.APP_SERVICE);
        try {
            Almacen almacen = cargarAlmacen(almacenId);
            try {
                almacenRepository.remove(almacen);
                return null;
            } catch (Exception ex) {
                //almacen.baja();
                return almacen;
            }
        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new UndefinedBussinesException(ex);
        } finally {
            log.finish();
        }
    }

    @Override
    public Almacen anularBaja(Integer almacenId) {
        FunctionLog log = new FunctionLog(Capa.APP_SERVICE);
        try {
            Almacen almacen = cargarAlmacen(almacenId);
            //almacen.anularBaja();
            return almacen;
        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new UndefinedBussinesException(ex);
        } finally {
            log.finish();
        }
    }
    
}