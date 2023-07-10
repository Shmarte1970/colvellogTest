package es.zarca.covellog.interfaces.facade.stock.internal;

import es.zarca.covellog.application.stock.ReservaGestionService;
import es.zarca.covellog.domain.model.albaran.Albaran;
import es.zarca.covellog.domain.model.albaran.AlbaranRepository;
import es.zarca.covellog.domain.model.app.Ordre;
import es.zarca.covellog.domain.model.contrato.Contrato;
import es.zarca.covellog.domain.model.contrato.ContratoLineaRO;
import es.zarca.covellog.domain.model.contrato.ContratoRepository;
import es.zarca.covellog.domain.model.stock.StockRepository;
import es.zarca.covellog.domain.model.stock.reservas.Reserva;
import es.zarca.covellog.domain.model.stock.reservas.ReservaLineaRO;
import es.zarca.covellog.domain.model.stock.reservas.ReservaRepository;
import es.zarca.covellog.infrastructure.log.Capa;
import es.zarca.covellog.infrastructure.log.FunctionLog;
import es.zarca.covellog.interfaces.facade.albaran.internal.assembler.AlbaranMiniDtoAssembler;
import es.zarca.covellog.interfaces.facade.exception.FacadeExceptionHandler;
import es.zarca.covellog.interfaces.facade.stock.EntradaAsistidaFacade;
import es.zarca.covellog.interfaces.facade.stock.dto.BookingMiniDatosTransversalesDto;
import es.zarca.covellog.interfaces.facade.stock.internal.assembler.StockMiniDtoAssembler;
import es.zarca.covellog.interfaces.facade.stock.internal.assembler.TipoProductoAssembler;
import es.zarca.covellog.interfaces.facade.stock.internal.assembler.reservas.ReservaAssembler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import es.zarca.covellog.domain.model.albaran.IAlbaranLinea;
import es.zarca.covellog.interfaces.facade.contrato.internal.assembler.ContratoPagoEstadoDtoAssembler;

/**
 *
 * @author francisco
 */
@ApplicationScoped
public class DefaultEntradaAsistidaFacade implements EntradaAsistidaFacade {
    @Inject
    private ReservaGestionService service;
    @Inject
    private AlbaranRepository albaranRepository;
    @Inject
    private ReservaRepository reservaRepository;
    @Inject
    private ContratoRepository contratoRepository;
    @Inject
    private StockRepository stockRepository;

    @Override
    public List<BookingMiniDatosTransversalesDto> findReservasPendientes(int first, int pageSize, Ordre sortOrdre, Map<String, Object> filters) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try {
            List<BookingMiniDatosTransversalesDto> result = new ArrayList();
            Map<String, Object> filtros = new HashMap();
            filtros.put("activa", true);
            filters.entrySet().forEach(entry -> {
                filtros.put(entry.getKey(), entry.getValue());
            });
            List<Reserva> reservas = reservaRepository.find(first, pageSize, sortOrdre, filtros);
            for (Reserva reserva : reservas) {
                for (ReservaLineaRO linea : reserva.getLineas()) {
                    if (
                        (!filtros.containsKey("numSerie")) || 
                        (linea.getNumSerie() == null) || 
                        ((linea.getNumSerie() != null) && (linea.getNumSerie().contains(filtros.get("numSerie").toString()))))
                    {
                        BookingMiniDatosTransversalesDto booking = new BookingMiniDatosTransversalesDto();
                        booking.setStock(StockMiniDtoAssembler.toDto(linea.getStock()));
                        booking.setBooking(linea.getBooking());
                        booking.setTipoProducto(TipoProductoAssembler.toDto(linea.getTipoProducto()));

                        booking.setLote(linea.getLote());
                        if ((reserva.getAlbaran() != null) && (reserva.getAlbaran().getContrato() != null)) {
                            booking.setContratoFriendlyId(reserva.getAlbaran().getContrato().getFriendlyId());
                            booking.setContratoId(reserva.getAlbaran().getContrato().getId());
                            booking.setPagoEstado(ContratoPagoEstadoDtoAssembler.toDto(reserva.getAlbaran().getContrato().getPagoEstado()));
                        }
                        booking.setAlbaran(AlbaranMiniDtoAssembler.toDto(reserva.getAlbaran()));
                        booking.setReserva(ReservaAssembler.toDto(reserva));
                        result.add(booking);
                    }
                }
            }
            return result;
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);            
        } finally {
            log.finish();
        }
        return null;
    }

    @Override
    public int findReservasPendientesTotalCount(Map<String, Object> filters) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try { 
            Map<String, Object> filtros = new HashMap();
            filtros.put("activa", true);
            filters.entrySet().forEach(entry -> {
                filtros.put(entry.getKey(), entry.getValue());
            });
            return reservaRepository.findTotalCount(filtros);
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
        return -1;
    }
    
    @Override
    public int findReservasPendientesTotalCount() {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try { 
            Map<String, Object> filtros = new HashMap();
            filtros.put("activa", true);
            return reservaRepository.findTotalCount(filtros);
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
        return -1;
    }
    
    @Override
    public List<BookingMiniDatosTransversalesDto> findAlbaranesPendientesSinReserva(int first, int pageSize, Ordre sortOrdre, Map<String, Object> filters) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try {
            Map<String, Object> filtros = new HashMap();
            filtros.put("activo", true);
            filtros.put("sinReserva", true);
            filters.entrySet().forEach(entry -> {
                filtros.put(entry.getKey(), entry.getValue());
            });
            List<BookingMiniDatosTransversalesDto> result = new ArrayList();
            List<Albaran> albaranes = albaranRepository.find(first, pageSize, sortOrdre, filtros);
            for (Albaran albaran : albaranes) {
                for (IAlbaranLinea linea : albaran.getLineas()) {
                    if (linea.getReserva() == null) {
                        if ((!filtros.containsKey("numSerie")) || 
                            (((String)filtros.get("numSerie")).isEmpty()) || 
                            ((linea.getNumSerie() != null) && (linea.getNumSerie().contains(filtros.get("numSerie").toString()))))
                        {
                            BookingMiniDatosTransversalesDto booking = new BookingMiniDatosTransversalesDto();
                            booking.setBooking(linea.getBooking());
                            booking.setTipoProducto(TipoProductoAssembler.toDto(linea.getTipoProducto()));
                            booking.setStock(StockMiniDtoAssembler.toDto(linea.getStock()));                        
                            booking.setLote(linea.getLote());
                            if (albaran.getContrato() != null) {
                                booking.setContratoFriendlyId(albaran.getContrato().getFriendlyId());
                                booking.setContratoId(albaran.getContrato().getId());
                                booking.setPagoEstado(ContratoPagoEstadoDtoAssembler.toDto(albaran.getContrato().getPagoEstado()));
                            }
                            booking.setAlbaran(AlbaranMiniDtoAssembler.toDto(albaran));
                            booking.setReserva(ReservaAssembler.toDto(linea.getReserva()));
                            result.add(booking);
                        }
                    }
                }
            }
            return result;
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);            
        } finally {
            log.finish();
        }
        return null;
    }

    @Override
    public int findAlbaranesPendientesSinReservaTotalCount(Map<String, Object> filters) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try { 
            Map<String, Object> filtros = new HashMap();
            filtros.put("activo", true);
            filtros.put("sinReserva", true);
            filters.entrySet().forEach(entry -> {
                filtros.put(entry.getKey(), entry.getValue());
            });
            return albaranRepository.findTotalCount(filtros);
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
        return -1;
    }
    
    @Override
    public int findAlbaranesPendientesSinReservaTotalCount() {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try { 
            Map<String, Object> filtros = new HashMap();
            filtros.put("activo", true);
            filtros.put("sinReserva", true);
            return reservaRepository.findTotalCount(filtros);
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
        return -1;
    }
    
    @Override
    public List<BookingMiniDatosTransversalesDto> findContratosPendientesSinAlbaran(int first, int pageSize, Ordre sortOrdre, Map<String, Object> filters) {
        FunctionLog log = new FunctionLog(Capa.FACADE);
        try {
            List<BookingMiniDatosTransversalesDto> result = new ArrayList();
            List<Contrato> contratos = contratoRepository.find(first, pageSize, sortOrdre, new HashMap());
            for (Contrato contrato : contratos) {
                for (ContratoLineaRO linea : contrato.getLineas()) {
                    BookingMiniDatosTransversalesDto booking = new BookingMiniDatosTransversalesDto();
                    booking.setFecha(linea.getFechaEntregaPrevista());
                    booking.setBooking(contrato.getFriendlyId() + "-" + linea.getBooking());
                    booking.setTipoProducto(TipoProductoAssembler.toDto(linea.getTipoProducto()));
                    booking.setStock(StockMiniDtoAssembler.toDto(linea.getProducto()));
                    booking.setPagoEstado(ContratoPagoEstadoDtoAssembler.toDto(contrato.getPagoEstado()));
                    booking.setLote(linea.getLote());
                    booking.setContratoFriendlyId(contrato.getFriendlyId());
                    booking.setContratoId(contrato.getId());
                    result.add(booking);
                }
            }
            return result;
        } catch (Exception ex) {
            FacadeExceptionHandler.handleException(ex);            
        } finally {
            log.finish();
        }
        return null;
    }

    @Override
    public int findContratosPendientesSinAlbaranTotalCount(Map<String, Object> filters) {
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
    public int findContratosPendientesSinAlbaranTotalCount() {
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
}