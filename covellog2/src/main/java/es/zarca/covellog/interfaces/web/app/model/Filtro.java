package es.zarca.covellog.interfaces.web.app.model;

/**
 *
 * @author francisco
 */
public class Filtro {
    private final String atributo;
    private final Operacion operacion;

    public Filtro(String atributo, Operacion operacion) {
        this.atributo = atributo;
        this.operacion = operacion;
    }

    public String getAtributo() {
        return atributo;
    }

    public Operacion getOperacion() {
        return operacion;
    }

    @Override
    public String toString() {
        return atributo + ":" + operacion.name();
    }
    
}
