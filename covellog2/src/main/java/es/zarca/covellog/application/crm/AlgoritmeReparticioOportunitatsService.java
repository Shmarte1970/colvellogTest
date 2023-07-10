/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.zarca.covellog.application.crm;

import es.zarca.covellog.domain.model.comercials.comercial.Comercial;
import es.zarca.covellog.domain.model.crm.repartidoroportunitats.ReglaReparticioOportunitats;
import java.util.List;


/**
 *
 * @author Francisco Torralbo
 */
public interface AlgoritmeReparticioOportunitatsService {
    void altaRegla(ReglaReparticioOportunitats regla);
    void editarRegla(ReglaReparticioOportunitats regla);
    void baixaRegla(ReglaReparticioOportunitats regla);
    List<Comercial> getComercialsPosibles();
}
