package es.zarca.covellog.interfaces.web.contratos.controller;
import es.zarca.covellog.infrastructure.log.Capa;
import es.zarca.covellog.infrastructure.log.FunctionLog;
import es.zarca.covellog.interfaces.facade.contrato.ContratoFacade;
import es.zarca.covellog.interfaces.facade.contrato.dto.ContratoLineaComplementoDto;
import es.zarca.covellog.interfaces.facade.stock.dto.TipoProductoDto;
import es.zarca.covellog.interfaces.web.helpers.ExceptionHandler;
import es.zarca.covellog.interfaces.web.helpers.JsfUtil;
import es.zarca.covellog.interfaces.web.productos.complementos.SelectorComplementoBean;
import es.zarca.covellog.interfaces.web.app.controller.GuardarCancelarBean;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.logging.Logger;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
/**
 *
 * @author francisco
 */
@Named("contratoLineaComplementoBean")
@ViewScoped
public class ContratoLineaComplementoBean extends GuardarCancelarBean implements Serializable {
    private static final Logger LOGGER = Logger.getLogger(ContratoLineaComplementoBean.class.getName());
    @Inject
    private ContratoFacade facade;
    @Inject
    private SelectorComplementoBean selectorComplementoBean;
    private TipoProductoDto complemento;
    private String concepto;
    private BigDecimal importe;
    private Integer cantidad;

    public TipoProductoDto getComplemento() {
        return complemento;
    }

    public void setComplemento(TipoProductoDto complemento) {
        this.complemento = complemento;
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
            complemento = null;
            concepto="";
            cantidad = 1;
            importe = BigDecimal.ZERO;
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }        

    void prepareModificar(ContratoLineaComplementoDto contratoLineaComplemento) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            complemento = contratoLineaComplemento.getComplemento();
            concepto = contratoLineaComplemento.getConcepto();
            cantidad = contratoLineaComplemento.getCantidad();
            importe = contratoLineaComplemento.getImporte();
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void prepareSelectComplemento(javax.faces.event.ActionEvent event) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            selectorComplementoBean.setDefaultFilters(Collections.singletonMap("clase:EQUALS", "COMP"));
                    
            selectorComplementoBean.setOnSeleccionarUpdate("@(.ModificarLinea-ComplementoSeleccionable)");
            selectorComplementoBean.setOnSeleccionarListener((ActionEvent event1) -> {
                complemento = selectorComplementoBean.getSelected();
                concepto = complemento.getDescripcion();
            });
            selectorComplementoBean.setOnCancelarListener((ActionEvent event1) -> {
            });
            
        } catch (Exception ex) {
            JsfUtil.addException(ex);
        } finally {
            log.finish();
        }
    }
    
}