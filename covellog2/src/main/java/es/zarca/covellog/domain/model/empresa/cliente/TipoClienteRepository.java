package es.zarca.covellog.domain.model.empresa.cliente;

import java.util.List;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public interface TipoClienteRepository {

    TipoCliente find(String id);
    List<TipoCliente> findAll();
    int findAllTotalCount();
    
}
