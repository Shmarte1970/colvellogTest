package es.zarca.covellog.interfaces.facade.empresas.facade.dto;

import es.zarca.covellog.interfaces.facade.adreces.facade.dto.DireccionPostalDto;
import es.zarca.covellog.interfaces.facade.comerciales.dto.ComercialDto;
import es.zarca.covellog.interfaces.facade.contactos.dto.ContactoDto;
import java.util.List;

/**
 *
 * @author francisco
 */
public class ClienteDto extends PotencialDto {
    
    private String codigoCliente;
    private TipoClienteDto tipoCliente;
    private DetalleContratacionDto detalleContratacion;
    private DetalleFacturacionDto detalleFacturacion;

    public ClienteDto() {
        detalleContratacion = new DetalleContratacionDto();
        detalleFacturacion = new DetalleFacturacionDto();
    }

    public ClienteDto(
        List<ComercialDto> comerciales, 
        List<ContactoDto> contactos, 
        DobleObservacionDto observaciones, 
        String codigoCliente, 
        TipoClienteDto tipoCliente,
        DireccionPostalDto direccionPostal, 
        DetalleFacturacionDto detalleFacturacion
    ) {
        super(comerciales, contactos, observaciones);
        this.codigoCliente = codigoCliente;
        this.tipoCliente = tipoCliente;
        this.detalleFacturacion = detalleFacturacion;
    }

    public String getCodigoCliente() {
        return codigoCliente;
    }

    public void setCodigoCliente(String codigoCliente) {
        this.codigoCliente = codigoCliente;
    }

    public TipoClienteDto getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(TipoClienteDto tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    public DetalleContratacionDto getDetalleContratacion() {
        return detalleContratacion;
    }

    public void setDetalleContratacion(DetalleContratacionDto detalleContratacion) {
        this.detalleContratacion = detalleContratacion;
    }
    
    public DetalleFacturacionDto getDetalleFacturacion() {
        return detalleFacturacion;
    }

    public void setDetalleFacturacion(DetalleFacturacionDto detalleFacturacion) {
        this.detalleFacturacion = detalleFacturacion;
    }

}