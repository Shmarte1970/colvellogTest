package es.zarca.covellog.interfaces.facade.stock.dto;

import java.util.List;

/**
 *
 * @author francisco
 */
public class TipoProductoDto {
    private String id;
    private String descripcion;
    private FamiliaProductoDto familia;
    private TipoProductoClaseDto clase;
    private String unidadMedida;
    private List<DocumentoDto> documentos;
    private String fechaAlta;
    private String fechaBaja;

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

    public FamiliaProductoDto getFamilia() {
        return familia;
    }

    public void setFamilia(FamiliaProductoDto familia) {
        this.familia = familia;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public TipoProductoClaseDto getClase() {
        return clase;
    }

    public void setClase(TipoProductoClaseDto clase) {
        this.clase = clase;
    }

    public List<DocumentoDto> getDocumentos() {
        return documentos;
    }

    public void setDocumentos(List<DocumentoDto> documentos) {
        this.documentos = documentos;
    }

    public String getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(String fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public String getFechaBaja() {
        return fechaBaja;
    }

    public void setFechaBaja(String fechaBaja) {
        this.fechaBaja = fechaBaja;
    }

}
