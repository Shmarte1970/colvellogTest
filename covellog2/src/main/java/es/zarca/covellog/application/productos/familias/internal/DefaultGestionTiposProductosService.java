package es.zarca.covellog.application.productos.familias.internal;

import es.zarca.covellog.application.exception.BusinessException;
import es.zarca.covellog.application.exception.UndefinedBussinesException;
import es.zarca.covellog.application.productos.familias.GestionTiposProductosService;
import es.zarca.covellog.application.productos.familias.exception.FamiliaProductoNotExistException;
import es.zarca.covellog.application.productos.familias.exception.TipoProductoClaseNotExistException;
import es.zarca.covellog.application.productos.familias.exception.TipoProductoNotExistException;
import es.zarca.covellog.application.productos.familias.exception.UnidadMedidaNotExistException;
import es.zarca.covellog.application.productos.familias.form.ModificarTipoProductoForm;
import es.zarca.covellog.application.productos.familias.form.NuevoTipoProductoForm;
import es.zarca.covellog.application.productos.familias.form.TipoProductoDocumentoForm;
import es.zarca.covellog.domain.model.producto.FamiliaProducto;
import es.zarca.covellog.domain.model.producto.FamiliaProductoRepository;
import es.zarca.covellog.domain.model.producto.TipoProducto;
import es.zarca.covellog.domain.model.producto.TipoProductoClase;
import es.zarca.covellog.domain.model.producto.TipoProductoClaseRepository;
import es.zarca.covellog.domain.model.producto.TipoProductoDocumento;
import es.zarca.covellog.domain.model.producto.TipoProductoRepository;
import es.zarca.covellog.domain.model.producto.UnidadMedida;
import es.zarca.covellog.domain.model.producto.UnidadMedidaRepository;
import es.zarca.covellog.infrastructure.assertions.ArgumentValidator;
import es.zarca.covellog.infrastructure.log.Capa;
import es.zarca.covellog.infrastructure.log.FunctionLog;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author francisco
 */
@Stateless
public class DefaultGestionTiposProductosService implements GestionTiposProductosService {

    @Inject
    TipoProductoRepository repository;
    @Inject
    FamiliaProductoRepository familiaProductoRepository;
    @Inject
    TipoProductoClaseRepository tipoProductoClaseRepository;
    @Inject
    UnidadMedidaRepository unidadMedidaRepository;
    
    private void form2TipoProducto(TipoProducto tipoProducto, NuevoTipoProductoForm form) {
        if (tipoProducto.getId() == null) {
            tipoProducto.setId(form.getId());
        }
        tipoProducto.setDescripcion(form.getDescripcion());
        FamiliaProducto familia = familiaProductoRepository.find(form.getFamiliaId());
        if (familia == null) {
            throw new FamiliaProductoNotExistException(form.getFamiliaId());
        }
        tipoProducto.setFamilia(familia);
        TipoProductoClase clase = tipoProductoClaseRepository.find(form.getClaseId());
        if (clase == null) {
            throw new TipoProductoClaseNotExistException(form.getClaseId());
        }
        tipoProducto.setClase(clase);
        UnidadMedida unidadMedida = unidadMedidaRepository.find(form.getUnidadMedidaId());
        if (unidadMedida == null) {
            throw new UnidadMedidaNotExistException(form.getUnidadMedidaId());
        }
        tipoProducto.setUnidadMedida(unidadMedida);
        
        
        List<TipoProductoDocumento> listaParaEliminar = new ArrayList<>();
        for (TipoProductoDocumentoForm documentoForm : form.getDocumentos()) {
            System.err.println("Antes hay: " + documentoForm.getId() + "-" + documentoForm.getNombre());
        }
        for (TipoProductoDocumento documento : tipoProducto.getDocumentos()) {
            Boolean eliminar = true;
            for (TipoProductoDocumentoForm tipoProductoDocumentoForm : form.getDocumentos()) {
                if (documento.getId().equals(tipoProductoDocumentoForm.getId())) {
                    eliminar = false;
                    break;
                }
            }
            if (eliminar) {
                listaParaEliminar.add(documento);                
            }
        }
        for (TipoProductoDocumento documento : listaParaEliminar) {
            System.err.println("Elimino: " + documento.getId() + "-" +  documento.getNombre());
            tipoProducto.eliminarDocumento(documento);
        }
        int index = 0;
        for (TipoProductoDocumentoForm documentoForm : form.getDocumentos()) {
            if ((documentoForm.getId() != null) && (documentoForm.getId() > 0)) {
                System.err.println("Documetno: " + documentoForm.getId() + " " +  documentoForm.getNombre());
                tipoProducto.setPosicionDocumento(tipoProducto.getDocumentoById(documentoForm.getId()), index);
            } else {
                TipoProductoDocumento documento = new TipoProductoDocumento(
                    documentoForm.getNombre(),
                    documentoForm.getDatos()
                );
                tipoProducto.anadirDocumento(documento, index);
            }
            index++;
        } 
    }
    
    @Override
    public TipoProducto nueva(NuevoTipoProductoForm form) {
        FunctionLog log = new FunctionLog(Capa.APP_SERVICE);
        try {
            ArgumentValidator.isNotNull(form, "form");
            TipoProducto tipoProducto = new TipoProducto();
            tipoProducto.setId(form.getId());
            form2TipoProducto(tipoProducto, form);
            repository.store(tipoProducto);
            return tipoProducto;
        } catch (BusinessException ex) {
            throw ex;
        }  finally {
            log.finish();
        }
    }
    
    private TipoProducto findTipoProducto(String id) {
        TipoProducto tipoProducto = repository.find(id);
        if (tipoProducto == null) {
            throw new TipoProductoNotExistException(id);
        }
        return tipoProducto;
    }

    @Override
    public TipoProducto modificar(String id, ModificarTipoProductoForm form) {
        FunctionLog log = new FunctionLog(Capa.APP_SERVICE);
        try {
            ArgumentValidator.isNotNull(form, "form");
            TipoProducto tipoProducto = findTipoProducto(id);
            form2TipoProducto(tipoProducto, form);
            return tipoProducto;
        } catch (BusinessException ex) {
            throw ex;
        }  finally {
            log.finish();
        }
    }

    @Override
    public TipoProducto baja(String id) {
        FunctionLog log = new FunctionLog(Capa.APP_SERVICE);
        try {
            TipoProducto tipoProducto = findTipoProducto(id);
            try {
                repository.remove(tipoProducto);
                return null;
            } catch (Exception ex) {
                tipoProducto.baja();
                return tipoProducto;
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
    public TipoProducto anularBaja(String id) {
        FunctionLog log = new FunctionLog(Capa.APP_SERVICE);
        try {
            TipoProducto tipoProducto = findTipoProducto(id);
            tipoProducto.anularBaja();
            return tipoProducto;
        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new UndefinedBussinesException(ex);
        } finally {
            log.finish();
        }
    }
    
}