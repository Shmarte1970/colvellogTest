package es.zarca.covellog.interfaces.facade.albaran.dto;

import java.util.Date;

/**
 *
 * @author francisco
 */
public class AlbaranMiniDto {
    private Integer id;
    private String codigoAlbaran;
    private Date fecha;
    private AlbaranTipoDto tipo;
    private String estado;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodigoAlbaran() {
        return codigoAlbaran;
    }

    public void setCodigoAlbaran(String codigoAlbaran) {
        this.codigoAlbaran = codigoAlbaran;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public AlbaranTipoDto getTipo() {
        return tipo;
    }

    public void setTipo(AlbaranTipoDto tipo) {
        this.tipo = tipo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
}
