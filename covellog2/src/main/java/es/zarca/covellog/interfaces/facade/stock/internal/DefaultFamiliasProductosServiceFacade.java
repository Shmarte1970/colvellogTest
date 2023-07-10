package es.zarca.covellog.interfaces.facade.stock.internal;

import es.zarca.covellog.application.exception.BusinessException;
import es.zarca.covellog.application.exception.UndefinedBussinesException;
import es.zarca.covellog.application.productos.familias.GestionFamiliasProductosService;
import es.zarca.covellog.application.productos.familias.form.ModificarFamiliaProductoForm;
import es.zarca.covellog.application.productos.familias.form.NuevaFamiliaProductoForm;
import es.zarca.covellog.domain.model.app.Ordre;
import es.zarca.covellog.domain.model.producto.FamiliaProducto;
import es.zarca.covellog.domain.model.producto.FamiliaProductoRepository;
import es.zarca.covellog.infrastructure.log.Capa;
import es.zarca.covellog.infrastructure.log.FunctionLog;
import es.zarca.covellog.interfaces.facade.stock.FamiliasProductosServiceFacade;
import es.zarca.covellog.interfaces.facade.stock.dto.FamiliaProductoDto;
import es.zarca.covellog.interfaces.facade.stock.internal.assembler.FamiliaProductoAssembler;
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
public class DefaultFamiliasProductosServiceFacade implements FamiliasProductosServiceFacade {

    @Inject
    private GestionFamiliasProductosService service;
    @Inject
    private FamiliaProductoRepository familiaProductoRepository;
    
    @Override
    public List<FamiliaProductoDto> find(int first, int pageSize, Ordre sortOrdre, Map<String, Object> filters) {
        List<FamiliaProducto> models = familiaProductoRepository.find(first, pageSize, sortOrdre, filters);
        List<FamiliaProductoDto>  dtos = new ArrayList();
        for (FamiliaProducto model : models) {
            dtos.add(FamiliaProductoAssembler.toDto(model));
        }
        return dtos;
    }

    @Override
    public int findTotalCount(Map<String, Object> filters) {
        return familiaProductoRepository.findTotalCount(filters);
    }
    
    @Override
    public int findAllTotalCount() {
        return familiaProductoRepository.findAllTotalCount();
    }

    @Override
    public FamiliaProductoDto nuevo(NuevaFamiliaProductoForm form) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try { 
            return FamiliaProductoAssembler.toDto(service.nueva(form));
        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new UndefinedBussinesException(ex);
        } finally {
            log.finish();
        }
        
    }

    @Override
    public FamiliaProductoDto modificar(int id, ModificarFamiliaProductoForm form) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try { 
            return FamiliaProductoAssembler.toDto(service.modificar(id, form));
        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new UndefinedBussinesException(ex);
        } finally {
            log.finish();
        }
    }

    @Override
    public FamiliaProductoDto baja(int id) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try { 
            return FamiliaProductoAssembler.toDto(service.baja(id));
        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new UndefinedBussinesException(ex);
        } finally {
            log.finish();
        }
    }

    @Override
    public FamiliaProductoDto anularBaja(int id) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try { 
            return FamiliaProductoAssembler.toDto(service.anularBaja(id));
        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new UndefinedBussinesException(ex);
        } finally {
            log.finish();
        }
    }
    
    

}
