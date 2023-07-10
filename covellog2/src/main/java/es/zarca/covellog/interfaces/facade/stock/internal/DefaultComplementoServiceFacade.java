package es.zarca.covellog.interfaces.facade.stock.internal;

import es.zarca.covellog.application.exception.BusinessException;
import es.zarca.covellog.application.exception.UndefinedBussinesException;
import es.zarca.covellog.application.productos.familias.GestionTiposProductosService;
import es.zarca.covellog.application.productos.familias.form.ModificarTipoProductoForm;
import es.zarca.covellog.application.productos.familias.form.NuevoTipoProductoForm;
import es.zarca.covellog.domain.model.app.Ordre;
import es.zarca.covellog.domain.model.producto.FamiliaProductoRepository;
import es.zarca.covellog.domain.model.producto.TipoProducto;
import es.zarca.covellog.domain.model.producto.TipoProductoClaseRepository;
import es.zarca.covellog.domain.model.producto.TipoProductoDocumento;
import es.zarca.covellog.domain.model.producto.TipoProductoRepository;
import es.zarca.covellog.domain.model.producto.UnidadMedidaRepository;
import es.zarca.covellog.infrastructure.log.Capa;
import es.zarca.covellog.infrastructure.log.FunctionLog;
import es.zarca.covellog.interfaces.facade.stock.ComplementosFacade;
import es.zarca.covellog.interfaces.facade.stock.dto.FamiliaProductoDto;
import es.zarca.covellog.interfaces.facade.stock.dto.TipoProductoClaseDto;
import es.zarca.covellog.interfaces.facade.stock.dto.TipoProductoDto;
import es.zarca.covellog.interfaces.facade.stock.dto.UnidadMedidaDto;
import es.zarca.covellog.interfaces.facade.stock.internal.assembler.FamiliaProductoAssembler;
import es.zarca.covellog.interfaces.facade.stock.internal.assembler.TipoProductoAssembler;
import es.zarca.covellog.interfaces.facade.stock.internal.assembler.TipoProductoClaseAssembler;
import es.zarca.covellog.interfaces.facade.stock.internal.assembler.UnidadMedidaAssembler;
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
public class DefaultComplementoServiceFacade implements ComplementosFacade {
    @Inject
    private GestionTiposProductosService service;
    @Inject
    private TipoProductoRepository tipoProductoRepository;
    @Inject
    private FamiliaProductoRepository familiaProductoRepository;
    @Inject
    private UnidadMedidaRepository unidadMedidaRepository;
    @Inject
    private TipoProductoClaseRepository tipoProductoClaseRepository;

    @Override
    public List<TipoProductoDto> find(int first, int pageSize, Ordre sortOrdre, Map<String, Object> filters) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try { 
            filters.put("clase_id", "COMP");
            return TipoProductoAssembler.toDto(tipoProductoRepository.find(first, pageSize, sortOrdre, filters));
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
            return tipoProductoRepository.findTotalCount();
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
            return tipoProductoRepository.findTotalCount(filters);
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    

}
