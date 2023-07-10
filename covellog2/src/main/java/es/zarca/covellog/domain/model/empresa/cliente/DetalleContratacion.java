package es.zarca.covellog.domain.model.empresa.cliente;

import javax.persistence.Embeddable;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
@Embeddable
public class DetalleContratacion implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "detcont_tte_entrega_adelantado")
    final private boolean transporteEntregaAdelantado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "detcont_tte_recogida_adelantado")
    final private boolean transporteRecogidaAdelantado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "detcont_montaje_adelantado")
    final private boolean montajeAdelantado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "detcont_desmontaje_adelantado")
    final private boolean desmontajeAdelantado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "detcont_meses_fianza")
    final private int mesesFianza;
    @Basic(optional = false)
    @NotNull
    @Column(name = "detcont_tipo_facturacion")
    final private String tipoFacturacion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "detcont_facturar_por")
    final private String facturarPor;
    @Basic(optional = false)
    @NotNull
    @Column(name = "detcont_descuento")
    final private Integer descuento;

    public DetalleContratacion() {
        this.transporteEntregaAdelantado = true;
        this.transporteRecogidaAdelantado = true;
        this.montajeAdelantado = true;
        this.desmontajeAdelantado = true;
        this.mesesFianza = 1;
        this.tipoFacturacion = TipoFacturacion.MES_ADELANTADO.getId();
        this.facturarPor = FacturarPor.MES.getId();
        this.descuento = 0;
    }
    
    public DetalleContratacion(boolean transporteEntregaAdelantado, boolean transporteRecogidaAdelantado, boolean montajeAdelantado, boolean desmontajeAdelantado, int mesesFianza, TipoFacturacion tipoFacturacion, FacturarPor facturarPor, Integer descuento) {
        this.transporteEntregaAdelantado = transporteEntregaAdelantado;
        this.transporteRecogidaAdelantado = transporteRecogidaAdelantado;
        this.montajeAdelantado = montajeAdelantado;
        this.desmontajeAdelantado = desmontajeAdelantado;
        this.mesesFianza = mesesFianza;
        this.tipoFacturacion = tipoFacturacion.getId();
        this.facturarPor = facturarPor.getId();
        this.descuento = descuento;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public boolean getTransporteEntregaAdelantado() {
        return transporteEntregaAdelantado;
    }

    public boolean getTransporteRecogidaAdelantado() {
        return transporteRecogidaAdelantado;
    }

    public boolean getMontajeAdelantado() {
        return montajeAdelantado;
    }

    public boolean getDesmontajeAdelantado() {
        return desmontajeAdelantado;
    }

    public int getMesesFianza() {
        return mesesFianza;
    }

    public TipoFacturacion getTipoFacturacion() {
        return TipoFacturacion.parse(tipoFacturacion);
    }

    public FacturarPor getFacturarPor() {
        return FacturarPor.parse(facturarPor);
    }
    
    public Integer getDescuento() {
        return descuento;
    }
 
}