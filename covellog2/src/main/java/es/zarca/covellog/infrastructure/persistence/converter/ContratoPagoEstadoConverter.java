package es.zarca.covellog.infrastructure.persistence.converter;

import es.zarca.covellog.domain.model.contrato.ContratoPagoEstado;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 *
 * @author francisco
 */
@Converter(autoApply = true)
public class ContratoPagoEstadoConverter implements AttributeConverter<ContratoPagoEstado, String> {

    @Override
    public String convertToDatabaseColumn(ContratoPagoEstado estado) {
        return estado.getId();
    }

    @Override
    public ContratoPagoEstado convertToEntityAttribute(String id) {
        return ContratoPagoEstado.fromId(id);
    }
    
}
