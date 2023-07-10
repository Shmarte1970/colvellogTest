package es.zarca.covellog.domain.model.empresa;

import es.zarca.covellog.domain.model.app.Ordre;
import es.zarca.covellog.interfaces.web.app.model.Filtro;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public interface EmpresaRepository {

    Empresa find(Integer id);
    Empresa findOrFail(Integer id);
    Empresa findByNombre(String nombre);
    List<Empresa> findAll();
    List<Empresa> findAll(int start, int size);    
    int findAllTotalCount();
    List<Empresa> findAll(Ordre ordre, Map<String, Object> filters);
    List<Empresa> findAll(int start, int size, Ordre ordre, Map<String, Object> filters);
    int findAllTotalCount(Map<String, Object> filters);
    Map<String, List<Empresa>> findAll(int start, int size, Ordre ordre, Map<String, Object> filters, String filter);
    Empresa store(Empresa empresa);
    void remove(Empresa empresa);
    String findMaxCodigoProveedor();
    String findMaxCodigoCliente();
    List<Filtro> getFiltrosPosibles();
    
    
}
