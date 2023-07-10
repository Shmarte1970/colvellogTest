package es.zarca.covellog.interfaces.facade.albaran.internal;

import es.zarca.covellog.domain.model.app.Ordre;
import es.zarca.covellog.infrastructure.log.Capa;
import es.zarca.covellog.infrastructure.log.FunctionLog;
import es.zarca.covellog.interfaces.facade.albaran.dto.AlbaranDto;
import es.zarca.covellog.interfaces.facade.albaran.internal.assembler.AlbaranDtoAssembler;
import es.zarca.covellog.interfaces.web.app.model.Filtro;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import es.zarca.covellog.application.albaranes.AlbaranesEntregaService;
import es.zarca.covellog.application.albaranes.form.AlbaranLineaForm;
import es.zarca.covellog.application.albaranes.form.ModificarAlbaranInfoForm;
import es.zarca.covellog.application.contratos.GestionContratoService;
import es.zarca.covellog.application.stock.ReservaGestionService;
import es.zarca.covellog.application.stock.form.AsignacionStockForm;
import es.zarca.covellog.domain.model.albaran.Albaran;
import es.zarca.covellog.domain.model.albaran.AlbaranRepository;
import es.zarca.covellog.interfaces.facade.contrato.dto.ContratoLineaDto;
import es.zarca.covellog.interfaces.facade.contrato.internal.assembler.ContratoLineaDtoAssembler;
import es.zarca.covellog.interfaces.facade.empresas.facade.dto.DireccionEnvioDto;
import es.zarca.covellog.interfaces.facade.exception.FacadeExceptionHandler;
import java.util.ArrayList;
import java.util.Date;
import es.zarca.covellog.interfaces.facade.albaran.AlbaranFacade;
import es.zarca.covellog.domain.model.stock.reservas.Reserva;
import es.zarca.covellog.interfaces.facade.albaran.dto.AlbaranSmallDto;
import es.zarca.covellog.interfaces.facade.albaran.internal.assembler.AlbaranSmallDtoAssembler;

/**
 *
 * @author francisco
 */
@ApplicationScoped
public class DefaultAlbaranFacade implements AlbaranFacade {
    private static final Logger LOGGER = Logger.getLogger(DefaultAlbaranFacade.class.getName());
    @Inject
    private AlbaranRepository albaranRepository;
    @Inject
    private AlbaranesEntregaService service;
    @Inject
    private GestionContratoService gestionContratoService;
    @Inject
    private ReservaGestionService reservaGestionService;
    
    @Override
    public AlbaranDto findById(Integer id) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try { 
            return AlbaranDtoAssembler.toDto(albaranRepository.find(id));
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
        return null;
    }

    @Override
    public List<AlbaranSmallDto> find(int first, int pageSize, Ordre sortOrdre, Map<String, Object> filters) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try { 
            return AlbaranSmallDtoAssembler.toDto(albaranRepository.find(first, pageSize, sortOrdre, filters));
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
        return null;
    }

    @Override
    public int findTotalCount() {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try { 
            return albaranRepository.findTotalCount();
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
        return 0;
    }
    
    @Override
    public int findTotalCount(Map<String, Object> filters) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try { 
            return albaranRepository.findTotalCount(filters);
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
        return 0;
    }
    
    @Override
    public List<Filtro> getFiltrosPosibles() {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try {
            return albaranRepository.getFiltrosPosibles();
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
        return null;
    }

    @Override
    public AlbaranDto nuevo(ModificarAlbaranInfoForm form) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try { 
            return AlbaranDtoAssembler.toDto(service.nuevo(form));
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
        return null;
    }

    @Override
    public void modificarInfoGeneral(Integer idAlbaran, ModificarAlbaranInfoForm form) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try {
            service.modificarInfoGeneral(idAlbaran, form);
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    @Override
    public void cambiarTransporte(Integer albaranId, Integer transporteId) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try {
            service.cambiarTransporte(albaranId, transporteId);
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    @Override
    public void cambiarOrigen(Integer albaranId, Integer ubicacionId) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try {
            service.cambiarOrigen(albaranId, ubicacionId);
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    @Override
    public void cambiarDestino(Integer albaranId, Integer ubicacionId) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try {
            service.cambiarDestino(albaranId, ubicacionId);
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    //control estados
    @Override
    public void anular(Integer albaranId) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try {
            service.anular(albaranId);
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    @Override
    public void cancelar(Integer albaranId) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try {
            service.reabrir(albaranId);
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    @Override
    public void activar(Integer albaranId) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try {
            service.activar(albaranId);
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    @Override
    public void finalizar(Integer albaranId) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try {
            service.finalizar(albaranId);
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    //Control global Salida/Llegada
    @Override
    public void marcarSalida(Integer albaranId, Date fecha) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try {
            service.marcarSalida(albaranId, fecha);
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    @Override
    public void desmarcarSalida(Integer albaranId) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try {
            service.desmarcarSalida(albaranId);
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    @Override
    public void marcarLlegada(Integer albaranId, Date fecha) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try {
            service.marcarLlegada(albaranId, fecha);
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    @Override
    public void desmarcarLlegada(Integer albaranId) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try {
            service.desmarcarLlegada(albaranId);
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    //Gestion lineas
    @Override
    public void lineasAnadir(AlbaranLineaForm form) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try {
           
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    @Override
    public void lineasEliminar(Integer albaranId, List<String> referencias) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try {
            service.lineasEliminar(albaranId, referencias);
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    @Override
    public void lineasBajar(Integer albaranId, List<String> referencias) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try {
            service.lineasBajar(albaranId, referencias);
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    @Override
    public void lineasSubir(Integer albaranId, List<String> referencias) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try {
            service.lineasSubir(albaranId, referencias);
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    //Control linea individual Salida/Llegada
    @Override
    public void lineasMarcarLlegada(Integer albaranId, List<String> referencias, Date fecha) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try {
            service.lineasMarcarLlegada(albaranId, referencias, fecha);
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    @Override
    public void lineasDesmarcarLlegada(Integer albaranId, List<String> referencias) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try {
            service.lineasDesmarcarLlegada(albaranId, referencias);
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    @Override
    public void lineasMarcarSalida(Integer albaranId, List<String> referencias, Date fecha) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try {
            service.lineasMarcarSalida(albaranId, referencias, fecha);
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    @Override
    public void lineasDesmarcarSalida(Integer albaranId, List<String> referencias) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try {
            service.lineasDesmarcarSalida(albaranId, referencias);
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }

    /*@Override
    public List<DireccionEnvioDto> findDireccionesEnvioPosibles(Integer albaranId) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try {
            AlbaranEntrega albaran = albaranRepository.findOrFail(albaranId);
            return DireccionEnvioAssembler.toDto(
                albaran.getCliente().getDireccionesEnvio()
            );
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
        return null;
    }
*/
    @Override
    public List<ContratoLineaDto> getLineasContratoQueSePuedenAnadir(Integer albaranId) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try {
            Albaran albaran = albaranRepository.findOrFail(albaranId);
            if (albaran.getContrato() == null) {
                return new ArrayList();
            }
            return ContratoLineaDtoAssembler.toDto(
                albaran.getContrato().getLineasContratoPendientesAlbaranEntrega()
            );
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
        return null;
    }

    @Override
    public void anadirLineasContrato(Integer contratoId, List<Integer> lineasContratoIds, Integer albaranId) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try {
            gestionContratoService.anadirLineasContratoAAlbaranEntrega(contratoId, lineasContratoIds, albaranId);
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }

    @Override
    public List<DireccionEnvioDto> findDireccionesEnvioPosibles(Integer albaranId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Integer crearAdmitase(
        Integer albaranId, 
        Boolean activar, 
        Date fecha, 
        List<AsignacionStockForm> asignaciones, 
        Integer transportistaId, 
        String transportistaNombre, 
        String chofer, 
        String matricula, 
        String observaciones
    ) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try {
            Reserva reserva = service.crearAdmitase(
                albaranId, 
                activar,
                fecha,
                asignaciones, 
                transportistaId, 
                transportistaNombre, 
                chofer, 
                matricula, 
                observaciones
            );
            return reserva.getId();
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
        return null;
    }
    
    @Override
    public Integer crearEntreguese(
        Integer albaranId, 
        Boolean activar, 
        Date fecha, 
        List<AsignacionStockForm> asignaciones, 
        Integer transportistaId, 
        String transportistaNombre, 
        String chofer, 
        String matricula, 
        String observaciones
    ) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try {
            Reserva reserva = service.crearEntreguese(
                albaranId, 
                activar,
                fecha,
                asignaciones, 
                transportistaId, 
                transportistaNombre, 
                chofer, 
                matricula, 
                observaciones
            );            
            return reserva.getId();
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
        return null;
    }

    @Override
    public Integer generarMovimiento(
        Integer albaranId, 
        Date fecha, 
        List<AsignacionStockForm> asignacionesForm,
        Integer transportistaId, 
        String transportistaNombre, 
        String chofer, 
        String matricula, 
        String observaciones
    ) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try {
            Reserva reserva = service.generarMovimiento(
                albaranId, 
                fecha, 
                asignacionesForm,
                transportistaId, 
                transportistaNombre, 
                chofer, 
                matricula, 
                observaciones
            );
            return reserva.getId();
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
        return null;
    }

    
    
    
}
