package es.zarca.covellog.application.productos.familias;

import es.zarca.covellog.application.productos.familias.form.ModificarFamiliaProductoForm;
import es.zarca.covellog.application.productos.familias.form.NuevaFamiliaProductoForm;
import es.zarca.covellog.domain.model.producto.FamiliaProducto;

/**
 *
 * @author francisco
 */
public interface GestionFamiliasProductosService {
    FamiliaProducto nueva(NuevaFamiliaProductoForm  form);
    FamiliaProducto modificar(int id, ModificarFamiliaProductoForm form);
    FamiliaProducto baja(int id);
    FamiliaProducto anularBaja(int id);
}
