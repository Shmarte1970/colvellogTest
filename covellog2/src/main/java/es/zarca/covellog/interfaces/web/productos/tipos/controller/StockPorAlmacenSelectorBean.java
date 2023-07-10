package es.zarca.covellog.interfaces.web.productos.tipos.controller;

import es.zarca.covellog.interfaces.facade.stock.StockFacade;
import es.zarca.covellog.interfaces.facade.stock.dto.StockPorAlmacenDto;
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
@Named("stockPorAlmacenSelectorBean")
@ViewScoped
public class StockPorAlmacenSelectorBean extends BusquedaTransversalSelector<StockPorAlmacenDto> implements Serializable {

    @Inject
    private StockFacade facade;
    
    private String getComposedId(StockPorAlmacenDto item) {
        return item.getUbicacion() + "-" + item.getTipoProducto().getId();
    }
    
    @Override
    public Object getItemRowKey(StockPorAlmacenDto item) {
        if (item == null) {
            return false;
        }
        return getComposedId(item);
    }

    @Override
    public boolean hasRowKey(StockPorAlmacenDto item, String rowKey) {
        if ( (item == null) || (getComposedId(item) == null) ) {
            return false;
        }
        return (getComposedId(item).equals(rowKey));
    }
    
    @Override
    public BusquedaFacade<StockPorAlmacenDto> getFacade() {
        return null;
    }      

}