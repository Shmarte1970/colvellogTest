/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.zarca.covellog.application.comercials;

import es.zarca.covellog.application.comercials.form.BaixaComercialForm;
import es.zarca.covellog.application.comercials.form.ComercialForm;
import es.zarca.covellog.application.comercials.form.AltaComercialForm;

/**
 *
 * @author Francisco Torralbo
 */
public interface ComercialService {
    void altaComercial(AltaComercialForm formulari);
    void editarComercial(ComercialForm formulari);
    void baixaComercial(BaixaComercialForm formulari);   
}
