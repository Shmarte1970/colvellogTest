package es.zarca.covellog.interfaces.web.movimiento;
import es.zarca.covellog.application.stock.exception.MovimientoNotExistException;
import es.zarca.covellog.infrastructure.assertions.ArgumentValidator;
import es.zarca.covellog.infrastructure.log.Capa;
import es.zarca.covellog.infrastructure.log.FunctionLog;
import es.zarca.covellog.interfaces.facade.stock.MovimientoFacade;
import es.zarca.covellog.interfaces.facade.stock.dto.MovimientoDto;
import es.zarca.covellog.interfaces.web.helpers.ExceptionHandler;
import java.io.Serializable;
import java.util.logging.Logger;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
/**
 *
 * @author francisco
 */
@Named("movimientoPrintBean")
@ViewScoped
public class MovimientoPrintBean implements Serializable {
    private static final Logger LOGGER = Logger.getLogger(MovimientoPrintBean.class.getName());
    @Inject
    private MovimientoFacade facade;
    private Integer id;
    private Integer numPagina;
    private Integer totalPaginas;
    private MovimientoDto movimiento;

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
    
    public MovimientoDto getMovimiento() {
        return movimiento;
    }

    public void setMovimiento(MovimientoDto movimiento) {
        this.movimiento = movimiento;
    }
    
    public void prepare() {
        prepare(id);
    }
    
    public void prepare(Integer movimientoId) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {            
            ArgumentValidator.isNotNull(movimientoId, "Debe especificar un id de movimiento."); 
            MovimientoDto movimientoAux = facade.findById(movimientoId);
            if (movimientoAux == null) {
                throw new MovimientoNotExistException(movimientoId);
            }
            prepare(movimientoAux);
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void prepare(MovimientoDto movimiento) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            ArgumentValidator.isNotNull(movimiento, "El movimiento es null.");
            this.movimiento = movimiento;
            numPagina = 1;
            totalPaginas = 1;
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
}