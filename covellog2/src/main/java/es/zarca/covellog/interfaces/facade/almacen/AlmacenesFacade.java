package es.zarca.covellog.interfaces.facade.almacen;

import es.zarca.covellog.application.almacenes.form.AlmacenForm;
import es.zarca.covellog.application.almacenes.form.EntradaAlmacenForm;
import es.zarca.covellog.application.almacenes.form.SalidaAlmacenForm;
import es.zarca.covellog.domain.model.app.Ordre;
import es.zarca.covellog.interfaces.facade.adreces.facade.dto.DireccionDto;
import es.zarca.covellog.interfaces.facade.almacen.dto.AlmacenDto;
import es.zarca.covellog.interfaces.facade.contactos.dto.ContactoDto;
import es.zarca.covellog.interfaces.web.app.controller.BusquedaFacade;
import java.util.List;
import java.util.Map;

/**
 *
 * @author francisco
 */
public interface AlmacenesFacade extends BusquedaFacade<AlmacenDto>  {
    AlmacenDto findById(Integer id);
    @Override
    List<AlmacenDto> find(int first, int pageSize, Ordre sortOrdre, Map<String, Object> filters);
    @Override
    int findTotalCount(Map<String, Object> filters);
    int findTotalCount();
    AlmacenDto nuevo(AlmacenForm form);
    AlmacenDto modificar(Integer id, AlmacenForm form);
    AlmacenDto baja(Integer id);
    AlmacenDto anularBaja(Integer id);

    List<ContactoDto> getContactosPosibles(Integer empresaId);
    DireccionDto getDireccionFiscalEmpresa(Integer empresaId);

    public void salida(Integer almacenId, SalidaAlmacenForm form);

    public void entrada(Integer almacenId, EntradaAlmacenForm form);

    
}
