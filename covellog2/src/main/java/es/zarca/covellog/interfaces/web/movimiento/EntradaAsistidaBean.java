package es.zarca.covellog.interfaces.web.movimiento;
import es.zarca.covellog.infrastructure.log.Capa;
import es.zarca.covellog.infrastructure.log.FunctionLog;
import es.zarca.covellog.interfaces.facade.stock.EntradaAsistidaFacade;
import es.zarca.covellog.interfaces.facade.stock.dto.BookingMiniDatosTransversalesDto;
import es.zarca.covellog.interfaces.web.helpers.ExceptionHandler;
import es.zarca.covellog.interfaces.web.helpers.JsfUtil;
import es.zarca.covellog.interfaces.web.helpers.OrdreAssembler;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.PrimeFaces;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
/**
 *
 * @author francisco
 */
@Named("entradaAsistidaBean")
@ViewScoped
public class EntradaAsistidaBean implements Serializable {
    private static final Logger LOGGER = Logger.getLogger(EntradaAsistidaBean.class.getName());
    @Inject
    private EntradaAsistidaFacade facade;
    @Inject
    private EntregarBookingReservaBean entregarBookingReservaBean;
    @Inject
    private ReservarBookingAlbaranBean reservarBookingAlbaranBean;
    @Inject
    private EntregarBookingAlbaranBean entregarBookingAlbaranBean;
    
    private String filtro = "";
    private List<BookingMiniDatosTransversalesDto> reservasPendientes;
    //private ReservasPendientesDtoLazyDataModel reservasPendientes;
    private List<BookingMiniDatosTransversalesDto> albaranesPendientesSinReserva;
    private List<BookingMiniDatosTransversalesDto> contratosPendientesSinAlbaran;
    
    public class ReservasPendientesDtoLazyDataModel extends LazyDataModel<BookingMiniDatosTransversalesDto> {
        private List<BookingMiniDatosTransversalesDto> list;
        
        public ReservasPendientesDtoLazyDataModel(){
            this.setRowCount(facade.findReservasPendientesTotalCount());
        }
        
        @Override
        public List<BookingMiniDatosTransversalesDto> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
            filters.put("ocultarAnulados", "");
            filters.put("tipo", "E");
            list = facade.findReservasPendientes(first, pageSize, OrdreAssembler.fromSortOrder(sortField, sortOrder), filters);
            this.setRowCount(facade.findReservasPendientesTotalCount(filters));
            return list;
        }
        
        @Override
	public Object getRowKey(BookingMiniDatosTransversalesDto dto) {
            return dto.getBooking();
	}

	@Override
	public BookingMiniDatosTransversalesDto getRowData(String rowKey) {
            for (BookingMiniDatosTransversalesDto dto : list) {
                if (dto.getBooking().equals(rowKey)) return dto;
            }
            return null;
        }
    }
    
    public EntradaAsistidaBean() {
    }

    public String getFiltro() {
        return filtro;
    }

    public void setFiltro(String filtro) {
        this.filtro = filtro;
    }

    public List<BookingMiniDatosTransversalesDto> getReservasPendientes() {
        if (reservasPendientes == null) {
            Map<String, Object> filters = new HashMap();
            filters.put("tipo", "E");
            reservasPendientes = facade.findReservasPendientes(0, 30, OrdreAssembler.fromSortOrder("fecha", SortOrder.ASCENDING), filters);
        }
        return reservasPendientes;
    }

    public List<BookingMiniDatosTransversalesDto> getAlbaranesPendientesSinReserva() {
        if (albaranesPendientesSinReserva == null) {
            Map<String, Object> filters = new HashMap();
            filters.put("tipo", "R");
            filters.put("numSerie", filtro);
            albaranesPendientesSinReserva = facade.findAlbaranesPendientesSinReserva(0, 30, OrdreAssembler.fromSortOrder("fecha", SortOrder.ASCENDING), filters);
        }
        return albaranesPendientesSinReserva;
    }

    public void setAlbaranesPendientesSinReserva(List<BookingMiniDatosTransversalesDto> albaranesPendientesSinReserva) {
        this.albaranesPendientesSinReserva = albaranesPendientesSinReserva;
    }

    public List<BookingMiniDatosTransversalesDto> getContratosPendientesSinAlbaran() {
        if (contratosPendientesSinAlbaran == null) {
            Map<String, Object> filters = new HashMap();
            filters.put("tipo", "E");
            contratosPendientesSinAlbaran = facade.findContratosPendientesSinAlbaran(0, 30, OrdreAssembler.fromSortOrder("fechaContrato", SortOrder.ASCENDING), filters);
        }
        return contratosPendientesSinAlbaran;
    }

    public void setContratosPendientesSinAlbaran(List<BookingMiniDatosTransversalesDto> contratosPendientesSinAlbaran) {
        this.contratosPendientesSinAlbaran = contratosPendientesSinAlbaran;
    }

    public void prepare() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
        } catch (Exception ex) {
            JsfUtil.addException(ex);
        } finally {
            log.finish();
        }
      
    }
    
    public void prepareGenerarMovimientoDesdeReserva(Integer reservaId, String booking) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            entregarBookingReservaBean.prepare(reservaId, booking);
            entregarBookingReservaBean.setOnGuardarListener((event) -> {
                reservasPendientes = null;
                albaranesPendientesSinReserva = null;
                contratosPendientesSinAlbaran = null;
            });
            PrimeFaces.current().ajax().update("@(.EntregarBookingReserva)");
            PrimeFaces.current().executeScript("PF('EntregarBookingReservaPopupDialog').show()");
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void prepareGenerarMovimientoDesdeAlbaran(Integer albaranId, String booking) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            entregarBookingAlbaranBean.prepare(albaranId, booking); 
            entregarBookingAlbaranBean.setOnGuardarListener((event) -> {
                reservasPendientes = null;
                albaranesPendientesSinReserva = null;
                contratosPendientesSinAlbaran = null;
            });
            PrimeFaces.current().ajax().update("@(.EntregarBookingAlbaran)");
            PrimeFaces.current().executeScript("PF('EntregarBookingAlbaranPopupDialog').show()");
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void prepareGenerarReservaDesdeAlbaran(Integer albaranId, String booking) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            reservarBookingAlbaranBean.prepare(albaranId, booking); 
            reservarBookingAlbaranBean.setOnGuardarListener((event) -> {
                reservasPendientes = null;
                albaranesPendientesSinReserva = null;
                contratosPendientesSinAlbaran = null;
            });
            PrimeFaces.current().ajax().update("@(.ReservarBookingAlbaran)");
            PrimeFaces.current().executeScript("PF('ReservarBookingAlbaranPopupDialog').show()");
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
   /*public void prepareGenerarReservaDesdeAlbaran2(Integer albaranId, String booking) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            AlbaranDto albaran = albaranFacade.findById(albaranId);
            ArgumentValidator.isNotNull(albaran, "El albaran no existe.");
            albaranLineaReservarBean.prepare();
            albaranLineaReservarBean.setTransportistaId(albaran.getTransportista() == null ? null : albaran.getTransportista().getId());
            albaranLineaReservarBean.setTransportistaNombre(albaran.getTransportista() == null ? "Sus medios" : albaran.getTransportista().getNombre());
            albaranLineaReservarBean.setOnGuardarListener((event) -> {
                List<String> bookings = new ArrayList();
                bookings.add(booking);
                Integer reservaId;
                if ("E".equals(albaran.getTipo().getId())) {
                    reservaId = albaranFacade.crearEntreguese(albaran.getId(), 
                        true,
                        albaran.getFecha(),
                        bookings, 
                        albaranLineaReservarBean.getTransportistaId(), 
                        albaranLineaReservarBean.getTransportistaNombre(), 
                        albaranLineaReservarBean.getChofer(), 
                        albaranLineaReservarBean.getMatricula(), 
                        albaranLineaReservarBean.getObservaciones()
                    );
                } else {
                    reservaId = albaranFacade.crearAdmitase(albaran.getId(), 
                        true,
                        albaran.getFecha(),
                        bookings, 
                        albaranLineaReservarBean.getTransportistaId(), 
                        albaranLineaReservarBean.getTransportistaNombre(), 
                        albaranLineaReservarBean.getChofer(), 
                        albaranLineaReservarBean.getMatricula(), 
                        albaranLineaReservarBean.getObservaciones()
                    );
                }
            });            
            PrimeFaces.current().ajax().update("@(.Reservar-EditForm)");
            PrimeFaces.current().executeScript("PF('ReservarPopupDialog').show()");
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }*/
    
    public void guardar(javax.faces.event.ActionEvent event) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
        } catch (Exception ex) {
            JsfUtil.addException(ex);
        } finally {
            log.finish();
        }
    }
    
    @FacesConverter(value="EntradaAsistidaItemConverter")
    public static class MovimientoLineaConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
           /* EntradaAsistidaBean bean = (EntradaAsistidaBean) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "entradaAsistidaBean");
            for (MovimientoLineaDto linea : bean.getMovimiento().getLineas()) {
                if (Objects.equals(String.valueOf(linea.getId()), value)) {
                    return linea;
                }
            }*/
            return null;
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
           /* if (object instanceof MovimientoLineaDto) {
                MovimientoLineaDto f = (MovimientoLineaDto) object;
                return f.getId().toString();
            } else {
                return null;
            }*/
            return null;
        }
    }
}