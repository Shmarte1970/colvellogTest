package es.zarca.covellog.interfaces.web.app.controller;

import es.zarca.covellog.domain.model.app.Ordre;
import es.zarca.covellog.interfaces.web.app.model.Filtro;
import java.util.List;
import java.util.Map;

public interface BusquedaFacade<T> {

    List<T> find(int start, int size, Ordre ordre, Map<String, Object> filters);
    int findTotalCount(Map<String, Object> filters);
    List<Filtro> getFiltrosPosibles();
    
}


