package es.zarca.covellog.infrastructure.persistence.converter;

import es.zarca.covellog.domain.model.contrato.ContratoLineaEstado;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 *
 * @author francisco
 */
@Converter(autoApply = true)
public class ContratoLineaEstadoConverter implements AttributeConverter<ContratoLineaEstado, String> {

    @Override
    public String convertToDatabaseColumn(ContratoLineaEstado estado) {
        return estado.getId();
    }

    @Override
    public ContratoLineaEstado convertToEntityAttribute(String id) {
        return ContratoLineaEstado.fromId(id);
    }
    
}
