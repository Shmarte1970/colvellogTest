package es.zarca.covellog.infrastructure.persistence.converter;

import es.zarca.covellog.domain.model.stock.CondicionStock;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 *
 * @author francisco
 */
@Converter(autoApply = true)
public class CondicionStockConverter implements AttributeConverter<CondicionStock, String> {

    @Override
    public String convertToDatabaseColumn(CondicionStock condicion) {
        return condicion.getId();
    }

    @Override
    public CondicionStock convertToEntityAttribute(String id) {
        return CondicionStock.fromId(id);
    }
    
}
