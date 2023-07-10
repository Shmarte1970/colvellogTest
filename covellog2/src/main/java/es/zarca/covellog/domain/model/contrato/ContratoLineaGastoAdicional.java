package es.zarca.covellog.domain.model.contrato;

import es.zarca.covellog.domain.model.producto.TipoProducto;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.concurrent.ThreadLocalRandom;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author francisco
 */
@Entity
@Table(name = "contrato_linea_gasto_adicional")
@XmlRootElement
class ContratoLineaGastoAdicional implements ContratoLineaGastoAdicionalRO, Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "orden")
    private Integer orden;
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "concepto")
    private String concepto;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cantidad")
    private int cantidad;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "importe")
    private BigDecimal importe;
    @JoinColumn(name = "contrato_linea_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private ContratoLinea linea;
    @JoinColumn(name = "tipo_producto_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private TipoProducto tipoProducto;
    
    public ContratoLineaGastoAdicional() {
    }

    public ContratoLineaGastoAdicional(ContratoLinea linea, TipoProducto tipoProducto, String concepto, int cantidad, BigDecimal importe, Integer orden) {
        id =  ThreadLocalRandom.current().nextInt();
        this.linea = linea;
        this.tipoProducto = tipoProducto;
        this.concepto = concepto;
        this.cantidad = cantidad;
        this.importe = importe;
        this.orden = orden;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    @Override
    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    @Override
    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public BigDecimal getImporte() {
        return importe;
    }
    
    public BigDecimal getImporteTotal() {
        return importe.multiply(new BigDecimal(cantidad));
    }

    public void setImporte(BigDecimal importe) {
        this.importe = importe;
    }

    @Override
    public ContratoLinea getLinea() {
        return linea;
    }

    public void setLinea(ContratoLinea linea) {
        this.linea = linea;
    }

    @Override
    public TipoProducto getTipoProducto() {
        return tipoProducto;
    }

    public void setTipoProducto(TipoProducto tipoProducto) {
        this.tipoProducto = tipoProducto;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ContratoLineaGastoAdicional)) {
            return false;
        }
        ContratoLineaGastoAdicional other = (ContratoLineaGastoAdicional) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "es.zarca.covellog.domain.model.contrato.ContratoLineaGastoAdicional[ id=" + id + " ]";
    }
    
}
