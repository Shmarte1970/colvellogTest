package es.zarca.covellog.application.transporte;

import es.zarca.covellog.domain.model.transporte.Transporte;

/**
 *
 * @author francisco
 */
public interface GestionTransportesService {
    Transporte nuevo(Integer empresaId, String nombre, Integer capacidad, String obsInternas, String obsPublicas);
    Transporte modificar(Integer transporteId, Integer empresaId, String nombre, Integer capacidad, String obsInternas, String obsPublicas);
    Transporte baja(Integer transporteId);
    Transporte anularBaja(Integer transporteId);
}
