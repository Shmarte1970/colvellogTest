package es.zarca.covellog.interfaces.facade.stock.dto.historico;

import es.zarca.covellog.interfaces.facade.stock.dto.EstadoDto;
import java.util.Date;

/**
 *
 * @author francisco
 */
public class StockHistoricoEstadoDto extends StockHistoricoDto {
    private Date fechaEfectiva;
    private Date fechaFinEfectiva;
    private EstadoDto estado;

    public Date getFechaEfectiva() {
        return fechaEfectiva;
    }

    public void setFechaEfectiva(Date fechaEfectiva) {
        this.fechaEfectiva = fechaEfectiva;
    }

    public Date getFechaFinEfectiva() {
        return fechaFinEfectiva;
    }

    public void setFechaFinEfectiva(Date fechaFinEfectiva) {
        this.fechaFinEfectiva = fechaFinEfectiva;
    }

    public EstadoDto getEstado() {
        return estado;
    }

    public void setEstado(EstadoDto estado) {
        this.estado = estado;
    }
    
}