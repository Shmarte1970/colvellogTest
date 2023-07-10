package es.zarca.covellog.infrastructure.persistence.converter;

import es.zarca.covellog.domain.model.stock.movimientos.MovimientoTipo;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 *
 * @author francisco
 */
@Converter(autoApply = true)
public class MovimientoTipoConverter implements AttributeConverter<MovimientoTipo, String> {

    @Override
    public String convertToDatabaseColumn(MovimientoTipo tipo) {
        return tipo.getId();
    }

    @Override
    public MovimientoTipo convertToEntityAttribute(String id) {
        return MovimientoTipo.fromId(id);
    }
    
}
