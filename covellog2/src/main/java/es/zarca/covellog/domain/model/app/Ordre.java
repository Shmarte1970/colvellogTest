/*
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.zarca.covellog.domain.model.app;

import org.primefaces.model.SortOrder;

/**
 *
 * @author francisco
 */
public class Ordre {
    private final String id;
    private final TipusOrdre tipusOrdre;

    public Ordre(String id, TipusOrdre tipusOrdre) {
        this.id = id;
        this.tipusOrdre = tipusOrdre;
    }

    public String getId() {
        return id;
    }

    public TipusOrdre getTipusOrdre() {
        return tipusOrdre;
    }
    
}
