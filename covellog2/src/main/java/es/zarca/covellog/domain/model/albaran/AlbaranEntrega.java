package es.zarca.covellog.domain.model.albaran;

import es.zarca.covellog.domain.model.producto.TipoProducto;
import es.zarca.covellog.domain.model.stock.Stock;
import es.zarca.covellog.domain.model.ubicacion.Ubicacion;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author francisco
 */
@Entity
@Table(name = "albaran_entrega")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AlbaranEntrega.findAll", query = "SELECT a FROM AlbaranEntrega a"),
    @NamedQuery(name = "AlbaranEntrega.findById", query = "SELECT a FROM AlbaranEntrega a WHERE a.id = :id")
})
public class AlbaranEntrega extends Albaran implements Serializable {

    public AlbaranEntrega() {
        super(AlbaranTipo.ENTREGA);
    }

    public void eliminar() {
        List<AlbaranLinea> lineasEliminar = new ArrayList();        
        for (AlbaranLinea linea : lineas) {
            lineasEliminar.add(linea);
        }
        lineasEliminar.forEach(lineaEliminar -> {
            lineas.remove(lineaEliminar);
            if (contrato != null) {
                contrato.onAlbaranEntregaLineaEliminada(lineaEliminar.getBooking());
            }
        });
        if (contrato != null) {
            contrato.eliminarAlbaranEntrega(this);
        }
       
    }

    @Override
    public void onMovimientoAnulado(String booking) {
        super.onMovimientoAnulado(booking);
        if (contrato != null) {
            try {
                contrato.onMovimientoEntregaAnulado(booking);
            } catch (Exception ex) {
                
            }
        }
    }

}