package es.zarca.covellog.domain.model.producto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author francisco
 */
@Entity
@Table(name = "tipo_producto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoProducto.findAll", query = "SELECT t FROM TipoProducto t"),
    @NamedQuery(name = "TipoProducto.findById", query = "SELECT t FROM TipoProducto t WHERE t.id = :id"),
    @NamedQuery(name = "TipoProducto.findByDescripcion", query = "SELECT t FROM TipoProducto t WHERE t.descripcion = :descripcion"),
    @NamedQuery(name = "TipoProducto.findByFechaAlta", query = "SELECT t FROM TipoProducto t WHERE t.fechaAlta = :fechaAlta"),
    @NamedQuery(name = "TipoProducto.findByFechaBaja", query = "SELECT t FROM TipoProducto t WHERE t.fechaBaja = :fechaBaja")})
public class TipoProducto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "id")
    private String id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 190)
    @Column(name = "descripcion")
    private String descripcion;
    @Size(min = 1, max = 190)
    @Column(name = "detalle")
    private String detalle;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_alta")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAlta;
    @Column(name = "fecha_baja")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaBaja;
    @JoinColumn(name = "familia_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private FamiliaProducto familia;
    @JoinColumn(name = "unidad_medida_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private UnidadMedida unidadMedida;
    @JoinColumn(name = "clase_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private TipoProductoClase clase;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipoProducto", orphanRemoval = true)
    @OrderBy("orden ASC")
    private List<TipoProductoDocumento> documentos;
    
    public TipoProducto() {
        fechaAlta = new Date();
        documentos = new ArrayList<>();
    }

    public TipoProducto(String id) {
        this.id = id;
        documentos = new ArrayList<>();
    }

    public TipoProducto(String id, String descripcion, Date fechaAlta) {
        this.id = id;
        this.descripcion = descripcion;
        this.fechaAlta = fechaAlta;
        documentos = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }
    
    public Date getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public Date getFechaBaja() {
        return fechaBaja;
    }

    public void baja() {
        this.fechaBaja = new Date();
    }
    
    public void anularBaja() {
        this.fechaBaja = null;
    }

    public FamiliaProducto getFamilia() {
        return familia;
    }

    public void setFamilia(FamiliaProducto familia) {
        this.familia = familia;
    }

    public UnidadMedida getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(UnidadMedida unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public TipoProductoClase getClase() {
        return clase;
    }

    public void setClase(TipoProductoClase clase) {
        this.clase = clase;
    }

    @XmlTransient
    public List<TipoProductoDocumento> getDocumentos() {
        return Collections.unmodifiableList(documentos);
    }

    public void eliminarDocumento(TipoProductoDocumento documento) {
        documentos.remove(documento);
        updateDocumentosListOrder();
    }
    
    public TipoProductoDocumento getDocumentoById(Integer documentoId) {
        if (documentoId != null) {
            for (TipoProductoDocumento documento : documentos) {
                System.err.println("comparo " + documento.getId() + " = " + documentoId);
                if (documentoId.equals(documento.getId())) {
                    System.err.println("retoirna " + + documento.getId());
                    return documento;
                }
            }
        }
        return null;
    }
    
    public int getPosicionDocumento(TipoProductoDocumento documento) {
        return documentos.indexOf(documento);
    }
    
    private void updateDocumentosListOrder() {
        int index = 0;
        for (TipoProductoDocumento documento : documentos) {
            documento.setOrden(index);
            System.err.println("Asigno orden: " + documento.getNombre() + " => " + index);
            index++;
        }
    }
    
    public void setPosicionDocumento(TipoProductoDocumento documento, Integer posicion) {
        System.err.println("MUEVO: " + documento.getNombre() + "-" + posicion );
        Integer posicionAnterior = documentos.indexOf(documento);
        if ((posicion < documentos.size()) && (posicionAnterior >= 0) && (!posicionAnterior.equals(posicion))) {
            TipoProductoDocumento documentoSecundario = documentos.get(posicion);
            documentos.set(posicion, documento);
            documentos.set(posicionAnterior, documentoSecundario);        
            updateDocumentosListOrder();
        }
    }
    
    public void anadirDocumento(TipoProductoDocumento documento) {
        documentos.add(documento);
        documento.setTipoProducto(this);
        documento.setOrden(documentos.size());
    }
    
    public void anadirDocumento(TipoProductoDocumento documento, Integer posicion) {
        anadirDocumento(documento);
        setPosicionDocumento(documento, posicion);
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
        if (!(object instanceof TipoProducto)) {
            return false;
        }
        TipoProducto other = (TipoProducto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "es.zarca.covellog.domain.model.TipoProducto[ id=" + id + " ]";
    }
    
}
