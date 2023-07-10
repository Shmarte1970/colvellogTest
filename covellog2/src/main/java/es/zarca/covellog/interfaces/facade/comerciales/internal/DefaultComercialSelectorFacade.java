package es.zarca.covellog.interfaces.facade.comerciales.internal;

import es.zarca.covellog.domain.model.comercials.comercial.Comercial;
import es.zarca.covellog.domain.model.comercials.comercial.ComercialRepository;
import es.zarca.covellog.domain.model.empresa.formapago.TipoVencimientoRepository;
import es.zarca.covellog.interfaces.facade.comerciales.ComercialSelectorFacade;
import es.zarca.covellog.interfaces.facade.comerciales.dto.ComercialDto;
import es.zarca.covellog.interfaces.facade.comerciales.internal.assembler.ComercialAssembler;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

/**
 *
 * @author francisco
 */
@ApplicationScoped
public class DefaultComercialSelectorFacade implements ComercialSelectorFacade {
    @Inject
    ComercialRepository repository;
    @Inject
    TipoVencimientoRepository tipoVencimientoRepository;

    @Override
    public List<ComercialDto> getComercialesPosibles() {
        return ComercialAssembler.toDto(repository.findAll());
    }

    @Override
    public ComercialDto getComercial(Integer comercialId) {
        Comercial comercial = repository.find(comercialId);
        return ComercialAssembler.toDto(comercial);
    }
            
}