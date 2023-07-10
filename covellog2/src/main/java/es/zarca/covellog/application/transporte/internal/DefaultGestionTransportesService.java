package es.zarca.covellog.application.transporte.internal;

import es.zarca.covellog.application.exception.BusinessException;
import es.zarca.covellog.application.exception.UndefinedBussinesException;
import es.zarca.covellog.application.transporte.GestionTransportesService;
import es.zarca.covellog.domain.model.empresa.DobleObservacion;
import es.zarca.covellog.domain.model.empresa.EmpresaRepository;
import es.zarca.covellog.domain.model.transporte.Transporte;
import es.zarca.covellog.domain.model.transporte.TransporteRepository;
import es.zarca.covellog.infrastructure.log.Capa;
import es.zarca.covellog.infrastructure.log.FunctionLog;
import java.util.Date;
import java.util.Set;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 *
 * @author francisco
 */
@Stateless
public class DefaultGestionTransportesService implements GestionTransportesService {

    @Inject
    TransporteRepository repository;
    @Inject
    EmpresaRepository empresaRepository;
     
    private void validate(Transporte transporte) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Transporte>> violations = validator.validate(transporte);
        if (violations.size() > 0) {
            throw new ConstraintViolationException(violations);
        }
    }
    
    @Override
    public Transporte nuevo(Integer empresaId, String nombre, Integer capacidad, String obsInternas, String obsPublicas) {
        FunctionLog log = new FunctionLog(Capa.APP_SERVICE);
        try {
            Transporte transporte = new Transporte();
            transporte.setProveedor(empresaRepository.findOrFail(empresaId));
            transporte.setCapacidad(capacidad);
            transporte.setNombre(nombre);
            transporte.setCapacidad(capacidad);
            transporte.setObservaciones(new DobleObservacion(obsInternas, obsPublicas));
            transporte.setCreatedAt(new Date());
            repository.store(transporte);
            return transporte;
        } catch (BusinessException ex) {
            throw ex;
        }  finally {
            log.finish();
        }
    }

    @Override
    public Transporte modificar(Integer transporteId, Integer empresaId, String nombre, Integer capacidad, String obsInternas, String obsPublicas) {
        FunctionLog log = new FunctionLog(Capa.APP_SERVICE);
        try {
            Transporte transporte = repository.findOrFail(transporteId);
            transporte.setProveedor(empresaRepository.findOrFail(empresaId));
            transporte.setCapacidad(capacidad);
            transporte.setNombre(nombre);
            transporte.setCapacidad(capacidad);
            transporte.setObservaciones(new DobleObservacion(obsInternas, obsPublicas));
            transporte.setUpdatedAt(new Date());
            return transporte;
        } catch (BusinessException ex) {
            throw ex;
        }  finally {
            log.finish();
        }
    }

    @Override
    public Transporte baja(Integer transporteId) {
        FunctionLog log = new FunctionLog(Capa.APP_SERVICE);
        try {
            Transporte transporte = repository.findOrFail(transporteId);
            try {
                repository.remove(transporte);
                return null;
            } catch (Exception ex) {
                transporte.baja();
                return transporte;
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
    public Transporte anularBaja(Integer transporteId) {
        FunctionLog log = new FunctionLog(Capa.APP_SERVICE);
        try {
            Transporte transporte = repository.findOrFail(transporteId);
            transporte.anularBaja();
            return transporte;
        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new UndefinedBussinesException(ex);
        } finally {
            log.finish();
        }
    }
    
}