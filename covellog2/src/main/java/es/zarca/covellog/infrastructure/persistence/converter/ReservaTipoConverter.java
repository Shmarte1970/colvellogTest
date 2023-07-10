package es.zarca.covellog.infrastructure.persistence.converter;

import es.zarca.covellog.domain.model.stock.reservas.ReservaTipo;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 *
 * @author francisco
 */
@Converter(autoApply = true)
public class ReservaTipoConverter implements AttributeConverter<ReservaTipo, String> {

    @Override
    public String convertToDatabaseColumn(ReservaTipo tipo) {
        return tipo.getId();
    }

    @Override
    public ReservaTipo convertToEntityAttribute(String id) {
        return ReservaTipo.fromId(id);
    }
    
}
