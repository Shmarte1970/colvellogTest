package es.zarca.covellog.interfaces.web.app.model;

/**
 *
 * @author francisco
 */
public class FiltroConValor extends Filtro {
    private final Object valor;
    
    public FiltroConValor(String atributo, Operacion operacion, Object valor) {
        super(atributo, operacion);
        this.valor = valor;
    }

    public Object getValor() {
        return valor;
    }

}
