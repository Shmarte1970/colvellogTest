package es.zarca.covellog.domain.model.stock.reservas;

/**
 *
 * @author francisco
 */
public enum ReservaEstado {
    
    ANULADO("AN", "ANULADO"),
    BORRADOR("BO", "BORRADOR"),
    ACTIVA("AC", "ACTIVA"),
    FINALIZADO("FI", "FINALIZADO");

    private final String id;
    private final String nombre;

    private ReservaEstado(String id, String nombre) {
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

    public static ReservaEstado fromId(String id) {
        switch(id) {
            case "AN":
                return ANULADO;
            case "BO":
                return BORRADOR;
            case "AC":
                return ACTIVA;
            case "FI":
                return FINALIZADO;
            default:
                throw new IllegalArgumentException("Id \"" + id + "\" not defined");
        }
    }

}