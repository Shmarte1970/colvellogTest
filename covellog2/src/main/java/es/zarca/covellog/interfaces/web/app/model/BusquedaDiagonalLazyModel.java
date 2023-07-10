package es.zarca.covellog.interfaces.web.app.model;

import org.primefaces.model.LazyDataModel;

/**
 *
 * @author francisco
 */
public interface BusquedaDiagonalLazyModel<T> {
    
    public Filtro[] getFiltrosPosibles();
    public LazyDataModel<T> getLazyDataModel(Filtro filtro);
    
}
