package es.zarca.covellog.application.productos.familias.form;

import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.Size;

/**
 *
 * @author francisco
 */
public class NuevoTipoProductoForm {
    @Size(min = 3, max = 50)
    private String id;
    @Size(max = 250)
    private String descripcion;
    private Integer familiaId;
    private String unidadMedidaId;
    private String claseId;
    private List<TipoProductoDocumentoForm> documentos = new ArrayList();

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

    public List<TipoProductoDocumentoForm> getDocumentos() {
        return documentos;
    }

    public void setDocumentos(List<TipoProductoDocumentoForm> documentos) {
        this.documentos = documentos;
    }

}