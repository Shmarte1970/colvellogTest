package es.zarca.covellog.interfaces.web.productos.tipos.controller;

import es.zarca.covellog.interfaces.web.productos.tipos.model.DocumentoListItem;
import es.zarca.covellog.application.productos.familias.form.ModificarTipoProductoForm;
import es.zarca.covellog.application.productos.familias.form.NuevoTipoProductoForm;
import es.zarca.covellog.application.productos.familias.form.TipoProductoDocumentoForm;
import es.zarca.covellog.infrastructure.log.Capa;
import es.zarca.covellog.infrastructure.log.FunctionLog;
import es.zarca.covellog.interfaces.facade.stock.TiposProductosServiceFacade;
import es.zarca.covellog.interfaces.facade.stock.dto.FamiliaProductoDto;
import es.zarca.covellog.interfaces.facade.stock.dto.TipoProductoClaseDto;
import es.zarca.covellog.interfaces.facade.stock.dto.TipoProductoDto;
import es.zarca.covellog.interfaces.facade.stock.dto.UnidadMedidaDto;
import es.zarca.covellog.interfaces.web.exception.PresentationException;
import es.zarca.covellog.interfaces.web.helpers.ExceptionHandler;
import es.zarca.covellog.interfaces.web.helpers.JsfUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.event.ActionListener;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
/**
 *
 * @author francisco
 */
@Named("editarTipoProductoBean")
@ViewScoped
public class EditarTipoProductoBean implements Serializable {
    private static final Logger LOGGER = Logger.getLogger(EditarTipoProductoBean.class.getName());
    @Inject
    private TiposProductosServiceFacade facade;
    private final List<FamiliaProductoDto> familiasPosibles = null;
    
    private ActionListener listener;
    private boolean nuevo = true;
    private String id;
    private String descripcion;
    private Integer familiaId;
    private String unidadMedidaId;
    private String claseId;
    private List<UploadedFile> uploadDocs;
    private List<DocumentoListItem> documentos = new ArrayList<>();
    private List<DocumentoListItem> documentosSeleccionados = new ArrayList<>();
    private Integer numNewDocs = 0;
    
    public EditarTipoProductoBean() {
    }

    public ActionListener getListener() {
        return listener;
    }

    public void setListener(ActionListener listener) {
        this.listener = listener;
    }
    
    public void prepareNuevo() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            nuevo = true;
            id = null;
            descripcion = "";
            familiaId = null;
            claseId = null;
            unidadMedidaId = "";
            documentos = new ArrayList<>();
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    void prepareModificar(String id) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            nuevo=false;
            TipoProductoDto tipoProducto = facade.findById(id);
            this.id = tipoProducto.getId();
            descripcion = tipoProducto.getDescripcion();
            familiaId = tipoProducto.getFamilia().getId();
            claseId = tipoProducto.getClase().getId();
            unidadMedidaId = tipoProducto.getUnidadMedida();
            documentos = new ArrayList<>();
            tipoProducto.getDocumentos().forEach(documento -> {
                documentos.add(new DocumentoListItem(documento.getId(), documento.getNombre()));
            });
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void guardar() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            if (nuevo) {
                guardarNuevo();
            } else {
                guardarModificar();
            }            
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    private void guardarNuevo() {
        NuevoTipoProductoForm form = new NuevoTipoProductoForm();
        form.setId(id);
        form.setDescripcion(descripcion);
        form.setFamiliaId(familiaId);
        form.setClaseId(claseId);
        form.setUnidadMedidaId(unidadMedidaId);
        documentos.forEach(documento -> {
            form.getDocumentos().add(new TipoProductoDocumentoForm(
                documento.getId() > 0 ? documento.getId() : null, 
                documento.getNombre(), 
                documento.getDatos())
            );
        });
        facade.nuevo(form);
        JsfUtil.addSuccessMessage("Tipo producto creado correctamente.");
    }
    
    private void guardarModificar() {
        ModificarTipoProductoForm form = new ModificarTipoProductoForm();        
        form.setDescripcion(descripcion);
        form.setFamiliaId(familiaId);
        form.setClaseId(claseId);
        form.setUnidadMedidaId(unidadMedidaId);
        documentos.forEach(documento -> {
            form.getDocumentos().add(new TipoProductoDocumentoForm(
                documento.getId() > 0 ? documento.getId() : null, 
                documento.getNombre(), 
                documento.getDatos())
            );
        });
        facade.modificar(id, form);
        JsfUtil.addSuccessMessage("Tipo producto modificado correctamente.");
    }
    
    public void cancelar() {
        id = "";
        descripcion = "";
        familiaId = null;
        claseId = null;
        unidadMedidaId = null;
        documentos = new ArrayList<>();
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    } 

    public List<FamiliaProductoDto> getFamiliasPosibles() {
        return facade.getFamiliasPosibles();
    }
    
    public List<UnidadMedidaDto> getUnidadesMedidaPosibles() {
        return facade.getUnidadesMedidaPosibles();
    }
    
    public List<TipoProductoClaseDto> getClasesPosibles() {
        return facade.getClasesPosibles();
    }

    public void upload(FileUploadEvent event) {
        UploadedFile uploadedFile = event.getFile();
        String fileName = uploadedFile.getFileName();
        String contentType = uploadedFile.getContentType();
        byte[] contents = uploadedFile.getContents(); // Or getInputStream()
        System.err.println("Archivo subido: " + fileName);
        JsfUtil.addSuccessMessage("Archivo subido: " + fileName);
        System.err.println("Archivo subido: " + fileName);
        numNewDocs++;
        documentos.add(new DocumentoListItem(-numNewDocs, fileName, contents));
    }
    
    public void assertSomeItemSelected() {
        if ((documentosSeleccionados == null) || (documentosSeleccionados.isEmpty())) {
            throw new PresentationException("Seleccione algun documento");
        }
    }
    
    public void documentacionSubir() {        
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try { 
            assertSomeItemSelected();
            for (DocumentoListItem documento : documentosSeleccionados) {
                Integer index = documentos.indexOf(documento);
                if (index > 0) {
                    DocumentoListItem aux = documentos.get(index - 1);
                    documentos.set(index - 1, documentos.get(index));
                    documentos.set(index, aux);
                } else {
                    JsfUtil.addErrorMessage("El documento \"" + documento.getNombre() + "\" no puede subir mas.");
                }
            }
        } catch (Exception e) {
            JsfUtil.addException(e);
        } finally {
            log.finish();
        }
    } 
     
    public void documentacionBajar() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try { 
            assertSomeItemSelected();
            for (DocumentoListItem documento : documentosSeleccionados) {
                Integer index = documentos.indexOf(documento);
                if (index < documentos.size() - 1) {
                    DocumentoListItem aux = documentos.get(index + 1);
                    documentos.set(index + 1, documentos.get(index));
                    documentos.set(index, aux);
                } else {
                    JsfUtil.addErrorMessage("El documento \"" + documento.getNombre() + "\" no puede bajar mas.");
                }
            }
        } catch (Exception e) {
            JsfUtil.addException(e);
        } finally {
            log.finish();
        }
    }
    
    public void documentacionEliminar() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try { 
            assertSomeItemSelected();
            for (DocumentoListItem documento : documentosSeleccionados) {
                documentos.remove(documento);
            }
        } catch (Exception e) {
            JsfUtil.addException(e);
        } finally {
            log.finish();
        }
    }
    
    public void documentacionVer() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try { 
            assertSomeItemSelected();
            for (DocumentoListItem documento : documentosSeleccionados) {
                //File temp = File.createTempFile(fileName, ".pdf");
                //File testPdfFile = new File("/home/francisco/albara_intern.pdf");
                byte[] datos = documento.getDatos();
                if (datos == null) {
                    datos = facade.getDatosDocumento(id, documento.getId());
                }
                JsfUtil.showFileBufferInNewTab(documento.getNombre(), datos);                
            }
        } catch (Exception e) {
            JsfUtil.addException(e);
        } finally {
            log.finish();
        }
    }

    public List<DocumentoListItem> getDocumentosSeleccionados() {
        return documentosSeleccionados;
    }

    public void setDocumentosSeleccionados(List<DocumentoListItem> documentosSeleccionados) {
        this.documentosSeleccionados = documentosSeleccionados;
    }    

    public boolean isNuevo() {
        return nuevo;
    }

    public void setNuevo(boolean nuevo) {
        this.nuevo = nuevo;
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getFamiliaId() {
        return familiaId;
    }

    public void setFamiliaId(Integer familiaId) {
        this.familiaId = familiaId;
    }

    public String getUnidadMedidaId() {
        return unidadMedidaId;
    }

    public void setUnidadMedidaId(String unidadMedidaId) {
        this.unidadMedidaId = unidadMedidaId;
    }

    public String getClaseId() {
        return claseId;
    }

    public void setClaseId(String claseId) {
        this.claseId = claseId;
    }

    public List<UploadedFile> getUploadDocs() {
        return uploadDocs;
    }

    public void setUploadDocs(List<UploadedFile> uploadDocs) {
        this.uploadDocs = uploadDocs;
    }

    public List<DocumentoListItem> getDocumentos() {
        return documentos;
    }

    public void setDocumentos(List<DocumentoListItem> documentos) {
        this.documentos = documentos;
    }  

    @FacesConverter(value="editarTipoProductoDocumentoListItemConverter")
    public static class SelectorListaDocumentosConverter implements Converter {
        
        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            EditarTipoProductoBean editarTipoProductoBean = (EditarTipoProductoBean) facesContext.getApplication().getELResolver().
                        getValue(facesContext.getELContext(), null, "editarTipoProductoBean");
            for (DocumentoListItem documento : editarTipoProductoBean.getDocumentos()) {
                if (documento.getId().equals(Integer.parseInt(value))) {
                    return documento;
                }
            }
            return null;
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            
            if (object == null) {
                return null;
            }
            if (object instanceof DocumentoListItem) {
                DocumentoListItem o = (DocumentoListItem) object;
                return o.getId().toString();
            } else {
                return null;
            }
        }
    }
}