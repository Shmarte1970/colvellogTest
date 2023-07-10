package es.zarca.covellog.interfaces.web.reservas;
import es.zarca.covellog.application.stock.exception.ReservaNotExistException;
import es.zarca.covellog.application.stock.exception.ReservaNotExistException;
import es.zarca.covellog.infrastructure.assertions.ArgumentValidator;
import es.zarca.covellog.infrastructure.log.Capa;
import es.zarca.covellog.infrastructure.log.FunctionLog;
import es.zarca.covellog.interfaces.facade.stock.ReservaFacade;
import es.zarca.covellog.interfaces.facade.stock.dto.reservas.ReservaDto;
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
@Named("reservaPrintBean")
@ViewScoped
public class ReservaPrintBean implements Serializable {
    private static final Logger LOGGER = Logger.getLogger(ReservaPrintBean.class.getName());
    @Inject
    private ReservaFacade facade;
    private Integer id;
    private Integer numPagina;
    private Integer totalPaginas;
    private ReservaDto reserva;

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
    
    public ReservaDto getReserva() {
        return reserva;
    }

    public void setReserva(ReservaDto reserva) {
        this.reserva = reserva;
    }
    
    public void prepare() {
        prepare(id);
    }
    
    public void prepare(Integer reservaId) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {            
            ArgumentValidator.isNotNull(reservaId, "Debe especificar un id de reserva."); 
            ReservaDto reservaAux = facade.findById(reservaId);
            if (reservaAux == null) {
                throw new ReservaNotExistException(reservaId);
            }
            prepare(reservaAux);
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void prepare(ReservaDto reserva) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            ArgumentValidator.isNotNull(reserva, "La reserva es null.");
            this.reserva = reserva;
            numPagina = 1;
            totalPaginas = 1;
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
}