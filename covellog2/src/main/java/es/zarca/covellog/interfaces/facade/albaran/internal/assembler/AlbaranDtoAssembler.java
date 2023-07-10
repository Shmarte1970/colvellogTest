package es.zarca.covellog.interfaces.facade.albaran.internal.assembler;

import es.zarca.covellog.domain.model.albaran.Albaran;
import es.zarca.covellog.interfaces.facade.albaran.dto.AlbaranDto;
import es.zarca.covellog.interfaces.facade.empresas.facade.internal.assembler.DobleObservacionAssembler;
import es.zarca.covellog.interfaces.facade.empresas.facade.internal.assembler.EmpresaMiniDtoAssembler;
import es.zarca.covellog.interfaces.facade.ubicaciones.internal.assembler.UbicacionAssembler;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author francisco
 */
public class AlbaranDtoAssembler {
    
    static public List<AlbaranDto> toDto(List<? extends Albaran> lista) {
        List<AlbaranDto> listaDto = new ArrayList<>();
        if (lista != null) {
            lista.forEach(model -> {
                listaDto.add(toDto(model));
            });
        }
        return listaDto;
    }
    
    static public AlbaranDto toDto(Albaran albaran) {
        if (albaran == null) {
            return null;
        }
        AlbaranDto dto = new AlbaranDto();
        dto.setId(albaran.getId());
        dto.setCodigoAlbaran(albaran.getFriendlyId());
        dto.setBooking(albaran.getBooking());
        dto.setFecha(albaran.getFecha());
        dto.setTipo(AlbaranTipoDtoAssembler.toDto(albaran.getTipo()));
        dto.setTransportista(EmpresaMiniDtoAssembler.toDto(albaran.getTransportista()));
        dto.setTransporte(InfoTransporteDtoAssembler.toDto(albaran.getInfoTransporte()));
        dto.setOrigen(UbicacionAssembler.toDto(albaran.getOrigen()));
        dto.setDestino(UbicacionAssembler.toDto(albaran.getDestino()));
        dto.setTextoAviso(albaran.getTextoAviso());
        dto.setObservaciones(DobleObservacionAssembler.toDto(albaran.getObservaciones()));
        dto.setCanReabrir(albaran.canReabrir());
        dto.setCanActivar(albaran.canActivar());
        dto.setCanAnular(albaran.canAnular());
        dto.setCanModificar(albaran.canModificar());
        dto.setCanFinalizar(albaran.canFinalizar());
        dto.setCanCrearReserva(albaran.canCrearReserva());
        dto.setEstado(albaran.getEstado().getNombre());
        dto.setEstadoFecha(albaran.getEstadoFecha());
        dto.setClienteId(albaran.getCliente().getId());
        dto.setClienteFriendlyId(albaran.getClienteFriendlyId());
        dto.setClienteCif(albaran.getClienteCif());
        dto.setClienteNombre(albaran.getClienteNombre());
        dto.setContratoId(albaran.getContrato().getId());
        dto.setContratoFriendlyId(albaran.getContratoFriendlyId());
        dto.setContratoCodigoPedidoCliente(albaran.getContratoCodigoPedido());
        dto.setLineas(AlbaranLineaDtoAssembler.toDto(albaran.getLineas()));
        return dto;
    }
    
}