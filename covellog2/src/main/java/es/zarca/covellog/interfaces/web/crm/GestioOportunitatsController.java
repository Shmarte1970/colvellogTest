package es.zarca.covellog.interfaces.web.crm;

import es.zarca.covellog.domain.model.comercials.comercial.Comercial;
import es.zarca.covellog.domain.model.comercials.divisiocomercial.DivisioComercial;
import es.zarca.covellog.domain.model.crm.oportunitat.EstatOportunitat;
import es.zarca.covellog.domain.model.crm.oportunitat.Oportunitat;
import es.zarca.covellog.domain.model.crm.oportunitat.OrigenOportunitat;
import es.zarca.covellog.interfaces.web.helpers.JsfUtil;
import es.zarca.covellog.interfaces.facade.crm.peticionscontacte.facade.OportunitatServiceFacade;
import es.zarca.covellog.interfaces.web.helpers.OrdreAssembler;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

@Named("gestioOportunitatsController")
@SessionScoped
public class GestioOportunitatsController implements Serializable {

    private static final Logger logger = Logger.getLogger(GestioOportunitatsController.class.getName());
    
    //@EJB
    @Inject
    private OportunitatServiceFacade ejbFacade;
    private Oportunitat selected;
    private OportunitatLazyDataModel items;

    public class OportunitatLazyDataModel extends LazyDataModel<Oportunitat> {
        private List<Oportunitat> list;
        
        public OportunitatLazyDataModel(){
            try {
                this.setRowCount(getFacade().llistarOportunitatsTotalCount());
            } catch (Exception e) {
                logger.log(Level.SEVERE, e.getMessage(), getClass().getName());
                JsfUtil.addErrorMessage(e.getMessage());
            }
        }
        
        @Override
        public List<Oportunitat> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {   
            try {
                list = getFacade().llistarOportunitats(first, pageSize, OrdreAssembler.fromSortOrder(sortField, sortOrder), filters);
                this.setRowCount(getFacade().llistarOportunitatsTotalCount(filters));
            } catch (Exception e) {
                logger.log(Level.SEVERE, e.getMessage(), getClass().getName());
                JsfUtil.addErrorMessage(e.getMessage());
            }
            return list;
        }
        
        @Override
	public Object getRowKey(Oportunitat poblacio) {
            return poblacio.getId();
	}

	@Override
	public Oportunitat getRowData(String rowKey) {
            for (Oportunitat oportunitat : list) {
                if (oportunitat.getId().equals(Integer.parseInt(rowKey))) return oportunitat;
            }
            return null;
        }
    }
    
    public GestioOportunitatsController() {
    }

    public Oportunitat getSelected() {
        return selected;
    }

    public void setSelected(Oportunitat selected) {
//        logger.log(Level.INFO, "SELECTED: {0}", selected.getNom());
        this.selected = selected;        
    }

    private OportunitatServiceFacade getFacade() {
        return ejbFacade;
    }

    public Oportunitat prepareCreate() {
        System.out.println("prepareCreateprepareCreateprepareCreateprepareCreateprepareCreate");
        selected = new Oportunitat();
        //selected.setComercial(new Comercial());        
        return selected;
    }

    public void cancelCreate() {
        System.out.println("cancelCreatecancelCreatecancelCreatecancelCreatecancelCreate");
        selected = null;
    }

    public void create() {
        if (selected != null) {
            try {
                
                getFacade().altaOportunitat(selected);
                JsfUtil.addSuccessMessage("Oportunitat creada correctamente");
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if ((msg != null) && (msg.length() > 0)) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, "Error de persistencia");
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, "Error de persistencia");
            }
        }
        /*if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }*/
    }
        
    public void update() {
        if (selected != null) {
            try {
                getFacade().editarOportunitat(selected);
                JsfUtil.addSuccessMessage("Oportunitat modificada correctamente");
                items = null;    
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if ((msg != null) && (msg.length() > 0)) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, "Error de persistencia");
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, "Error de persistencia");
            }
        }
    }

    public void destroy() {
        if (selected != null) {
            try {
                getFacade().baixaOportunitat(selected);
                JsfUtil.addSuccessMessage("Oportunitat eliminada correctamente");
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if ((msg != null) && (msg.length() > 0)) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, "Error de persistencia");
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, "Error de persistencia");
            }
        }
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public Oportunitat getOportunitat(java.lang.Integer id) {
        return getFacade().buscarPerId(id);
    }
    
    public OportunitatLazyDataModel getItems(){
        if (items == null) {
            items = new OportunitatLazyDataModel();
        }  
        return items;
    }
    
    public List<DivisioComercial> getDivisionsComercialsPosibles() {
        if (selected == null) {
            return new ArrayList();
        }
        return ejbFacade.getDivisionsComercialsPosibles(selected);
    }
    
    public List<EstatOportunitat> getEstatsPosibles() {
        if (selected == null) {
            return new ArrayList();
        }
        return ejbFacade.getEstatsPosibles(selected);
    }
    
    public List<OrigenOportunitat> getOrigensPosibles() {
        if (selected == null) {
            return new ArrayList();
        }
        return ejbFacade.getOrigensPosibles(selected);
    }
    
    public List<Comercial> getComercialsPosibles() {
        if (selected == null) {
            return new ArrayList();
        }
        return ejbFacade.getComercialsPosibles(selected);
    }
    
    public Comercial getComercial(java.lang.Integer id) {
        return getFacade().buscarComercialPerId(id);
    }

    
    @FacesConverter(forClass = Oportunitat.class)
    public static class GestioOportunitatsControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            GestioOportunitatsController controller = (GestioOportunitatsController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "gestioOportunitatsController");
            return controller.getOportunitat(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Oportunitat) {
                Oportunitat o = (Oportunitat) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "OJO Oportunitat: object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Oportunitat.class.getName()});
                return null;
            }
        }

    }
    
    @FacesConverter(forClass = Comercial.class)
    public static class GestioComercialsControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            GestioOportunitatsController controller = (GestioOportunitatsController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "gestioOportunitatsController");
            System.out.println("COMERCIALLLLLLLLLLLLLLLL " + getKey(value));
            return controller.getComercial(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Comercial) {
                Comercial o = (Comercial) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "OJO Comercial: object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Comercial.class.getName()});
                return null;
            }
        }

    }
    

}
