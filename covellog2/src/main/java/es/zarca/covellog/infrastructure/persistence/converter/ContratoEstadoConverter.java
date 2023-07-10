package es.zarca.covellog.infrastructure.persistence.converter;

import es.zarca.covellog.domain.model.contrato.ContratoEstado;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 *
 * @author francisco
 */
@Converter(autoApply = true)
public class ContratoEstadoConverter implements AttributeConverter<ContratoEstado, String> {

    @Override
    public String convertToDatabaseColumn(ContratoEstado estado) {
        return estado.getId();
    }

    @Override
    public ContratoEstado convertToEntityAttribute(String id) {
        return ContratoEstado.fromId(id);
    }
    
}
