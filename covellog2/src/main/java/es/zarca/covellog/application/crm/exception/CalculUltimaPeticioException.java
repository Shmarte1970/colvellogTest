/*
 * Proyecto Covellog2
 * 
 * Este archivo ha sido creado para ZARCA y no se puede usar sin su consentimiento.
 */
package es.zarca.covellog.application.crm.exception;

import es.zarca.covellog.application.exception.BusinessException;
import es.zarca.covellog.domain.model.crm.peticiocontacte.PeticioContacte;
import java.util.List;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public class CalculUltimaPeticioException extends BusinessException {

    public CalculUltimaPeticioException(List<PeticioContacte> peticions, int posicio) {
        super("Error al calcular la fecha de la última petición de contacto importada. Tamaño lista: " + peticions.size() + " Posición: " + String.valueOf(posicio));
    }
    
}
