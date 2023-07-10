package es.zarca.covellog.interfaces.web.contratos.controller;
import es.zarca.covellog.application.contratos.form.ContratoCondicionesForm;
import es.zarca.covellog.infrastructure.log.Capa;
import es.zarca.covellog.infrastructure.log.FunctionLog;
import es.zarca.covellog.interfaces.facade.contrato.ContratoFacade;
import es.zarca.covellog.interfaces.facade.contrato.dto.ContratoDto;
import es.zarca.covellog.interfaces.facade.empresas.facade.dto.DetalleContratacionDto;
import es.zarca.covellog.interfaces.facade.empresas.facade.dto.FacturarPorDto;
import es.zarca.covellog.interfaces.facade.empresas.facade.dto.TipoFacturacionDto;
import es.zarca.covellog.interfaces.web.helpers.ExceptionHandler;
import es.zarca.covellog.interfaces.web.helpers.JsfUtil;
import es.zarca.covellog.interfaces.web.app.controller.GuardarCancelarBean;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.eclipse.persistence.jpa.jpql.Assert;
/**
 *
 * @author francisco
 */
@Named("condicionesContratoBean")
@ViewScoped
public class CondicionesContratoBean extends GuardarCancelarBean implements Serializable {
    private static final Logger LOGGER = Logger.getLogger(CondicionesContratoBean.class.getName());
    @Inject
    private ContratoFacade facade;
    @Inject
    private ModificarContratoBean modificarContratoBean;
    private Integer id=0;
    /*private boolean transporteEntregaAdelantado;
    private boolean transporteRecogidaAdelantado;
    private boolean montajeAdelantado;
    private boolean desmontajeAdelantado;
    private int mesesFianza;
    private TipoFacturacionDto tipoFacturacion;
    private FacturarPorDto facturarPor;
    private Integer descuento;*/
    private List<TipoFacturacionDto> tiposFacturacionPosibles;
    private List<FacturarPorDto> facturarPorPosibles;
    private DetalleContratacionDto condiciones;
    public CondicionesContratoBean() {
    }
    
    public ContratoDto getContrato() {
        return modificarContratoBean.getContrato();
    }
    
    public void setContrato(ContratoDto contrato) {
        modificarContratoBean.setContrato(contrato);
    }

    public DetalleContratacionDto getCondiciones() {
        if (condiciones == null) {
            condiciones = getContrato().getCondiciones();
        }
        return condiciones;
    }

    public void setCondiciones(DetalleContratacionDto condiciones) {
        this.condiciones = condiciones;
    }
    
    /*public boolean isTransporteEntregaAdelantado() {
        return transporteEntregaAdelantado;
    }

    public void setTransporteEntregaAdelantado(boolean transporteEntregaAdelantado) {
        this.transporteEntregaAdelantado = transporteEntregaAdelantado;
    }

    public boolean isTransporteRecogidaAdelantado() {
        return transporteRecogidaAdelantado;
    }

    public void setTransporteRecogidaAdelantado(boolean transporteRecogidaAdelantado) {
        this.transporteRecogidaAdelantado = transporteRecogidaAdelantado;
    }

    public boolean isMontajeAdelantado() {
        return montajeAdelantado;
    }

    public void setMontajeAdelantado(boolean montajeAdelantado) {
        this.montajeAdelantado = montajeAdelantado;
    }

    public boolean isDesmontajeAdelantado() {
        return desmontajeAdelantado;
    }

    public void setDesmontajeAdelantado(boolean desmontajeAdelantado) {
        this.desmontajeAdelantado = desmontajeAdelantado;
    }

    public int getMesesFianza() {
        return mesesFianza;
    }

    public void setMesesFianza(int mesesFianza) {
        this.mesesFianza = mesesFianza;
    }

    public TipoFacturacionDto getTipoFacturacion() {
        return tipoFacturacion;
    }

    public void setTipoFacturacion(TipoFacturacionDto tipoFacturacion) {
        this.tipoFacturacion = tipoFacturacion;
    }

    public FacturarPorDto getFacturarPor() {
        return facturarPor;
    }

    public void setFacturarPor(FacturarPorDto facturarPor) {
        this.facturarPor = facturarPor;
    }

    public Integer getDescuento() {
        return descuento;
    }

    public void setDescuento(Integer descuento) {
        this.descuento = descuento;
    }*/

    public List<TipoFacturacionDto> getTiposFacturacionPosibles() {
        if (tiposFacturacionPosibles == null) {
            tiposFacturacionPosibles = facade.getTiposFacturacionPosibles();
        }
        return tiposFacturacionPosibles;
    }

    public void setTiposFacturacionPosibles(List<TipoFacturacionDto> tiposFacturacionPosibles) {
        this.tiposFacturacionPosibles = tiposFacturacionPosibles;
    }

    public List<FacturarPorDto> getFacturarPorPosibles() {
        if (facturarPorPosibles == null) {
            facturarPorPosibles = facade.getFacturarPorPosibles();
        }
        return facturarPorPosibles;
    }

    public void setFacturarPorPosibles(List<FacturarPorDto> facturarPorPosibles) {
        this.facturarPorPosibles = facturarPorPosibles;
    }
    
    public void prepare() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            Assert.isNotNull(getContrato(), "El contrato es null.");
           /* DetalleContratacionDto condiciones = getContrato().getCondiciones();
            if (condiciones != null) {
                transporteEntregaAdelantado = condiciones.isTransporteEntregaAdelantado();
                transporteRecogidaAdelantado = condiciones.isTransporteRecogidaAdelantado();
                montajeAdelantado = condiciones.isMontajeAdelantado();
                desmontajeAdelantado = condiciones.isDesmontajeAdelantado();
                mesesFianza = condiciones.getMesesFianza();
                tipoFacturacion = condiciones.getTipoFacturacion();
                facturarPor = condiciones.getFacturarPor();
                descuento = condiciones.getDescuento();
            }
            
            facturarPorPosibles = facade.getFacturarPorPosibles();*/
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    @Override
    protected void guardarImpl() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        ContratoCondicionesForm form = new ContratoCondicionesForm();
      /*  form.setTransporteEntregaAdelantado(transporteEntregaAdelantado);
        form.setTransporteRecogidaAdelantado(transporteRecogidaAdelantado);
        form.setMontajeAdelantado(montajeAdelantado);
        form.setDesmontajeAdelantado(desmontajeAdelantado);
        form.setMesesFianza(mesesFianza);
        form.setTipoFacturacionId(tipoFacturacion.getId());
        form.setFacturarPor(facturarPor.getId());
        form.setDescuento(descuento);*/
        form.setTransporteEntregaAdelantado(condiciones.isTransporteEntregaAdelantado());
        form.setTransporteRecogidaAdelantado(condiciones.isTransporteRecogidaAdelantado());
        form.setMontajeAdelantado(condiciones.isMontajeAdelantado());
        form.setDesmontajeAdelantado(condiciones.isDesmontajeAdelantado());
        form.setMesesFianza(condiciones.getMesesFianza());
        form.setTipoFacturacionId(condiciones.getTipoFacturacion().getId());
        form.setFacturarPor(condiciones.getFacturarPor().getId());
        form.setDescuento(condiciones.getDescuento());
        facade.modificarCondiciones(getContrato().getId(), form);
        setContrato(null);
        prepare();
        JsfUtil.addSuccessMessage("Contrato modificado correctamente");
        log.finish();
    }
    
    

}