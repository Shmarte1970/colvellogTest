package es.zarca.covellog.interfaces.facade.empresas.facade.dto;

import es.zarca.covellog.interfaces.facade.adreces.facade.dto.DireccionDto;
import es.zarca.covellog.interfaces.facade.contactos.dto.ContactoDto;
import java.util.List;
import javax.validation.constraints.Size;

/**
 *
 * @author francisco
 */
public class EmpresaDto {
    private Integer id;
    private String cif;
    private boolean esCorrectoCif;
    private String aliasNombre;
    @Size(min = 3, max = 200)
    private String nombre;
    @Size(max = 100)
    private String alias;
    private IdiomaDto idioma;
    private DireccionDto direccionFiscal;
    @Size(max = 200)
    private String horario;
    @Size(max = 200)
    private String web;
    private String palabrasClave;
    private Boolean posibleCrearRolPotencial;
    private Boolean posibleConvertirPotencialEnCliente;
    private Boolean posibleCrearRolCliente;
    private Boolean posibleConvertirClienteEnComercial;
    private Boolean posibleCrearRolProveedor;
    
    private List<ContactoDto> contactos;
    private List<DireccionEnvioDto> direccionesEnvio;
    private PotencialDto potencial;
    private ClienteDto cliente;
    private ProveedorDto proveedor;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCif() {
        return cif;
    }

    public void setCif(String cif) {
        this.cif = cif;
    }

    public boolean isEsCorrectoCif() {
        return esCorrectoCif;
    }

    public void setEsCorrectoCif(boolean esCorrectoCif) {
        this.esCorrectoCif = esCorrectoCif;
    }

    public String getAliasNombre() {
        return aliasNombre;
    }

    public void setAliasNombre(String aliasNombre) {
        this.aliasNombre = aliasNombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public IdiomaDto getIdioma() {
        return idioma;
    }

    public void setIdioma(IdiomaDto idioma) {
        this.idioma = idioma;
    }

    public DireccionDto getDireccionFiscal() {
        return direccionFiscal;
    }

    public void setDireccionFiscal(DireccionDto direccionFiscal) {
        this.direccionFiscal = direccionFiscal;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public String getPalabrasClave() {
        return palabrasClave;
    }

    public void setPalabrasClave(String palabrasClave) {
        this.palabrasClave = palabrasClave;
    }

    public Boolean getPosibleCrearRolPotencial() {
        return posibleCrearRolPotencial;
    }

    public void setPosibleCrearRolPotencial(Boolean posibleCrearRolPotencial) {
        this.posibleCrearRolPotencial = posibleCrearRolPotencial;
    }

    public Boolean getPosibleConvertirPotencialEnCliente() {
        return posibleConvertirPotencialEnCliente;
    }

    public void setPosibleConvertirPotencialEnCliente(Boolean posibleConvertirPotencialEnCliente) {
        this.posibleConvertirPotencialEnCliente = posibleConvertirPotencialEnCliente;
    }

    public Boolean getPosibleCrearRolCliente() {
        return posibleCrearRolCliente;
    }

    public void setPosibleCrearRolCliente(Boolean posibleCrearRolCliente) {
        this.posibleCrearRolCliente = posibleCrearRolCliente;
    }

    public Boolean getPosibleConvertirClienteEnComercial() {
        return posibleConvertirClienteEnComercial;
    }

    public void setPosibleConvertirClienteEnComercial(Boolean posibleConvertirClienteEnComercial) {
        this.posibleConvertirClienteEnComercial = posibleConvertirClienteEnComercial;
    }

    public Boolean getPosibleCrearRolProveedor() {
        return posibleCrearRolProveedor;
    }

    public void setPosibleCrearRolProveedor(Boolean posibleCrearRolProveedor) {
        this.posibleCrearRolProveedor = posibleCrearRolProveedor;
    }

    public List<ContactoDto> getContactos() {
        return contactos;
    }

    public void setContactos(List<ContactoDto> contactos) {
        this.contactos = contactos;
    }

    public List<DireccionEnvioDto> getDireccionesEnvio() {
        return direccionesEnvio;
    }

    public void setDireccionesEnvio(List<DireccionEnvioDto> direccionesEnvio) {
        this.direccionesEnvio = direccionesEnvio;
    }

    public PotencialDto getPotencial() {
        return potencial;
    }

    public void setPotencial(PotencialDto potencial) {
        this.potencial = potencial;
    }

    public ClienteDto getCliente() {
        return cliente;
    }

    public void setCliente(ClienteDto cliente) {
        this.cliente = cliente;
    }

    public ProveedorDto getProveedor() {
        return proveedor;
    }

    public void setProveedor(ProveedorDto proveedor) {
        this.proveedor = proveedor;
    }
    
}