package es.zarca.covellog.application.empresas.internal;

import es.zarca.covellog.application.comercials.exception.ComercialNotExistException;
import es.zarca.covellog.application.empresas.EmpresaService;
import es.zarca.covellog.application.empresas.exception.EmpresaMismoNombreExistException;
import es.zarca.covellog.application.empresas.exception.EmpresaNoTieneClienteException;
import es.zarca.covellog.application.empresas.exception.EmpresaNotExistException;
import es.zarca.covellog.application.empresas.exception.TipoClienteNotExistException;
import es.zarca.covellog.application.exception.BusinessException;
import es.zarca.covellog.application.exception.UndefinedBussinesException;
import es.zarca.covellog.application.idiomas.exception.IdiomaNotExistException;
import es.zarca.covellog.domain.model.adreces.adreça.Direccion;
import es.zarca.covellog.domain.model.adreces.adreça.DireccionPostal;
import es.zarca.covellog.domain.model.comercials.comercial.Comercial;
import es.zarca.covellog.domain.model.comercials.comercial.ComercialRepository;
import es.zarca.covellog.domain.model.contactos.Contacto;
import es.zarca.covellog.domain.model.empresa.CanalesContacto;
import es.zarca.covellog.domain.model.empresa.Cif;
import es.zarca.covellog.domain.model.empresa.cliente.Cliente;
import es.zarca.covellog.domain.model.empresa.cliente.CodigoCliente;
import es.zarca.covellog.domain.model.empresa.EmpresaDireccionEnvio;
import es.zarca.covellog.domain.model.empresa.proveedor.CodigoProveedor;
import es.zarca.covellog.domain.model.empresa.DobleObservacion;
import es.zarca.covellog.domain.model.empresa.Empresa;
import es.zarca.covellog.domain.model.empresa.EmpresaRepository;
import es.zarca.covellog.domain.model.empresa.PalabrasClave;
import es.zarca.covellog.domain.model.empresa.Potencial;
import es.zarca.covellog.domain.model.empresa.cliente.DetalleContratacion;
import es.zarca.covellog.domain.model.empresa.cliente.DetalleFacturacion;
import es.zarca.covellog.domain.model.empresa.cliente.TipoCliente;
import es.zarca.covellog.domain.model.empresa.cliente.TipoClienteRepository;
import es.zarca.covellog.domain.model.empresa.formapago.FormaPago;
import es.zarca.covellog.domain.model.empresa.formapago.TipoPago;
import es.zarca.covellog.domain.model.empresa.formapago.TipoVencimiento;
import es.zarca.covellog.domain.model.idiomas.idioma.CodigoIdioma;
import es.zarca.covellog.domain.model.idiomas.idioma.Idioma;
import es.zarca.covellog.domain.model.idiomas.idioma.IdiomaRepository;
import es.zarca.covellog.infrastructure.assertions.ArgumentValidator;
import es.zarca.covellog.infrastructure.log.Capa;
import es.zarca.covellog.infrastructure.log.FunctionLog;
import es.zarca.covellog.interfaces.facade.empresas.facade.dto.EmpresaDto;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;


/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
@Stateless
public class DefaultEmpresaService implements EmpresaService {

    private static final Logger LOGGER = Logger.getLogger(DefaultEmpresaService.class.getName());
    
    @Inject
    private EmpresaRepository empresaRepository;
    @Inject
    private IdiomaRepository idiomaRepository;
    @Inject
    private ComercialRepository comercialRepository;
    @Inject
    private TipoClienteRepository tipoClienteRepository;
    
    private Empresa getEmpresa(Integer empresaId) {
        Empresa empresa = empresaRepository.find(empresaId);
        if (empresa == null) {
            throw new EmpresaNotExistException(empresaId);
        }
        return empresa;
    } 
    
    private Idioma getIdioma(CodigoIdioma codigoIdioma) {
        Idioma idioma = idiomaRepository.find(codigoIdioma);
        if (idioma == null) {
           throw new IdiomaNotExistException(codigoIdioma);
        }
        return idioma;
    }

    @Override
    public Empresa altaEmpresa(
        Cif cif,
        String nombre,
        String alias,
        String horario,
        CodigoIdioma codigoIdioma,
        Direccion direccionFiscal,
        String web,
        PalabrasClave palabrasClave
    ) {
        Empresa empresa = empresaRepository.findByNombre(nombre);
        if (empresa != null) {
            throw new EmpresaMismoNombreExistException(nombre);
        }
        Idioma idioma = getIdioma(codigoIdioma);
        empresa = new Empresa();
        empresa.setCif(cif);
        empresa.setNombre(nombre);
        empresa.setAlias(alias);
        empresa.setHorario(horario);
        empresa.setIdioma(idioma);
        empresa.setDireccionFiscal(direccionFiscal);
        empresa.setWeb(web);
        empresa.setPalabrasClave(palabrasClave);
        empresa = empresaRepository.store(empresa);
        return empresa;
    }

    @Override
    public void modificarEmpresa(EmpresaDto form) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void eliminarEmpresa(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<TipoPago> getTiposPagosPosibles() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<TipoVencimiento> getVencimientosPosibles() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Idioma> getIdiomasPosibles() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Empresa modificarInfoEmpresa(Integer empresaId, Cif cif, String nombre, String alias, String horario, CodigoIdioma codigoIdioma, Direccion direccion, String web, PalabrasClave palabrasClave) {
        FunctionLog log = new FunctionLog(Capa.APP_SERVICE);
        try {
            if (empresaId == null) {
                throw new UndefinedBussinesException("empresa null");
            }
            if (codigoIdioma == null) {
                throw new UndefinedBussinesException("codigoIdioma null");
            }
            Idioma idioma = idiomaRepository.find(codigoIdioma);
            if (idioma == null) {
                throw new IdiomaNotExistException(codigoIdioma);
            }
            Empresa empresa = empresaRepository.find(empresaId);
            if (empresa == null) {
                throw new EmpresaNotExistException(empresaId);
            }
            Empresa empresaAux = empresaRepository.findByNombre(nombre);
            if ((empresaAux != null) && (!empresa.equals(empresaAux))) {
                throw new EmpresaMismoNombreExistException(nombre);
            }
            empresa.setCif(cif);
            empresa.setNombre(nombre);
            empresa.setAlias(alias);
            empresa.setHorario(horario);
            empresa.setIdioma(idioma);
            empresa.setDireccionFiscal(direccion);
            empresa.setWeb(web);
            empresa.setPalabrasClave(palabrasClave);
            
            return empresa;
        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new UndefinedBussinesException(ex);
        } finally {
            log.finish();
        }
    }
    
    private Potencial getPotencial(Empresa empresa) {
        Potencial potencial = empresa.getPotencial();
        if (potencial == null) {
            //throw new EmpresaNoTienePotencialException(empresa);
            potencial = new Potencial();
        }
        return potencial;
    }
    
    private List<Comercial> cargarComerciales(List<Integer> comercialIds) {
        List<Comercial> comerciales = new ArrayList<>();
        for (Integer comercialId : comercialIds) {
            Comercial comercial = comercialRepository.find(comercialId);
            if (comercial == null) {
                throw new ComercialNotExistException(comercialId);
            }
            comerciales.add(comercial);
        }
        return comerciales;
    }
    
    private List<Contacto> cargarContactos(Empresa empresa, List<Integer> contactoIds) {
        List<Contacto> contactos = new ArrayList<>();
        if ((contactoIds != null) && ( empresa != null)) {
            contactoIds.forEach(contactoId -> {         
                contactos.add(empresa.getContacto(contactoId));
            });
        }
        return contactos;
    }
    
    
    @Override
    public void potencialEliminar(Integer empresaId) {
        FunctionLog log = new FunctionLog(Capa.APP_SERVICE);
        Empresa empresa = getEmpresa(empresaId);
        empresa.potencialEliminar();
        log.finish();
    }

    @Override
    public void proveedorEliminar(Integer empresaId) {
        FunctionLog log = new FunctionLog(Capa.APP_SERVICE);
        Empresa empresa = getEmpresa(empresaId);
        empresa.proveedorEliminar();
        log.finish();
    }
    
    @Override
    public void potencialModificar(Integer empresaId, List<Integer> comercialIds, List<Integer> contactoIds, DobleObservacion observaciones) {
        FunctionLog log = new FunctionLog(Capa.APP_SERVICE);
        try {
            Empresa empresa = getEmpresa(empresaId);
            empresa.potencialModificar(
                cargarComerciales(comercialIds), 
                cargarContactos(empresa, contactoIds), 
                observaciones
            );            
        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new UndefinedBussinesException(ex);
        } finally {
            log.finish();
        }
    }
    
    @Override
    public void potencialCrear(Integer empresaId, List<Integer> comercialIds, List<Integer> contactoIds, DobleObservacion observaciones) {
        FunctionLog log = new FunctionLog(Capa.APP_SERVICE);
        try {
            Empresa empresa = getEmpresa(empresaId);
            
            empresa.potencialCrear(
                cargarComerciales(comercialIds), 
                cargarContactos(empresa, contactoIds), 
                observaciones
            );            
        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new UndefinedBussinesException(ex);
        } finally {
            log.finish();
        }
    }
    
    private Cliente getCliente(Empresa empresa) {
        Cliente cliente = empresa.getCliente();
        if (cliente == null) {
            throw new EmpresaNoTieneClienteException(empresa);
        }
        return cliente;
    }
    
    private TipoCliente getTipoCliente(String tipoClienteId) {
        TipoCliente tipocliente = tipoClienteRepository.find(tipoClienteId);
        if (tipocliente == null) {
            throw new TipoClienteNotExistException(tipoClienteId);
        }
        return tipocliente;
    }
     
    @Override
    public void crearCliente(Integer empresaId, CodigoCliente codigoCliente, String tipoClienteId, List<Integer> comercialIds, List<Integer> contactoIds, DobleObservacion dobleObservacion) {
        FunctionLog log = new FunctionLog(Capa.APP_SERVICE);
        
        try {
            if (codigoCliente == null) {
                //codigoCliente = new CodigoCliente("CL" + String.format("%06d", empresaId));
                ArgumentValidator.required(codigoCliente, "Codigo Cliente");
            }
            Empresa empresa = getEmpresa(empresaId);
            empresa.clienteCrear(
                codigoCliente,
                getTipoCliente(tipoClienteId),
                cargarComerciales(comercialIds),
                cargarContactos(empresa, contactoIds), 
                dobleObservacion
            );
        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new UndefinedBussinesException(ex);
        } finally {
            log.finish();
        }
    }
    
    @Override
    public void clienteModificar(Integer empresaId, CodigoCliente codigoCliente, String tipoClienteId, List<Integer> comercialIds, List<Integer> contactoIds, DobleObservacion dobleObservacion) {
        FunctionLog log = new FunctionLog(Capa.APP_SERVICE);
        try {
            Empresa empresa = getEmpresa(empresaId);
            empresa.clienteModificar(
                codigoCliente,
                getTipoCliente(tipoClienteId),
                cargarComerciales(comercialIds),
                cargarContactos(empresa, contactoIds), 
                dobleObservacion
            );
        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new UndefinedBussinesException(ex);
        } finally {
            log.finish();
        }
    }
    
    @Override
    public void clienteEliminar(Integer empresaId) {
        FunctionLog log = new FunctionLog(Capa.APP_SERVICE);
        try {
            getEmpresa(empresaId).clienteEliminar();
        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new UndefinedBussinesException(ex);
        } finally {
            log.finish();
        }
    }
    
    @Override
    public void clienteConvertirEnPotencial(Integer empresaId) {
        FunctionLog log = new FunctionLog(Capa.APP_SERVICE);
        try {
            getEmpresa(empresaId).clienteConvertirEnPotencial();
        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new UndefinedBussinesException(ex);
        } finally {
            log.finish();
        }
    }
    
    @Override
    public void modificarInfoClienteDetalleContratacion(Integer empresaId, DetalleContratacion detalleContratacion) {
        FunctionLog log = new FunctionLog(Capa.APP_SERVICE);
        try {
            Empresa empresa = getEmpresa(empresaId);
            Cliente cliente = getCliente(empresa);
            cliente.setDetalleContratacion(detalleContratacion);
        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new UndefinedBussinesException(ex);
        } finally {
            log.finish();
        }
    }

    @Override
    public void modificarInfoClienteDetalleFacturacion(Integer empresaId, DetalleFacturacion detalleFacturacion) {
        FunctionLog log = new FunctionLog(Capa.APP_SERVICE);
        try {
            Empresa empresa = getEmpresa(empresaId);
            Cliente cliente = getCliente(empresa);
            List<Contacto> contactosManaged = new ArrayList<>();
            for (Contacto contacto : detalleFacturacion.getContactos()) {
                contactosManaged.add(empresa.getContacto(contacto.getId()));
            }
            detalleFacturacion = new DetalleFacturacion(
                detalleFacturacion.isExentoIva(), 
                contactosManaged,
                detalleFacturacion.getFormaPagoVenta(), 
                detalleFacturacion.getFormaPagoAlquiler(),  
                detalleFacturacion.getDireccionPostal(), 
                detalleFacturacion.isMalPagador(), 
                detalleFacturacion.getObservaciones()
            );
            cliente.setDetalleFacturacion(detalleFacturacion);
        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new UndefinedBussinesException(ex);
        } finally {
            log.finish();
        }
    }
    
    @Override
    public boolean isPotencialCreable(Integer id) {
        Empresa empresa = empresaRepository.find(id);
        return (empresa.getPotencial() == null) && (empresa.getCliente() == null);
    }

    @Override
    public boolean isClienteCreable(Integer id) {
        Empresa empresa = empresaRepository.find(id);
        return (empresa.getCliente() == null) && (empresa.getPotencial() == null);
    }

    @Override
    public boolean isProveedorCreable(Integer id) {
        Empresa empresa = empresaRepository.find(id);
        return (empresa.getProveedor() == null);
    }

    @Override
    public void proveedorCrear(
        Integer empresaId, 
        CodigoProveedor codigoProveedor, 
        List<Integer> comercialIds, 
        List<Integer> contactoIds, 
        FormaPago formaPago, 
        DireccionPostal direccion, 
        DobleObservacion dobleObservacion
    ) {        
        FunctionLog log = new FunctionLog(Capa.APP_SERVICE);
        try {
            Empresa empresa = getEmpresa(empresaId);
            if (codigoProveedor == null) {
                ArgumentValidator.required(codigoProveedor, "Codigo Proveedor");
                //codigoProveedor = new CodigoProveedor("PR" + String.format("%06d", empresaId));
            }
            empresa.proveedorCrear(
                codigoProveedor, 
                cargarComerciales(comercialIds),
                cargarContactos(empresa, contactoIds), 
                formaPago, 
                direccion, 
                dobleObservacion
            );
        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new UndefinedBussinesException(ex);
        } finally {
            log.finish();
        }
    }
    
    @Override
    public void proveedorModificar(
        Integer empresaId, 
        CodigoProveedor codigoProveedor, 
        List<Integer> comercialIds, 
        List<Integer> contactoIds, 
        FormaPago formaPago, 
        DireccionPostal direccion, 
        DobleObservacion dobleObservacion
    ) {
        FunctionLog log = new FunctionLog(Capa.APP_SERVICE);
        try {
            Empresa empresa = getEmpresa(empresaId);
            if (codigoProveedor == null) {
                ArgumentValidator.required(codigoProveedor, "Codigo Proveedor");
                //codigoProveedor = new CodigoProveedor("PR" + String.format("%06d", empresaId));
            }
            empresa.proveedorModificar(
                codigoProveedor, 
                cargarComerciales(comercialIds),
                cargarContactos(empresa, contactoIds), 
                formaPago, 
                direccion, 
                dobleObservacion
            );
        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new UndefinedBussinesException(ex);
        } finally {
            log.finish();
        }
    }

    @Override
    public void anadirContacto(Integer empresaId, String nombre, String apellidos, String descripcion, CanalesContacto canalesContacto, CodigoIdioma idiomaId, String horario, String observaciones) {
        FunctionLog log = new FunctionLog(Capa.APP_SERVICE);
        Empresa empresa = getEmpresa(empresaId);
        Idioma idioma = getIdioma(idiomaId);
        empresa.contactoAnadir(
            nombre,
            apellidos,
            canalesContacto, 
            descripcion, 
            idioma, 
            observaciones, 
            horario
        );
        log.finish();
    }

    @Override
    public void modificarContacto(Integer empresaId, Integer contactoId, String nombre, String apellidos, String descripcion, CanalesContacto canalesContacto, CodigoIdioma idiomaId, String horario, String observaciones) {
        FunctionLog log = new FunctionLog(Capa.APP_SERVICE);
        Empresa empresa = getEmpresa(empresaId);
        Idioma idioma = getIdioma(idiomaId);
        empresa.contactoModificar(
            contactoId,
            nombre, 
            apellidos,
            canalesContacto, 
            descripcion, 
            idioma, 
            observaciones, 
            horario
        );
        log.finish();
    }

    @Override
    public void eliminarContacto(Integer empresaId, Integer contactoId) {
        FunctionLog log = new FunctionLog(Capa.APP_SERVICE);
        Empresa empresa = getEmpresa(empresaId);
        Contacto contacto = empresa.getContacto(contactoId);
        empresa.contactoEliminar(contacto);
        log.finish();
    }

    @Override
    public void subirContacto(Integer empresaId, Integer contactoId) {
        FunctionLog log = new FunctionLog(Capa.APP_SERVICE);
        Empresa empresa = getEmpresa(empresaId);
        Contacto contacto = empresa.getContacto(contactoId);
        empresa.contactoSubir(contacto);
        log.finish();
    }

    @Override
    public void bajarContacto(Integer empresaId, Integer contactoId) {
        FunctionLog log = new FunctionLog(Capa.APP_SERVICE);
        Empresa empresa = getEmpresa(empresaId);
        Contacto contacto = empresa.getContacto(contactoId);
        empresa.contactoBajar(contacto);
        log.finish();
    }
    
    @Override
    public void anadirDireccionEnvio(
        Integer empresaId, 
        List<Integer> contactoIds, 
        String nombre, 
        String descripcion, 
        String horario, 
        Direccion direccion, 
        DobleObservacion observaciones
    ) {
        FunctionLog log = new FunctionLog(Capa.APP_SERVICE);
        Empresa empresa = getEmpresa(empresaId);
        empresa.direccionEnvioCrear(
            nombre, 
            descripcion,
            cargarContactos(empresa, contactoIds), 
            horario, 
            direccion,
            observaciones
        );
        log.finish();
    }
    
    @Override
    public void modificarDireccionEnvio(
        Integer empresaId, 
        Integer direccionEnvioId, 
        List<Integer> contactoIds, 
        String nombre, 
        String descripcion, 
        String horario, 
        Direccion direccion, 
        DobleObservacion observaciones
    ) {        
        FunctionLog log = new FunctionLog(Capa.APP_SERVICE);
        Empresa empresa = getEmpresa(empresaId);
        empresa.direccionEnvioModificar(     
            direccionEnvioId, 
            nombre, 
            descripcion,
            cargarContactos(empresa, contactoIds), 
            horario, 
            direccion,
            observaciones
        );
        log.finish();
    }
    
    @Override
    public void eliminarDireccionEnvio(Integer empresaId, Integer direccionEnvioId) {
        FunctionLog log = new FunctionLog(Capa.APP_SERVICE);
        Empresa empresa = getEmpresa(empresaId);
        EmpresaDireccionEnvio direccionEnvio = empresa.getDireccionEnvio(direccionEnvioId);
        empresa.direccionEnvioEliminar(direccionEnvio);
        log.finish();
    }

    @Override
    public void subirDireccionEnvio(Integer empresaId, Integer direccionEnvioId) {
        FunctionLog log = new FunctionLog(Capa.APP_SERVICE);
        log.finish();
    }

    @Override
    public void bajarDireccionEnvio(Integer empresaId, Integer direccionEnvioId) {
        FunctionLog log = new FunctionLog(Capa.APP_SERVICE);
        log.finish();
    }

    @Override
    public void potencialBloquear(Integer empresaId) {
        FunctionLog log = new FunctionLog(Capa.APP_SERVICE);
        getEmpresa(empresaId).potencialBloquear();
        log.finish();
    }

    @Override
    public void potencialDesbloquear(Integer empresaId) {
        FunctionLog log = new FunctionLog(Capa.APP_SERVICE);
        getEmpresa(empresaId).potencialDesbloquear();
        log.finish();
    }
    
    @Override
    public void clienteBloquear(Integer empresaId) {
        FunctionLog log = new FunctionLog(Capa.APP_SERVICE);
        getEmpresa(empresaId).clienteBloquear();
        log.finish();
    }

    @Override
    public void clienteDesbloquear(Integer empresaId) {
        FunctionLog log = new FunctionLog(Capa.APP_SERVICE);
        getEmpresa(empresaId).clienteDesbloquear();
        log.finish();
    }
    
    @Override
    public void proveedorBloquear(Integer empresaId) {
        FunctionLog log = new FunctionLog(Capa.APP_SERVICE);
        getEmpresa(empresaId).proveedorBloquear();
        log.finish();
    }

    @Override
    public void proveedorDesbloquear(Integer empresaId) {
        FunctionLog log = new FunctionLog(Capa.APP_SERVICE);
        getEmpresa(empresaId).proveedorDesbloquear();
        log.finish();
    }
    
    
}
