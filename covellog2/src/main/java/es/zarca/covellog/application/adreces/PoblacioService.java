/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.zarca.covellog.application.adreces;

import es.zarca.covellog.application.adreces.form.AltaPoblacioForm;
import es.zarca.covellog.application.adreces.form.BaixaPoblacioForm;
import es.zarca.covellog.application.adreces.form.EditarPoblacioForm;

/**
 *
 * @author Francisco Torralbo
 */
public interface PoblacioService {
    void altaPoblacio(AltaPoblacioForm formulari);
    void editarPoblacio(EditarPoblacioForm formulari);
    void baixaPoblacio(BaixaPoblacioForm formulari);   
}
