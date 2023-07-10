package es.zarca.covellog.interfaces.facade.almacen.dto;

import es.zarca.covellog.interfaces.facade.ubicaciones.dto.UbicacionDto;
import java.util.Date;

/**
 *
 * @author francisco
 */
public class AlmacenDto extends UbicacionDto {
    private Date fechaBaja;

    public Date getFechaBaja() {
        return fechaBaja;
    }

    public void setFechaBaja(Date fechaBaja) {
        this.fechaBaja = fechaBaja;
    }
   
}