package es.zarca.covellog.infrastructure.persistence.converter;

import es.zarca.covellog.domain.model.albaran.AlbaranEstado;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 *
 * @author francisco
 */
@Converter(autoApply = true)
public class AlbaranEstadoConverter implements AttributeConverter<AlbaranEstado, String> {

    @Override
    public String convertToDatabaseColumn(AlbaranEstado condicion) {
        return condicion.getId();
    }

    @Override
    public AlbaranEstado convertToEntityAttribute(String id) {
        return AlbaranEstado.fromId(id);
    }
    
}
