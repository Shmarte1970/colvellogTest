package es.zarca.covellog.domain.model.empresa.formapago;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author francisco
 */
@MappedSuperclass
public class FormaPagoLinea implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "num_linea")
    private Integer numLinea;
    @Basic(optional = false)
    @NotNull
    @Column(name = "porcentaje")
    private Integer porcentaje;
    @JoinColumn(name = "tipo_pago_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private TipoPago tipoPago;
    @Size(max = 25)
    @Column(name = "cuenta")
    private String cuenta;
    @JoinColumn(name = "tipo_vencimiento_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private TipoVencimiento tipoVencimiento;
    @Column(name = "dia_pago")
    private Integer diaPago;
    
    public FormaPagoLinea() {
    }

    public FormaPagoLinea(Integer numLinea, Integer porcentaje, TipoPago tipoPago, String cuenta, TipoVencimiento tipoVencimiento, Integer diaPago) {
        this.numLinea = numLinea;
        this.porcentaje = porcentaje;
        this.tipoPago = tipoPago;
        this.cuenta = cuenta;
        this.tipoVencimiento = tipoVencimiento;
        this.diaPago = diaPago;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNumLinea() {
        return numLinea;
    }

    public void setNumLinea(Integer numLinea) {
        this.numLinea = numLinea;
    }

    public Integer getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(Integer porcentaje) {
        this.porcentaje = porcentaje;
    }

    public TipoPago getTipoPago() {
        return tipoPago;
    }

    public void setTipoPago(TipoPago tipoPago) {
        this.tipoPago = tipoPago;
    }

    public String getCuenta() {
        return cuenta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }

    public TipoVencimiento getTipoVencimiento() {
        return tipoVencimiento;
    }

    public void setTipoVencimiento(TipoVencimiento tipoVencimiento) {
        this.tipoVencimiento = tipoVencimiento;
    }

    public Integer getDiaPago() {
        return diaPago;
    }

    public void setDiaPago(Integer diaPago) {
        this.diaPago = diaPago;
    }

    
}
