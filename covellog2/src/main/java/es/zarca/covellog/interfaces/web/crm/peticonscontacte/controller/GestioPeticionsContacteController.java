package es.zarca.covellog.interfaces.web.crm.peticonscontacte.controller;

import es.zarca.covellog.interfaces.facade.crm.peticionscontacte.facade.ImportarPeticionsContacteServiceFacade;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import es.zarca.covellog.interfaces.facade.crm.peticionscontacte.facade.PeticioContacteServiceFacade;
import es.zarca.covellog.interfaces.facade.crm.peticionscontacte.facade.dto.PeticioContacteDTO;
import es.zarca.covellog.interfaces.web.helpers.JsfUtil;
import es.zarca.covellog.interfaces.web.helpers.OrdreAssembler;
import java.math.BigDecimal;
import java.util.Date;
import java.util.ResourceBundle;
import javax.ejb.EJBException;

@Named("gestioPeticionsContacteController")
@SessionScoped
public class GestioPeticionsContacteController implements Serializable {
    private static final Logger logger = Logger.getLogger(GestioPeticionsContacteController.class.getName());
    
    //@EJB
    @Inject
    private PeticioContacteServiceFacade peticioContacteFacade;
    @Inject
    private ImportarPeticionsContacteServiceFacade importarPeticionsContacteServiceFacade;
    private PeticioContacteDTO selected;
    private PeticioContacteLazyDataModel items;
    private boolean convirtiendo = false;

    public class PeticioContacteLazyDataModel extends LazyDataModel<PeticioContacteDTO> {
        private List<PeticioContacteDTO> list;
        
        public PeticioContacteLazyDataModel(){
            
            try {
                this.setRowCount(getFacade().llistarPeticionsContacteTotalCount());
            } catch (Exception e) {
                logger.log(Level.SEVERE, e.getMessage(), getClass().getName());
                JsfUtil.addErrorMessage(e.getMessage());
            }            
        }
        
        @Override
        public List<PeticioContacteDTO> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
            try {
                this.setRowCount(getFacade().llistarPeticionsContacteTotalCount(filters));
                list = getFacade().llistarPeticionsContacte(first, pageSize, OrdreAssembler.fromSortOrder(sortField, sortOrder), filters);
            } catch (Exception e) {
                logger.log(Level.SEVERE, e.getMessage(), getClass().getName());
                JsfUtil.addErrorMessage(e.getMessage());
            }
            return list;
        }
        
        @Override
	public Object getRowKey(PeticioContacteDTO peticioContacte) {
            return peticioContacte.getSubmitTime();
	}

	@Override
	public PeticioContacteDTO getRowData(String rowKey) {
            for (PeticioContacteDTO peticioContacte : list) {
                if (peticioContacte.getSubmitTime().equals(Integer.parseInt(rowKey))) return peticioContacte;
            }
            return null;
        }
    }
    
    public GestioPeticionsContacteController() {
    }

    public PeticioContacteDTO getSelected() {
        return selected;
    }

    public void setSelected(PeticioContacteDTO selected) {
        this.selected = selected;
    }

    private PeticioContacteServiceFacade getFacade() {
        return peticioContacteFacade;
    }

    public PeticioContacteDTO getPeticioContacte(BigDecimal submitTime) {
        return getFacade().buscarPerSubmitTime(submitTime);
    }
    
    public PeticioContacteLazyDataModel getItems(){
        if (items == null) {
            items = new PeticioContacteLazyDataModel();
        }  
        return items;
    }

    public void convertirEnOportunitats(){
        convirtiendo = true;
        try {
                int quantitat = importarPeticionsContacteServiceFacade.importarPeticionsContactePendents();
                if (quantitat > 0) {
                    JsfUtil.addSuccessMessage(String.valueOf(quantitat) + " Peticiones de contacto importadas correctamente.");
                } else {
                    JsfUtil.addWarningMessage("No había nada que importar.");
                }
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if ( msg == null ) {
                    JsfUtil.addErrorMessage(ex.getMessage());
                } else if ((msg != null) && (msg.length() > 0)) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("es.zarca.covellog.interfaces.web.AdrecesBundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("es.zarca.covellog.interfaces.web.AdrecesBundle").getString("PersistenceErrorOccured"));
            }
        
        convirtiendo = false;
    }

    public Date getUltimaPeticioContacteConvertida() {
        return importarPeticionsContacteServiceFacade.getUltimaPeticioContacteConvertida();
    }
    
    public Boolean getConvirtiendo() {
        return convirtiendo;
    }
    
    
    
    @FacesConverter(forClass = PeticioContacteDTO.class)
    public static class GestioPeticioContacteControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            GestioPeticionsContacteController controller = (GestioPeticionsContacteController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "gestioPeticioContacteController");
            return controller.getPeticioContacte(getKey(value));
        }

        BigDecimal getKey(String value) {
            return new BigDecimal(value);
        }

        String getStringKey(BigDecimal value) {
            return value.toPlainString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof PeticioContacteDTO) {
                PeticioContacteDTO o = (PeticioContacteDTO) object;
                return getStringKey(o.getSubmitTime());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "OJO PeticioContacte: object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), PeticioContacteDTO.class.getName()});
                return null;
            }
        }

    }

}
