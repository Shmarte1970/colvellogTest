package es.zarca.covellog.interfaces.web.empresas.model;

import es.zarca.covellog.interfaces.facade.empresas.facade.dto.EmpresaDto;
import es.zarca.covellog.interfaces.web.empresas.controller.ModificarEmpresaEstado;

/**
 *
 * @author francisco
 */
public class ModificarEmpresaModel {
    private Integer empresaId;
    //private String tabId;
    private EmpresaDto empresa;
    private Boolean mostrarTabEmpresa = false;
    private Boolean mostrarTabContactos = false;
    private Boolean mostrarTabDireccionesEnvio = false;
    private Boolean mostrarTabPotencial = false;
    private Boolean mostrarTabCliente = false;
    private Boolean mostrarTabProveedor = false;
    private ModificarEmpresaEstado estado = ModificarEmpresaEstado.LISTADO;
    private ModificarEmpresaEstado estadoInicial = ModificarEmpresaEstado.LISTADO;
    private Integer tabIndex = 0;
    private String tipoListado;
    private Boolean esUrlWithParameters;
    
    
    public Integer getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(Integer empresaId) {
        this.empresaId = empresaId;
    }

   /* public String getTabId() {
        return tabId;
    }

    public void setTabId(String tabId) {
        this.tabId = tabId;
    }*/

    public EmpresaDto getEmpresa() {
        return empresa;
    }

    public void setEmpresa(EmpresaDto empresa) {
        this.empresa = empresa;
    }

    public Boolean getMostrarTabEmpresa() {
        return mostrarTabEmpresa;
    }

    public void setMostrarTabEmpresa(Boolean mostrarTabEmpresa) {
        this.mostrarTabEmpresa = mostrarTabEmpresa;
    }

    public Boolean getMostrarTabContactos() {
        return mostrarTabContactos;
    }

    public void setMostrarTabContactos(Boolean mostrarTabContactos) {
        this.mostrarTabContactos = mostrarTabContactos;
    }

    public Boolean getMostrarTabDireccionesEnvio() {
        return mostrarTabDireccionesEnvio;
    }

    public void setMostrarTabDireccionesEnvio(Boolean mostrarTabDireccionesEnvio) {
        this.mostrarTabDireccionesEnvio = mostrarTabDireccionesEnvio;
    }

    public Boolean getMostrarTabPotencial() {
        return mostrarTabPotencial;
    }

    public void setMostrarTabPotencial(Boolean mostrarTabPotencial) {
        this.mostrarTabPotencial = mostrarTabPotencial;
    }

    public Boolean getMostrarTabCliente() {
        return mostrarTabCliente;
    }

    public void setMostrarTabCliente(Boolean mostrarTabCliente) {
        this.mostrarTabCliente = mostrarTabCliente;
    }

    public Boolean getMostrarTabProveedor() {
        return mostrarTabProveedor;
    }

    public void setMostrarTabProveedor(Boolean mostrarTabProveedor) {
        this.mostrarTabProveedor = mostrarTabProveedor;
    }

    public ModificarEmpresaEstado getEstado() {
        return estado;
    }

    public void setEstado(ModificarEmpresaEstado estado) {
        this.estado = estado;
    }

    public ModificarEmpresaEstado getEstadoInicial() {
        return estadoInicial;
    }

    public void setEstadoInicial(ModificarEmpresaEstado estadoInicial) {
        this.estadoInicial = estadoInicial;
    }
    
    public Integer getTabIndex() {
        return tabIndex;
    }

    public void setTabIndex(Integer tabIndex) {
        System.err.println("setTabIndex: " + tabIndex.toString());
        this.tabIndex = tabIndex;
    }
    
    public String getPotencialTabClass() {
        if ((empresa == null) || (empresa.getPotencial() == null) || (empresa.getPotencial().getFechaBloqueo() == null)) {
            return "ui-message-error";
        } else {
            return "ui-message-error";
        }
    }

    public String getTipoListado() {
        return tipoListado;
    }

    public void setTipoListado(String tipoListado) {
        this.tipoListado = tipoListado;
    }

    public Boolean getEsUrlWithParameters() {
        return esUrlWithParameters;
    }
    
    public void setEsUrlWithParameters(Boolean esUrlWithParameterws) {
        this.esUrlWithParameters = esUrlWithParameterws;
    }
    
}