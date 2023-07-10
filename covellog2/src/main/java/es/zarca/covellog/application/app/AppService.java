package es.zarca.covellog.application.app;

import es.zarca.covellog.application.crm.event.NovaOportunitatEvent;
import es.zarca.covellog.domain.model.app.App;
import java.util.Map;


/**
 *
 * @author Francisco Torralbo
 */
public interface AppService {
    void setPropietat(App app, String id, String valor);
    String getPropietat(App app, String id);
    Map<String,String> getPropietats(App app);

    public void onNovaOportunitat(NovaOportunitatEvent event);
}
