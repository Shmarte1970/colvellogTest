package es.zarca.covellog.interfaces.facade.empresas.facade.internal.assembler;

import es.zarca.covellog.domain.model.empresa.cliente.Cliente;
import es.zarca.covellog.infrastructure.util.DateUtil.DateUtil;
import es.zarca.covellog.interfaces.facade.comerciales.internal.assembler.ComercialAssembler;
import es.zarca.covellog.interfaces.facade.contactos.internal.assembler.ContactoAssembler;
import es.zarca.covellog.interfaces.facade.empresas.facade.dto.ClienteDto;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author francisco
 */
public class ClienteAssembler {
    
    static public ClienteDto toDto(Cliente cliente) {
        if (cliente == null) {
            return null;
        }
        ClienteDto dto = new ClienteDto();
        dto.setCodigoCliente(cliente.getCodigoCliente().getCodiString());
        dto.setTipoCliente(TipoClienteAssembler.toDto(cliente.getTipoCliente()));
        dto.setComerciales(ComercialAssembler.toDto(cliente.getComerciales()));
        dto.setContactos(ContactoAssembler.toDto(cliente.getContactos()));
        dto.setObservaciones(DobleObservacionAssembler.toDto(cliente.getObservaciones()));
        dto.setFechaBloqueo(DateUtil.dateTimeToString(cliente.getFechaBloqueo()));
        dto.setDetalleFacturacion(DetalleFacturacionAssembler.toDto(cliente.getDetalleFacturacion()));
        dto.setDetalleContratacion(DetalleContratacionAssembler.toDto(cliente.getDetalleContratacion()));
        return dto;
    }
    
    static public List<ClienteDto> toDto(List<Cliente> clientes) {
        if (clientes == null) {
            return null;
        }
        List<ClienteDto> dtos = new ArrayList<>();
        clientes.forEach((cliente) -> {
            dtos.add(toDto(cliente));
        });
        return dtos;
    }
    
}
