package es.zarca.covellog.application.albaranes.internal;

import es.zarca.covellog.application.albaranes.AlbaranesEntregaService;
import es.zarca.covellog.application.albaranes.form.AlbaranLineaForm;
import es.zarca.covellog.application.albaranes.form.AsignacionStockAssembler;
import es.zarca.covellog.application.albaranes.form.ModificarAlbaranInfoForm;
import es.zarca.covellog.application.exception.AppExceptionHandler;
import es.zarca.covellog.application.stock.form.AsignacionStockForm;
import es.zarca.covellog.domain.model.adreces.poblacio.PoblacioRepository;
import es.zarca.covellog.domain.model.albaran.Albaran;
import es.zarca.covellog.domain.model.albaran.AlbaranRepository;
import es.zarca.covellog.domain.model.almacen.AlmacenRepository;
import es.zarca.covellog.domain.model.empresa.Empresa;
import es.zarca.covellog.domain.model.empresa.EmpresaRepository;
import es.zarca.covellog.domain.model.stock.StockRepository;
import es.zarca.covellog.domain.model.stock.reservas.Reserva;
import es.zarca.covellog.domain.model.transporte.TransporteRepository;
import es.zarca.covellog.domain.model.ubicacion.Ubicacion;
import es.zarca.covellog.domain.model.ubicacion.UbicacionRepository;
import es.zarca.covellog.infrastructure.assertions.ArgumentValidator;
import es.zarca.covellog.infrastructure.log.Capa;
import es.zarca.covellog.infrastructure.log.FunctionLog;
import es.zarca.covellog.interfaces.facade.empresas.facade.internal.assembler.DobleObservacionAssembler;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import es.zarca.covellog.domain.model.stock.movimientos.AsignacionStock;
import java.util.ArrayList;

/**
 *
 * @author francisco
 */
@Stateless
public class DefaultAlbaranesEntregaService implements AlbaranesEntregaService {
    @Inject
    private AlbaranRepository albaranRepository;
    @Inject
    private EmpresaRepository empresaRepository;
    @Inject
    private PoblacioRepository poblacionRepository;
    @Inject
    private TransporteRepository transporteRepository;
    @Inject
    private AlmacenRepository almacenRepository;
    @Inject
    private StockRepository stockRepository;
    @Inject
    private UbicacionRepository ubicacionRepository;
    
    private void assertAlbaranIdParameterNotNull(Integer albaranId) {
        ArgumentValidator.isNotNull(albaranId, "El id de albaran es obligatorio.");
    }
    
    private Albaran formToAlbaran(Albaran albaran, ModificarAlbaranInfoForm form) {
        albaran.modificarFecha(form.getFecha());
        /*albaran.cambiarTransporte(transporteRepository.findOrFail(form.getTransporteId()));
        albaran.cambiarOrigen(almacenRepository.findOrFail(form.getAlmacenId()));
        albaran.setDestino(DireccionAssembler.toModel(form.getDireccion(), poblacionRepository));*/
        albaran.modificarTextoAviso(form.getTextoAviso());
        albaran.modificarObservaciones(DobleObservacionAssembler.toModel(form.getObservaciones()));
        return albaran;
    }
    
    private Albaran createAlbaran(ModificarAlbaranInfoForm form) {
        if (form == null) {
            return null;
        }
        return formToAlbaran(new Albaran(), form);
    }
    
    @Override
    public Albaran nuevo(ModificarAlbaranInfoForm form) {
        FunctionLog log = new FunctionLog(Capa.APP_SERVICE);
        try {
            Albaran albaran = createAlbaran(form);
            albaranRepository.store(albaran);
            return albaran;
        } catch (Exception ex) {
            AppExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
        return null;
    }
    
    @Override
    public void modificarInfoGeneral(Integer albaranId, ModificarAlbaranInfoForm form) {
        FunctionLog log = new FunctionLog(Capa.APP_SERVICE);
        try {
            assertAlbaranIdParameterNotNull(albaranId);
            ArgumentValidator.isNotNull(form, "El formulario de modificacion de info general nno puede ser null.");
            Albaran albaran = albaranRepository.findOrFail(albaranId);
            formToAlbaran(albaran, form);
        } catch (Exception ex) {
            AppExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }

    @Override
    public void cambiarTransporte(Integer albaranId, Integer transporteId) {
        FunctionLog log = new FunctionLog(Capa.APP_SERVICE);
        try {
            assertAlbaranIdParameterNotNull(albaranId);
            Albaran albaran = albaranRepository.findOrFail(albaranId);
            if (transporteId == null) {
               albaran.cambiarTransporte(null);
            } else {
                albaran.cambiarTransporte(transporteRepository.findOrFail(transporteId));
            }
        } catch (Exception ex) {
            AppExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    @Override
    public void cambiarOrigen(Integer albaranId, Integer ubicacionId) {
        FunctionLog log = new FunctionLog(Capa.APP_SERVICE);
        try {
            assertAlbaranIdParameterNotNull(albaranId);
            ArgumentValidator.isNotNull(ubicacionId, "El origen es obligatorio.");
            Albaran albaran = albaranRepository.findOrFail(albaranId);
            Ubicacion ubicacion = ubicacionRepository.findOrFail(ubicacionId);
            albaran.asignarOrigen(ubicacion);
        } catch (Exception ex) {
            AppExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    @Override
    public void cambiarDestino(Integer albaranId, Integer ubicacionId) {
        FunctionLog log = new FunctionLog(Capa.APP_SERVICE);
        try {
            assertAlbaranIdParameterNotNull(albaranId);
            ArgumentValidator.isNotNull(ubicacionId, "El destino es obligatorio.");
            Albaran albaran = albaranRepository.findOrFail(albaranId);
            Ubicacion ubicacion = ubicacionRepository.findOrFail(ubicacionId);
            albaran.asignarDestino(ubicacion);
        } catch (Exception ex) {
            AppExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    @Override
    public void anular(Integer albaranId) {
        FunctionLog log = new FunctionLog(Capa.APP_SERVICE);
        try {
            assertAlbaranIdParameterNotNull(albaranId);
            Albaran albaran = albaranRepository.findOrFail(albaranId);
            albaran.anular();
        } catch (Exception ex) {
            AppExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    @Override
    public void reabrir(Integer albaranId) {
        FunctionLog log = new FunctionLog(Capa.APP_SERVICE);
        try {
            assertAlbaranIdParameterNotNull(albaranId);
            Albaran albaran = albaranRepository.findOrFail(albaranId);
            albaran.reabrir();
        } catch (Exception ex) {
            AppExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    @Override
    public void activar(Integer albaranId) {
        FunctionLog log = new FunctionLog(Capa.APP_SERVICE);
        try {
            assertAlbaranIdParameterNotNull(albaranId);
            Albaran albaran = albaranRepository.findOrFail(albaranId);
            albaran.activar();
        } catch (Exception ex) {
            AppExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    @Override
    public void finalizar(Integer albaranId) {
        FunctionLog log = new FunctionLog(Capa.APP_SERVICE);
        try {
            assertAlbaranIdParameterNotNull(albaranId);
            Albaran albaran = albaranRepository.findOrFail(albaranId);
            albaran.finalizar(new Date());
        } catch (Exception ex) {
            AppExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    @Override
    public void marcarSalida(Integer albaranId, Date fecha) {
        FunctionLog log = new FunctionLog(Capa.APP_SERVICE);
        try {
            assertAlbaranIdParameterNotNull(albaranId);
            Albaran albaran = albaranRepository.findOrFail(albaranId);
            albaran.marcarSalida(fecha);
        } catch (Exception ex) {
            AppExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    @Override
    public void desmarcarSalida(Integer albaranId) {
        FunctionLog log = new FunctionLog(Capa.APP_SERVICE);
        try {
            assertAlbaranIdParameterNotNull(albaranId);
            Albaran albaran = albaranRepository.findOrFail(albaranId);
            albaran.desmarcarSalida();
        } catch (Exception ex) {
            AppExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    @Override
    public void marcarLlegada(Integer albaranId, Date fecha) {
        FunctionLog log = new FunctionLog(Capa.APP_SERVICE);
        try {
            assertAlbaranIdParameterNotNull(albaranId);
            Albaran albaran = albaranRepository.findOrFail(albaranId);
            albaran.marcarLlegada(fecha);
        } catch (Exception ex) {
            AppExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    @Override
    public void desmarcarLlegada(Integer albaranId) {
        FunctionLog log = new FunctionLog(Capa.APP_SERVICE);
        try {
            assertAlbaranIdParameterNotNull(albaranId);
            Albaran albaran = albaranRepository.findOrFail(albaranId);
            albaran.desmarcarLlegada();
        } catch (Exception ex) {
            AppExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
   
    @Override
    public void lineasAnadir(Integer albaranId, AlbaranLineaForm form) {
        FunctionLog log = new FunctionLog(Capa.APP_SERVICE);
        try {
            throw new Exception("FUNCION ANULADA");
            /*assertAlbaranIdParameterNotNull(albaranId);
            Albaran albaran = albaranRepository.findOrFail(albaranId);
            albaran.lineaAnadir(form.getReferencia(), form.getStock(), form.getDescripcion());*/
        } catch (Exception ex) {
            AppExceptionHandler.handleException(ex);    
        } finally {
            log.finish();
        }
    }
    
    @Override
    public void lineasEliminar(Integer albaranId, List<String> referencias) {
        FunctionLog log = new FunctionLog(Capa.APP_SERVICE);
        try {
            assertAlbaranIdParameterNotNull(albaranId);
            Albaran albaran = albaranRepository.findOrFail(albaranId);
            referencias.forEach(referencia -> {
                albaran.lineaEliminar(referencia);
            });
        } catch (Exception ex) {
            AppExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    @Override
    public void lineasBajar(Integer albaranId, List<String> referencias) {
        FunctionLog log = new FunctionLog(Capa.APP_SERVICE);
        try {
            assertAlbaranIdParameterNotNull(albaranId);
            Albaran albaran = albaranRepository.findOrFail(albaranId);
            albaran.lineasBajar(referencias);
           /* for(int i = 0; i < lineas.size(); i++) {
                albaran.lineaBajar(referencias.get(i));
            }
            
            for(int i = 0; i < referencias.size(); i++) {
                albaran.lineaBajar(referencias.get(i));
            }*/
        } catch (Exception ex) {
            AppExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    @Override
    public void lineasSubir(Integer albaranId, List<String> referencias) {
        FunctionLog log = new FunctionLog(Capa.APP_SERVICE);
        try {
            assertAlbaranIdParameterNotNull(albaranId);
            Albaran albaran = albaranRepository.findOrFail(albaranId);
            albaran.lineasSubir(referencias);
            /*for(int i = referencias.size() - 1; i >= 0; i--) {
                albaran.lineaSubir(referencias.get(i));
            }*/
        } catch (Exception ex) {
            AppExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    @Override
    public void lineasMarcarSalida(Integer albaranId, List<String> referencias, Date fecha) {
        FunctionLog log = new FunctionLog(Capa.APP_SERVICE);
        try {
            assertAlbaranIdParameterNotNull(albaranId);
            Albaran albaran = albaranRepository.findOrFail(albaranId);
            referencias.forEach(referencia -> {
                albaran.lineaMarcarSalida(referencia, fecha);
            });
        } catch (Exception ex) {
            AppExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    @Override
    public void lineasDesmarcarSalida(Integer albaranId, List<String> referencias) {
        FunctionLog log = new FunctionLog(Capa.APP_SERVICE);
        try {
            assertAlbaranIdParameterNotNull(albaranId);
            Albaran albaran = albaranRepository.findOrFail(albaranId);
            referencias.forEach(referencia -> {
                albaran.lineaDesmarcarSalida(referencia);
            });
        } catch (Exception ex) {
            AppExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
  
    @Override
    public void lineasMarcarLlegada(Integer albaranId, List<String> referencias, Date fecha) {
        FunctionLog log = new FunctionLog(Capa.APP_SERVICE);
        try {
            assertAlbaranIdParameterNotNull(albaranId);
            Albaran albaran = albaranRepository.findOrFail(albaranId);
            referencias.forEach(referencia -> {
                albaran.lineaMarcarLlegada(referencia, fecha);
            });
        } catch (Exception ex) {
            AppExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    @Override
    public void lineasDesmarcarLlegada(Integer albaranId, List<String> referencias) {
        FunctionLog log = new FunctionLog(Capa.APP_SERVICE);
        try {
            assertAlbaranIdParameterNotNull(albaranId);
            Albaran albaran = albaranRepository.findOrFail(albaranId);
            referencias.forEach(referencia -> {
                albaran.lineaDesmarcarLlegada(referencia);
            });
        } catch (Exception ex) {
            AppExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }

    @Override
    public Reserva crearEntreguese(
        Integer albaranId, 
        Boolean activar,
        Date fecha,
        List<AsignacionStockForm> asignacionesForm, 
        Integer transportistaId, 
        String transportistaNombre, 
        String chofer, 
        String matricula, 
        String observaciones
    ) {
        FunctionLog log = new FunctionLog(Capa.APP_SERVICE);
        try {
            assertAlbaranIdParameterNotNull(albaranId);
            Albaran albaran = albaranRepository.findOrFail(albaranId);
            Empresa transportista;
            if (transportistaId != null) {
                transportista = empresaRepository.find(transportistaId);
            } else {
                transportista = null;
            }
            Reserva reserva = albaran.crearEntreguese(
                fecha, 
                AsignacionStockAssembler.toDomainObject(asignacionesForm, stockRepository), 
                transportista, 
                transportistaNombre, 
                chofer, 
                matricula, 
                observaciones
            );
            if (activar) {
                reserva.activar();
            }
            return reserva;
        } catch (Exception ex) {
            AppExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
        return null;
    }
    
    @Override
    public Reserva crearAdmitase(
        Integer albaranId,
        Boolean activar,
        Date fecha,
        List<AsignacionStockForm> asignacionesForm, 
        Integer transportistaId, 
        String transportistaNombre, 
        String chofer, 
        String matricula, 
        String observaciones
    ) {
        FunctionLog log = new FunctionLog(Capa.APP_SERVICE);
        try {
            assertAlbaranIdParameterNotNull(albaranId);
            Albaran albaran = albaranRepository.findOrFail(albaranId);
            Empresa transportista;
            if (transportistaId != null) {
                transportista = empresaRepository.find(transportistaId);
            } else {
                transportista = null;
            }
          
            Reserva reserva = albaran.crearAdmitase(
                fecha, 
                AsignacionStockAssembler.toDomainObject(asignacionesForm, stockRepository), 
                transportista, 
                transportistaNombre, 
                chofer, 
                matricula, 
                observaciones
            );
            if (activar) {
                reserva.activar();
            }
            return reserva;
        } catch (Exception ex) {
            AppExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
        return null;
    }

    @Override
    public Reserva generarMovimiento(
        Integer albaranId, 
        Date fecha, 
        List<AsignacionStockForm> asignacionesForm, 
        Integer transportistaId, 
        String transportistaNombre, 
        String chofer, 
        String matricula, 
        String observaciones
    ) {
        FunctionLog log = new FunctionLog(Capa.APP_SERVICE);
        try {
            assertAlbaranIdParameterNotNull(albaranId);
            Albaran albaran = albaranRepository.findOrFail(albaranId);
            Empresa transportista;
            if (transportistaId != null) {
                transportista = empresaRepository.find(transportistaId);
            } else {
                transportista = null;
            }
            Reserva reserva = albaran.generarMovimiento(
                fecha, 
                AsignacionStockAssembler.toDomainObject(asignacionesForm, stockRepository), 
                transportista, 
                transportistaNombre, 
                chofer, 
                matricula, 
                observaciones
            );
            return reserva;
        } catch (Exception ex) {
            AppExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
        return null;
    }
 
    
}