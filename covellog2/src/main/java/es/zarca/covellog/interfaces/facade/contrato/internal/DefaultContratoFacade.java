package es.zarca.covellog.interfaces.facade.contrato.internal;

import es.zarca.covellog.application.contratos.GestionContratoService;
import es.zarca.covellog.application.contratos.form.ContratoCondicionesForm;
import es.zarca.covellog.application.contratos.form.ContratoFacturacionForm;
import es.zarca.covellog.application.contratos.form.ContratoLineaForm;
import es.zarca.covellog.application.contratos.form.ContratoPersonasForm;
import es.zarca.covellog.application.contratos.form.ModificarContratoGeneralForm;
import es.zarca.covellog.application.email.EmailService;
import es.zarca.covellog.application.email.form.EmailForm;
import es.zarca.covellog.domain.model.albaran.AlbaranEntregaRepository;
import es.zarca.covellog.domain.model.app.Ordre;
import es.zarca.covellog.domain.model.contrato.Contrato;
import es.zarca.covellog.domain.model.contrato.ContratoRepository;
import es.zarca.covellog.domain.model.contrato.ContratoTipoOperacion;
import es.zarca.covellog.domain.model.empresa.Empresa;
import es.zarca.covellog.domain.model.empresa.EmpresaRepository;
import es.zarca.covellog.domain.model.empresa.cliente.FacturarPor;
import es.zarca.covellog.domain.model.empresa.cliente.TipoFacturacion;
import es.zarca.covellog.domain.model.generic.DocumentoRO;
import es.zarca.covellog.domain.model.stock.StockRepository;
import es.zarca.covellog.infrastructure.log.Capa;
import es.zarca.covellog.infrastructure.log.FunctionLog;
import es.zarca.covellog.interfaces.facade.albaran.dto.AlbaranDto;
import es.zarca.covellog.interfaces.facade.albaran.internal.assembler.AlbaranDtoAssembler;
import es.zarca.covellog.interfaces.facade.contrato.ContratoFacade;
import es.zarca.covellog.interfaces.facade.contrato.dto.ContratoDto;
import es.zarca.covellog.interfaces.facade.contrato.dto.ContratoSmallDto;
import es.zarca.covellog.interfaces.facade.contrato.dto.ContratoTipoOperacionDto;
import es.zarca.covellog.interfaces.facade.contrato.internal.assembler.ContratoDtoAssembler;
import es.zarca.covellog.interfaces.facade.contrato.internal.assembler.ContratoSmallDtoAssembler;
import es.zarca.covellog.interfaces.facade.contrato.internal.assembler.ContratoTipoOperacionDtoAssembler;
import es.zarca.covellog.interfaces.facade.contactos.dto.ContactoDto;
import es.zarca.covellog.interfaces.facade.empresas.facade.dto.EmpresaDto;
import es.zarca.covellog.interfaces.facade.empresas.facade.dto.FacturarPorDto;
import es.zarca.covellog.interfaces.facade.empresas.facade.dto.TipoFacturacionDto;
import es.zarca.covellog.interfaces.facade.contactos.internal.assembler.ContactoAssembler;
import es.zarca.covellog.interfaces.facade.empresas.facade.internal.assembler.EmpresaAssembler;
import es.zarca.covellog.interfaces.facade.empresas.facade.internal.assembler.FacturarPorAssembler;
import es.zarca.covellog.interfaces.facade.empresas.facade.internal.assembler.TipoFacturacionAssembler;
import es.zarca.covellog.interfaces.facade.exception.FacadeExceptionHandler;
import es.zarca.covellog.interfaces.facade.stock.dto.IdCantidadDto;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

/**
 *
 * @author francisco
 */
@ApplicationScoped
public class DefaultContratoFacade implements ContratoFacade {
    @Inject
    private ContratoRepository contratoRepository;
    @Inject
    private EmpresaRepository empresaRepository;
    @Inject
    private StockRepository stockRepository;
    @Inject
    private AlbaranEntregaRepository albaranEntregaRepository;
    @Inject
    private GestionContratoService gestionContratoService;
    @Inject
    private EmailService emailService;
    
    @Override
    public List<ContratoSmallDto> find(int first, int pageSize, Ordre sortOrdre, Map<String, Object> filters) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try { 
            return ContratoSmallDtoAssembler.toDto(contratoRepository.find(first, pageSize, sortOrdre, filters));
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
        return null;
    }
    
    @Override
    public List<ContratoSmallDto> findAll() {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try { 
            return ContratoSmallDtoAssembler.toDto(contratoRepository.findAll());
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
        return null;
    }
    
    @Override
    public ContratoDto findById(int id) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try { 
            return ContratoDtoAssembler.toDto(contratoRepository.find(id));
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
        return null;
    }

    @Override
    public int findTotalCount(Map<String, Object> filters) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try { 
            return contratoRepository.findTotalCount(filters);
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
        return -1;
    }
    
    @Override
    public int findTotalCount() {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try { 
            return contratoRepository.findTotalCount();
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
        return -1;
    }

    @Override
    public void crear(Date fechaContrato, Integer clienteId) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try { 
            gestionContratoService.crear(fechaContrato, clienteId);
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }

    @Override
    public ContratoDto modificarGeneral(Integer id, ModificarContratoGeneralForm form) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try { 
            return ContratoDtoAssembler.toDto(gestionContratoService.modificarGeneral(id, form));
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
        return null;
    }
    
    @Override
    public ContratoDto modificarCondiciones(Integer id, ContratoCondicionesForm form) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try {            
            return ContratoDtoAssembler.toDto(gestionContratoService.modificarCondiciones(id, form));
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
        return null;
    }
    
    @Override
    public ContratoDto modificarDatosFacturacion(Integer id, ContratoFacturacionForm form) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try {
            return ContratoDtoAssembler.toDto(gestionContratoService.modificarDatosFacturacion(id, form));
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
        return null;
    }
    
    @Override
    public List<ContratoTipoOperacionDto> getTiposOperacionPosibles() {
        List<ContratoTipoOperacionDto> dtos = new ArrayList<>();
        for (ContratoTipoOperacion contratoTipoOperacion : ContratoTipoOperacion.values()) {
            dtos.add(ContratoTipoOperacionDtoAssembler.toDto(contratoTipoOperacion));
        }
        return dtos;
    }
    
    @Override
    public List<TipoFacturacionDto> getTiposFacturacionPosibles() {
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
    public List<ContactoDto> getContactosPosibles(int clienteId) {
        Empresa empresa = empresaRepository.findOrFail(clienteId);
        return ContactoAssembler.toDto(empresa.getContactos());
    }

    @Override
    public EmpresaDto buscarEmpresa(Integer id) {
        return EmpresaAssembler.toDto(empresaRepository.findOrFail(id));
    }

    @Override
    public ContratoDto modificarPersonas(Integer id, ContratoPersonasForm form) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try {
            return ContratoDtoAssembler.toDto(gestionContratoService.modificarPersonas(id, form));
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
        return null;
    }

    @Override
    public byte[] getDatosDocumento(Integer contratoId, Integer documentoId) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try {
            Contrato contrato = contratoRepository.find(contratoId);
            DocumentoRO documento = contrato.getDocumento(documentoId);
            return documento.getDatos();
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
        return null;
    }

    @Override
    public ContratoDto addDocumento(Integer contratoId, String fileName, byte[] contents) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try {
            gestionContratoService.addDocumento(contratoId, fileName, contents);
            return ContratoDtoAssembler.toDto(contratoRepository.find(contratoId));
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
        return null;
    }
    
    @Override
    public ContratoDto removeDocumento(Integer contratoId, Integer documentoId) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try {
            gestionContratoService.removeDocumento(contratoId, documentoId);
            return ContratoDtoAssembler.toDto(contratoRepository.find(contratoId));
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
        return null;
    }

    @Override
    public ContratoDto removeDocumentos(Integer contratoId, List<Integer> documentoIds) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try {
            gestionContratoService.removeDocumentos(contratoId, documentoIds);
            return ContratoDtoAssembler.toDto(contratoRepository.find(contratoId));
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
        return null;
    }

    @Override
    public ContratoDto bajarDocumentos(Integer contratoId, List<Integer> documentoIds) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try {
            gestionContratoService.bajarDocumentos(contratoId, documentoIds);
            return ContratoDtoAssembler.toDto(contratoRepository.find(contratoId));
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
        return null;
    }

    @Override
    public ContratoDto subirDocumentos(Integer contratoId, List<Integer> documentoIds) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try {
            gestionContratoService.subirDocumentos(contratoId, documentoIds);
            return ContratoDtoAssembler.toDto(contratoRepository.find(contratoId));
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
        return null;
    }

    @Override
    public List<IdCantidadDto> getLotesPosibles(String filtro) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try { 
            return stockRepository.findLotes(filtro);
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
        return null;
    }

    @Override
    public ContratoDto modificarLinea(Integer contratoId, Integer lineaId, ContratoLineaForm form) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try {
            Contrato contrato = gestionContratoService.modificarLinea(contratoId, lineaId, form);
            //contrato = contratoRepository.find(contratoId);
            return ContratoDtoAssembler.toDto(contrato);
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
        return null;
    }
    
    @Override
    public Integer crearLinea(Integer contratoId, ContratoLineaForm form) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try { 
            return gestionContratoService.crearLinea(contratoId, form).getId();
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
        return null;
    }

    @Override
    public void lineaModificarFechaEntregaPrevista(Integer contratoId, List<Integer> lineasContratoIds, Date fecha) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try { 
            gestionContratoService.modificarFechaEntregaPrevista(contratoId, lineasContratoIds, fecha);
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }

    @Override
    public void entregarProductos(Integer contratoId, List<Integer> ids, Date fecha) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try { 
            gestionContratoService.entregarProductos(contratoId, ids, fecha);
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }

    @Override
    public void modificarFechaEntregaProductos(Integer contratoId, List<Integer> ids, Date fecha) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try { 
            gestionContratoService.modificarFechaEntrega(contratoId, ids, fecha);
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    @Override
    public void cancelarEntregaProductos(Integer contratoId, List<Integer> ids) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try { 
            gestionContratoService.cancelarEntregaProductos(contratoId, ids);
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    @Override
    public void solicitarRecogidaProductos(Integer contratoId, List<Integer> ids, Date fecha) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try { 
            gestionContratoService.solicitarRecogidaProductos(contratoId, ids, fecha);
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    @Override
    public void cancelarSolicitudRecogidaProductos(Integer contratoId, List<Integer> ids) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try {   
            gestionContratoService.cancelarSolicitudRecogidaProductos(contratoId, ids);
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    @Override
    public void recogerProductos(Integer contratoId, List<Integer> ids, Date fecha) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try { 
            gestionContratoService.recogerProductos(contratoId, ids, fecha);
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    @Override
    public void modificarFechaRecogidaProductos(Integer contratoId, List<Integer> ids, Date fecha) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try { 
            gestionContratoService.modificarFechaRecogida(contratoId, ids, fecha);
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    @Override
    public void cancelarRecogidaProductos(Integer contratoId, List<Integer> ids) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try { 
            gestionContratoService.cancelarRecogidaProductos(contratoId, ids);
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }

    @Override
    public void eliminarLinea(Integer contratoId, List<Integer> ids) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try { 
            gestionContratoService.eliminarLinea(contratoId, ids);
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }

    @Override
    public List<AlbaranDto> getAlbaranesEntrega(Integer contratoId) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try {
            System.err.println("COJONES AHORA ALGO");
            return AlbaranDtoAssembler.toDto(contratoRepository.findOrFail(contratoId).getAlbaranesEntrega());
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
        return null;
    }
    
    @Override
    public List<AlbaranDto> getAlbaranesRecogida(Integer contratoId) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try { 
            return AlbaranDtoAssembler.toDto(contratoRepository.findOrFail(contratoId).getAlbaranesRecogida());
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
        return null;
    }

    @Override
    public void anadirLineasContratoAAlbaran(Integer contratoId, List<Integer> lineasContratoIds, Integer albaranId) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try { 
            gestionContratoService.anadirLineasContratoAAlbaranEntrega(
                contratoId,
                lineasContratoIds,
                albaranId
            );
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }

    @Override
    public void crearAlbaranEntrega(Integer contratoId, List<Integer> lineasContratoIds) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try { 
            gestionContratoService.crearAlbaranEntrega(
                contratoId,
                lineasContratoIds
            );
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }

    public AlbaranEntregaRepository getAlbaranEntregaRepository() {
        return albaranEntregaRepository;
    }

    public void setAlbaranEntregaRepository(AlbaranEntregaRepository albaranEntregaRepository) {
        this.albaranEntregaRepository = albaranEntregaRepository;
    }
    
    @Override
    public void eliminarAlbaranEntrega(Integer albaranId) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try { 
            gestionContratoService.eliminarAlbaran(albaranId);
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }

    @Override
    public void crearAlbaranRecogida(Integer contratoId, List<Integer> lineasContratoIds) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try { 
            gestionContratoService.crearAlbaranRecogida(
                contratoId,
                lineasContratoIds
            );
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }

    @Override
    public void cambiarCliente(Integer contratoId, Integer empresaId) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try { 
            gestionContratoService.cambiarCliente(
                contratoId,
                empresaId
            );
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }

    @Override
    public void traspasar(Integer contratoId, Integer empresaId, Date fecha) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try { 
            gestionContratoService.traspasar(
                contratoId,
                empresaId,
                fecha
            );
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }

    @Override
    public void modificarEstadoPago(Integer contratoId, String estadoPago) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try { 
            gestionContratoService.modificarEstadoPago(contratoId, estadoPago);
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }

    @Override
    public void enviarPorEmail(Integer contratoId, EmailForm emailForm) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try { 
            emailService.enviarEmail(emailForm);
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    
    
}