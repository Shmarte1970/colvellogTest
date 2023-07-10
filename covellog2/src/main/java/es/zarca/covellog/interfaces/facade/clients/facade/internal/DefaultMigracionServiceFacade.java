package es.zarca.covellog.interfaces.facade.clients.facade.internal;

import es.zarca.covellog.application.app.MigracionService;
import es.zarca.covellog.interfaces.facade.migracion.MigracionServiceFacade;
import java.io.Serializable;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
@ApplicationScoped
public class DefaultMigracionServiceFacade implements MigracionServiceFacade, Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private MigracionService migracionService;

    @Override
    public void migracion() {
        migracionService.migrar();
    }
    
}
