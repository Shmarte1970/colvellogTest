package es.zarca.covellog.application.almacenes.internal;

import es.zarca.covellog.application.almacenes.form.AlmacenForm;
import es.zarca.covellog.domain.model.almacen.Almacen;

/**
 *
 * @author francisco
 */
public interface AlmacenesService {
    Almacen nuevo(AlmacenForm  form);
    Almacen modificar(Integer almacenId, AlmacenForm form);
    Almacen baja(Integer almacenId);
    Almacen anularBaja(Integer almacenId);
}