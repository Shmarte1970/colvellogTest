/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.zarca.covellog.application.comercials.internal;

import es.zarca.covellog.application.comercials.ComercialService;
import es.zarca.covellog.application.comercials.form.AltaComercialForm;
import es.zarca.covellog.application.comercials.form.BaixaComercialForm;
import es.zarca.covellog.application.comercials.form.ComercialForm;
import javax.ejb.Stateless;

/**
 *
 * @author francisco
 */
@Stateless
public class DefaultComercialService implements ComercialService {

    @Override
    public void altaComercial(AltaComercialForm formulari) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void editarComercial(ComercialForm formulari) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void baixaComercial(BaixaComercialForm formulari) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
