package es.zarca.covellog.domain.model.stock.reservas;

import java.io.Serializable;
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

/**
 *
 * @author francisco
 */
@Entity
@Table(name = "reserva_linea_check_list")
public class ReservaLineaCheckList implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "orden")
    private int orden;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "descripcion")
    private String descripcion;
    @JoinColumn(name = "reserva_linea_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private ReservaLinea reservaLinea;

    public ReservaLineaCheckList() {
    }

    public ReservaLineaCheckList(ReservaLinea reservaLinea, int orden, String descripcion) {
        this.reservaLinea = reservaLinea;
        this.orden = orden;
        this.descripcion = descripcion;
    }

    public Integer getId() {
        return id;
    }

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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
        if (!(object instanceof ReservaLineaCheckList)) {
            return false;
        }
        ReservaLineaCheckList other = (ReservaLineaCheckList) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "es.zarca.covellog.domain.model.stock.reservas.ReservaLineaCheckList[ id=" + id + " ]";
    }
    
}
