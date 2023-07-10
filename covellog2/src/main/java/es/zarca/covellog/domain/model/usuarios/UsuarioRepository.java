/*
 * Proyecto Covellog2
 * 
 * Este archivo ha sido creado para ZARCA y no se puede usar sin su consentimiento.
 */
package es.zarca.covellog.domain.model.usuarios;

import es.zarca.covellog.domain.model.app.Ordre;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public interface UsuarioRepository {

    Usuario find(Integer id);
    Usuario findByUsername(String username);
    List<Usuario> findAll();
    List<Usuario> findAll(int start, int size);
    int findAllTotalCount();
    List<Usuario> findAll(Ordre ordre, Map<String, Object> filters);
    List<Usuario> findAll(int start, int size, Ordre ordre, Map<String, Object> filters);
    int findAllTotalCount(Map<String, Object> filters);
    void store(Usuario usuari);
    void remove(Usuario usuari);
    
}
