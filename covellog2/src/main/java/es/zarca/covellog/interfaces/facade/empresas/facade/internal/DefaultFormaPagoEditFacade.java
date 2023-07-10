package es.zarca.covellog.interfaces.facade.empresas.facade.internal;

import es.zarca.covellog.domain.model.empresa.formapago.TipoPago;
import es.zarca.covellog.domain.model.empresa.formapago.TipoPagoRepository;
import es.zarca.covellog.domain.model.empresa.formapago.TipoVencimiento;
import es.zarca.covellog.domain.model.empresa.formapago.TipoVencimientoRepository;
import es.zarca.covellog.interfaces.facade.empresas.facade.FormaPagoEditFacade;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

/**
 *
 * @author francisco
 */
@ApplicationScoped
public class DefaultFormaPagoEditFacade implements FormaPagoEditFacade {
    @Inject
    TipoPagoRepository tipoPagoRepository;
    @Inject
    TipoVencimientoRepository tipoVencimientoRepository;
            
            
    @Override
    public List<TipoPago> getTiposPagosPosibles() {
        return tipoPagoRepository.findAll();
    }

    @Override
    public List<TipoVencimiento> getTiposVencimientoPosibles() {
        return tipoVencimientoRepository.findAll();
    }

}
