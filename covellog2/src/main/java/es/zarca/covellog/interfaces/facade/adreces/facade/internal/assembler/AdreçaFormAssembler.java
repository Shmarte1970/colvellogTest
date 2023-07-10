package es.zarca.covellog.interfaces.facade.adreces.facade.internal.assembler;

import es.zarca.covellog.domain.model.adreces.adreça.Adreça;
import es.zarca.covellog.interfaces.facade.adreces.facade.form.AdreçaForm;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public class AdreçaFormAssembler {
    
    public AdreçaForm toForm(Adreça adreça) {
        if (adreça == null) {
            Integer num = 10;
            return new AdreçaForm("adreça null", "00000",  num, "poblacio null");
            //return null;
        }
        AdreçaForm form = new AdreçaForm(adreça.getAdreça(), adreça.getCodigoPostal().getString(),  adreça.getPoblacio().getId(), adreça.getPoblacio().getNom());
        return form;
    }
}
