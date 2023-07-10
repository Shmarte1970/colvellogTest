package es.zarca.covellog.interfaces.web.productos.complementos;

import es.zarca.covellog.interfaces.facade.stock.TiposProductosServiceFacade;
import es.zarca.covellog.interfaces.facade.stock.dto.TipoProductoDto;
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
@Named("selectorComplementoBean")
@ViewScoped
public class SelectorComplementoBean extends BusquedaTransversalSelector<TipoProductoDto> implements Serializable {

    @Inject
    private TiposProductosServiceFacade facade;
    
    @Override
    public Object getItemRowKey(TipoProductoDto item) {
        if (item == null) {
            return false;
        }
        return item.getId();
    }

    @Override
    public boolean hasRowKey(TipoProductoDto item, String rowKey) {
        if ( (item == null) || (item.getId() == null) ) {
            return false;
        }
        return (item.getId().equals(rowKey));
    }
    
    @Override
    public BusquedaFacade<TipoProductoDto> getFacade() {
        return facade;
    }      

}