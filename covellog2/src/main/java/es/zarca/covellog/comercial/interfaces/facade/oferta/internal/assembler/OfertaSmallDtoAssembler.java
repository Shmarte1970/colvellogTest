package es.zarca.covellog.comercial.interfaces.facade.oferta.internal.assembler;

import es.zarca.covellog.comercial.domain.oferta.IOfertaLinea;
import es.zarca.covellog.comercial.domain.oferta.Oferta;
import es.zarca.covellog.infrastructure.util.DateUtil.DateUtil;
import es.zarca.covellog.interfaces.facade.base.internal.assembler.EstadoDtoAssembler;
import es.zarca.covellog.interfaces.facade.empresas.facade.internal.assembler.DobleObservacionAssembler;
import es.zarca.covellog.comercial.interfaces.facade.oferta.dto.OfertaSmallDto;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author francisco
 */
public class OfertaSmallDtoAssembler {
    
    static public List<OfertaSmallDto> toDto(List<Oferta> lista) {
        List<OfertaSmallDto> listaDto = new ArrayList<>();
        if (lista != null) {
            lista.forEach(model -> {
                listaDto.add(toDto(model));
            });
        }
        return listaDto;
    }
    
    static public OfertaSmallDto toDto(Oferta oferta) {
        if (oferta == null) {
            return null;
        }
        OfertaSmallDto dto = new OfertaSmallDto();
        dto.setId(oferta.getId());
        dto.setFriendlyId(oferta.getFriendlyId());
        dto.setFecha(DateUtil.dateTimeToString(oferta.getFecha()));
        dto.setObservaciones(DobleObservacionAssembler.toDto(oferta.getObservaciones()));
     //   dto.setClienteId(oferta.getCliente().getId());
       // dto.setClienteNombre(oferta.getCliente().getAliasNombre());
        //dto.setClienteCif(oferta.getCliente().getCif().getCifString());
        dto.setEstado(EstadoDtoAssembler.toDto(oferta.getEstado()));
        String productos = "";
        for (IOfertaLinea linea : oferta.getLineas()) {
            if (!productos.isEmpty()) {
                productos += ", ";
            }
            if (linea.getNumSerie() == null) {
                productos += "S/A";
            } else {
                productos += linea.getNumSerie();
            }
            productos += "(" + linea.getTipoProducto().getId() + ")";
        }
        dto.setProductos(productos);
        return dto;
    }

}