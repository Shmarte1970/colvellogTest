package es.zarca.covellog.domain.model.empresa;

import java.util.List;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public interface EmpresaTipoRolRepository {

    EmpresaTipoRol find(Integer id);
    List<EmpresaTipoRol> findAll();
    int findAllTotalCount();
    
}
