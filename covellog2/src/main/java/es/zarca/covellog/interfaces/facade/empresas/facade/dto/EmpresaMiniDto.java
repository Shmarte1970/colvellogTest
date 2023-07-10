package es.zarca.covellog.interfaces.facade.empresas.facade.dto;

/**
 *
 * @author francisco
 */
public class EmpresaMiniDto {
    private Integer id;
    private String friendlyId;
    private String cif;
    private String nombre;
    private String alias;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFriendlyId() {
        return friendlyId;
    }

    public void setFriendlyId(String friendlyId) {
        this.friendlyId = friendlyId;
    }
    
    public String getCif() {
        return cif;
    }

    public void setCif(String cif) {
        this.cif = cif;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
    
}