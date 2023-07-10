package es.zarca.covellog.application.productos.familias.form;


/**
 *
 * @author francisco
 */

public class TipoProductoDocumentoForm {
    private Integer id;
    private String nombre;
    private byte[] datos;

    public TipoProductoDocumentoForm(Integer id) {
        this.id = id;
    }
    
    public TipoProductoDocumentoForm(Integer id, String nombre, byte[] datos) {
        this.id = id;
        this.nombre = nombre;
        this.datos = datos;
    }

    public TipoProductoDocumentoForm(String nombre, byte[] datos) {
        this.nombre = nombre;
        this.datos = datos;
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