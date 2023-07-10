/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.zarca.covellog.application.adreces;

import es.zarca.covellog.application.adreces.form.AltaProvinciaForm;
import es.zarca.covellog.application.adreces.form.BaixaProvinciaForm;
import es.zarca.covellog.application.adreces.form.EditarProvinciaForm;

/**
 *
 * @author Francisco Torralbo
 */
public interface ProvinciaService {
    void altaProvincia(AltaProvinciaForm form);
    void editarProvincia(EditarProvinciaForm form);
    void baixaProvincia(BaixaProvinciaForm form);   
}
