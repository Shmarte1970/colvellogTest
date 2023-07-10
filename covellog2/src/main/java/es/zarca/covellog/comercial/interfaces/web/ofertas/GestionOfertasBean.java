package es.zarca.covellog.comercial.interfaces.web.ofertas;

import es.zarca.covellog.infrastructure.log.Capa;
import es.zarca.covellog.infrastructure.log.FunctionLog;
import es.zarca.covellog.interfaces.facade.contrato.ContratoFacade;
import es.zarca.covellog.interfaces.facade.contrato.dto.ContratoSmallDto;
import es.zarca.covellog.interfaces.web.contratos.controller.CrearContratoBean;
import es.zarca.covellog.interfaces.web.contratos.controller.ModificarContratoBean;
import es.zarca.covellog.interfaces.web.helpers.ExceptionHandler;
import es.zarca.covellog.interfaces.web.helpers.OrdreAssembler;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
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
@Named("gestionOfertasBean")
@ViewScoped
public class GestionOfertasBean implements Serializable {
    private static final Logger LOGGER = Logger.getLogger(GestionOfertasBean.class.getName());
    @Inject
    private ContratoFacade facade;
    @Inject
    private ModificarContratoBean modificarContratoBean;
    @Inject
    private CrearContratoBean crearContratoBean;
    private List<ContratoSmallDto> selecteds = new ArrayList();
    private ContratoSmallDtoLazyDataModel items;
    private String filtro = "";
    private AccionPanel accionActual = AccionPanel.LISTA;
    private AccionDialogo dialogoActual = AccionDialogo.NONE;
    
    
    public enum AccionPanel {
        LISTA("lista", "Gestion Contratos"),
        CREAR("crear", "Crear Contrato"),
        MODIFICAR("modificar", "Modificar Contrato");

        private final String id;
        private final String titulo;

        private AccionPanel(String id, String titulo) {
            this.id = id;
            this.titulo = titulo;
        }
        
        public String getId() {
            return id;
        }
        
        public String getTitulo() {
            return titulo;
        }
    }
    
    public enum AccionDialogo {
        NONE("NONE", "NONE"),
        CREAR("crear", "Crear");

        private final String id;
        private final String titulo;

        private AccionDialogo(String id, String titulo) {
            this.id = id;
            this.titulo = titulo;
        }
        
        public String getId() {
            return id;
        }
        
        public String getTitulo() {
            return titulo;
        }
    }

    public class ContratoSmallDtoLazyDataModel extends LazyDataModel<ContratoSmallDto> {
        private List<ContratoSmallDto> list;
        
        public ContratoSmallDtoLazyDataModel(){
            this.setRowCount(facade.findTotalCount());
        }
        
        @Override
        public List<ContratoSmallDto> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
          /*  if (!mostrarBajas) {
                filters.put("ocultarBajas", "");
            }*/
            list = facade.find(first, pageSize, OrdreAssembler.fromSortOrder(sortField, sortOrder), filters);
            this.setRowCount(facade.findTotalCount(filters));
            return list;
        }
        
        @Override
	public Object getRowKey(ContratoSmallDto dto) {
            return dto.getId();
	}

	@Override
	public ContratoSmallDto getRowData(String rowKey) {
            if(list == null) {
                return null;
            }
            for (ContratoSmallDto dto : list) {
                if (dto.getId().equals(Integer.parseInt(rowKey))) {
                    return dto;
                }
            }
            return null;
        }
    }

    public ContratoSmallDto getSelected() {
        if ((selecteds != null) && (selecteds.size() > 0)) {
            return selecteds.get(0);
        }
        return null;
    }

    public List<ContratoSmallDto> getSelecteds() {
        return selecteds;
    }

    public void setSelecteds(List<ContratoSmallDto> selecteds) {
        this.selecteds = selecteds;
    }
    
    public ContratoSmallDtoLazyDataModel getItems(){
        if (items == null) {
            items = new ContratoSmallDtoLazyDataModel();
        }  
        return items;
    }

    public void setItems(ContratoSmallDtoLazyDataModel items) {
        this.items = items;
    }
    
    public String getFiltro() {
        return filtro;
    }

    public void setFiltro(String filtro) {
        this.filtro = filtro;
    }

    public String getAccionActual() {
        return accionActual.getId();
    }

    public AccionDialogo getDialogoActual() {
        return dialogoActual;
    }
    
    public void prepareNuevo() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            crearContratoBean.prepare();
            crearContratoBean.setOnGuardarListener((event) -> {
                items = null;
                accionActual = GestionOfertasBean.AccionPanel.LISTA;
                PrimeFaces.current().ajax().update(":mi-layout-contenido");
            });
            crearContratoBean.setOnCancelarListener((event) -> {
                accionActual = GestionOfertasBean.AccionPanel.LISTA;
                PrimeFaces.current().ajax().update(":mi-layout-contenido");
            });
            accionActual = AccionPanel.CREAR;
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    } 
    
    public void prepareModificar() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            context.getApplication().getNavigationHandler().handleNavigation(context, null, "/ofertass/modificar?faces-redirect=true&id=" + selecteds.get(0).getId());
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
}