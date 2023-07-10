package es.zarca.covellog.interfaces.facade.empresas.facade.internal.assembler;

import es.zarca.covellog.domain.model.adreces.poblacio.PoblacioRepository;
import es.zarca.covellog.domain.model.contactos.Contacto;
import es.zarca.covellog.domain.model.empresa.Empresa;
import es.zarca.covellog.domain.model.empresa.cliente.DetalleFacturacion;
import es.zarca.covellog.interfaces.facade.adreces.facade.internal.assembler.DireccionPostalAssembler;
import es.zarca.covellog.interfaces.facade.contactos.dto.ContactoDto;
import es.zarca.covellog.interfaces.facade.contactos.internal.assembler.ContactoAssembler;
import es.zarca.covellog.interfaces.facade.empresas.facade.dto.DetalleFacturacionDto;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author francisco
 */
public class DetalleFacturacionAssembler {
    
    static public DetalleFacturacionDto toDto(DetalleFacturacion detalleFacturacion) {
        if (detalleFacturacion == null) {
            return null;
        }
        DetalleFacturacionDto dto = new DetalleFacturacionDto();
        dto.setExentoIva(detalleFacturacion.isExentoIva());
        //dto.setContacto(ContactoAssembler.toDto(detalleFacturacion.getContacto()));
        dto.setContactos(ContactoAssembler.toDto(detalleFacturacion.getContactos()));
        dto.setFormaPagoVenta(FormaPagoAssembler.toDto(detalleFacturacion.getFormaPagoVenta()));
        dto.setFormaPagoAlquiler(FormaPagoAssembler.toDto(detalleFacturacion.getFormaPagoAlquiler()));
        dto.setDireccionPostal(DireccionPostalAssembler.toDto(detalleFacturacion.getDireccionPostal()));
        dto.setMalPagador(detalleFacturacion.isMalPagador());
        dto.setObservaciones(DobleObservacionAssembler.toDto(detalleFacturacion.getObservaciones()));
        return dto;
    }
    
    static public List<DetalleFacturacionDto> toDto(List<DetalleFacturacion> detalleFacturacions) {
        if (detalleFacturacions == null) {
            return null;
        }
        List<DetalleFacturacionDto> dtos = new ArrayList<>();
        detalleFacturacions.forEach((detalleFacturacion) -> {
            dtos.add(toDto(detalleFacturacion));
        });
        return dtos;
    }
    
    static public DetalleFacturacion toModel(DetalleFacturacionDto detalleFacturacion, Empresa empresa, PoblacioRepository poblacionRepository) {
        if (detalleFacturacion == null) {
            return null;
        }
        //Contacto contacto = empresa.getContacto(detalleFacturacion.getContacto().getId());
        List<Contacto> contactos = new ArrayList<>();
        for (ContactoDto contactoDto : detalleFacturacion.getContactos()) {
            contactos.add(empresa.getContacto(contactoDto.getId()));
        }
        DetalleFacturacion model = new DetalleFacturacion(
            detalleFacturacion.getExentoIva(),
           // contacto,
            contactos,
            FormaPagoAssembler.toModel(detalleFacturacion.getFormaPagoVenta()),
            FormaPagoAssembler.toModel(detalleFacturacion.getFormaPagoAlquiler()),
            DireccionPostalAssembler.toModel(detalleFacturacion.getDireccionPostal(), poblacionRepository),
            detalleFacturacion.getMalPagador(),
            DobleObservacionAssembler.toModel(detalleFacturacion.getObservaciones())
        );
        return model;
    }
    
}