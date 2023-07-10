package es.zarca.covellog.application.productos.familias;

import es.zarca.covellog.application.productos.familias.form.ModificarTipoProductoForm;
import es.zarca.covellog.application.productos.familias.form.NuevoTipoProductoForm;
import es.zarca.covellog.domain.model.producto.TipoProducto;

/**
 *
 * @author francisco
 */
public interface GestionTiposProductosService {
    TipoProducto nueva(NuevoTipoProductoForm  form);
    TipoProducto modificar(String id, ModificarTipoProductoForm form);
    TipoProducto baja(String id);
    TipoProducto anularBaja(String id);
}
