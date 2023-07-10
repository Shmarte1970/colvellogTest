package es.zarca.covellog.interfaces.web.empresas.cliente.model;

import es.zarca.covellog.interfaces.facade.empresas.facade.dto.DetalleContratacionDto;
import es.zarca.covellog.interfaces.facade.empresas.facade.dto.FacturarPorDto;
import es.zarca.covellog.interfaces.facade.empresas.facade.dto.TipoFacturacionDto;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author francisco
 */
public class ClienteContratacionModel implements Serializable {
    private Integer empresaId;
    private DetalleContratacionDto detalleContratacion;
    private List<TipoFacturacionDto> tiposFacturacionPosibles;
    private List<FacturarPorDto> facturarPorPosibles;

    public Integer getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(Integer empresaId) {
        this.empresaId = empresaId;
    }

    public DetalleContratacionDto getDetalleContratacion() {
        return detalleContratacion;
    }

    public void setDetalleContratacion(DetalleContratacionDto detalleContratacion) {
        this.detalleContratacion = detalleContratacion;
    }

    public List<TipoFacturacionDto> getTiposFacturacionPosibles() {
        return tiposFacturacionPosibles;
    }

    public void setTiposFacturacionPosibles(List<TipoFacturacionDto> tiposFacturacionPosibles) {
        this.tiposFacturacionPosibles = tiposFacturacionPosibles;
    }

    public List<FacturarPorDto> getFacturarPorPosibles() {
        return facturarPorPosibles;
    }

    public void setFacturarPorPosibles(List<FacturarPorDto> facturarPorPosibles) {
        this.facturarPorPosibles = facturarPorPosibles;
    }
    
}