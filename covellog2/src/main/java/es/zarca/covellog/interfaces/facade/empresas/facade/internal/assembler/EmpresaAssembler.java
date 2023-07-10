package es.zarca.covellog.interfaces.facade.empresas.facade.internal.assembler;

import es.zarca.covellog.domain.model.contactos.Contacto;
import es.zarca.covellog.domain.model.empresa.Empresa;
import es.zarca.covellog.interfaces.facade.adreces.facade.internal.assembler.DireccionAssembler;
import es.zarca.covellog.interfaces.facade.contactos.internal.assembler.ContactoAssembler;
import es.zarca.covellog.interfaces.facade.empresas.facade.dto.EmpresaDto;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author francisco
 */
public class EmpresaAssembler {
    
    static public EmpresaDto toDto(Empresa empresa) {
        if (empresa == null) {
            return null;
        }
        EmpresaDto dto = new EmpresaDto();
        dto.setId(empresa.getId());
        dto.setCif(empresa.getCif() == null ? null : empresa.getCif().toString());
        dto.setEsCorrectoCif(true);
        dto.setAliasNombre(empresa.getAliasNombre());
        dto.setNombre(empresa.getNombre());
        dto.setAlias(empresa.getAlias());
        dto.setIdioma(IdiomaAssembler.toDto(empresa.getIdioma()));
        dto.setDireccionFiscal(DireccionAssembler.toDto(empresa.getDireccionFiscal()));
        dto.setHorario(empresa.getHorario());
        dto.setWeb(empresa.getWeb());
        dto.setPalabrasClave(empresa.getPalabrasClave().getString());
        dto.setPosibleCrearRolPotencial(empresa.isPosibleCrearRolPotencial());
        dto.setPosibleConvertirPotencialEnCliente(empresa.isPosibleConvertirPotencialEnCliente());
        dto.setPosibleCrearRolCliente(empresa.isPosibleCrearRolCliente());
        dto.setPosibleConvertirClienteEnComercial(empresa.isPosibleConvertirClienteEnComercial());
        dto.setPosibleCrearRolProveedor(empresa.isPosibleCrearRolProveedor());
        dto.setContactos(ContactoAssembler.toDto(empresa.getContactos()));
        dto.setDireccionesEnvio(DireccionEnvioAssembler.toDto(empresa.getDireccionesEnvio()));
        dto.setPotencial(PotencialAssembler.toDto(empresa.getPotencial()));
        dto.setProveedor(ProveedorAssembler.toDto(empresa.getProveedor()));
        dto.setCliente(ClienteAssembler.toDto(empresa.getCliente()));
        return dto;
    }
    
    static public List<EmpresaDto> toDto(List<Empresa> empresas) {
        if (empresas == null) {
            return null;
        }
        List<EmpresaDto> dtos = new ArrayList<>();
        empresas.forEach((empresa) -> {
            dtos.add(toDto(empresa));
        });
        return dtos;
    }

}
