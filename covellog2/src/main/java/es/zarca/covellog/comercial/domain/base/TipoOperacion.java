package es.zarca.covellog.comercial.domain.base;

import javax.persistence.Entity;

/**
 *
 * @author francisco
 */
@Entity
public class TipoOperacion extends Nombre {
    public static TipoOperacion ALQUILER = new TipoOperacion("AL");
    public static TipoOperacion VENTA = new TipoOperacion("VE");

    public TipoOperacion() {
    }

    public TipoOperacion(String id) {
        super(id);
    }
    
}
