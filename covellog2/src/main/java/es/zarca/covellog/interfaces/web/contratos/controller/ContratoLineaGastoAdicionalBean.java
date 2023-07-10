package es.zarca.covellog.interfaces.web.contratos.controller;
import es.zarca.covellog.infrastructure.log.Capa;
import es.zarca.covellog.infrastructure.log.FunctionLog;
import es.zarca.covellog.interfaces.facade.contrato.ContratoFacade;
import es.zarca.covellog.interfaces.facade.contrato.dto.ContratoLineaGastoAdicionalDto;
import es.zarca.covellog.interfaces.facade.stock.dto.TipoProductoDto;
import es.zarca.covellog.interfaces.web.helpers.ExceptionHandler;
import es.zarca.covellog.interfaces.web.helpers.JsfUtil;
import es.zarca.covellog.interfaces.web.productos.complementos.SelectorGastoAdicionalBean;
import es.zarca.covellog.interfaces.web.app.controller.GuardarCancelarBean;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.logging.Logger;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
/**
 *
 * @author francisco
 */
@Named("contratoLineaGastoAdicionalBean")
@ViewScoped
public class ContratoLineaGastoAdicionalBean extends GuardarCancelarBean implements Serializable {
    private static final Logger LOGGER = Logger.getLogger(ContratoLineaGastoAdicionalBean.class.getName());
    @Inject
    private ContratoFacade facade;
    @Inject
    private SelectorGastoAdicionalBean selectorGastoAdicionalBean;
    private TipoProductoDto gastoAdicional;
    private String concepto;
    private BigDecimal importe;
    private Integer cantidad;

    public TipoProductoDto getGastoAdicional() {
        return gastoAdicional;
    }

    public void setGastoAdicional(TipoProductoDto gastoAdicional) {
        this.gastoAdicional = gastoAdicional;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public BigDecimal getImporte() {
        return importe;
    }

    public void setImporte(BigDecimal importe) {
        this.importe = importe;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
     
    public void onSelect() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            
        } catch (Exception e) {
            JsfUtil.addException(e);
        } finally {
            log.finish();
        }        
    }
    
    public void prepareNuevo() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            gastoAdicional = null;
            concepto="";
            cantidad = 1;
            importe = BigDecimal.ZERO;
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }        

    void prepareModificar(ContratoLineaGastoAdicionalDto contratoLineaGastoAdicional) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            gastoAdicional = contratoLineaGastoAdicional.getGastoAdicional();
            concepto = contratoLineaGastoAdicional.getConcepto();
            cantidad = contratoLineaGastoAdicional.getCantidad();
            importe = contratoLineaGastoAdicional.getImporte();
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void prepareSelectGastoAdicional(javax.faces.event.ActionEvent event) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            selectorGastoAdicionalBean.setDefaultFilters(Collections.singletonMap("clase:EQUALS", "GAST_ADIC"));
                    
            selectorGastoAdicionalBean.setOnSeleccionarUpdate("@(.ModificarLinea-GastoAdicionalSeleccionable)");
            selectorGastoAdicionalBean.setOnSeleccionarListener((ActionEvent event1) -> {
                gastoAdicional = selectorGastoAdicionalBean.getSelected();
                concepto = gastoAdicional.getDescripcion();
            });
            selectorGastoAdicionalBean.setOnCancelarListener((ActionEvent event1) -> {
            });
            
        } catch (Exception ex) {
            JsfUtil.addException(ex);
        } finally {
            log.finish();
        }
    }
    
}