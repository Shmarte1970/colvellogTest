package es.zarca.covellog.interfaces.facade.empresas.facade.internal;

import es.zarca.covellog.application.adreces.form.DireccionPostalForm;
import es.zarca.covellog.application.empresas.EmpresaService;
import es.zarca.covellog.application.empresas.exception.EmpresaNotExistException;
import es.zarca.covellog.application.exception.BusinessException;
import es.zarca.covellog.application.exception.UndefinedBussinesException;
import es.zarca.covellog.domain.model.adreces.poblacio.Poblacio;
import es.zarca.covellog.domain.model.adreces.poblacio.PoblacioRepository;
import es.zarca.covellog.domain.model.app.Ordre;
import es.zarca.covellog.domain.model.comercials.comercial.Comercial;
import es.zarca.covellog.domain.model.comercials.comercial.ComercialRepository;
import es.zarca.covellog.domain.model.empresa.CanalesContacto;
import es.zarca.covellog.domain.model.empresa.Cif;
import es.zarca.covellog.domain.model.empresa.cliente.CodigoCliente;
import es.zarca.covellog.domain.model.empresa.proveedor.CodigoProveedor;
import es.zarca.covellog.domain.model.empresa.Empresa;
import es.zarca.covellog.domain.model.empresa.EmpresaRepository;
import es.zarca.covellog.domain.model.empresa.PalabrasClave;
import es.zarca.covellog.domain.model.empresa.cliente.FacturarPor;
import es.zarca.covellog.domain.model.empresa.cliente.TipoClienteRepository;
import es.zarca.covellog.domain.model.empresa.cliente.TipoFacturacion;
import es.zarca.covellog.domain.model.empresa.formapago.TipoPago;
import es.zarca.covellog.domain.model.empresa.formapago.TipoPagoRepository;
import es.zarca.covellog.domain.model.empresa.formapago.TipoVencimiento;
import es.zarca.covellog.domain.model.empresa.formapago.TipoVencimientoRepository;
import es.zarca.covellog.domain.model.idiomas.idioma.CodigoIdioma;
import es.zarca.covellog.domain.model.idiomas.idioma.Idioma;
import es.zarca.covellog.domain.model.idiomas.idioma.IdiomaRepository;
import es.zarca.covellog.infrastructure.log.Capa;
import es.zarca.covellog.infrastructure.log.FunctionLog;
import es.zarca.covellog.interfaces.facade.adreces.facade.dto.DireccionDto;
import es.zarca.covellog.interfaces.facade.adreces.facade.internal.assembler.DireccionAssembler;
import es.zarca.covellog.interfaces.facade.adreces.facade.internal.assembler.DireccionPostalAssembler;
import es.zarca.covellog.interfaces.facade.contactos.dto.ContactoDto;
import es.zarca.covellog.interfaces.facade.contactos.internal.assembler.ContactoAssembler;
import es.zarca.covellog.interfaces.facade.empresas.facade.EmpresaServiceFacade;
import es.zarca.covellog.interfaces.facade.empresas.facade.dto.DetalleContratacionDto;
import es.zarca.covellog.interfaces.facade.empresas.facade.dto.DetalleFacturacionDto;
import es.zarca.covellog.interfaces.facade.empresas.facade.dto.DireccionEnvioDto;
import es.zarca.covellog.interfaces.facade.empresas.facade.dto.DobleObservacionDto;
import es.zarca.covellog.interfaces.facade.empresas.facade.dto.EmpresaDto;
import es.zarca.covellog.interfaces.facade.empresas.facade.dto.FacturarPorDto;
import es.zarca.covellog.interfaces.facade.empresas.facade.dto.FormaPagoDto;
import es.zarca.covellog.interfaces.facade.empresas.facade.dto.TipoClienteDto;
import es.zarca.covellog.interfaces.facade.empresas.facade.dto.TipoFacturacionDto;
import es.zarca.covellog.interfaces.facade.empresas.facade.internal.assembler.DetalleContratacionAssembler;
import es.zarca.covellog.interfaces.facade.empresas.facade.internal.assembler.DetalleFacturacionAssembler;
import es.zarca.covellog.interfaces.facade.empresas.facade.internal.assembler.DireccionEnvioAssembler;
import es.zarca.covellog.interfaces.facade.empresas.facade.internal.assembler.DobleObservacionAssembler;
import es.zarca.covellog.interfaces.facade.empresas.facade.internal.assembler.EmpresaAssembler;
import es.zarca.covellog.interfaces.facade.empresas.facade.internal.assembler.FacturarPorAssembler;
import es.zarca.covellog.interfaces.facade.empresas.facade.internal.assembler.FormaPagoAssembler;
import es.zarca.covellog.interfaces.facade.empresas.facade.internal.assembler.TipoClienteAssembler;
import es.zarca.covellog.interfaces.facade.empresas.facade.internal.assembler.TipoFacturacionAssembler;
import es.zarca.covellog.interfaces.facade.exception.FacadeExceptionHandler;
import es.zarca.covellog.interfaces.facade.ubicaciones.dto.UbicacionDto;
import es.zarca.covellog.interfaces.facade.ubicaciones.internal.assembler.UbicacionAssembler;
import es.zarca.covellog.interfaces.web.app.model.Filtro;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

/**
 *
 * @author francisco
 */
@ApplicationScoped
public class DefaultEmpresaServiceFacade implements EmpresaServiceFacade {

    private static final Logger LOGGER = Logger.getLogger(DefaultEmpresaServiceFacade.class.getName());
    
    @Inject
    private EmpresaService empresaService;
    @Inject
    private EmpresaRepository empresaRepository;
    @Inject
    private TipoPagoRepository tipoPagoRepository;
    @Inject
    private TipoVencimientoRepository tipoVencimientoRepository;
    @Inject
    private TipoClienteRepository tipoClienteRepository;
    @Inject
    private IdiomaRepository idiomaRepository;
    @Inject
    private ComercialRepository comercialRepository;
    @Inject
    private PoblacioRepository poblacionRepository;
     
    @Override
    public EmpresaDto altaEmpresa(
        String cif,
        String nombre,
        String alias,
        String horario,
        String idiomaId,
        DireccionDto direccionFiscal,
        String web,
        String palabrasClave
    ) {
        Empresa empresa = empresaService.altaEmpresa(
            ((cif == null) || (cif.isEmpty())) ? null : new Cif(cif),
            nombre,
            alias,
            horario,
            new CodigoIdioma(idiomaId),
            direccionFiscal == null ? null : DireccionAssembler.toModel(direccionFiscal, poblacionRepository),
            web,
            new PalabrasClave(palabrasClave)
        );
        return EmpresaAssembler.toDto(empresa);
    }

    @Override
    public void modificarEmpresa(EmpresaDto form) {
        empresaService.modificarEmpresa(form);
    }

    @Override
    public void eliminarEmpresa(Integer id) {
        empresaService.eliminarEmpresa(id);
    }

    @Override
    public List<TipoPago> getTiposPagosPosibles() {
        return tipoPagoRepository.findAll();
    }

    @Override
    public List<TipoVencimiento> getVencimientosPosibles() {
        return tipoVencimientoRepository.findAll();
    }

    @Override
    public List<Idioma> getIdiomasPosibles() {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try { 
            return idiomaRepository.findAll();
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
        return null;
    }
    
    @Override
    public Idioma getIdiomaPosible(String codigoIdioma) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try { 
            return idiomaRepository.find(new CodigoIdioma(codigoIdioma));
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
        return null;
    }

    @Override
    public List<Empresa> listarEmpresas() {
        return empresaRepository.findAll();
    }

    @Override
    public List<Empresa> listarEmpresas(int start, int size) {
        return empresaRepository.findAll(start, size);
    }

    @Override
    public int listarEmpresasTotalCount() {
        return empresaRepository.findAllTotalCount();
    }

    @Override
    public List<Empresa> listarEmpresas(Ordre ordre, Map<String, Object> filters) {
        return empresaRepository.findAll(ordre, filters);
    }

    @Override
    public List<Empresa> listarEmpresas(int start, int size, Ordre ordre, Map<String, Object> filters) {
        return empresaRepository.findAll(start, size, ordre, filters);
    }

    @Override
    public int listarEmpresasTotalCount(Map<String, Object> filters) {
        return empresaRepository.findAllTotalCount(filters);
    }

    @Override
    public Map<String, List<Empresa>> listarEmpresas(int start, int size, Ordre ordre, Map<String, Object> filters, String filter) {
        return empresaRepository.findAll(start, size, ordre, filters, filter);
    }

    @Override
    public Empresa buscarPorId(Integer id) {
        return empresaRepository.find(id);
    }
    
    @Override
    public EmpresaDto buscarPorIdDto(Integer id) {
        return EmpresaAssembler.toDto(empresaRepository.find(id));
    }
    
    @Override
    public List<Comercial> getComercialesPosibles() {
        return comercialRepository.findAll();
    }

    @Override
    public List<Poblacio> getPoblacionesPosibles() {
        return poblacionRepository.findAll();
    }

    @Override
    public Poblacio buscarPoblacionPorId(Integer id) {
        return poblacionRepository.find(id);
    }

    @Override
    public DireccionDto findDireccionFiscalEmpresa(Integer empresaId) {
        return DireccionAssembler.toDto(
            empresaRepository.findOrFail(empresaId).getDireccionFiscal()
        );
    }
    
    @Override
    public EmpresaDto modificarInfoEmpresa(Integer empresaId, String cif, String nombre, String alias, String horario, String codigoIdioma, DireccionDto direccion, String web, String palabrasClave) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try {
            Empresa empresa = empresaService.modificarInfoEmpresa(
                empresaId,
                ((cif == null) || (cif.isEmpty())) ? null : new Cif(cif),
                nombre,
                alias,
                horario,
                new CodigoIdioma(codigoIdioma),
                direccion == null ? null : DireccionAssembler.toModel(direccion, poblacionRepository),
                web,
                new PalabrasClave(palabrasClave)
            );
            return EmpresaAssembler.toDto(empresa);
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
        return null;
    }
    
    @Override
    public boolean isPotencialCreable(Integer id) {
        return empresaService.isPotencialCreable(id);
    }

    @Override
    public boolean isClienteCreable(Integer id) {
        return empresaService.isClienteCreable(id);
    }

    @Override
    public boolean isProveedorCreable(Integer id) {
        return empresaService.isProveedorCreable(id);
    }

    @Override
    public void modificarPotencial(Integer empresaId, List<Integer> comercialIds, List<Integer> contactoIds, DobleObservacionDto observaciones) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try { 
            empresaService.potencialModificar(empresaId, comercialIds, contactoIds, DobleObservacionAssembler.toModel(observaciones));
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    @Override
    public void crearPotencial(Integer empresaId, List<Integer> comercialIds, List<Integer> contactoIds, DobleObservacionDto observaciones) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try {
            empresaService.potencialCrear(empresaId, comercialIds, contactoIds, DobleObservacionAssembler.toModel(observaciones));
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    @Override
    public void eliminarPotencial(Integer empresaId) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try {
            empresaService.potencialEliminar(empresaId);
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }      
    }
    
    @Override
    public void potencialBloquear(Integer empresaId) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try {
            empresaService.potencialBloquear(empresaId);
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }

    @Override
    public void potencialDesbloquear(Integer empresaId) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try {
            empresaService.potencialDesbloquear(empresaId);
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }

    @Override
    public void crearCliente(Integer empresaId, String codigoCliente, String tipoClienteId, List<Integer> comercialIds, List<Integer> contactoIds, DobleObservacionDto observaciones) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try {
           /* DireccionPostal direccionModel = null;
            if (direccion != null) {
                Poblacio poblacion = poblacionRepository.find(direccion.getPoblacion().getId());
                if (poblacion == null) {
                    throw new PoblacioNotExistException(direccion.getPoblacion().getId());
                }
                direccionModel = new DireccionPostal(direccion.getAtt(), direccion.getDireccion(), direccion.getDireccion2(), new CodigoPostal(direccion.getCodigoPostal()), poblacion);
            } */
            empresaService.crearCliente(
                empresaId, 
                codigoCliente == null || codigoCliente.isEmpty() ? null : new CodigoCliente(codigoCliente),
                tipoClienteId,
                comercialIds, 
                contactoIds, 
                DobleObservacionAssembler.toModel(observaciones)
            );
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    @Override
    public void modificarCliente(Integer empresaId, String codigoCliente, String tipoClienteId, List<Integer> comercialIds, List<Integer> contactoIds, DobleObservacionDto observaciones) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try {
            empresaService.clienteModificar(
                empresaId,
                codigoCliente == null || codigoCliente.isEmpty() ? null : new CodigoCliente(codigoCliente),
                tipoClienteId,
                comercialIds, 
                contactoIds, 
                DobleObservacionAssembler.toModel(observaciones)
            );
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    @Override
    public void eliminarCliente(Integer empresaId) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try {
            empresaService.clienteEliminar(empresaId);
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    @Override
    public void convertirClienteEnPotencial(Integer empresaId) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try {
            empresaService.clienteConvertirEnPotencial(empresaId);
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    @Override
    public void modificarInfoClienteContratacion(Integer empresaId, DetalleContratacionDto detalleContratacion) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try {
            empresaService.modificarInfoClienteDetalleContratacion(empresaId, DetalleContratacionAssembler.toModel(detalleContratacion));
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    @Override
    public List<TipoFacturacionDto> geTiposFacturacionPosibles() {
        List<TipoFacturacionDto> dtos = new ArrayList<>();
        for (TipoFacturacion tipoFacturacion : TipoFacturacion.values()) {
            dtos.add(TipoFacturacionAssembler.toDto(tipoFacturacion));
        }
        return dtos;
    }

    @Override
    public List<FacturarPorDto> getFacturarPorPosibles() {
        List<FacturarPorDto> dtos = new ArrayList<>();
        for (FacturarPor facturarPor : FacturarPor.values()) {
            dtos.add(FacturarPorAssembler.toDto(facturarPor));
        }
        return dtos;
    }

    @Override
    public void modificarInfoClienteFacturacion(Integer empresaId, DetalleFacturacionDto detalleFacturacion) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try {
            Empresa empresa = empresaRepository.find(empresaId);
            if (empresa == null) {
                throw new EmpresaNotExistException(empresaId);
            }
            empresaService.modificarInfoClienteDetalleFacturacion(empresaId, DetalleFacturacionAssembler.toModel(detalleFacturacion, empresa, poblacionRepository));
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    @Override
    public void proveedorCrear(
        Integer empresaId,
        String codigoProveedor,
        List<Integer> comercialIds, 
        List<Integer> contactoIds,
        FormaPagoDto formaPagoDto,
        DireccionPostalForm direccion, 
        DobleObservacionDto observaciones
    ) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try {
            empresaService.proveedorCrear(
                empresaId,
                codigoProveedor == null || codigoProveedor.isEmpty() ? null : new CodigoProveedor(codigoProveedor),
                comercialIds, 
                contactoIds,
                FormaPagoAssembler.toModel(formaPagoDto),
                DireccionPostalAssembler.toModel(direccion, poblacionRepository),
                DobleObservacionAssembler.toModel(observaciones)
            );
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    @Override
    public void modificarProveedor(
        Integer empresaId,
        String codigoProveedor,
        List<Integer> comercialIds, 
        List<Integer> contactoIds,
        FormaPagoDto formaPagoDto,
        DireccionPostalForm direccion, 
        DobleObservacionDto observaciones
    ) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try {
            empresaService.proveedorModificar(
                empresaId,
                codigoProveedor == null || codigoProveedor.isEmpty() ? null : new CodigoProveedor(codigoProveedor),
                comercialIds, 
                contactoIds,
                FormaPagoAssembler.toModel(formaPagoDto),
                DireccionPostalAssembler.toModel(direccion, poblacionRepository),
                DobleObservacionAssembler.toModel(observaciones)
            );
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }

    @Override
    public List<ContactoDto> findContactos(Integer empresaId) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try {
            return ContactoAssembler.toDto(empresaRepository.findOrFail(empresaId).getContactos());
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
        return null;
    }
    
    @Override
    public void anadirContacto(Integer empresaId, ContactoDto contacto) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try {
            empresaService.anadirContacto(
                empresaId, 
                contacto.getNombre(),
                contacto.getApellidos(),
                contacto.getDescripcion(),
                new CanalesContacto(contacto.getTelefono(), contacto.getTelefono2(), contacto.getEmail()),
                new CodigoIdioma(contacto.getIdioma().getId()),
                contacto.getHorario(),
                contacto.getObservaciones()
            );
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }

    @Override
    public void modificarContacto(Integer empresaId, ContactoDto contacto) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        CanalesContacto canalesContacto = new CanalesContacto(contacto.getTelefono(), contacto.getTelefono2(), contacto.getEmail());
        try {
            System.err.println("COJONES ************************** vamos tirando: " + contacto.getApellidos());
            empresaService.modificarContacto(
                empresaId,
                contacto.getId(),
                contacto.getNombre(),
                contacto.getApellidos(),
                contacto.getDescripcion(),
                canalesContacto,
                new CodigoIdioma(contacto.getIdioma().getId()),
                contacto.getHorario(),
                contacto.getObservaciones()
            );
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }

    @Override
    public void eliminarContacto(Integer empresaId, Integer contactoId) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try {
            empresaService.eliminarContacto(empresaId, contactoId);
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }

    @Override
    public void subirContacto(Integer empresaId, Integer contactoId) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try {
            empresaService.subirContacto(empresaId, contactoId);
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }

    @Override
    public void bajarContacto(Integer empresaId, Integer contactoId) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try {
            empresaService.bajarContacto(empresaId, contactoId);
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    
    @Override
    public List<DireccionEnvioDto> findDireccionesEnvio(Integer empresaId) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try {
            return DireccionEnvioAssembler.toDto(
                empresaRepository.findOrFail(empresaId).getDireccionesEnvio()
            );
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
        return null;
    }

    @Override
    public DireccionEnvioDto findDireccionEnvio(Integer empresaId, Integer direccionEnvioId) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try {
            return DireccionEnvioAssembler.toDto(
                empresaRepository.findOrFail(empresaId).getDireccionEnvio(direccionEnvioId)
            );
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
        return null;
    }
    
    @Override
    public void anadirDireccionEnvio(Integer empresaId, DireccionEnvioDto direccionEnvio) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try {
            //Assert.required(direccionEnvio.getContactoPrincipal(), "contacto");
            empresaService.anadirDireccionEnvio(empresaId,
                ContactoAssembler.dtoToArrayId(direccionEnvio.getContactos()),
                direccionEnvio.getNombre(),
                direccionEnvio.getDescripcion(),    
                direccionEnvio.getHorario(),
                DireccionAssembler.toModel(direccionEnvio.getDireccion(), poblacionRepository),
                DobleObservacionAssembler.toModel(direccionEnvio.getObservaciones())
            );
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }

    @Override
    public void modificarDireccionEnvio(Integer empresaId, DireccionEnvioDto direccionEnvio) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try {
            empresaService.modificarDireccionEnvio(empresaId,
                direccionEnvio.getId(),
                ContactoAssembler.dtoToArrayId(direccionEnvio.getContactos()),
                direccionEnvio.getNombre(),
                direccionEnvio.getDescripcion(),
                direccionEnvio.getHorario(),
                DireccionAssembler.toModel(direccionEnvio.getDireccion(), poblacionRepository),
                DobleObservacionAssembler.toModel(direccionEnvio.getObservaciones())
            );
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    @Override
    public void eliminarDireccionEnvio(Integer empresaId, Integer direccionEnvioId) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try {
            empresaService.eliminarDireccionEnvio(empresaId, direccionEnvioId);
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }        
    }

    @Override
    public void eliminarProveedor(Integer empresaId) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try {
            empresaService.proveedorEliminar(empresaId);
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }      
    }

    @Override
    public List<TipoClienteDto> getTiposClientePosibles() {
        return TipoClienteAssembler.toDto(tipoClienteRepository.findAll());
    }
    
    @Override
    public void proveedorBloquear(Integer empresaId) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try {
            empresaService.proveedorBloquear(empresaId);
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }

    @Override
    public void proveedorDesbloquear(Integer empresaId) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try {
            empresaService.proveedorDesbloquear(empresaId);
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }

    @Override
    public void clienteBloquear(Integer empresaId) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try {
            empresaService.clienteBloquear(empresaId);
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }

    @Override
    public void clienteDesbloquear(Integer empresaId) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try {
            empresaService.clienteDesbloquear(empresaId);
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }

    @Override
    public String sugerirCodigoProveedor() {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try {
            String codigo = empresaRepository.findMaxCodigoProveedor();
            codigo = codigo.substring(2);
            Integer num = Integer.parseInt(codigo);
            return "PR" + String.format("%06d", Integer.parseInt(codigo) + 1);
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
        return null;
    }
    
    @Override
    public String sugerirCodigoCliente() {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try {
            String codigo = empresaRepository.findMaxCodigoCliente();
            codigo = codigo.substring(2);
            Integer num = Integer.parseInt(codigo);
            return "CL" + String.format("%06d", Integer.parseInt(codigo) + 1);
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
        return null;
    }

    @Override
    public Map<String, List<Empresa>> buscarEnDiagonal(String filtro) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try {
            return empresaRepository.findAll(0, 0, null, null, filtro);
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
        return null;
    }

    @Override
    public List<Filtro> getFiltrosPosibles() {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try {
            return empresaRepository.getFiltrosPosibles();
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
        return null;
    }

    @Override
    public boolean esCorrectoCif(String cif) {
        try {
            new Cif(cif);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

}
