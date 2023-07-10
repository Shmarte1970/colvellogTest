package es.zarca.covellog.interfaces.web.albaranes.controller;
import es.zarca.covellog.domain.model.albaran.exception.AlbaranNotExistException;
import es.zarca.covellog.infrastructure.assertions.ArgumentValidator;
import es.zarca.covellog.infrastructure.log.Capa;
import es.zarca.covellog.infrastructure.log.FunctionLog;
import es.zarca.covellog.interfaces.facade.albaran.dto.AlbaranDto;
import es.zarca.covellog.interfaces.web.helpers.ExceptionHandler;
import java.io.Serializable;
import java.util.logging.Logger;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import es.zarca.covellog.interfaces.facade.albaran.AlbaranFacade;
/**
 *
 * @author francisco
 */
@Named("printAlbaranEntregaBean")
@ViewScoped
public class PrintAlbaranEntregaBean implements Serializable {
    private static final Logger LOGGER = Logger.getLogger(PrintAlbaranEntregaBean.class.getName());
    @Inject
    private AlbaranFacade facade;
    private Integer id;
    private Integer numPagina;
    private Integer totalPaginas;
    private AlbaranDto albaran;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNumPagina() {
        return numPagina;
    }

    public void setNumPagina(Integer numPagina) {
        this.numPagina = numPagina;
    }

    public Integer getTotalPaginas() {
        return totalPaginas;
    }

    public void setTotalPaginas(Integer totalPaginas) {
        this.totalPaginas = totalPaginas;
    }
    
    public AlbaranDto getAlbaran() {
        return albaran;
    }

    public void setAlbaran(AlbaranDto albaran) {
        this.albaran = albaran;
    }
    
    public void prepare() {
        prepare(id);
    }
    
    public void prepare(Integer albaranId) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {            
            ArgumentValidator.isNotNull(albaranId, "Debe especificar un id de albaran."); 
            AlbaranDto albaranAux = facade.findById(albaranId);
            if (albaranAux == null) {
                throw new AlbaranNotExistException(albaranId);
            }
            prepare(albaranAux);
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void prepare(AlbaranDto albaran) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            ArgumentValidator.isNotNull(albaran, "El albaran es null.");
            this.albaran = albaran;
            numPagina = 1;
            totalPaginas = 1;
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
}