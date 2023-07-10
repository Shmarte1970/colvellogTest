package es.zarca.covellog.domain.model.stock;

/**
 *
 * @author francisco
 */
public enum CondicionStock {
    
    AVERIADO("DM", "AVERIADO"),
    BAJO_REPARACION("BR", "BAJO REPARACION"),
    OK("OK", "OK"),
    PENDIENTE_PERITAR("WE", "PENDIENTE PERITAR");

    private final String id;
    private final String nombre;

    private CondicionStock(String id, String nombre) {
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

    public static CondicionStock fromId(String id) {
        switch(id) {
            case "DM":
                return AVERIADO;
            case "BR":
                return BAJO_REPARACION;
            case "OK":
                return OK;
            case "WE":
                return PENDIENTE_PERITAR;
            default:
                throw new IllegalArgumentException("Id \"" + id + "\" not defined");
        }
    }

}