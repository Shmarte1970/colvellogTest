package es.zarca.covellog.interfaces.web.crm.algoritmereparticiooportunitats.controller;

import es.zarca.covellog.domain.model.comercials.comercial.Comercial;
import es.zarca.covellog.domain.model.crm.repartidoroportunitats.ReglaReparticioOportunitats;
import es.zarca.covellog.interfaces.web.helpers.JsfUtil;
import es.zarca.covellog.interfaces.facade.crm.peticionscontacte.facade.AlgoritmeReparticioOportunitatsServiceFacade;
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

@Named("algoritmeReparticioOportunitatsController")
@SessionScoped
public class AlgoritmeReparticioOportunitatsController implements Serializable {

    private static final Logger logger = Logger.getLogger(AlgoritmeReparticioOportunitatsController.class.getName());
    
    //@EJB
    @Inject
    private AlgoritmeReparticioOportunitatsServiceFacade ejbFacade;
    private ReglaReparticioOportunitats selected;
    private ReglaReparticioOportunitatsLazyDataModel items;

    public class ReglaReparticioOportunitatsLazyDataModel extends LazyDataModel<ReglaReparticioOportunitats> {
        private List<ReglaReparticioOportunitats> list;
        
        public ReglaReparticioOportunitatsLazyDataModel(){
            logger.log(Level.INFO, "{0}.ReglaReparticioOportunitatsLazyDataModel() => Es crea el ReglaReparticioOportunitatsLazyDataModel", getClass().getName());
            this.setRowCount(getFacade().llistarReglesTotalCount());
        }
        
        @Override
        public List<ReglaReparticioOportunitats> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
            logger.log(Level.INFO, "{0}.load({1},{2})", new Object[]{getClass().getName(), String.valueOf(first), String.valueOf(pageSize)});
            list = getFacade().llistarRegles(first, pageSize, OrdreAssembler.fromSortOrder(sortField, sortOrder), filters);
            this.setRowCount(getFacade().llistarReglesTotalCount(filters));
            return list;
        }
        
        @Override
	public Object getRowKey(ReglaReparticioOportunitats poblacio) {
            return poblacio.getId();
	}

	@Override
	public ReglaReparticioOportunitats getRowData(String rowKey) {
            for (ReglaReparticioOportunitats poblacio : list) {
                if (poblacio.getId().equals(Integer.parseInt(rowKey))) return poblacio;
            }
            return null;
        }
    }
    
    public AlgoritmeReparticioOportunitatsController() {
    }

    public ReglaReparticioOportunitats getSelected() {
        return selected;
    }

    public void setSelected(ReglaReparticioOportunitats selected) {
//        logger.log(Level.INFO, "SELECTED: {0}", selected.getNom());
        this.selected = selected;        
    }

    private AlgoritmeReparticioOportunitatsServiceFacade getFacade() {
        return ejbFacade;
    }

    public ReglaReparticioOportunitats prepareCreate() {
        System.out.println("prepareCreateprepareCreateprepareCreateprepareCreateprepareCreate");
        selected = new ReglaReparticioOportunitats();
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
                
                System.out.println("11111111 " + selected);
                System.out.println("11111111 " + selected.getComercial());
                getFacade().altaRegla(selected);
                JsfUtil.addSuccessMessage("Regla creada correctamente");
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
                getFacade().editarRegla(selected);
                JsfUtil.addSuccessMessage("Regla modificada correctamente");
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
                getFacade().baixaRegla(selected);
                JsfUtil.addSuccessMessage("Regla eliminada correctamente");
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

    public ReglaReparticioOportunitats getReglaReparticioOportunitats(java.lang.Integer id) {
        return getFacade().buscarPerId(id);
    }
    
    public Comercial getComercial(java.lang.Integer id) {
        return getFacade().getComercialsPosibles().get(id-1);
    }
    
    public ReglaReparticioOportunitatsLazyDataModel getItems(){
        if (items == null) {
            items = new ReglaReparticioOportunitatsLazyDataModel();
        }  
        return items;
    }

     
    public List<Comercial> getComercialsPosibles() {
        return getFacade().getComercialsPosibles();
    }
    
    
    @FacesConverter(forClass = ReglaReparticioOportunitats.class)
    public static class AlgoritmeReparticioOportunitatsControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            AlgoritmeReparticioOportunitatsController controller = (AlgoritmeReparticioOportunitatsController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "algoritmeReparticioOportunitatsController");
            return controller.getReglaReparticioOportunitats(getKey(value));
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
            if (object instanceof ReglaReparticioOportunitats) {
                ReglaReparticioOportunitats o = (ReglaReparticioOportunitats) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "OJO ReglaReparticioOportunitats: object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), ReglaReparticioOportunitats.class.getName()});
                return null;
            }
        }

    }
    
    @FacesConverter(forClass = Comercial.class)
    public static class AlgoritmeReparticioOportunitatsController2Converter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            AlgoritmeReparticioOportunitatsController controller = (AlgoritmeReparticioOportunitatsController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "algoritmeReparticioOportunitatsController");
            System.out.println("Converter: " + controller.getComercial(getKey(value)));
            return controller.getComercial(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            System.err.println("KUMKUKU: '"  + value + "'");
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
