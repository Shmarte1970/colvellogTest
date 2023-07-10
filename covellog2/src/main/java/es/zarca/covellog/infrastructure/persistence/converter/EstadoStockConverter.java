package es.zarca.covellog.infrastructure.persistence.converter;

import es.zarca.covellog.domain.model.stock.StockEstado;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 *
 * @author francisco
 */
@Converter(autoApply = true)
public class EstadoStockConverter implements AttributeConverter<StockEstado, String> {

    @Override
    public String convertToDatabaseColumn(StockEstado estado) {
        return estado.getId();
    }

    @Override
    public StockEstado convertToEntityAttribute(String id) {
        //return StockEstado.fromId(id);
        return null;
    }
    
}
