package es.zarca.covellog.domain.model.stock;

import es.zarca.covellog.domain.model.almacen.Almacen;
import es.zarca.covellog.domain.model.empresa.Empresa;
import es.zarca.covellog.domain.model.producto.TipoProducto;
import es.zarca.covellog.domain.model.ubicacion.Ubicacion;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author francisco
 */
@Entity
@Table(name = "stock_por_lote")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "StockPorAlmacen.findAll", query = "SELECT s FROM StockPorAlmacen s"),
    @NamedQuery(name = "StockPorAlmacen.findById", query = "SELECT s FROM StockPorAlmacen s WHERE s.id = :id")})
public class StockPorAlmacen implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    @Size(max = 100)
    private String id;
    @JoinColumn(name = "tipo_producto_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private TipoProducto tipoProducto;
    @JoinColumn(name = "propietario_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Empresa propietario;
    @JoinColumn(name = "ubicacion_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Ubicacion ubicacion;
    @JoinColumn(name = "flota_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Flota flota;
    @JoinColumn(name = "estado_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private StockEstado estado;
    @NotNull
    @Column(name = "condicion_id")
    private CondicionStock condicion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "stock")
    private Integer stock;

    public StockPorAlmacen() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public TipoProducto getTipoProducto() {
        return tipoProducto;
    }

    public void setTipoProducto(TipoProducto tipoProducto) {
        this.tipoProducto = tipoProducto;
    }

    public Empresa getPropietario() {
        return propietario;
    }

    public void setPropietario(Empresa propietario) {
        this.propietario = propietario;
    }

    public Ubicacion getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Flota getFlota() {
        return flota;
    }

    public void setFlota(Flota flota) {
        this.flota = flota;
    }

    public StockEstado getEstado() {
        return estado;
    }

    public void setEstado(StockEstado estado) {
        this.estado = estado;
    }

    public CondicionStock getCondicion() {
        return condicion;
    }

    public void setCondicion(CondicionStock condicion) {
        this.condicion = condicion;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final StockPorAlmacen other = (StockPorAlmacen) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

}