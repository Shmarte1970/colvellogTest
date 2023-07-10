package es.zarca.covellog.interfaces.facade.transporte.dto;

import es.zarca.covellog.interfaces.facade.empresas.facade.dto.DobleObservacionDto;
import es.zarca.covellog.interfaces.facade.empresas.facade.dto.EmpresaMiniDto;
import java.io.Serializable;

public class TransporteDto implements Serializable {
    private Integer id;
    private EmpresaMiniDto empresa;
    private String nombre;
    private Integer capacidad;
    private DobleObservacionDto observaciones;
    private String fechaBaja;
    

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public EmpresaMiniDto getEmpresa() {
        return empresa;
    }

    public void setEmpresa(EmpresaMiniDto empresa) {
        this.empresa = empresa;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(Integer capacidad) {
        this.capacidad = capacidad;
    }

    public DobleObservacionDto getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(DobleObservacionDto observaciones) {
        this.observaciones = observaciones;
    }

    public String getFechaBaja() {
        return fechaBaja;
    }

    public void setFechaBaja(String fechaBaja) {
        this.fechaBaja = fechaBaja;
    }

}
