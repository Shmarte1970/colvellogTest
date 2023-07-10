package es.zarca.covellog.interfaces.web.clients.controller;

import es.zarca.covellog.interfaces.web.helpers.JsfUtil;
import es.zarca.covellog.interfaces.facade.clients.facade.dto.ClientDTO;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
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
import es.zarca.covellog.interfaces.facade.clients.facade.ClientServiceFacade;
import es.zarca.covellog.interfaces.facade.clients.facade.form.ClientForm;
import es.zarca.covellog.interfaces.web.helpers.OrdreAssembler;
import es.zarca.covellog.interfaces.web.helpers.Textos;

@Named("gestioClientController")
@SessionScoped
public class GestioClientController implements Serializable {
    private static final Logger LOGGER = Logger.getLogger(GestioClientController.class.getName());
    private static final Textos TEXTOS = Textos.getTextos("Clients");
    
    //@EJB
    @Inject
    private ClientServiceFacade ejbFacade;
    private ClientDTO selected;
    private ClientForm formulari;
    private ClientLazyDataModel items;

    public class ClientLazyDataModel extends LazyDataModel<ClientDTO> {
        private List<ClientDTO> list;
        
        public ClientLazyDataModel(){
            this.setRowCount(getFacade().llistarClientsTotalCount());
        }
        
        /*@Override
        public List<ClientDTO> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
            list = getFacade().llistarClients(first, pageSize, OrdreAssembler.fromSortOrder(sortField, sortOrder), filters);
            this.setRowCount(getFacade().llistarClientsTotalCount(filters));
            return list;
        }*/
        
        @Override
	public Object getRowKey(ClientDTO client) {
            return client.getId();
	}

	@Override
	public ClientDTO getRowData(String rowKey) {
            for (ClientDTO client : list) {
                if (client.getId().equals(Integer.parseInt(rowKey))) return client;
            }
            return null;
        }
    }
    
    public GestioClientController() {
    }

    public ClientDTO getSelected() {
        return selected;
    }

    public void setSelected(ClientDTO selected) {
        this.selected = selected;
    }

    public ClientForm getFormulari() {
        return formulari;
    }

    public void setFormulari(ClientForm formulari) {
        this.formulari = formulari;
    }

    private ClientServiceFacade getFacade() {
        return ejbFacade;
    }

    public ClientDTO prepareCreate() {
        selected = new ClientDTO();
        formulari = new ClientForm();
        return selected;
    }
    
    public void prepareUpdate() {
        formulari =  getFacade().buscarPerId(selected.getId());
        if (formulari == null) {
            JsfUtil.addErrorMessage(TEXTOS.getText("ClientNotExists"));
        }
    }
    
    public void cancelCreate() {
        LOGGER.log(Level.SEVERE, "CANCEL CREATE");
        selected = null;
    }

    public void create() {
        LOGGER.log(Level.SEVERE, "CREATE");
        if (selected != null) {
            try {
                getFacade().altaClient(formulari);
                JsfUtil.addSuccessMessage(TEXTOS.getText("ClientCreated"));
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if ((msg != null) && (msg.length() > 0)) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, TEXTOS.getText("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                LOGGER.log(Level.SEVERE, ex.getMessage());
                JsfUtil.addErrorMessage(ex, TEXTOS.getText("PersistenceErrorOccured"));
            }
        }
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        if (selected != null) {
            try {
                getFacade().modificarClient(formulari);
                JsfUtil.addSuccessMessage(TEXTOS.getText("ClientUpdated")); 
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if ((msg != null) && (msg.length() > 0)) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, TEXTOS.getText("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                JsfUtil.addErrorMessage(ex, TEXTOS.getText("PersistenceErrorOccured"));
            }
        }
    }

    public void destroy() {
        if (selected != null) {
            try {
                getFacade().baixaClient(formulari);
                JsfUtil.addSuccessMessage(TEXTOS.getText("ClientDeleted")); 
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if ((msg != null) && (msg.length() > 0)) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, TEXTOS.getText("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                JsfUtil.addErrorMessage(ex, TEXTOS.getText("PersistenceErrorOccured"));
            }
        }
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public ClientForm getClient(java.lang.Integer id) {
        return getFacade().buscarPerId(id);
    }
    
    public ClientLazyDataModel getItems(){
        if (items == null) {
            items = new ClientLazyDataModel();
        }  
        return items;
    }

    @FacesConverter(forClass = ClientDTO.class)
    public static class GestioClientControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            GestioClientController controller = (GestioClientController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "gestioClientController");
            return controller.getClient(getKey(value));
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
            if (object instanceof ClientDTO) {
                ClientDTO o = (ClientDTO) object;
                return getStringKey(o.getId());
            } else {
                return null;
            }
        }

    }

}