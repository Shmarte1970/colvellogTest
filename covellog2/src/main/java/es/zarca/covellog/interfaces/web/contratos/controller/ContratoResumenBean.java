package es.zarca.covellog.interfaces.web.contratos.controller;
import es.zarca.covellog.interfaces.facade.contrato.ContratoFacade;
import es.zarca.covellog.interfaces.facade.contrato.dto.ContratoDto;
import java.io.Serializable;
import java.util.logging.Logger;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
/**
 *
 * @author francisco
 */
@Named("contratoResumenBean")
@ViewScoped
public class ContratoResumenBean implements Serializable {
    private static final Logger LOGGER = Logger.getLogger(ContratoResumenBean.class.getName());
    @Inject
    private ContratoFacade facade;
    @Inject
    private ModificarContratoBean modificarContratoBean;
    
    /**
     * hola
     * @return el valor bueno
     */
    public ContratoDto getContrato() {
        return modificarContratoBean.getContrato();
    }
    
    public void setContrato(ContratoDto contrato) {
        modificarContratoBean.setContrato(contrato);
    }

}