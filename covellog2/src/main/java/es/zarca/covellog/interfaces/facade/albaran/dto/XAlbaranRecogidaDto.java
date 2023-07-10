package es.zarca.covellog.interfaces.facade.albaran.dto;

import es.zarca.covellog.interfaces.facade.adreces.facade.dto.DireccionDto;
import es.zarca.covellog.interfaces.facade.almacen.dto.AlmacenDto;
import es.zarca.covellog.interfaces.facade.empresas.facade.dto.DobleObservacionDto;
import java.util.Date;
import java.util.List;

/**
 *
 * @author francisco
 */
public class XAlbaranRecogidaDto {
    private Integer id;
    private String codigoAlbaran;
    private Date fecha;
    private InfoTransporteDto transporte;
    private AlmacenDto almacen;
    private DireccionDto direccionEnvio;
    private String textoAviso;
    private DobleObservacionDto observaciones;
    private List<AlbaranLineaDto> lineas;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodigoAlbaran() {
        return codigoAlbaran;
    }

    public void setCodigoAlbaran(String codigoAlbaran) {
        this.codigoAlbaran = codigoAlbaran;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public InfoTransporteDto getTransporte() {
        return transporte;
    }

    public void setTransporte(InfoTransporteDto transporte) {
        this.transporte = transporte;
    }

    public AlmacenDto getAlmacen() {
        return almacen;
    }

    public void setAlmacen(AlmacenDto almacen) {
        this.almacen = almacen;
    }

    public DireccionDto getDireccionEnvio() {
        return direccionEnvio;
    }

    public void setDireccionEnvio(DireccionDto direccionEnvio) {
        this.direccionEnvio = direccionEnvio;
    }

    public String getTextoAviso() {
        return textoAviso;
    }

    public void setTextoAviso(String textoAviso) {
        this.textoAviso = textoAviso;
    }

    public DobleObservacionDto getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(DobleObservacionDto observaciones) {
        this.observaciones = observaciones;
    }

    public List<AlbaranLineaDto> getLineas() {
        return lineas;
    }

    public void setLineas(List<AlbaranLineaDto> lineas) {
        this.lineas = lineas;
    }
   
}
