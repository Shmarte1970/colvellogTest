/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.zarca.covellog.interfaces.web.helpers;

import es.zarca.covellog.domain.model.app.Ordre;
import es.zarca.covellog.domain.model.app.TipusOrdre;
import org.primefaces.model.SortOrder;

/**
 *
 * @author francisco
 */
public class OrdreAssembler {
    
    public static Ordre fromSortOrder(String sortField, SortOrder sortOrder) {
        if ((sortField == null) || (sortOrder == null)) {
            return null;
        }
        TipusOrdre tipusOrdre;
        switch (sortOrder) {
            case ASCENDING:
                tipusOrdre = TipusOrdre.ASCENDENT;
                break;
            case DESCENDING:
                tipusOrdre = TipusOrdre.DESCENDENT;
                break;
            default:
                tipusOrdre = TipusOrdre.SENSE_ORDRE;
                break;
        }
        return new Ordre(sortField, tipusOrdre);
    }
}
