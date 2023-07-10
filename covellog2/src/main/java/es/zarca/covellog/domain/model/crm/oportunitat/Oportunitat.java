package es.zarca.covellog.domain.model.crm.oportunitat;

import es.zarca.covellog.domain.model.clients.potencial.Potencial2;
import es.zarca.covellog.domain.model.comercials.comercial.Comercial;
import es.zarca.covellog.domain.model.comercials.divisiocomercial.DivisioComercial;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author francisco
 */
@Entity
@Table(name = "oportunitat")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Oportunitat.findAll", query = "SELECT o FROM Oportunitat o")
    , @NamedQuery(name = "Oportunitat.findById", query = "SELECT o FROM Oportunitat o WHERE o.id = :id")
    , @NamedQuery(name = "Oportunitat.findByData", query = "SELECT o FROM Oportunitat o WHERE o.dataCreacio = :dataCreacio")
    , @NamedQuery(name = "Oportunitat.findByConcepte", query = "SELECT o FROM Oportunitat o WHERE o.concepte = :concepte")})
public class Oportunitat implements Serializable {

    

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "data_creacio")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCreacio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "data_oportunitat")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataOportunitat;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "concepte")
    private String concepte;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 0, max = 65535)
    @Column(name = "detall")
    private String detall;
    @JoinColumn(name = "estat_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private EstatOportunitat estat;
    @JoinColumn(name = "origen_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private OrigenOportunitat origen;
    @JoinColumn(name = "potencial_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Potencial2 potencial;
    @JoinColumn(name = "divisio_comercial_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private DivisioComercial divisioComercial;
    @JoinColumn(name = "comercial_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Comercial comercial;

    public Oportunitat() {
        this.dataCreacio = new Date();
    }

    public Oportunitat(Integer id) {
        this.id = id;
        this.dataCreacio = new Date();
    }

    public Oportunitat(Integer id, Date dataOportunitat, String concepte, String detall) {
        this.id = id;
        this.dataCreacio = dataOportunitat;
        this.concepte = concepte;
        this.detall = detall;
        this.dataCreacio = new Date();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDataCreacio() {
        return dataCreacio;
    }

    public void setDataCreacio(Date dataCreacio) {
        this.dataCreacio = dataCreacio;
    }
    
    public Date getDataOportunitat() {
        return dataOportunitat;
    }

    public void setDataOportunitat(Date dataOportunitat) {
        this.dataOportunitat = dataOportunitat;
    }

    public String getConcepte() {
        return concepte;
    }

    public void setConcepte(String concepte) {
        this.concepte = concepte;
    }

    public String getDetall() {
        return detall;
    }

    public void setDetall(String detall) {
        this.detall = detall;
    }

    public EstatOportunitat getEstat() {
        return estat;
    }

    public void setEstat(EstatOportunitat estat) {
        this.estat = estat;
    }

    public OrigenOportunitat getOrigen() {
        return origen;
    }

    public void setOrigen(OrigenOportunitat origen) {
        this.origen = origen;
    }

    public Potencial2 getPotencial() {
        return potencial;
    }

    public void setPotencial(Potencial2 potencial) {
        this.potencial = potencial;
    }

    public DivisioComercial getDivisioComercial() {
        return divisioComercial;
    }

    public void setDivisioComercial(DivisioComercial divisioComercial) {
        this.divisioComercial = divisioComercial;
    }

    public Comercial getComercial() {
        return comercial;
    }

    public void setComercial(Comercial comercial) {
        this.comercial = comercial;
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
        if (!(object instanceof Oportunitat)) {
            return false;
        }
        Oportunitat other = (Oportunitat) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "es.zarca.covellog.domain.model.crm.oportunitat.Oportunitat[ id=" + id + " ]";
    }
    
}
