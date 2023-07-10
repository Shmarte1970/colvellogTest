package es.zarca.covellog.comercial.interfaces.facade.oferta.internal.assembler;

import es.zarca.covellog.domain.model.contrato.Contrato;
import es.zarca.covellog.infrastructure.util.DateUtil.DateUtil;
import es.zarca.covellog.interfaces.facade.adreces.facade.internal.assembler.DireccionAssembler;
import es.zarca.covellog.interfaces.facade.comerciales.internal.assembler.ComercialAssembler;
import es.zarca.covellog.interfaces.facade.empresas.facade.internal.assembler.DobleObservacionAssembler;
import es.zarca.covellog.interfaces.facade.contrato.dto.ContratoDto;
import es.zarca.covellog.interfaces.facade.contactos.internal.assembler.ContactoAssembler;
import es.zarca.covellog.interfaces.facade.contrato.internal.assembler.ContratoLineaDtoAssembler;
import es.zarca.covellog.interfaces.facade.contrato.internal.assembler.ContratoResumenDtoAssembler;
import es.zarca.covellog.interfaces.facade.empresas.facade.internal.assembler.DetalleContratacionAssembler;
import es.zarca.covellog.interfaces.facade.empresas.facade.internal.assembler.DetalleFacturacionAssembler;
import es.zarca.covellog.interfaces.facade.empresas.facade.internal.assembler.EmpresaMiniDtoAssembler;
import es.zarca.covellog.interfaces.facade.helpers.assemblers.TimestampEntityDtoAssembler;
import es.zarca.covellog.interfaces.facade.stock.internal.assembler.DocumentoDtoAssembler;
import es.zarca.covellog.interfaces.facade.ubicaciones.internal.assembler.UbicacionAssembler;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author francisco
 */
public class OfertaDtoAssembler {
    
    static public List<ContratoDto> toDto(List<Contrato> lista) {
        List<ContratoDto> listaDto = new ArrayList<>();
        if (lista != null) {
            lista.forEach(model -> {
                listaDto.add(toDto(model));
            });
        }
        return listaDto;
    }
    
    static public ContratoDto toDto(Contrato contrato) {
        if (contrato == null) {
            return null;
        }
        ContratoDto dto = new ContratoDto();
        dto.setId(contrato.getId());
        dto.setFriendlyId(contrato.getFriendlyId());
        dto.setCliente(EmpresaMiniDtoAssembler.toDto(contrato.getCliente()));
        dto.setEstado(contrato.getEstado().getNombre());
        dto.setEstadoFecha(contrato.getEstadoFecha());
        dto.setEstadoPago(contrato.getPagoEstado().getNombre());
        dto.setEstadoPagoFecha(contrato.getPagoEstadoFecha());
        dto.setFechaContrato(contrato.getFechaContrato());
        if (contrato.getDireccionEnvio() != null) {
            dto.setDireccionCorrespondencia(
                DireccionAssembler.toDto(contrato.getDireccionEnvio().getDireccion())
            );
        }
        dto.setDireccionEnvio(
            UbicacionAssembler.toDto(contrato.getDireccionEnvio())
        );
        dto.setCodigoPedidoCliente(contrato.getCodigoPedidoCliente());
        dto.setCodigoProveedor(contrato.getCodigoProveedor());
        dto.setPrevisionMesesAlquiler(contrato.getPrevisionMesesAlquiler());
        dto.setObservaciones(DobleObservacionAssembler.toDto(contrato.getObservaciones()));
        dto.setCondiciones(DetalleContratacionAssembler.toDto(contrato.getCondiciones()));
        dto.setFacturacion(DetalleFacturacionAssembler.toDto(contrato.getDetalleFacturacion()));
        dto.setComerciales(ComercialAssembler.toDto(contrato.getComerciales()));
        dto.setContactos(ContactoAssembler.toDto(contrato.getContactos()));
        dto.setFirmante(ContactoAssembler.toDto(contrato.getFirmante()));
        dto.setDocumentos(DocumentoDtoAssembler.toDto(contrato.getDocumentos()));
        dto.setLineas(ContratoLineaDtoAssembler.toDto(contrato.getLineas()));
        dto.setResumen(ContratoResumenDtoAssembler.toDto(contrato));
        /*TimestampEntityDto timestampEntityDto = new TimestampEntityDto();
        timestampEntityDto.setCreatedDate(createdDate);
        timestampEntityDto.setUpdatedDate(updatedDate);*/
        dto.setAuditoria(TimestampEntityDtoAssembler.toDto(contrato));
        return dto;
    }
    
}