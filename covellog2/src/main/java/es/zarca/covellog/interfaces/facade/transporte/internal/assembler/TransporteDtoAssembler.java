package es.zarca.covellog.interfaces.facade.transporte.internal.assembler;

import es.zarca.covellog.domain.model.transporte.Transporte;
import es.zarca.covellog.infrastructure.util.DateUtil.DateUtil;
import es.zarca.covellog.interfaces.facade.empresas.facade.internal.assembler.DobleObservacionAssembler;
import es.zarca.covellog.interfaces.facade.empresas.facade.internal.assembler.EmpresaMiniDtoAssembler;
import es.zarca.covellog.interfaces.facade.transporte.dto.TransporteDto;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author francisco
 */
public class TransporteDtoAssembler {
    
    static public List<TransporteDto> toDto(List<Transporte> lista) {
        List<TransporteDto> listaDto = new ArrayList<>();
        if (lista != null) {
            lista.forEach(model -> {
                listaDto.add(toDto(model));
            });
        }
        return listaDto;
    }
    
    static public TransporteDto toDto(Transporte transporte) {
        if (transporte == null) {
            return null;
        }
        TransporteDto dto = new TransporteDto();
        dto.setId(transporte.getId());
        dto.setEmpresa(EmpresaMiniDtoAssembler.toDto(transporte.getProveedor()));
        dto.setNombre(transporte.getNombre());
        dto.setCapacidad(transporte.getCapacidad());
        dto.setObservaciones(DobleObservacionAssembler.toDto(transporte.getObservaciones()));
        dto.setFechaBaja(DateUtil.dateTimeToString(transporte.getFechaBaja()));
        return dto;
    }
    
}