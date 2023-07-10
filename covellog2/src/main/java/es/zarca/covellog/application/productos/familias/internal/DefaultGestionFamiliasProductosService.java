package es.zarca.covellog.application.productos.familias.internal;

import es.zarca.covellog.application.exception.BusinessException;
import es.zarca.covellog.application.exception.UndefinedBussinesException;
import es.zarca.covellog.application.productos.familias.GestionFamiliasProductosService;
import es.zarca.covellog.application.productos.familias.exception.FamiliaProductoNotExistException;
import es.zarca.covellog.application.productos.familias.form.ModificarFamiliaProductoForm;
import es.zarca.covellog.application.productos.familias.form.NuevaFamiliaProductoForm;
import es.zarca.covellog.domain.model.producto.FamiliaProducto;
import es.zarca.covellog.domain.model.producto.FamiliaProductoRepository;
import es.zarca.covellog.infrastructure.assertions.ArgumentValidator;
import es.zarca.covellog.infrastructure.log.Capa;
import es.zarca.covellog.infrastructure.log.FunctionLog;
import java.util.Date;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author francisco
 */
@Stateless
public class DefaultGestionFamiliasProductosService implements GestionFamiliasProductosService {

    @Inject
    FamiliaProductoRepository repository;
    
    @Override
    public FamiliaProducto nueva(NuevaFamiliaProductoForm form) {
        FunctionLog log = new FunctionLog(Capa.APP_SERVICE);
        try {
            FamiliaProducto familiaProducto = new FamiliaProducto();
            familiaProducto.setNombre(form.getNombre());
            familiaProducto.setObservaciones(form.getObservaciones());
            repository.store(familiaProducto);
            return familiaProducto;
        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new UndefinedBussinesException(ex);
        } finally {
            log.finish();
        }
    }
    
    private FamiliaProducto findFamiliaProducto(int id) {
        FamiliaProducto familiaProducto = repository.find(id);
        if (familiaProducto == null) {
            throw new FamiliaProductoNotExistException(id);
        }
        return familiaProducto;
    }

    @Override
    public FamiliaProducto modificar(int id, ModificarFamiliaProductoForm form) {
        FunctionLog log = new FunctionLog(Capa.APP_SERVICE);
        try {
            ArgumentValidator.isNotNull(form, "form");
            FamiliaProducto familiaProducto = findFamiliaProducto(id);
            familiaProducto.setNombre(form.getNombre());
            familiaProducto.setObservaciones(form.getObservaciones());
            log.info("Observaciones: " + familiaProducto.getObservaciones());
            return familiaProducto;
        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new UndefinedBussinesException(ex);
        } finally {
            log.finish();
        }
    }

    @Override
    public FamiliaProducto baja(int id) {
        FunctionLog log = new FunctionLog(Capa.APP_SERVICE);
        try {
            FamiliaProducto familiaProducto = findFamiliaProducto(id);
            try {
                repository.remove(familiaProducto);
                return null;
            } catch (Exception ex) {
                familiaProducto.baja(new Date());
                return familiaProducto;
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
    public FamiliaProducto anularBaja(int id) {
        FunctionLog log = new FunctionLog(Capa.APP_SERVICE);
        try {
            FamiliaProducto familiaProducto = findFamiliaProducto(id);
            familiaProducto.anularBaja();
            return familiaProducto;
        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new UndefinedBussinesException(ex);
        } finally {
            log.finish();
        }
    }
    
}