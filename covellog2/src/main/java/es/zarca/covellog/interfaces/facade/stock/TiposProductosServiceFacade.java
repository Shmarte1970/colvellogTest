package es.zarca.covellog.interfaces.facade.stock;

import es.zarca.covellog.application.productos.familias.form.ModificarTipoProductoForm;
import es.zarca.covellog.application.productos.familias.form.NuevoTipoProductoForm;
import es.zarca.covellog.domain.model.app.Ordre;
import es.zarca.covellog.interfaces.facade.stock.dto.FamiliaProductoDto;
import es.zarca.covellog.interfaces.facade.stock.dto.TipoProductoClaseDto;
import es.zarca.covellog.interfaces.facade.stock.dto.TipoProductoDto;
import es.zarca.covellog.interfaces.facade.stock.dto.UnidadMedidaDto;
import es.zarca.covellog.interfaces.web.app.controller.BusquedaFacade;
import java.util.List;
import java.util.Map;

/**
 *
 * @author francisco
 */
public interface TiposProductosServiceFacade extends BusquedaFacade<TipoProductoDto> {
    @Override
    List<TipoProductoDto> find(int first, int pageSize, Ordre sortOrdre, Map<String, Object> filters);
    @Override
    int findTotalCount(Map<String, Object> filters);
    int findTotalCount();
    TipoProductoDto nuevo(NuevoTipoProductoForm form);
    TipoProductoDto modificar(String id, ModificarTipoProductoForm form);
    TipoProductoDto baja(String id);
    TipoProductoDto anularBaja(String id);
    List<FamiliaProductoDto> getFamiliasPosibles();
    List<UnidadMedidaDto> getUnidadesMedidaPosibles();
    List<TipoProductoClaseDto> getClasesPosibles();
    TipoProductoDto findById(String id);
    byte[] getDatosDocumento(String tipoProductoId, Integer documentoId);
}
