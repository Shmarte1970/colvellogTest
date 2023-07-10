package es.zarca.covellog.domain.model.adreces.adreça;

import es.zarca.covellog.domain.model.adreces.poblacio.Poblacio;
import javax.persistence.Embeddable;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
@Embeddable
public class DireccionPostal implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "att")
    private String att;
    @Basic(optional = false)
    @NotNull
    @Column(name = "direccion")
    protected String direccion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "direccion2")
    protected String direccion2;
    @Embedded
    protected CodigoPostal codigoPostal;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "poblacion_id")
    protected Poblacio poblacion;

    public DireccionPostal() {
    }

    public DireccionPostal(String att, String direccion, String direccion2, CodigoPostal codigoPostal, Poblacio poblacion) {
        this.att = att;
        this.direccion = direccion;
        this.direccion2 = direccion2;
        this.codigoPostal = codigoPostal;
        this.poblacion = poblacion;
    }

    public String getAtt() {
        return att;
    }

    public void setAtt(String att) {
        this.att = att;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getDireccion2() {
        return direccion2;
    }

    public void setDireccion2(String direccion2) {
        this.direccion2 = direccion2;
    }

    public CodigoPostal getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(CodigoPostal codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public Poblacio getPoblacion() {
        return poblacion;
    }

    public void setPoblacion(Poblacio poblacion) {
        this.poblacion = poblacion;
    }
    
}