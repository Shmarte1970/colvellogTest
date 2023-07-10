package es.zarca.covellog.interfaces.web.transportes;

import java.io.Serializable;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.inject.Inject;
import es.zarca.covellog.interfaces.facade.transporte.TransportesFacade;
import es.zarca.covellog.interfaces.facade.transporte.dto.TransporteDto;
import es.zarca.covellog.interfaces.web.app.controller.BusquedaFacade;
import es.zarca.covellog.interfaces.web.app.controller.BusquedaSelector;
import javax.enterprise.context.SessionScoped;

@Named("transporteSelectorBean")
@SessionScoped
public class TransporteSelectorBean extends BusquedaSelector<TransporteDto> implements Serializable {
    private static final Logger LOGGER = Logger.getLogger(TransporteSelectorBean.class.getName());
    
    @Inject
    private TransportesFacade facade;
    
    @Override
    public Object getItemRowKey(TransporteDto item) {
        if (item == null) {
            return false;
        }
        return item.getId();
    }

    @Override
    public boolean hasRowKey(TransporteDto item, String rowKey) {
        if ( (item == null) || (item.getId() == null) ) {
            return false;
        }
        
        return (item.getId().toString().equals(rowKey));
    }
    
    @Override
    public BusquedaFacade<TransporteDto> getFacade() {
        return facade;
    }
 
}
