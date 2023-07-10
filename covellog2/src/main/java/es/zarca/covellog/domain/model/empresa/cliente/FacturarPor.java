package es.zarca.covellog.domain.model.empresa.cliente;

import es.zarca.covellog.domain.model.empresa.cliente.exception.FacturarPorNotExistException;

/**
 *
 * @author francisco
 */
public enum FacturarPor {
    MES("M"),
    DIA("D");
    
    private final String id;

    private FacturarPor(String id) {
        this.id = id;
    }
    
    public String getId() { return id; }

    public static FacturarPor parse(String id) {
        for (FacturarPor item : FacturarPor.values()) {
                if (item.getId().equals(id)) {
                return item;
            }
        }
        throw new FacturarPorNotExistException(id);
    }
    
}