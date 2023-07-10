package es.zarca.covellog.interfaces.facade.stock;

import es.zarca.covellog.domain.model.app.Ordre;
import es.zarca.covellog.interfaces.facade.stock.dto.BookingMiniDatosTransversalesDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author francisco
 */
public interface EntradaAsistidaFacade {
    List<BookingMiniDatosTransversalesDto> findReservasPendientes(int first, int pageSize, Ordre sortOrdre, Map<String, Object> filters);
    int findReservasPendientesTotalCount(Map<String, Object> filters);
    int findReservasPendientesTotalCount();
    
    List<BookingMiniDatosTransversalesDto> findAlbaranesPendientesSinReserva(int first, int pageSize, Ordre sortOrdre, Map<String, Object> filters);
    int findAlbaranesPendientesSinReservaTotalCount(Map<String, Object> filters);
    int findAlbaranesPendientesSinReservaTotalCount();
    
    List<BookingMiniDatosTransversalesDto> findContratosPendientesSinAlbaran(int first, int pageSize, Ordre sortOrdre, Map<String, Object> filters);
    int findContratosPendientesSinAlbaranTotalCount(Map<String, Object> filters);
    int findContratosPendientesSinAlbaranTotalCount();
}