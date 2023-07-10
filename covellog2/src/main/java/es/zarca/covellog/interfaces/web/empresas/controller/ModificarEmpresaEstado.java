package es.zarca.covellog.interfaces.web.empresas.controller;

/**
 *
 * @author francisco
 */
public enum ModificarEmpresaEstado {
    LISTADO("lista"),
    POTENCIALES_LISTADO("potenciales-lista"),
    CLIENTES_LISTADO("clientes-lista"),
    PROVEEDORES_LISTADO("proveedores-lista"),
    CREAR_EMPRESA("empresa-crear"),
    EMPRESA("modificar-pestanas"),
    CONTACTOS("modificar-pestanas"),
    DIRECCIONES_ENVIO("modificar-pestanas"),
    CREAR_POTENCIAL("potencial-crear"),
    POTENCIAL("modificar-pestanas"),
    CONVERTIR_EN_CLIENTE("potencial-convertir-en-cliente"),
    CREAR_CLIENTE("cliente-crear"),
    CLIENTE_GENERAL("modificar-pestanas"),
    CLIENTE_CONTRATACION("modificar-pestanas"),
    CLIENTE_FACTURACION("modificar-pestanas"),
    CREAR_PROVEEDOR("proveedor-crear"),
    PROVEEDOR("modificar-pestanas");
    
    private String url;

    private ModificarEmpresaEstado(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
    
}
