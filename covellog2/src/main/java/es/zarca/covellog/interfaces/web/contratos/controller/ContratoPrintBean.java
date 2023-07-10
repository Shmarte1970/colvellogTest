package es.zarca.covellog.interfaces.web.contratos.controller;
import es.zarca.covellog.application.stock.exception.ContratoNotExistException;
import es.zarca.covellog.infrastructure.assertions.ArgumentValidator;
import es.zarca.covellog.infrastructure.log.Capa;
import es.zarca.covellog.infrastructure.log.FunctionLog;
import es.zarca.covellog.interfaces.facade.comerciales.dto.ComercialDto;
import es.zarca.covellog.interfaces.web.helpers.ExceptionHandler;
import java.io.Serializable;
import java.util.logging.Logger;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import es.zarca.covellog.interfaces.facade.contrato.ContratoFacade;
import es.zarca.covellog.interfaces.facade.contrato.dto.ContratoDto;
/**
 *
 * @author francisco
 */
@Named("contratoPrintBean")
@ViewScoped
public class ContratoPrintBean implements Serializable {
    private static final Logger LOGGER = Logger.getLogger(ContratoPrintBean.class.getName());
    @Inject
    private ContratoFacade facade;
    private Integer id;
    private Integer numPagina;
    private Integer totalPaginas;
    private ContratoDto contrato;

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
    
    public ContratoDto getContrato() {
        return contrato;
    }

    public void setContrato(ContratoDto contrato) {
        this.contrato = contrato;
    }
    
    public ComercialDto getComercialPrincipal() {
        try {
            return getContrato().getComerciales().get(0);
        } catch (Exception e) {
            return null;
        }
    }
    
    public void prepare() {
        prepare(id);
    }
    
    public void prepare(Integer contratoId) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {            
            ArgumentValidator.isNotNull(contratoId, "Debe especificar un id de contrato."); 
            ContratoDto albaranAux = facade.findById(contratoId);
            if (albaranAux == null) {
                throw new ContratoNotExistException(contratoId);
            }
            prepare(albaranAux);
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void prepare(ContratoDto albaran) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            ArgumentValidator.isNotNull(albaran, "El contrato es null.");
            this.contrato = albaran;
            numPagina = 1;
            totalPaginas = 1;
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
}