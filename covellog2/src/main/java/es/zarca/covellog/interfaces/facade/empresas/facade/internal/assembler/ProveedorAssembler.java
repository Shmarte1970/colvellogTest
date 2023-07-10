    package es.zarca.covellog.interfaces.facade.empresas.facade.internal.assembler;

import es.zarca.covellog.domain.model.empresa.proveedor.Proveedor;
import es.zarca.covellog.infrastructure.util.DateUtil.DateUtil;
import es.zarca.covellog.interfaces.facade.adreces.facade.internal.assembler.DireccionPostalAssembler;
import es.zarca.covellog.interfaces.facade.comerciales.internal.assembler.ComercialAssembler;
import es.zarca.covellog.interfaces.facade.contactos.internal.assembler.ContactoAssembler;
import es.zarca.covellog.interfaces.facade.empresas.facade.dto.ProveedorDto;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author francisco
 */
public class ProveedorAssembler {
    
    static public ProveedorDto toDto(Proveedor proveedor) {
        if (proveedor == null) {
            return null;
        }   
        ProveedorDto dto = new ProveedorDto();
        dto.setCodigoProveedor(proveedor.getCodigoProveedor() == null ? "" : proveedor.getCodigoProveedor().getCodiString());
        dto.setComerciales(ComercialAssembler.toDto(proveedor.getComerciales()));
        dto.setContactos(ContactoAssembler.toDto(proveedor.getContactos()));
        dto.setDireccionPostal(DireccionPostalAssembler.toDto(proveedor.getDireccionPostal()));
        dto.setObservaciones(DobleObservacionAssembler.toDto(proveedor.getObservaciones()));
        dto.setFormaPago(FormaPagoAssembler.toDto(proveedor.getFormaPago()));
        dto.setFechaBloqueo(DateUtil.dateTimeToString(proveedor.getFechaBloqueo()));
        return dto;
    }
    
    static public List<ProveedorDto> toDto(List<Proveedor> proveedors) {
        if (proveedors == null) {
            return null;
        }
        List<ProveedorDto> dtos = new ArrayList<>();
        proveedors.forEach((proveedor) -> {
            dtos.add(toDto(proveedor));
        });
        return dtos;
    }
    
}
