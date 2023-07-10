package es.zarca.covellog.application.empresas;

import es.zarca.covellog.domain.model.adreces.adreça.Direccion;
import es.zarca.covellog.domain.model.adreces.adreça.DireccionPostal;
import es.zarca.covellog.domain.model.empresa.CanalesContacto;
import es.zarca.covellog.domain.model.empresa.Cif;
import es.zarca.covellog.domain.model.empresa.cliente.CodigoCliente;
import es.zarca.covellog.domain.model.empresa.proveedor.CodigoProveedor;
import es.zarca.covellog.domain.model.empresa.DobleObservacion;
import es.zarca.covellog.domain.model.empresa.Empresa;
import es.zarca.covellog.domain.model.empresa.PalabrasClave;
import es.zarca.covellog.domain.model.empresa.cliente.DetalleContratacion;
import es.zarca.covellog.domain.model.empresa.cliente.DetalleFacturacion;
import es.zarca.covellog.domain.model.empresa.formapago.FormaPago;
import es.zarca.covellog.domain.model.empresa.formapago.TipoPago;
import es.zarca.covellog.domain.model.empresa.formapago.TipoVencimiento;
import es.zarca.covellog.domain.model.idiomas.idioma.CodigoIdioma;
import es.zarca.covellog.domain.model.idiomas.idioma.Idioma;
import es.zarca.covellog.interfaces.facade.empresas.facade.dto.EmpresaDto;
import java.util.List;

/**
 *
 * @author francisco
 */
public interface EmpresaService {
    Empresa altaEmpresa(
        Cif cif,
        String nombre,
        String alias,
        String horario,
        CodigoIdioma codigoIdioma,
        Direccion direccionFiscal,
        String web,
        PalabrasClave palabrasClave
    );
    void modificarEmpresa(EmpresaDto form);
    void eliminarEmpresa(Integer id);
    List<TipoPago> getTiposPagosPosibles();
    List<TipoVencimiento> getVencimientosPosibles();
    List<Idioma> getIdiomasPosibles();
    Empresa modificarInfoEmpresa(Integer empresaId, Cif cif, String nombre, String alias, String horario, CodigoIdioma codigoIdioma, Direccion direccion, String web, PalabrasClave palabrasClave);

    boolean isPotencialCreable(Integer id);
    boolean isClienteCreable(Integer id);
    boolean isProveedorCreable(Integer id);

    void potencialModificar(Integer empresaId, List<Integer> comercialIds, List<Integer> contactoIds, DobleObservacion observaciones);
    void potencialCrear(Integer empresaId, List<Integer> comercialIds, List<Integer> contactoIds, DobleObservacion observaciones);
    
    void crearCliente(
        Integer empresaId, 
        CodigoCliente codigoCliente, 
        String tipoClienteId,
        List<Integer> comercialIds, 
        List<Integer> contactoIds, 
        DobleObservacion dobleObservacion
    );
    
    void clienteModificar(
        Integer empresaId,
        CodigoCliente codigoCliente,
        String tipoClienteId,
        List<Integer> comercialIds,
        List<Integer> contactoIds,
        DobleObservacion dobleObservacion
    );
    
    void clienteEliminar(Integer empresaId);
    
    void clienteConvertirEnPotencial(Integer empresaId);
    
    void modificarInfoClienteDetalleContratacion(Integer empresaId, DetalleContratacion detalleContratacion);
    void modificarInfoClienteDetalleFacturacion(Integer empresaId, DetalleFacturacion detalleFacturacion);
    
    void proveedorCrear(
        Integer empresaId,
        CodigoProveedor codigoProveedor,
        List<Integer> comercialIds,
        List<Integer> contactoIds,
        FormaPago formaPago,
        DireccionPostal direccion,
        DobleObservacion dobleObservacion
    );
    
    void proveedorModificar(
        Integer empresaId,
        CodigoProveedor codigoProveedor,
        List<Integer> comercialIds,
        List<Integer> contactoIds,
        FormaPago formaPago,
        DireccionPostal direccion,
        DobleObservacion dobleObservacion
    );
    
    void anadirContacto(
        Integer empresaId, 
        String nombre,
        String apellidos,
        String descripcion,
        CanalesContacto canalesContacto,
        CodigoIdioma idiomaId,
        String horario,
        String observaciones
    );
    void modificarContacto(
        Integer empresaId, 
        Integer contactoId,
        String nombre,
        String apellidos,
        String descripcion,
        CanalesContacto canalesContacto,
        CodigoIdioma idiomaId,
        String horario,
        String observaciones
    );
    void eliminarContacto(Integer empresaId, Integer contactoId);
    void subirContacto(Integer empresaId, Integer contactoId);
    void bajarContacto(Integer empresaId, Integer contactoId);
    
    void anadirDireccionEnvio(
        Integer empresaId,
        List<Integer> contactoIds,
        String nombre,
        String descripcion,
        String horario,
        Direccion direccion,
        DobleObservacion observaciones
    );
    void modificarDireccionEnvio(
        Integer empresaId,
        Integer direccionEnvioId,
        List<Integer> contactoIds,
        String nombre,
        String descripcion,
        String horario,
        Direccion direccion,
        DobleObservacion observaciones
    );
    void eliminarDireccionEnvio(Integer empresaId, Integer direccionEnvioId);
    void subirDireccionEnvio(Integer empresaId, Integer direccionEnvioId);
    void bajarDireccionEnvio(Integer empresaId, Integer direccionEnvioId);

    void potencialEliminar(Integer empresaId);
    
    void proveedorEliminar(Integer empresaId);

    void potencialBloquear(Integer empresaId);
    void potencialDesbloquear(Integer empresaId);

    void clienteBloquear(Integer empresaId);
    void clienteDesbloquear(Integer empresaId);
    
    void proveedorBloquear(Integer empresaId);
    void proveedorDesbloquear(Integer empresaId);

    

    
    
}


