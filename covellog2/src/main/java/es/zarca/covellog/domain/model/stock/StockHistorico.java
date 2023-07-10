package es.zarca.covellog.domain.model.stock;

import es.zarca.covellog.infrastructure.assertions.ArgumentValidator;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author francisco
 */
@MappedSuperclass
public class StockHistorico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @JoinColumn(name = "stock_id", referencedColumnName = "id")
    @ManyToOne(optional = false,fetch = FetchType.EAGER)
    private Stock stock;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_inicio")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInicio;
    
    @Basic(optional = false)
    //@NotNull
    @Column(name = "fecha_fin")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFin;
    
    @Lob
    @Size(max = 10000)
    @Column(name = "observaciones")
    private String observaciones;

    public StockHistorico() {
    }

    public StockHistorico(Stock stock, Date fechaInicio, String observaciones) {
        ArgumentValidator.isNotNull(stock, "No se puede crear un historico de producto sin especificar el producto.");
        ArgumentValidator.isNotNull(fechaInicio, "No se puede crear un historico sin fecha de inicio.");
        this.stock = stock;
        this.fechaInicio = fechaInicio;
        this.fechaFin = null;
        this.observaciones = observaciones;
    }
    
    public StockHistorico(Stock stock, Date fechaInicio, Date fechaFin, String observaciones) {
        ArgumentValidator.isNotNull(stock, "No se puede crear un historico de producto sin especificar el producto.");
        ArgumentValidator.isNotNull(fechaInicio, "No se puede crear un historico sin fecha de inicio.");
        ArgumentValidator.isNotNull(fechaFin, "No se puede crear un historico sin fecha de fin.");
        ArgumentValidator.isFalse(fechaFin.before(fechaInicio), "La fecha de fin de periodo no puede ser menor que la fecha de inicio.");
        this.stock = stock;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.observaciones = observaciones;
    }
    
    public Integer getId() {
        return id;
    }

    public Stock getStock() {
        return stock;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public String getObservaciones() {
        return observaciones;
    }
    
    public void finalizar(Date fecha) {
        ArgumentValidator.isNotNull(fecha, "No puede finalizar un periodo sin asignar fecha fin.");
        ArgumentValidator.isNull(fechaFin, "No puede finalizar un periodo ya finalizado.");
        ArgumentValidator.isFalse(fecha.before(fechaInicio), "La fecha de fin de periodo no puede ser menor que la fecha de inicio.");
        fechaFin = fecha;
    }
    
    public void reabrir() {
        ArgumentValidator.isNotNull(fechaFin, "No puede reabrir el periodo de " + this.getClass().getSimpleName() + " porque no estaba finalizado.");
        fechaFin = null;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.id);
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
        final StockHistorico other = (StockHistorico) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
}