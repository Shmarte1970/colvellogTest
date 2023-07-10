package es.zarca.covellog.application.albaranes.form;

import es.zarca.covellog.domain.model.stock.Stock;

/**
 *
 * @author francisco
 */
public class AlbaranLineaForm {
    private String referencia;
    private Stock stock;
    private String descripcion;

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
}