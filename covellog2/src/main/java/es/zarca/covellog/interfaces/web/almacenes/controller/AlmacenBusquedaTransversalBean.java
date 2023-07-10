package es.zarca.covellog.interfaces.web.almacenes.controller;

import es.zarca.covellog.interfaces.facade.almacen.AlmacenesFacade;
import es.zarca.covellog.interfaces.facade.almacen.dto.AlmacenDto;
import es.zarca.covellog.interfaces.web.app.controller.BusquedaFacade;
import es.zarca.covellog.interfaces.web.app.controller.BusquedaTransversalSelector;
import java.io.Serializable;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author francisco
 */
@Named("almacenBusquedaTransversalBean")
@ViewScoped
public class AlmacenBusquedaTransversalBean extends BusquedaTransversalSelector<AlmacenDto> implements Serializable {

    @Inject
    private AlmacenesFacade facade;
    
    @Override
    public Object getItemRowKey(AlmacenDto item) {
        if (item == null) {
            return false;
        }
        return item.getId();
    }

    @Override
    public boolean hasRowKey(AlmacenDto item, String rowKey) {
        if ( (item == null) || (item.getId() == null) ) {
            return false;
        }
        return (item.getId().toString().equals(rowKey));
    }
    
    @Override
    public BusquedaFacade<AlmacenDto> getFacade() {
        return facade;
    }      

}