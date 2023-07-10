package es.zarca.covellog.application.almacenes.form;

import java.util.Date;
import java.util.List;

/**
 *
 * @author francisco
 */
public class SalidaAlmacenForm {
    private List<Integer> stockIds;
    private Date fecha;
    private Integer clienteId;
    private Integer transportistaId;
    private String transportistaNombre;
    private String chofer;
    private String matricula;
    private String observaciones;

    public List<Integer> getStockIds() {
        return stockIds;
    }

    public void setStockIds(List<Integer> stockIds) {
        this.stockIds = stockIds;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Integer getClienteId() {
        return clienteId;
    }

    public void setClienteId(Integer clienteId) {
        this.clienteId = clienteId;
    }

    public Integer getTransportistaId() {
        return transportistaId;
    }

    public void setTransportistaId(Integer transportistaId) {
        this.transportistaId = transportistaId;
    }

    public String getTransportistaNombre() {
        return transportistaNombre;
    }

    public void setTransportistaNombre(String transportistaNombre) {
        this.transportistaNombre = transportistaNombre;
    }

    public String getChofer() {
        return chofer;
    }

    public void setChofer(String chofer) {
        this.chofer = chofer;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    


}
