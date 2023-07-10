package es.zarca.covellog.infrastructure.persistence.converter;

import es.zarca.covellog.domain.model.contrato.ContratoTipoOperacion;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 *
 * @author francisco
 */
@Converter(autoApply = true)
public class TipoOperacionConverter implements AttributeConverter<ContratoTipoOperacion, String> {

    @Override
    public String convertToDatabaseColumn(ContratoTipoOperacion tipoOperacion) {
        return tipoOperacion.getId();
    }

    @Override
    public ContratoTipoOperacion convertToEntityAttribute(String id) {
        return ContratoTipoOperacion.fromId(id);
    }
    
}
