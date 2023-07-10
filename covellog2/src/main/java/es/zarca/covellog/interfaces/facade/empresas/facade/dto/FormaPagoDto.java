package es.zarca.covellog.interfaces.facade.empresas.facade.dto;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author usuario
 */
public class FormaPagoDto {
    
    private List<FormaPagoLineaDto> lineas;

    public FormaPagoDto() {
        lineas = new ArrayList<>();
    }
    
    public FormaPagoDto(List<FormaPagoLineaDto> lineas) {
        this.lineas = lineas;
    }

    public List<FormaPagoLineaDto> getLineas() {
        return lineas;
    }

    public void setLineas(List<FormaPagoLineaDto> lineas) {
        this.lineas = lineas;
    }
    
}
