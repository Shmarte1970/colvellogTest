/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.zarca.covellog.application.adreces;

import es.zarca.covellog.application.adreces.exception.PaisCodiNotUniqueException;
import es.zarca.covellog.application.adreces.exception.PaisNomNotUniqueException;
import es.zarca.covellog.application.adreces.form.AltaPaisForm;
import es.zarca.covellog.application.adreces.form.BaixaPaisForm;
import es.zarca.covellog.application.adreces.form.EditarPaisForm;

/**
 *
 * @author Francisco Torralbo
 */
public interface PaisService {
    void altaPais(AltaPaisForm form) throws PaisNomNotUniqueException, PaisCodiNotUniqueException;
    void modificarPais(EditarPaisForm form);
    void baixaPais(BaixaPaisForm form);   
}
