package es.zarca.covellog.interfaces.web.productos.tipos.model;

/**
 *
 * @author francisco
 */
public class DocumentoListItem {
    private Integer id;
    private String nombre;
    private byte[] datos;

    public DocumentoListItem(Integer id, String nombre, byte[] datos) {
        this.id = id;
        this.nombre = nombre;
        this.datos = datos;
    }

    public DocumentoListItem(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public byte[] getDatos() {
        return datos;
    }

    public void setDatos(byte[] datos) {
        this.datos = datos;
    }
    
}
