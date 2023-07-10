package es.zarca.covellog.domain.model.albaran;

import java.io.Serializable;
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
@Table(name = "albaran_recogida")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AlbaranRecogida.findAll", query = "SELECT a FROM AlbaranRecogida a"),
    @NamedQuery(name = "AlbaranRecogida.findById", query = "SELECT a FROM AlbaranRecogida a WHERE a.id = :id")
})
public class AlbaranRecogida extends Albaran implements Serializable {

     public AlbaranRecogida() {
        super(AlbaranTipo.RECOGIDA);
    }
     
    @Override
    public void onMovimientoAnulado(String booking) {
        super.onMovimientoAnulado(booking);
        if (getContrato() != null) {
            try {
                getContrato().onMovimientoRecogidaAnulado(booking);
            } catch (Exception ex) {
                
            }
        }
    }
    
}