package es.zarca.covellog.application.contratos;

import es.zarca.covellog.application.contratos.form.ContratoCondicionesForm;
import es.zarca.covellog.application.contratos.form.ContratoFacturacionForm;
import es.zarca.covellog.application.contratos.form.ContratoLineaForm;
import es.zarca.covellog.application.contratos.form.ContratoPersonasForm;
import es.zarca.covellog.application.contratos.form.ModificarContratoGeneralForm;
import es.zarca.covellog.domain.model.contrato.Contrato;
import es.zarca.covellog.domain.model.contrato.ContratoLineaRO;
import es.zarca.covellog.interfaces.facade.empresas.facade.dto.FacturarPorDto;
import es.zarca.covellog.interfaces.facade.empresas.facade.dto.TipoFacturacionDto;
import java.util.Date;
import java.util.List;

/**
 *
 * @author francisco
 */
public interface GestionContratoService {
    void crear(Date fechaContrato, Integer clienteId);

    Contrato modificarGeneral(Integer id, ModificarContratoGeneralForm form);
    Contrato modificarDatosFacturacion(Integer id, ContratoFacturacionForm form);
    Contrato modificarCondiciones(Integer id, ContratoCondicionesForm form);
    Contrato modificarPersonas(Integer id, ContratoPersonasForm form);
    List<TipoFacturacionDto> geTiposFacturacionPosibles();
    List<FacturarPorDto> getFacturarPorPosibles();
    void addDocumento(Integer contratoId, String fileName, byte[] contents);
    void removeDocumento(Integer contratoId, Integer documentoId);
    void removeDocumentos(Integer contratoId, List<Integer> documentoIds);
    void bajarDocumentos(Integer contratoId, List<Integer> documentoIds);
    void subirDocumentos(Integer contratoId, List<Integer> documentoIds);

    Contrato modificarLinea(Integer contratoId, Integer lineaId, ContratoLineaForm form);
    ContratoLineaRO crearLinea(Integer contratoId, ContratoLineaForm form);
    
    public void modificarFechaEntregaPrevista(Integer contratoId, List<Integer> lineasContratoIds, Date fecha);
    
    void entregarProductos(Integer contratoId, List<Integer> ids, Date fecha);
    void modificarFechaEntrega(Integer contratoId, List<Integer> ids, Date fecha);
    void cancelarEntregaProductos(Integer contratoId, List<Integer> ids);
    
    void solicitarRecogidaProductos(Integer contratoId, List<Integer> ids, Date fecha);    
    void cancelarSolicitudRecogidaProductos(Integer contratoId, List<Integer> ids);
    
    void recogerProductos(Integer contratoId, List<Integer> ids, Date fecha);
    void modificarFechaRecogida(Integer contratoId, List<Integer> ids, Date fecha);
    void cancelarRecogidaProductos(Integer contratoId, List<Integer> ids);
    
    void eliminarLinea(Integer contratoId, List<Integer> ids);
    void anadirLineasContratoAAlbaranEntrega(Integer contratoId, List<Integer> lineasContratoIds, Integer albaranId);
    void anadirLineasContratoAAlbaranRecogida(Integer contratoId, List<Integer> lineasContratoIds, Integer albaranId);
    void crearAlbaranEntrega(Integer contratoId, List<Integer> lineasContratoIds);
    void eliminarAlbaran(Integer albaranId);

    public void crearAlbaranRecogida(Integer contratoId, List<Integer> lineasContratoIds);

    public void cambiarCliente(Integer contratoId, Integer nuevoClienteId);
    public Contrato traspasar(Integer contratoId, Integer nuevoClienteId, Date fecha);
    public Contrato copiarContrato(Integer contratoId);

    public void modificarEstadoPago(Integer contratoId, String estadoPago);

    

    
    
}