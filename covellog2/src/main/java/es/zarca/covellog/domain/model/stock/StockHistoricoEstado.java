package es.zarca.covellog.domain.model.stock;

import es.zarca.covellog.domain.model.contrato.Contrato;
import es.zarca.covellog.infrastructure.assertions.ArgumentValidator;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author francisco
 */
@Entity
@Table(name = "stock_historico_estado")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "StockHistoricoEstado.findAll", query = "SELECT s FROM StockHistoricoEstado s"),
    @NamedQuery(name = "StockHistoricoEstado.findById", query = "SELECT s FROM StockHistoricoEstado s WHERE s.id = :id"),
    @NamedQuery(name = "StockHistoricoEstado.findByFechaInicio", query = "SELECT s FROM StockHistoricoEstado s WHERE s.fechaInicio = :fechaInicio"),
    @NamedQuery(name = "StockHistoricoEstado.findByFechaFin", query = "SELECT s FROM StockHistoricoEstado s WHERE s.fechaFin = :fechaFin"),
    @NamedQuery(name = "StockHistoricoEstado.findByFechaInicioEfectiva", query = "SELECT s FROM StockHistoricoEstado s WHERE s.fechaInicioEfectiva = :fechaInicioEfectiva"),
    @NamedQuery(name = "StockHistoricoEstado.findByFechaFinEfectiva", query = "SELECT s FROM StockHistoricoEstado s WHERE s.fechaFinEfectiva = :fechaFinEfectiva"),
    @NamedQuery(name = "StockHistoricoEstado.findByEstado", query = "SELECT s FROM StockHistoricoEstado s WHERE s.estado = :estado")})
public class StockHistoricoEstado extends StockHistorico implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Basic(optional = false)
    @Column(name = "fecha_inicio_efectiva")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInicioEfectiva;
    
    @Basic(optional = false)
    @Column(name = "fecha_fin_efectiva")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFinEfectiva;
    
    @JoinColumn(name = "estado_id", referencedColumnName = "id")
    @ManyToOne
    private StockEstado estado;
    
    @JoinColumn(name = "contrato_id", referencedColumnName = "id")
    @ManyToOne
    private Contrato contrato;
    
    public StockHistoricoEstado() {
    }

    public StockHistoricoEstado(Date fechaInicioEfectiva, StockEstado estado, Contrato contrato, Stock stock, Date fechaInicio, String observaciones) {
        super(stock, fechaInicio, observaciones);
        this.fechaInicioEfectiva = fechaInicioEfectiva;
        this.fechaFinEfectiva = null;
        this.estado = estado;
        this.contrato = contrato;
    }
    
   /* public StockHistoricoEstado(Date fechaInicioEfectiva, Date fechaFinEfectiva, EstadoStock estado, Contrato contrato, Stock stock, Date fechaInicio, Date fechaFin, String observaciones) {
        super(stock, fechaInicio, fechaFin, observaciones);
        this.fechaInicioEfectiva = fechaInicioEfectiva;
        this.fechaFinEfectiva = fechaFinEfectiva;
        this.estado = estado;
        this.contrato = contrato;
    }*/

   /* public StockHistoricoEstado(EstadoStock estado, Contrato contrato, Stock stock, Date fechaInicio, String observaciones) {
        super(stock, fechaInicio, observaciones);
        this.fechaInicioEfectiva = null;
        this.fechaFinEfectiva = null;
        this.estado = estado;
        this.contrato = contrato;
    }*/
    
    public StockHistoricoEstado(StockEstado estado, Contrato contrato, Stock stock, Date fechaInicio, Date fechaFin, String observaciones) {
        super(stock, fechaInicio, fechaFin, observaciones);
        this.fechaInicioEfectiva = null;
        this.fechaFinEfectiva = null;
        this.estado = estado;
        this.contrato = contrato;
    }

    public Date getFechaInicioEfectiva() {
        return fechaInicioEfectiva;
    }

    public void setFechaInicioEfectiva(Date fechaInicioEfectiva) {
        this.fechaInicioEfectiva = fechaInicioEfectiva;
    }

    public Date getFechaFinEfectiva() {
        return fechaFinEfectiva;
    }

    public void setFechaFinEfectiva(Date fechaFinEfectiva) {
        this.fechaFinEfectiva = fechaFinEfectiva;
    }
    
    public StockEstado getEstado() {
        return estado;
    }

    public Contrato getContrato() {
        return contrato;
    }

    @Override
    public String toString() {
        return "es.zarca.covellog.domain.model.stock.StockHistoricoEstado[ id=" + getId() + " ]";
    }
    
}