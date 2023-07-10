package es.zarca.covellog.interfaces.facade.stock.dto.historico;

import es.zarca.covellog.interfaces.facade.empresas.facade.dto.EmpresaMiniDto;

/**
 *
 * @author francisco
 */
public class StockHistoricoPropietarioDto extends StockHistoricoDto {
    private EmpresaMiniDto propietario;
    private String tags;

    public EmpresaMiniDto getPropietario() {
        System.err.println("COJONES PROPI 2");
        return propietario;
    }

    public void setPropietario(EmpresaMiniDto propietario) {
        this.propietario = propietario;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

}