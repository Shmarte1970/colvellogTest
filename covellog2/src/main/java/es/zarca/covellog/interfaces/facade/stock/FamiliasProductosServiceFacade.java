package es.zarca.covellog.interfaces.facade.stock;

import es.zarca.covellog.application.productos.familias.form.ModificarFamiliaProductoForm;
import es.zarca.covellog.application.productos.familias.form.NuevaFamiliaProductoForm;
import es.zarca.covellog.domain.model.app.Ordre;
import es.zarca.covellog.interfaces.facade.stock.dto.FamiliaProductoDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author francisco
 */
public interface FamiliasProductosServiceFacade {
    List<FamiliaProductoDto> find(int first, int pageSize, Ordre sortOrdre, Map<String, Object> filters);
    int findTotalCount(Map<String, Object> filters);
    int findAllTotalCount();
    FamiliaProductoDto nuevo(NuevaFamiliaProductoForm form);
    FamiliaProductoDto modificar(int id, ModificarFamiliaProductoForm form);
    FamiliaProductoDto baja(int id);
    FamiliaProductoDto anularBaja(int id);
}
