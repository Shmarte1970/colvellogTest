package es.zarca.covellog.interfaces.facade.contrato;

import es.zarca.covellog.application.contratos.form.ContratoCondicionesForm;
import es.zarca.covellog.application.contratos.form.ContratoFacturacionForm;
import es.zarca.covellog.application.contratos.form.ContratoLineaForm;
import es.zarca.covellog.application.contratos.form.ContratoPersonasForm;
import es.zarca.covellog.application.contratos.form.ModificarContratoGeneralForm;
import es.zarca.covellog.application.email.form.EmailForm;
import es.zarca.covellog.domain.model.app.Ordre;
import es.zarca.covellog.interfaces.facade.albaran.dto.AlbaranDto;
import es.zarca.covellog.interfaces.facade.contactos.dto.ContactoDto;
import es.zarca.covellog.interfaces.facade.contrato.dto.ContratoDto;
import es.zarca.covellog.interfaces.facade.contrato.dto.ContratoSmallDto;
import es.zarca.covellog.interfaces.facade.contrato.dto.ContratoTipoOperacionDto;
import es.zarca.covellog.interfaces.facade.empresas.facade.dto.EmpresaDto;
import es.zarca.covellog.interfaces.facade.empresas.facade.dto.FacturarPorDto;
import es.zarca.covellog.interfaces.facade.empresas.facade.dto.TipoFacturacionDto;
import es.zarca.covellog.interfaces.facade.stock.dto.IdCantidadDto;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author francisco
 */
public interface ContratoFacade {
    
    List<ContratoSmallDto> find(int first, int pageSize, Ordre sortOrdre, Map<String, Object> filters);
    List<ContratoSmallDto> findAll();
    ContratoDto findById(int id);
    int findTotalCount(Map<String, Object> filters);
    int findTotalCount();
    void crear(Date fechaContrato, Integer clienteId);
    ContratoDto modificarGeneral(Integer id, ModificarContratoGeneralForm form);
    ContratoDto modificarCondiciones(Integer id, ContratoCondicionesForm form);
    ContratoDto modificarDatosFacturacion(Integer id, ContratoFacturacionForm form);
    ContratoDto modificarPersonas(Integer id, ContratoPersonasForm form);
    List<ContratoTipoOperacionDto> getTiposOperacionPosibles();
    List<TipoFacturacionDto> getTiposFacturacionPosibles();
    List<FacturarPorDto> getFacturarPorPosibles();
    List<ContactoDto> getContactosPosibles(int clienteId);
    EmpresaDto buscarEmpresa(Integer id);
    byte[] getDatosDocumento(Integer contratoId, Integer documentoId);
    ContratoDto addDocumento(Integer id, String fileName, byte[] contents);
    ContratoDto removeDocumento(Integer contratoId, Integer documentoId);
    ContratoDto removeDocumentos(Integer contratoId, List<Integer> documentoIds);
    ContratoDto bajarDocumentos(Integer contratoId, List<Integer> documentoIds);
    ContratoDto subirDocumentos(Integer contratoId, List<Integer> documentoIds);
    List<IdCantidadDto> getLotesPosibles(String filtro);
    ContratoDto modificarLinea(Integer contratoId, Integer lineaId, ContratoLineaForm form);
    Integer crearLinea(Integer contratoId, ContratoLineaForm form);
    public void lineaModificarFechaEntregaPrevista(Integer contratoId, List<Integer> lineasContratoIds, Date fecha);
    void entregarProductos(Integer contratoId, List<Integer> ids, Date fecha);
    void modificarFechaEntregaProductos(Integer contratoId, List<Integer> ids, Date fecha);
    void cancelarEntregaProductos(Integer contratoId, List<Integer> ids);
    void solicitarRecogidaProductos(Integer contratoId, List<Integer> ids, Date fecha);
    void cancelarSolicitudRecogidaProductos(Integer contratoId, List<Integer> ids);
    void recogerProductos(Integer contratoId, List<Integer> ids, Date fecha);
    public void modificarFechaRecogidaProductos(Integer id, List<Integer> ids, Date fecha);
    void cancelarRecogidaProductos(Integer contratoId, List<Integer> ids);
    void eliminarLinea(Integer contratoId, List<Integer> ids);
    List<AlbaranDto> getAlbaranesEntrega(Integer contratoId);
    List<AlbaranDto> getAlbaranesRecogida(Integer contratoId);
    void anadirLineasContratoAAlbaran(Integer contratoId, List<Integer> lineasContratoIds, Integer albaranId);

    void crearAlbaranEntrega(Integer contratoId, List<Integer> lineasContratoIds);

    void eliminarAlbaranEntrega(Integer albaranId);

    public void crearAlbaranRecogida(Integer contratoId, List<Integer> lineasContratoIds);

    public void cambiarCliente(Integer contratoId, Integer empresaId);
    public void traspasar(Integer contratoId, Integer empresaId, Date fecha);

    public void modificarEstadoPago(Integer contratoId, String estadoPago);

    public void enviarPorEmail(Integer contratoId, EmailForm emailForm);

    
}