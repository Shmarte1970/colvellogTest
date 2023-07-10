package es.zarca.covellog.domain.model.empresa.cliente;

import es.zarca.covellog.domain.model.empresa.cliente.exception.TipoFacturacionNotExistException;

/**
 *
 * @author francisco
 */
public enum TipoFacturacion {
    MES_VENCIDO("MV", "MES VENCIDO"),
    MES_ADELANTADO("MA", "MES POR ADELANTADO");
    
    private final String id;
    private final String nombre;

    private TipoFacturacion(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }
    
    public String getId() { return id; }
    public String getNombre() { return nombre; }

    public static TipoFacturacion parse(String id) {
        for (TipoFacturacion item : TipoFacturacion.values()) {
                if (item.getId().equals(id)) {
                return item;
            }
        }
        throw new TipoFacturacionNotExistException(id);
    }
    
}