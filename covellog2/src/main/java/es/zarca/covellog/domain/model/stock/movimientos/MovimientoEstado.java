package es.zarca.covellog.domain.model.stock.movimientos;

/**
 *
 * @author francisco
 */
public enum MovimientoEstado {
    
    ANULADO("AN", "ANULADO"),
    BORRADOR("BO", "BORRADOR"),
    FINALIZADO("FI", "FINALIZADO");

    private final String id;
    private final String nombre;

    private MovimientoEstado(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public String toString() {
        return id;
    }

    public static MovimientoEstado fromId(String id) {
        switch(id) {
            case "AN":
                return ANULADO;
            case "BO":
                return BORRADOR;
            case "FI":
                return FINALIZADO;
            default:
                throw new IllegalArgumentException("Id \"" + id + "\" not defined");
        }
    }

}