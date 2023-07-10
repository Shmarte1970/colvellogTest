package es.zarca.covellog.interfaces.web.albaranes.controller;

import es.zarca.covellog.infrastructure.log.Capa;
import es.zarca.covellog.infrastructure.log.FunctionLog;
import es.zarca.covellog.interfaces.facade.albaran.AlbaranFacade;
import es.zarca.covellog.interfaces.facade.albaran.dto.AlbaranDto;
import es.zarca.covellog.interfaces.facade.albaran.dto.AlbaranSmallDto;
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
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
/**
 *
 * @author francisco
 */
@Named("gestionAlbaranesBean")
@ViewScoped
public class GestionAlbaranesBean implements Serializable {
    private static final Logger LOGGER = Logger.getLogger(GestionAlbaranesBean.class.getName());
    @Inject
    private AlbaranFacade facade;
    @Inject
    private ModificarAlbaranBean modificarAlbaranBean;
    private List<AlbaranSmallDto> selecteds = new ArrayList();
    private AlbaranDtoLazyDataModel items;
    private String filtro = "";
    private AccionPanel accionActual = AccionPanel.LISTA;
    private AccionDialogo dialogoActual = AccionDialogo.NONE;
    
    
    public enum AccionPanel {
        LISTA("lista", "Gestion Albaranes"),
        CREAR("crear", "Crear Albaran"),
        MODIFICAR("modificar", "Modificar Albaran");

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

    public class AlbaranDtoLazyDataModel extends LazyDataModel<AlbaranSmallDto> {
        private List<AlbaranSmallDto> list;
        
        public AlbaranDtoLazyDataModel(){
            this.setRowCount(facade.findTotalCount());
        }
        
        @Override
        public List<AlbaranSmallDto> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
          /*  if (!mostrarBajas) {
                filters.put("ocultarBajas", "");
            }*/
                
            list = facade.find(first, pageSize, OrdreAssembler.fromSortOrder(sortField, sortOrder), filters);
            this.setRowCount(facade.findTotalCount(filters));
            return list;
        }
        
        @Override
	public Object getRowKey(AlbaranSmallDto dto) {
            return dto.getId();
	}

	@Override
	public AlbaranSmallDto getRowData(String rowKey) {
            if(list == null) {
                return null;
            }
            for (AlbaranSmallDto dto : list) {
                if (dto.getId().equals(Integer.parseInt(rowKey))) {
                    return dto;
                }
            }
            return null;
        }
    }

    public AlbaranSmallDto getSelected() {
        if ((selecteds != null) && (selecteds.size() > 0)) {
            return selecteds.get(0);
        }
        return null;
    }

    public List<AlbaranSmallDto> getSelecteds() {
        return selecteds;
    }

    public void setSelecteds(List<AlbaranSmallDto> selecteds) {
        this.selecteds = selecteds;
    }
    
    public AlbaranDtoLazyDataModel getItems(){
        if (items == null) {
            items = new AlbaranDtoLazyDataModel();
        }  
        return items;
    }

    public void setItems(AlbaranDtoLazyDataModel items) {
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
    
    public void prepareModificar() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            context.getApplication().getNavigationHandler().handleNavigation(context, null, "/albaranes/modificar?faces-redirect=true&id=" + selecteds.get(0).getId());
            /*modificarAlbaranBean.prepare(selecteds);            
            accionActual = AccionPanel.MODIFICAR;*/
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
}