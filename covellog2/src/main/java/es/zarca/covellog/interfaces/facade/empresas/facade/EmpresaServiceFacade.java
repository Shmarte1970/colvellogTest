package es.zarca.covellog.interfaces.facade.empresas.facade;

import es.zarca.covellog.application.adreces.form.DireccionPostalForm;
import es.zarca.covellog.domain.model.adreces.poblacio.Poblacio;
import es.zarca.covellog.domain.model.app.Ordre;
import es.zarca.covellog.domain.model.comercials.comercial.Comercial;
import es.zarca.covellog.domain.model.empresa.Empresa;
import es.zarca.covellog.domain.model.empresa.formapago.TipoPago;
import es.zarca.covellog.domain.model.empresa.formapago.TipoVencimiento;
import es.zarca.covellog.domain.model.idiomas.idioma.Idioma;
import es.zarca.covellog.interfaces.facade.adreces.facade.dto.DireccionDto;
import es.zarca.covellog.interfaces.facade.contactos.dto.ContactoDto;
import es.zarca.covellog.interfaces.facade.empresas.facade.dto.DetalleContratacionDto;
import es.zarca.covellog.interfaces.facade.empresas.facade.dto.DetalleFacturacionDto;
import es.zarca.covellog.interfaces.facade.empresas.facade.dto.DireccionEnvioDto;
import es.zarca.covellog.interfaces.facade.empresas.facade.dto.DobleObservacionDto;
import es.zarca.covellog.interfaces.facade.empresas.facade.dto.EmpresaDto;
import es.zarca.covellog.interfaces.facade.empresas.facade.dto.FacturarPorDto;
import es.zarca.covellog.interfaces.facade.empresas.facade.dto.FormaPagoDto;
import es.zarca.covellog.interfaces.facade.empresas.facade.dto.TipoClienteDto;
import es.zarca.covellog.interfaces.facade.empresas.facade.dto.TipoFacturacionDto;
import es.zarca.covellog.interfaces.facade.ubicaciones.dto.UbicacionDto;
import es.zarca.covellog.interfaces.web.app.model.Filtro;
import java.util.List;
import java.util.Map;


/**
 *
 * @author francisco
 */
public interface EmpresaServiceFacade {
    EmpresaDto altaEmpresa(
        String cif,
        String nombre,
        String alias,
        String horario,
        String idiomaId,
        DireccionDto direccionFiscal,
        String web,
        String palabrasClave
    );
    void modificarEmpresa(EmpresaDto form);
    void eliminarEmpresa(Integer id);
    
    List<Empresa> listarEmpresas();
    List<Empresa> listarEmpresas(int start, int size);    
    int listarEmpresasTotalCount();
    List<Empresa> listarEmpresas(Ordre ordre, Map<String, Object> filters);
    List<Empresa> listarEmpresas(int start, int size, Ordre ordre, Map<String, Object> filters);
    int listarEmpresasTotalCount(Map<String, Object> filters);
    Map<String, List<Empresa>> listarEmpresas(int start, int size, Ordre ordre, Map<String, Object> filters, String filter);

    Empresa buscarPorId(Integer id);
    EmpresaDto buscarPorIdDto(Integer id);
    
    List<TipoPago> getTiposPagosPosibles();
    List<TipoVencimiento> getVencimientosPosibles();
    List<Idioma> getIdiomasPosibles();
    Idioma getIdiomaPosible(String codigoIdioma);
    List<Comercial> getComercialesPosibles();
    List<Poblacio> getPoblacionesPosibles();

    Poblacio buscarPoblacionPorId(Integer id);

    DireccionDto findDireccionFiscalEmpresa(Integer empresaId);

    EmpresaDto modificarInfoEmpresa(
        Integer empresaId,
        String cif,
        String nombre,
        String alias,
        String horario,
        String codigoIdioma,
        DireccionDto direccion,
        String web,
        String palabrasClave
    );
    void modificarPotencial(
        Integer empresaId,
        List<Integer> comercialIds, 
        List<Integer> contactoIds, 
        DobleObservacionDto observaciones
    );
    void crearPotencial(
        Integer empresaId,
        List<Integer> comercialIds, 
        List<Integer> contactoIds,
        DobleObservacionDto observaciones
    );
    
    List<TipoClienteDto> getTiposClientePosibles();
    
    void crearCliente(
        Integer empresaId,
        String codigoCliente,
        String tipoClienteId,
        List<Integer> comercialIds, 
        List<Integer> contactoIds,
        DobleObservacionDto observaciones
    );
    
    void modificarCliente(
        Integer empresaId,
        String codigoCliente,
        String tipoClienteId,
        List<Integer> comercialIds, 
        List<Integer> contactoIds,
        DobleObservacionDto observaciones
    );
    
    void eliminarCliente(Integer empresaId);
    
    void convertirClienteEnPotencial(Integer id);
    
    
    void modificarInfoClienteContratacion(
        Integer empresaId,
        DetalleContratacionDto detalleContratacion
    );
    
    List<TipoFacturacionDto> geTiposFacturacionPosibles();
    List<FacturarPorDto> getFacturarPorPosibles();
    
    void modificarInfoClienteFacturacion(Integer empresaId, DetalleFacturacionDto detalleFacturacion);
    
    void proveedorCrear(
        Integer empresaId,
        String codigoProveedor,
        List<Integer> comercialIds, 
        List<Integer> contactoIds,
        FormaPagoDto formaPagoDto,
        DireccionPostalForm direccion, 
        DobleObservacionDto observaciones
    );
    
    void modificarProveedor(
        Integer empresaId,
        String codigoProveedor,
        List<Integer> comercialIds, 
        List<Integer> contactoIds,
        FormaPagoDto formaPagoDto,
        DireccionPostalForm direccion, 
        DobleObservacionDto observaciones
    );
    
    void eliminarPotencial(Integer empresaId);
    
    void eliminarProveedor(Integer empresaId);
           
    boolean isPotencialCreable(Integer id);
    boolean isClienteCreable(Integer id);
    boolean isProveedorCreable(Integer id);

    List<ContactoDto> findContactos(Integer empresaId);
    void anadirContacto(Integer empresaId, ContactoDto contacto);
    void modificarContacto(Integer empresaId, ContactoDto contacto);
    void eliminarContacto(Integer empresaId, Integer contactoId);
    void subirContacto(Integer empresaId, Integer contactoId);
    void bajarContacto(Integer empresaId, Integer contactoId);
    
    
    DireccionEnvioDto findDireccionEnvio(Integer empresaId, Integer direccionEnvioId);
    void anadirDireccionEnvio(Integer empresaId, DireccionEnvioDto direccionEnvio);
    void modificarDireccionEnvio(Integer empresaId, DireccionEnvioDto direccionEnvio);
    void eliminarDireccionEnvio(Integer empresaId, Integer direccionEnvioId);

    void potencialBloquear(Integer empresaId);
    void potencialDesbloquear(Integer empresaId);

    public void clienteBloquear(Integer empresaId);
    public void clienteDesbloquear(Integer empresaId);
    
    public void proveedorBloquear(Integer empresaId);
    public void proveedorDesbloquear(Integer empresaId);

    public String sugerirCodigoCliente();
    public String sugerirCodigoProveedor();

    public Map<String, List<Empresa>> buscarEnDiagonal(String filtro);

    public List<Filtro> getFiltrosPosibles();

    public boolean esCorrectoCif(String cif);
    
    public List<DireccionEnvioDto> findDireccionesEnvio(Integer empresaId);

}
