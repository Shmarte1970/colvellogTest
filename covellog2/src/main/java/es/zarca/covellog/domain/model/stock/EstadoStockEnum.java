package es.zarca.covellog.domain.model.stock;

/**
 *
 * @author francisco
 */
public enum EstadoStockEnum {
    
    ALQUILADO("AL", "ALQUILADO"),
    BAJA("BA", "BAJA"),
    BLOQUEADO("BL", "BLOQUEADO"),
    DISPONIBLE("DI", "DISPONIBLE"),
    RESERVADO_ALQUILER("RA", "RESERVADO ALQUILER"),
    RESERVADO_VENTA("RV", "RESERVADO VENTA"),
    VENDIDO("VE", "VENDIDO");

    private final String id;
    private final String nombre;

    private EstadoStockEnum(String id, String nombre) {
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

    public static EstadoStockEnum fromId(String id) {
        switch(id) {
            case "AL":
                return ALQUILADO;
            case "BA":
                return BAJA;
            case "BL":
                return BLOQUEADO;
            case "DI":
                return DISPONIBLE;
            case "RA":
                return RESERVADO_ALQUILER;
            case "RV":
                return RESERVADO_VENTA;
            case "VE":
                return VENDIDO;
            default:
                throw new IllegalArgumentException("Id \"" + id + "\" not defined");
        }
    }

}