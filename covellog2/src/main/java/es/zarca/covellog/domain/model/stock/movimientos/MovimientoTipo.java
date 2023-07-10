package es.zarca.covellog.domain.model.stock.movimientos;

/**
 *
 * @author francisco
 */
public enum MovimientoTipo {
    
    ENTRADA("E", "ENTRADA"),
    SALIDA("S", "SALIDA");

    private final String id;
    private final String nombre;

    private MovimientoTipo(String id, String nombre) {
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

    public static MovimientoTipo fromId(String id) {
        switch(id) {
            case "E":
                return ENTRADA;
            case "S":
                return SALIDA;
            default:
                throw new IllegalArgumentException("Id \"" + id + "\" not defined");
        }
    }

}