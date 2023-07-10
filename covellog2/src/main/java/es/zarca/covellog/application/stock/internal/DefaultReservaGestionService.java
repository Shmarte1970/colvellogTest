
package es.zarca.covellog.application.stock.internal;

import es.zarca.covellog.application.exception.AppExceptionHandler;
import es.zarca.covellog.application.stock.ReservaGestionService;
import es.zarca.covellog.application.stock.exception.ReservaNotExistException;
import es.zarca.covellog.application.stock.form.AsignacionStockForm;
import es.zarca.covellog.application.stock.form.ReservaForm;
import es.zarca.covellog.application.stock.form.ReservaLineaForm;
import es.zarca.covellog.domain.model.almacen.AlmacenRepository;
import es.zarca.covellog.domain.model.empresa.Empresa;
import es.zarca.covellog.domain.model.empresa.EmpresaRepository;
import es.zarca.covellog.domain.model.producto.TipoProductoRepository;
import es.zarca.covellog.domain.model.stock.reservas.Reserva;
import es.zarca.covellog.domain.model.stock.reservas.ReservaRepository;
import es.zarca.covellog.domain.model.stock.reservas.ReservaTipo;
import es.zarca.covellog.domain.model.stock.StockRepository;
import es.zarca.covellog.domain.model.stock.movimientos.AsignacionStock;
import es.zarca.covellog.domain.model.ubicacion.UbicacionRepository;
import es.zarca.covellog.infrastructure.assertions.ArgumentValidator;
import es.zarca.covellog.infrastructure.log.Capa;
import es.zarca.covellog.infrastructure.log.FunctionLog;
import es.zarca.covellog.interfaces.facade.stock.dto.reservas.ReservaDto;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author francisco
 */
@Stateless
public class DefaultReservaGestionService implements ReservaGestionService {

    @Inject
    private ReservaRepository reservaRepository;
    @Inject
    private EmpresaRepository empresaRepository;
    @Inject
    private StockRepository stockRepository;
    @Inject
    private AlmacenRepository almacenRepository;
    @Inject
    private TipoProductoRepository tipoProductoRepository;
    @Inject
    private UbicacionRepository ubicacionRepository;
    
    private Reserva findReserva(Integer reservaId) {
        Reserva reserva = reservaRepository.findOrFail(reservaId);
        if (reserva == null) {
            throw new ReservaNotExistException(reservaId);
        }
        return reserva;
    }

    @Override
    public void modificar(Integer reservaId, ReservaForm form) {
        FunctionLog log = new FunctionLog(Capa.APP_SERVICE);
        try {
            ArgumentValidator.isNotNull(form.getBooking(), "El booking no puede ser null");

            Reserva reserva = reservaRepository.findOrFail(reservaId);
            reserva.setUbicacion(ubicacionRepository.findOrFail(form.getAlmacenId()));
            reserva.setFecha(form.getFecha());            
            reserva.setBooking(form.getBooking());
            reserva.setCliente(empresaRepository.findOrFail(form.getClienteId()));
            if (form.getTransportistaId() == null) {
                reserva.setTransportista(null);
                reserva.setTransportistaNombre(form.getTransportistaNombre());
            } else {
                reserva.setTransportista(empresaRepository.findOrFail(form.getTransportistaId()));
                reserva.setTransportistaNombre(reserva.getTransportista().getNombre());
            }
            reserva.setChofer(form.getChofer());
            reserva.setMatricula(form.getMatricula());
            reserva.setObservaciones(form.getObservaciones());
          /*  for (ReservaLineaForm linea : form.getLineas()) {
                reserva.lineaAnadir(
                    linea.getBooking(), 
                    stockRepository.findOrFail(linea.getStockId()),
                    tipoProductoRepository.findOrFail(linea.getTipoProductoId()), 
                    linea.getLote(), 
                    linea.getNumSerie()
                );
            }*/
        } catch (Exception ex) {
            ex.printStackTrace();
            AppExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }

    @Override
    public void anular(Integer reservaId) {
        reservaRepository.findOrFail(reservaId).anular();
    }

    @Override
    public void activar(Integer reservaId) {
        reservaRepository.findOrFail(reservaId).activar();
    }

    @Override
    public void reabrir(Integer reservaId) {
        reservaRepository.findOrFail(reservaId).reabrir();
    }
    
    @Override
    public void eliminar(Integer reservaId) {
        reservaRepository.remove(reservaRepository.findOrFail(reservaId));
    }

    @Override
    public Reserva nuevoAdmitase(ReservaForm form) {
        FunctionLog log = new FunctionLog(Capa.APP_SERVICE);
        try {
            Reserva reserva = new Reserva(
                ReservaTipo.ENTRADA, 
                form.getFecha(),
                almacenRepository.findOrFail(form.getAlmacenId()),
                form.getBooking(),
                empresaRepository.findOrFail(form.getClienteId()), 
                form.getTransportistaId() == null ? null : empresaRepository.findOrFail(form.getTransportistaId()),
                form.getTransportistaNombre(),
                form.getChofer(), 
                form.getMatricula(), 
                form.getObservaciones()
            );
            for (ReservaLineaForm linea : form.getLineas()) {
                if (linea.getStockId() != null) {
                    reserva.lineaAnadir(
                        linea.getBooking(), 
                        stockRepository.findOrFail(linea.getStockId()),
                        null
                    );
                } else {
                    reserva.lineaAnadir(
                        linea.getBooking(), 
                        tipoProductoRepository.findOrFail(linea.getTipoProductoId()), 
                        linea.getLote(), 
                        null
                    );
                }
                
            }
            reservaRepository.store(reserva);
            return reserva;
        } catch (Exception ex) {
            AppExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
        return null;
    }

    @Override
    public Reserva nuevoEntreguese(ReservaForm form) {
        FunctionLog log = new FunctionLog(Capa.APP_SERVICE);
        try {
            Reserva reserva = new Reserva(
                ReservaTipo.SALIDA, 
                form.getFecha(),
                almacenRepository.findOrFail(form.getAlmacenId()),
                form.getBooking(),
                empresaRepository.findOrFail(form.getClienteId()), 
                form.getTransportistaId() == null ? null : empresaRepository.findOrFail(form.getTransportistaId()),
                form.getTransportistaNombre(),
                form.getChofer(), 
                form.getMatricula(), 
                form.getObservaciones()
            );
            for (ReservaLineaForm linea : form.getLineas()) {
                if (linea.getStockId() != null) {
                    reserva.lineaAnadir(
                        linea.getBooking(), 
                        stockRepository.findOrFail(linea.getStockId()),
                        null
                    );
                } else {
                    reserva.lineaAnadir(
                        linea.getBooking(), 
                        tipoProductoRepository.findOrFail(linea.getTipoProductoId()), 
                        linea.getLote(), 
                        null
                    );
                }
                
            }
            reservaRepository.store(reserva);
            return reserva;
        } catch (Exception ex) {
            AppExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
        return null;
    }

    @Override
    public ReservaDto getEntradaAutocompleteFromStockId(Integer StockId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void generarMovimientos(Integer reservaId, Date fecha, List<AsignacionStockForm> asignacionesForm) {
        FunctionLog log = new FunctionLog(Capa.APP_SERVICE);
        try {
            Reserva reserva = reservaRepository.findOrFail(reservaId);
            List<AsignacionStock> asignaciones = new ArrayList();
            for (AsignacionStockForm asignacion : asignacionesForm) {
                asignaciones.add(new AsignacionStock(asignacion.getBooking(), stockRepository.findOrFail(asignacion.getStockId())));
            }
            reserva.generarMovimientos(
                fecha,
                asignaciones
            );
        } catch (Exception ex) {
            AppExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }

    @Override
    public void generarMovimientos(
        Integer reservaId, 
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
            ArgumentValidator.isNotEmpty(asignacionesForm, "No puede generar movimientos sin asignar productos.");
            Reserva reserva = reservaRepository.findOrFail(reservaId);
            List<AsignacionStock> asignaciones = new ArrayList();
            Empresa transportista = null;
            for (AsignacionStockForm asignacion : asignacionesForm) {
                ArgumentValidator.isNotNull(asignaciones, "asignaciones NULLL");
                ArgumentValidator.isNotNull(asignacion, "asignacion NULLL");
                ArgumentValidator.isNotNull(stockRepository, "stockRepository NULLL");
                ArgumentValidator.isNotNull(asignacion.getStockId(), "getStockId NULLL");
                ArgumentValidator.isNotNull(asignacion.getBooking(), "getBooking NULLL");
                stockRepository.findOrFail(asignacion.getStockId());
                AsignacionStock asignacionStock = new AsignacionStock(asignacion.getBooking(), stockRepository.findOrFail(asignacion.getStockId()));
                asignaciones.add(asignacionStock);
            }
            if (transportistaId != null) {
                transportista = empresaRepository.findOrFail(transportistaId);
                transportistaNombre = transportista.getNombre();
            }
            reserva.generarMovimientos(
                fecha,
                asignaciones,
                transportista, 
                transportistaNombre, 
                chofer, 
                matricula, 
                observaciones
            );
        } catch (Exception ex) {
            AppExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    
    
}