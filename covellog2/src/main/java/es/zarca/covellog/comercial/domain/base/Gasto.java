package es.zarca.covellog.comercial.domain.base;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author francisco
 */
@MappedSuperclass
public class Gasto implements Serializable {

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
    
    Gasto() {
    }

    Gasto(String concepto, int cantidad, BigDecimal importe, Integer orden) {
        id =  ThreadLocalRandom.current().nextInt();
        this.concepto = concepto;
        this.cantidad = cantidad;
        this.importe = importe;
        this.orden = orden;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

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
    public boolean equals(Object object) {
        if (!(object instanceof Gasto)) {
            return false;
        }
        Gasto other = (Gasto) object;
        return !((this.id == null && other.getId() != null) || (this.id != null && !this.id.equals(other.getId())));
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public String toString() {
        return "es.zarca.covellog.comercial.domain.base.Gasto[ id=" + id + " ]";
    }
    
}
