package es.zarca.covellog.interfaces.facade.albaran.dto;

import java.util.Date;

/**
 *
 * @author francisco
 */
public class AlbaranRecogidaMiniDto {
    private Integer id;
    private String codigoAlbaran;
    private Date fecha;

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

}
