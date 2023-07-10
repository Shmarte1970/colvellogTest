/*
 * Proyecto Covellog2
 * 
 * Este archivo ha sido creado para ZARCA y no se puede usar sin su consentimiento.
 */
package es.zarca.covellog.infrastructure.persistence.exception;

import es.zarca.covellog.domain.model.app.Ordre;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public class OrdenIncorrectoException extends PersistenceException {
  
    public OrdenIncorrectoException(String entityName, Ordre orden) {
        super("No se puede ordenar " + entityName + " por " + orden.getId() + " " + orden.getTipusOrdre().name() + ".");
    }
        
}
