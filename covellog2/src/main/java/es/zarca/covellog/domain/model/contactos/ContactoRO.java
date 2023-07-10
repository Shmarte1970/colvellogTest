package es.zarca.covellog.domain.model.contactos;

import es.zarca.covellog.domain.model.empresa.CanalesContacto;
import es.zarca.covellog.domain.model.idiomas.idioma.Idioma;

/**
 *
 * @author francisco
 */
public interface ContactoRO {

    public Integer getId();
    public String getNombre();
    public String getApellidos();
    public String getNombreCompleto();
    public CanalesContacto getCanalesContacto();
    public String getDescripcion();
    public Idioma getIdioma();
    public String getObservaciones();
    public String getHorario();
    
}
