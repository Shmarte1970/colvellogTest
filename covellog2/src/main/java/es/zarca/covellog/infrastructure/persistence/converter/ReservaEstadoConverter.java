package es.zarca.covellog.infrastructure.persistence.converter;

import es.zarca.covellog.domain.model.stock.reservas.ReservaEstado;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 *
 * @author francisco
 */
@Converter(autoApply = true)
public class ReservaEstadoConverter implements AttributeConverter<ReservaEstado, String> {

    @Override
    public String convertToDatabaseColumn(ReservaEstado estado) {
        return estado.getId();
    }

    @Override
    public ReservaEstado convertToEntityAttribute(String id) {
        return ReservaEstado.fromId(id);
    }
    
}
