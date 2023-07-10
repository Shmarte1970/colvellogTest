package es.zarca.covellog.domain.model.empresa;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author francisco
 */
@MappedSuperclass
public class EmpresaRol implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "empresa_id")
    protected Integer empresaId;
    @Basic(optional = false)
    @Column(name = "fecha_bloqueo")
    @Temporal(TemporalType.TIMESTAMP)
    protected Date fechaBloqueo;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="empresa_id", insertable = false, updatable = false)
    protected Empresa empresa;
    @Embedded
    protected DobleObservacion observaciones;
    
    public EmpresaRol() {
    }
    
    public EmpresaRol(Empresa empresa) {
        this.empresa = empresa;
        this.empresaId = empresa.getId();
    }

    public EmpresaRol(Empresa empresa, DobleObservacion observaciones) {
        this.empresa = empresa;
        this.observaciones = observaciones;
    }
    
    public Date getFechaBloqueo() {
        return fechaBloqueo;
    }

    public void setFechaBloqueo(Date fechaBloqueo) {
        this.fechaBloqueo = fechaBloqueo;
    }
    
    public boolean isBloqueado() {
        return this.fechaBloqueo != null;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
        this.empresaId = empresa.getId();
    }

    public DobleObservacion getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(DobleObservacion observaciones) {
        this.observaciones = observaciones;
    }

    @Override
    public String toString() {
        return "es.zarca.covellog.domain.model.empresa.EmpresaRol[ id=" + empresaId + " ]";
    }
    
}
