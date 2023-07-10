package es.zarca.covellog.domain.model.generic;

/**
 *
 * @author francisco
 */
public interface DocumentoRO {

    public Integer getId();
    public int getOrden();
    public String getNombre();
    public byte[] getDatos();

}
