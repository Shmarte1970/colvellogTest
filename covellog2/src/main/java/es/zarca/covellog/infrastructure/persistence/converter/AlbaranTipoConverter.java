package es.zarca.covellog.infrastructure.persistence.converter;

import es.zarca.covellog.domain.model.albaran.AlbaranTipo;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 *
 * @author francisco
 */
@Converter(autoApply = true)
public class AlbaranTipoConverter implements AttributeConverter<AlbaranTipo, String> {

    @Override
    public String convertToDatabaseColumn(AlbaranTipo tipo) {
        if (tipo == null) {
            return null;
        }
        return tipo.getId();
    }

    @Override
    public AlbaranTipo convertToEntityAttribute(String id) {
        return AlbaranTipo.fromId(id);
    }
    
}
