package es.zarca.covellog.domain.model.contrato;

/**
 *
 * @author francisco
 */
public enum ContratoTipoOperacion {
    
    ALQUILER("AL", "ALQUILER"),
    VENTA("VE", "VENTA");

    private final String id;
    private final String nombre;

    private ContratoTipoOperacion(String id, String nombre) {
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

    public static ContratoTipoOperacion fromId(String id) {
        switch(id) {
            case "AL":
                return ALQUILER;
            case "VE":
                return VENTA;
            default:
                throw new IllegalArgumentException("Id \"" + id + "\" not defined");
        }
    }

}