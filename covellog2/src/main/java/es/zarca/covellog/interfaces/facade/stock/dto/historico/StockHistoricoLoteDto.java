package es.zarca.covellog.interfaces.facade.stock.dto.historico;

/**
 *
 * @author francisco
 */
public class StockHistoricoLoteDto extends StockHistoricoDto {
    private String lote;

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }
    
}