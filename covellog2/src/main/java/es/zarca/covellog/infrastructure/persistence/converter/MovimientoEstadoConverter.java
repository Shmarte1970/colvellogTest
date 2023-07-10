package es.zarca.covellog.infrastructure.persistence.converter;

import es.zarca.covellog.domain.model.stock.movimientos.MovimientoEstado;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 *
 * @author francisco
 */
@Converter(autoApply = true)
public class MovimientoEstadoConverter implements AttributeConverter<MovimientoEstado, String> {

    @Override
    public String convertToDatabaseColumn(MovimientoEstado estado) {
        return estado.getId();
    }

    @Override
    public MovimientoEstado convertToEntityAttribute(String id) {
        return MovimientoEstado.fromId(id);
    }
    
}
