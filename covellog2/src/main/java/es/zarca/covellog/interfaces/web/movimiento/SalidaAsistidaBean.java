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
import java.util.logging.Level;
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
@Named("salidaAsistidaBean")
@ViewScoped
public class SalidaAsistidaBean implements Serializable {
    private static final Logger LOGGER = Logger.getLogger(SalidaAsistidaBean.class.getName());
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
            filters.put("tipo", "S");
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
    
    public SalidaAsistidaBean() {
    }

    public String getFiltro() {
        return filtro;
    }

    public void setFiltro(String filtro) {
        
        if (!this.filtro.equals(filtro)) {
            this.filtro = filtro;
            reservasPendientes = null;
            albaranesPendientesSinReserva = null;
            contratosPendientesSinAlbaran = null;
            LOGGER.log(Level.INFO, "[z] setFiltro {0}", filtro);
        } else {
            LOGGER.log(Level.INFO, "[z] setFiltro MISMO {0}", filtro);
        }
        
    }

    public List<BookingMiniDatosTransversalesDto> getReservasPendientes() {
        if (reservasPendientes == null) {
            Map<String, Object> filters = new HashMap();
            filters.put("tipo", "S");
            filters.put("numSerie", filtro);
            reservasPendientes = facade.findReservasPendientes(0, 30, OrdreAssembler.fromSortOrder("fecha", SortOrder.ASCENDING), filters);
        }
        return reservasPendientes;
    }

    public List<BookingMiniDatosTransversalesDto> getAlbaranesPendientesSinReserva() {
        if (albaranesPendientesSinReserva == null) {
            Map<String, Object> filters = new HashMap();
            filters.put("tipo", "E");
            filters.put("numSerie", filtro);
            System.err.println("COJONES: bien");
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
            filters.put("tipo", "S");
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
            PrimeFaces.current().ajax().update("ReservarBookingAlbaranPopup:ReservarBookingAlbaranPopupDlg");
            PrimeFaces.current().executeScript("PF('ReservarBookingAlbaranPopupDialog').show()");
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
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