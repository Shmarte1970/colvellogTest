package es.zarca.covellog.domain.model.contrato;

/**
 *
 * @author francisco
 */
public interface ContratoDocumentoRO {

    public Integer getId();
    public int getOrden();
    public String getNombre();
    public byte[] getDatos();

}
