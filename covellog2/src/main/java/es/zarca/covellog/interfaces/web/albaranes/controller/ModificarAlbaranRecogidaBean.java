package es.zarca.covellog.interfaces.web.albaranes.controller;
import es.zarca.covellog.infrastructure.log.Capa;
import es.zarca.covellog.infrastructure.log.FunctionLog;
import es.zarca.covellog.interfaces.facade.albaran.dto.AlbaranDto;
import es.zarca.covellog.interfaces.web.contratos.controller.ModificarContratoBean;
import es.zarca.covellog.interfaces.web.helpers.ExceptionHandler;
import es.zarca.covellog.interfaces.web.app.controller.GuardarCancelarBean;
import java.io.Serializable;
import java.util.logging.Logger;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.eclipse.persistence.jpa.jpql.Assert;
import es.zarca.covellog.interfaces.facade.albaran.AlbaranFacade;
/**
 *
 * @author francisco
 */
@Named("modificarAlbaranRecogidaBean")
@ViewScoped
public class ModificarAlbaranRecogidaBean extends GuardarCancelarBean implements Serializable {
    private static final Logger LOGGER = Logger.getLogger(ModificarAlbaranRecogidaBean.class.getName());
    @Inject
    private AlbaranFacade facade;
    @Inject
    private ModificarContratoBean modificarContratoBean;
    private AlbaranDto albaran;

    public void prepare() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void prepare(Integer albaranId) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            Assert.isNotNull(albaranId, "El id de albaran es null.");
            albaran = facade.findById(albaranId);
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }

  
    
    
}