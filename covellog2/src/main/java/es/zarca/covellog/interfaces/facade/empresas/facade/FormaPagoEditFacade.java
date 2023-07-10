package es.zarca.covellog.interfaces.facade.empresas.facade;

import es.zarca.covellog.domain.model.empresa.formapago.TipoPago;
import es.zarca.covellog.domain.model.empresa.formapago.TipoVencimiento;
import java.util.List;


/**
 *
 * @author francisco
 */
public interface FormaPagoEditFacade {
    List<TipoPago> getTiposPagosPosibles();
    List<TipoVencimiento> getTiposVencimientoPosibles();
}
