/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.zarca.covellog.interfaces.facade.crm.peticionscontacte.facade.internal;

import es.zarca.covellog.application.crm.ImportarPeticionsContacteService;
import es.zarca.covellog.application.crm.exception.CrmPersistenceException;
import es.zarca.covellog.infrastructure.persistence.exception.PersistenceException;
import es.zarca.covellog.interfaces.facade.crm.peticionscontacte.facade.ImportarPeticionsContacteServiceFacade;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.eclipse.persistence.exceptions.DatabaseException;

/**
 *
 * @author francisco
 */
@ApplicationScoped
public class DefaultImportarPeticionsContacteServiceFacade implements ImportarPeticionsContacteServiceFacade, Serializable {
    private static final long serialVersionUID = 1L;
    
    @Inject
    private ImportarPeticionsContacteService importarPeticionsContacteService;
   
    @Override
    public Integer importarPeticionsContactePendents() {
        try{
            return importarPeticionsContacteService.importarPeticionsContactePendents();
        } catch (ConstraintViolationException e) {
            String message = "";
            int i=1;
            for (ConstraintViolation actual : e.getConstraintViolations()) {
                message = message + " " + i + ": " + actual.getPropertyPath().toString() + " " + actual.getMessage();
            }
            throw new CrmPersistenceException(message);
        } catch (DatabaseException e) {
            throw new PersistenceException(e.getMessage());
        }
    }
    
    @Override
    public Date getUltimaPeticioContacteConvertida() {
        BigDecimal ultima = importarPeticionsContacteService.getUltimaPeticioContacteConvertida();       
        return new Date(ultima.multiply(new BigDecimal(1000)).longValue());
    }
    
}
