package es.zarca.covellog.interfaces.facade.transporte;

import es.zarca.covellog.domain.model.app.Ordre;
import es.zarca.covellog.interfaces.facade.empresas.facade.dto.EmpresaDto;
import es.zarca.covellog.interfaces.facade.transporte.dto.TransporteDto;
import es.zarca.covellog.interfaces.web.app.controller.BusquedaFacade;
import es.zarca.covellog.interfaces.web.app.model.Filtro;
import java.util.List;
import java.util.Map;

/**
 *
 * @author francisco
 */
public interface TransportesFacade extends BusquedaFacade<TransporteDto> {
    TransporteDto findById(Integer id);
    @Override
    List<TransporteDto> find(int first, int pageSize, Ordre sortOrdre, Map<String, Object> filters);
    @Override
    int findTotalCount(Map<String, Object> filters);
    int findTotalCount();
    TransporteDto nuevo(Integer empresaId, String nombre, Integer capacidad, String obsInternas, String obsPublicas);
    TransporteDto modificar(Integer transporteId, Integer empresaId, String nombre, Integer capacidad, String obsInternas, String obsPublicas);
    TransporteDto baja(Integer transporteId);
    TransporteDto anularBaja(Integer transporteId);
    List<EmpresaDto> getProveedoresPosibles();
    @Override
    List<Filtro> getFiltrosPosibles();
}
