
package es.zarca.covellog.domain.model.stock;

import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author francisco
 */
@Stateless
public class StockDomainService {
    @Inject
    private StockEstadoRepository stockEstadoRepository;
    
    public StockEstado getEstado(String estadoId) {
        return stockEstadoRepository.findOrFail(estadoId);
    }
    
}
