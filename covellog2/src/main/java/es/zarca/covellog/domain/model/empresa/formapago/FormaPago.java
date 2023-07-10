package es.zarca.covellog.domain.model.empresa.formapago;

import es.zarca.covellog.domain.model.empresa.formapago.exception.FormaPagoNoSuma100Exception;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public class FormaPago implements Serializable {

    private static final long serialVersionUID = 1L;    
 
    private final List<FormaPagoLinea> lineas;

    public FormaPago() {
        lineas = new ArrayList<>();
    }

    public FormaPago(List<FormaPagoLinea> lineas) {
        Integer suma = 0;
        suma = lineas.stream().map(linea -> linea.getPorcentaje()).reduce(suma, Integer::sum);
        if (suma != 100) {
            throw new FormaPagoNoSuma100Exception();
        }
        this.lineas = lineas;
    }
    
    public List<FormaPagoLinea> getLineas() {
        return lineas;
    }

}
