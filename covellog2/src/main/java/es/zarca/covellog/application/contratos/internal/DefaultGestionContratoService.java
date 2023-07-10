package es.zarca.covellog.application.contratos.internal;

import es.zarca.covellog.application.contratos.GestionContratoService;
import es.zarca.covellog.application.contratos.form.ContratoCondicionesForm;
import es.zarca.covellog.application.contratos.form.ContratoFacturacionForm;
import es.zarca.covellog.application.contratos.form.ContratoLineaComplementoForm;
import es.zarca.covellog.application.contratos.form.ContratoLineaForm;
import es.zarca.covellog.application.contratos.form.ContratoLineaGastoAdicionalForm;
import es.zarca.covellog.application.contratos.form.ContratoPersonasForm;
import es.zarca.covellog.application.contratos.form.ModificarContratoGeneralForm;
import es.zarca.covellog.application.exception.BusinessException;
import es.zarca.covellog.application.exception.UndefinedBussinesException;
import es.zarca.covellog.domain.model.adreces.poblacio.PoblacioRepository;
import es.zarca.covellog.domain.model.albaran.Albaran;
import es.zarca.covellog.domain.model.albaran.AlbaranEntrega;
import es.zarca.covellog.domain.model.albaran.AlbaranEntregaRepository;
import es.zarca.covellog.domain.model.albaran.AlbaranRecogida;
import es.zarca.covellog.domain.model.albaran.AlbaranRecogidaRepository;
import es.zarca.covellog.domain.model.albaran.AlbaranRepository;
import es.zarca.covellog.domain.model.comercials.comercial.Comercial;
import es.zarca.covellog.domain.model.comercials.comercial.ComercialRepository;
import es.zarca.covellog.domain.model.contactos.Contacto;
import es.zarca.covellog.domain.model.contrato.Contrato;
import es.zarca.covellog.domain.model.contrato.ContratoLineaComplementoVO;
import es.zarca.covellog.domain.model.contrato.ContratoLineaGastoAdicionalVO;
import es.zarca.covellog.domain.model.contrato.ContratoLineaRO;
import es.zarca.covellog.domain.model.contrato.ContratoPagoEstado;
import es.zarca.covellog.domain.model.contrato.ContratoRepository;
import es.zarca.covellog.domain.model.contrato.ContratoTipoOperacion;
import es.zarca.covellog.domain.model.contrato.TransporteConPrecio;
import es.zarca.covellog.domain.model.empresa.DobleObservacion;
import es.zarca.covellog.domain.model.empresa.Empresa;
import es.zarca.covellog.domain.model.empresa.EmpresaRepository;
import es.zarca.covellog.domain.model.empresa.cliente.DetalleContratacion;
import es.zarca.covellog.domain.model.empresa.cliente.DetalleFacturacion;
import es.zarca.covellog.domain.model.empresa.cliente.FacturarPor;
import es.zarca.covellog.domain.model.empresa.cliente.TipoFacturacion;
import es.zarca.covellog.domain.model.producto.TipoProductoRepository;
import es.zarca.covellog.domain.model.stock.StockRepository;
import es.zarca.covellog.domain.model.transporte.TransporteRepository;
import es.zarca.covellog.domain.model.ubicacion.UbicacionRepository;
import es.zarca.covellog.infrastructure.assertions.ArgumentValidator;
import es.zarca.covellog.infrastructure.log.Capa;
import es.zarca.covellog.infrastructure.log.FunctionLog;
import es.zarca.covellog.interfaces.facade.adreces.facade.internal.assembler.DireccionPostalAssembler;
import es.zarca.covellog.interfaces.facade.empresas.facade.dto.FacturarPorDto;
import es.zarca.covellog.interfaces.facade.empresas.facade.dto.TipoFacturacionDto;
import es.zarca.covellog.interfaces.facade.empresas.facade.internal.assembler.FormaPagoAssembler;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author francisco
 */
@Stateless
public class DefaultGestionContratoService implements GestionContratoService {
@PersistenceContext(unitName = "covellog2-pu")
    private EntityManager entityManager;

    @Inject
    private ContratoRepository contratoRepository;
    @Inject
    private EmpresaRepository empresaRepository;
    @Inject
    private PoblacioRepository poblacioRepository;
    @Inject
    private ComercialRepository comercialRepository;
    @Inject
    private TipoProductoRepository tipoProductoRepository;
    @Inject
    private UbicacionRepository ubicacionRepository;
    @Inject
    private TransporteRepository transporteRepository;
    @Inject
    private StockRepository stockRepository;
    @Inject
    private AlbaranRepository albaranRepository;
    @Inject
    private AlbaranEntregaRepository albaranEntregaRepository;
    @Inject
    private AlbaranRecogidaRepository albaranRecogidaRepository;
    
    @Override
    public void crear(Date fechaContrato, Integer clienteId) {
        Empresa cliente = empresaRepository.findOrFail(clienteId);
        Contrato contrato = new Contrato(fechaContrato, cliente);
        contratoRepository.store(contrato);
    }

    @Override
    public Contrato modificarGeneral(Integer id, ModificarContratoGeneralForm form) {
        FunctionLog log = new FunctionLog(Capa.APP_SERVICE);
        try {
            Contrato contrato = contratoRepository.findOrFail(id);
            ArgumentValidator.isNotNull(contrato.getCliente(), "El contrato no tiene cliente");
            contrato.modificarFechaContrato(form.getFecha());
            contrato.modificarCodigoPedidoCliente(form.getCodigoPedidoCliente());
            contrato.modificarCodigoProveedor(form.getCodigoPedidoProveedor());
            contrato.cambiarDireccionEnvio(
                contrato.getCliente().getDireccionEnvio(form.getDireccionEnvioId())
            );
            contrato.modificarPrevisionMesesAlquiler(form.getPrevisionMesesAlquiler());
            contrato.modificarObservaciones(
                new DobleObservacion(
                    form.getObservaciones().getObsInternas(), 
                    form.getObservaciones().getObsPublicas()
                )
            );
            return contrato;
        } catch (BusinessException ex) {
            throw ex;
        }  finally {
            log.finish();
        }
    }

    @Override
    public Contrato modificarCondiciones(Integer id, ContratoCondicionesForm form) {
        FunctionLog log = new FunctionLog(Capa.APP_SERVICE);
        try {
            Contrato contrato = contratoRepository.findOrFail(id);
            Integer descuento = 0;
            if (form.getDescuento() != null) {
                descuento = form.getDescuento();
            }
            DetalleContratacion condiciones = new DetalleContratacion(
                form.isTransporteEntregaAdelantado(), 
                form.isTransporteRecogidaAdelantado(),
                form.isMontajeAdelantado(),
                form.isDesmontajeAdelantado(), 
                form.getMesesFianza(), 
                TipoFacturacion.parse(form.getTipoFacturacionId()),
                FacturarPor.parse(form.getFacturarPor()),
                descuento
            );
            contrato.modificarCondiciones(condiciones);
            return contrato;
        } catch (BusinessException ex) {
            throw ex;
        }  finally {
            log.finish();
        }
    }
    
    private List<Contacto> cargarContactos(Empresa empresa, List<Integer> contactoIds) {
        List<Contacto> contactos = new ArrayList<>();
        if ((contactoIds != null) && ( empresa != null)) {
            contactoIds.forEach(contactoId -> {         
                contactos.add(empresa.getContacto(contactoId));
            });
        }
        return contactos;
    }
    
    @Override
    public Contrato modificarDatosFacturacion(Integer id, ContratoFacturacionForm form) {
        FunctionLog log = new FunctionLog(Capa.APP_SERVICE);
        try {
            Contrato contrato = contratoRepository.findOrFail(id);
            Integer descuento = 0;
            DetalleFacturacion detalleFacturacion = new DetalleFacturacion(
                form.getExentoIva(), 
                cargarContactos(contrato.getCliente(), form.getContactos()),
                FormaPagoAssembler.toModel(form.getFormaPagoVenta()),
                FormaPagoAssembler.toModel(form.getFormaPagoAlquiler()),
                DireccionPostalAssembler.toModel(form.getDireccionPostal(), poblacioRepository),
                false,
                new DobleObservacion(form.getObservaciones().getObsInternas(), form.getObservaciones().getObsPublicas())
            );
            contrato.modificarDetalleFacturacion(detalleFacturacion);
            return contrato;
        } catch (BusinessException ex) {
            throw ex;
        }  finally {
            log.finish();
        }
    }

    @Override
    public List<TipoFacturacionDto> geTiposFacturacionPosibles() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<FacturarPorDto> getFacturarPorPosibles() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private List<Comercial> cargarComerciales(List<Integer> comercialIds) {
        List<Comercial> comerciales = new ArrayList<>();
        comercialIds.forEach(comercialId -> {
            comerciales.add(comercialRepository.find(comercialId));
        });
        return comerciales;
    }
    
    @Override
    public Contrato modificarPersonas(Integer id, ContratoPersonasForm form) {
        FunctionLog log = new FunctionLog(Capa.APP_SERVICE);
        try {
            Contrato contrato = contratoRepository.findOrFail(id);
            contrato.cambiarComerciales(cargarComerciales(form.getComercialesId()));
            if (form.getFirmanteId() == null) {
                contrato.cambiarFirmante(null);
            } else {
                contrato.cambiarFirmante(contrato.getCliente().getContacto(form.getFirmanteId()));
            }
            contrato.cambiarContactos(cargarContactos(contrato.getCliente(), form.getContactosId()));
            return contrato;
        } catch (BusinessException ex) {
            throw ex;
        }  finally {
            log.finish();
        }
    }

    @Override
    public void addDocumento(Integer contratoId, String fileName, byte[] contents) {
        FunctionLog log = new FunctionLog(Capa.APP_SERVICE);
        try {
            Contrato contrato = contratoRepository.findOrFail(contratoId);
            contrato.anadirDocumento(fileName, contents);
        } catch (BusinessException ex) {
            throw ex;
        }  finally {
            log.finish();
        }
    }
    
    @Override
    public void removeDocumento(Integer contratoId, Integer documentoId) {
        FunctionLog log = new FunctionLog(Capa.APP_SERVICE);
        try {
            Contrato contrato = contratoRepository.findOrFail(contratoId);
            contrato.eliminarDocumento(documentoId);
        } catch (BusinessException ex) {
            throw ex;
        }  finally {
            log.finish();
        }
    }

    @Override
    public void removeDocumentos(Integer contratoId, List<Integer> documentoIds) {
        FunctionLog log = new FunctionLog(Capa.APP_SERVICE);
        try {
            Contrato contrato = contratoRepository.findOrFail(contratoId);
            documentoIds.forEach(documentoId -> {
                contrato.eliminarDocumento(documentoId);
            });
        } catch (BusinessException ex) {
            throw ex;
        }  finally {
            log.finish();
        }
    }

    @Override
    public void bajarDocumentos(Integer contratoId, List<Integer> documentoIds) {
        FunctionLog log = new FunctionLog(Capa.APP_SERVICE);
        try {
            Contrato contrato = contratoRepository.findOrFail(contratoId);
            documentoIds.forEach(documentoId -> {
                contrato.bajarDocumento(documentoId);
            });
        } catch (BusinessException ex) {
            throw ex;
        }  finally {
            log.finish();
        }
    }

    @Override
    public void subirDocumentos(Integer contratoId, List<Integer> documentoIds) {
        FunctionLog log = new FunctionLog(Capa.APP_SERVICE);
        try {
            Contrato contrato = contratoRepository.findOrFail(contratoId);
            documentoIds.forEach(documentoId -> {
                contrato.subirDocumento(documentoId);
            });
        } catch (BusinessException ex) {
            throw ex;
        }  finally {
            log.finish();
        }
    }

    private List<ContratoLineaComplementoVO> complementosFormToComplementosVO(List<ContratoLineaComplementoForm> complementosForm) {
        List<ContratoLineaComplementoVO> complementos = new ArrayList();
        complementosForm.forEach(complementoForm -> {
            complementos.add(
                new ContratoLineaComplementoVO(
                        complementoForm.getId(),
                        tipoProductoRepository.findOrFail(complementoForm.getComplementoId()),
                        complementoForm.getConcepto(),
                        complementoForm.getCantidad(),
                        complementoForm.getImporte()
                )
            );
        });
        return complementos;
    }
    
    private List<ContratoLineaGastoAdicionalVO> gastosAdicionalesFormToGastosAdicionalesVO(List<ContratoLineaGastoAdicionalForm> gastosAdicionalesForm) {
        List<ContratoLineaGastoAdicionalVO> gastosAdicionales = new ArrayList();
        gastosAdicionalesForm.forEach(gastoAdicionalForm -> {
            gastosAdicionales.add(
                new ContratoLineaGastoAdicionalVO(
                    gastoAdicionalForm.getId(),
                    tipoProductoRepository.findOrFail(gastoAdicionalForm.getGastoAdicionalId()),
                    gastoAdicionalForm.getConcepto(),
                    gastoAdicionalForm.getCantidad(),
                    gastoAdicionalForm.getImporte()
                )
            );
        });
        return gastosAdicionales;
    }

    @Override
    public Contrato modificarLinea(Integer contratoId, Integer lineaId, ContratoLineaForm form) {
        FunctionLog log = new FunctionLog(Capa.APP_SERVICE);
        try {
            ArgumentValidator.isNotNull(contratoId, "El id de contrato no puede ser NULL");
            ArgumentValidator.isNotNull(lineaId, "El id de linea no puede ser NULL");
            ArgumentValidator.isNotNull(form, "El formulario no puede ser NULL");
            Contrato contrato = contratoRepository.findOrFail(contratoId);
            contrato.lineaCambiarTipoOperacion(lineaId, ContratoTipoOperacion.fromId(form.getTipoOperacion()));
            contrato.lineaModificarFechaEntregaPrevista(lineaId, form.getFechaEntregaPrevista());
            if (form.getStock() != null) {                
                contrato.lineaAsignarProducto(
                    lineaId, 
                    stockRepository.findOrFail(form.getStock())
                );                
            } else {
                contrato.lineaAsignarProducto(
                    lineaId, 
                    tipoProductoRepository.findOrFail(form.getTipoProducto()),
                    ubicacionRepository.findOrFail(form.getUbicacion()),
                    form.getLote()
                );
            }
            contrato.lineaModificarConcepto(lineaId, form.getConcepto());
            contrato.lineaModificarImporte(lineaId, form.getImporte());
            contrato.lineaModificarTransporteEntregaConPrecio(lineaId, formEntregaToTransporteConPrecio(form));
            contrato.lineaModificarTransporteRecogidaConPrecio(lineaId, formRecogidaToTransporteConPrecio(form));
            contrato.lineaModificarObservaciones(lineaId, formObservacionesToDobleObservacion(form));
            contrato.lineaModificarComplementos(lineaId, complementosFormToComplementosVO(form.getComplementos()));
            contrato.lineaModificarGastosAdicionales(lineaId, gastosAdicionalesFormToGastosAdicionalesVO(form.getGastosAdicionales()));
            contrato.lineaActualizarAlbaranSiTiene(lineaId);
            return contrato;
        } catch (BusinessException ex) {
            throw ex;
        }  finally {
            log.finish();
        }
    }
    
    private TransporteConPrecio formEntregaToTransporteConPrecio(ContratoLineaForm form) {
        if (form.getEntrega() != null) {
            return new TransporteConPrecio(
                transporteRepository.findOrFail(form.getEntrega().getTransporteId()), 
                form.getEntrega().getImporte()
            );
        }
        return null;
    }
    
    private TransporteConPrecio formRecogidaToTransporteConPrecio(ContratoLineaForm form) {
        if (form.getRecogida() != null) {
            return new TransporteConPrecio(
                transporteRepository.findOrFail(form.getRecogida().getTransporteId()), 
                form.getRecogida().getImporte()
            );
        }
        return null;
    }
    
    private DobleObservacion formObservacionesToDobleObservacion(ContratoLineaForm form) {
        if (form.getObservaciones()!= null) {
            return new DobleObservacion(form.getObservaciones().getObsInternas(), form.getObservaciones().getObsPublicas());
        }
        return null;
    }
    
    @Override
    public ContratoLineaRO crearLinea(Integer contratoId, ContratoLineaForm form) {
        FunctionLog log = new FunctionLog(Capa.APP_SERVICE);
        try {
            ArgumentValidator.isNotNull(contratoId, "El id de contrato no puede ser NULL");
            ArgumentValidator.isNotNull(form, "El formulario no puede ser NULL");
            ContratoLineaRO linea;
            Contrato contrato = contratoRepository.findOrFail(contratoId);
            if (ContratoTipoOperacion.VENTA.getId().equals(form.getTipoOperacion())) {
                if (form.getStock() != null) {                
                    linea = contrato.anadirLineaVenta(
                        form.getFechaEntregaPrevista(),
                        stockRepository.findOrFail(form.getStock()),
                        form.getConcepto(),
                        form.getImporte(),
                        formEntregaToTransporteConPrecio(form),
                        complementosFormToComplementosVO(form.getComplementos()),
                        gastosAdicionalesFormToGastosAdicionalesVO(form.getGastosAdicionales()),
                        formObservacionesToDobleObservacion(form)
                    );

                } else if (form.getLote() != null) {
                    linea = contrato.anadirLineaVenta(
                        form.getFechaEntregaPrevista(),
                        tipoProductoRepository.findOrFail(form.getTipoProducto()),
                        ubicacionRepository.findOrFail(form.getUbicacion()),
                        form.getLote(),
                        form.getConcepto(),
                        form.getImporte(),
                        formEntregaToTransporteConPrecio(form),
                        complementosFormToComplementosVO(form.getComplementos()),
                        gastosAdicionalesFormToGastosAdicionalesVO(form.getGastosAdicionales()),
                        formObservacionesToDobleObservacion(form)                    
                    );
                } else {
                    linea = contrato.anadirLineaVenta(
                        form.getFechaEntregaPrevista(),
                        tipoProductoRepository.findOrFail(form.getTipoProducto()),
                        ubicacionRepository.findOrFail(form.getUbicacion()),
                        form.getConcepto(),
                        form.getImporte(),
                        formEntregaToTransporteConPrecio(form),
                        complementosFormToComplementosVO(form.getComplementos()),
                        gastosAdicionalesFormToGastosAdicionalesVO(form.getGastosAdicionales()),
                        formObservacionesToDobleObservacion(form)                    
                    );
                }
            } else {
                if (form.getStock() != null) {                
                    linea = contrato.anadirLineaAlquiler(
                        form.getFechaEntregaPrevista(),
                        stockRepository.findOrFail(form.getStock()),
                        form.getConcepto(),
                        form.getImporte(),
                        formEntregaToTransporteConPrecio(form),
                        formRecogidaToTransporteConPrecio(form),
                        complementosFormToComplementosVO(form.getComplementos()),
                        gastosAdicionalesFormToGastosAdicionalesVO(form.getGastosAdicionales()),
                        formObservacionesToDobleObservacion(form)
                    );

                } else if (form.getLote() != null) {                    
                    linea = contrato.anadirLineaAlquiler(
                        form.getFechaEntregaPrevista(),
                        tipoProductoRepository.findOrFail(form.getTipoProducto()),
                        ubicacionRepository.findOrFail(form.getUbicacion()),
                        form.getLote(),
                        form.getConcepto(),
                        form.getImporte(),
                        formEntregaToTransporteConPrecio(form),
                        formRecogidaToTransporteConPrecio(form),
                        complementosFormToComplementosVO(form.getComplementos()),
                        gastosAdicionalesFormToGastosAdicionalesVO(form.getGastosAdicionales()),
                        formObservacionesToDobleObservacion(form)                    
                    );
                } else {
                    linea = contrato.anadirLineaAlquiler(
                        form.getFechaEntregaPrevista(),
                        tipoProductoRepository.findOrFail(form.getTipoProducto()),
                        ubicacionRepository.findOrFail(form.getUbicacion()),
                        form.getConcepto(),
                        form.getImporte(),
                        formEntregaToTransporteConPrecio(form),
                        formRecogidaToTransporteConPrecio(form),
                        complementosFormToComplementosVO(form.getComplementos()),
                        gastosAdicionalesFormToGastosAdicionalesVO(form.getGastosAdicionales()),
                        formObservacionesToDobleObservacion(form)                    
                    );
                }    
            }
            
            return linea;
        } catch (BusinessException ex) {
            throw ex;
        }  finally {
            log.finish();
        }
    }

    @Override
    public void modificarFechaEntregaPrevista(Integer contratoId, List<Integer> lineasContratoIds, Date fecha) {
        FunctionLog log = new FunctionLog(Capa.APP_SERVICE);
        try {
            ArgumentValidator.isNotNull(contratoId, "El id de contrato no puede ser NULL.");
            ArgumentValidator.isNotEmpty(lineasContratoIds, "La lista de lineas de contrato no puede estar vacia.");
            ArgumentValidator.isNotNull(fecha, "La fecha no puede ser NULL.");
            Contrato contrato = contratoRepository.findOrFail(contratoId);
            contrato.lineaModificarFechaEntregaPrevista(lineasContratoIds, fecha);
        } catch (BusinessException ex) {
            throw ex;
        }  finally {
            log.finish();
        }
    }

    @Override
    public void entregarProductos(Integer contratoId, List<Integer> ids, Date fecha) {
        FunctionLog log = new FunctionLog(Capa.APP_SERVICE);
        try {
            ArgumentValidator.isNotNull(contratoId, "El id de contrato no puede ser NULL.");
            ArgumentValidator.isNotEmpty(ids, "La lista de lineas de contrato no puede estar vacia.");
            ArgumentValidator.isNotNull(fecha, "La fecha no puede ser NULL.");
            Contrato contrato = contratoRepository.findOrFail(contratoId);
            contrato.lineaEntregar(ids, fecha);
        } catch (BusinessException ex) {
            throw ex;
        }  finally {
            log.finish();
        }
    }

    @Override
    public void modificarFechaEntrega(Integer contratoId, List<Integer> ids, Date fecha) {
        FunctionLog log = new FunctionLog(Capa.APP_SERVICE);
        try {
            ArgumentValidator.isNotNull(contratoId, "El id de contrato no puede ser NULL.");
            ArgumentValidator.isNotEmpty(ids, "La lista de lineas de contrato no puede estar vacia.");
            ArgumentValidator.isNotNull(fecha, "La fecha no puede ser NULL.");
            Contrato contrato = contratoRepository.findOrFail(contratoId);
            contrato.lineaModificarFechaEntrega(ids, fecha);
        } catch (BusinessException ex) {
            throw ex;
        }  finally {
            log.finish();
        }
    }
    
    @Override
    public void cancelarEntregaProductos(Integer contratoId, List<Integer> ids) {
        FunctionLog log = new FunctionLog(Capa.APP_SERVICE);
        try {
            ArgumentValidator.isNotNull(contratoId, "El id de contrato no puede ser NULL.");
            ArgumentValidator.isNotEmpty(ids, "La lista de lineas de contrato no puede estar vacia.");
            Contrato contrato = contratoRepository.findOrFail(contratoId);
            contrato.lineaCancelarEntrega(ids);
        } catch (BusinessException ex) {
            throw ex;
        }  finally {
            log.finish();
        }
    }
    
    @Override
    public void solicitarRecogidaProductos(Integer contratoId, List<Integer> ids, Date fecha) {
        FunctionLog log = new FunctionLog(Capa.APP_SERVICE);
        try {
            ArgumentValidator.isNotNull(contratoId, "El id de contrato no puede ser NULL.");
            ArgumentValidator.isNotEmpty(ids, "La lista de lineas de contrato no puede estar vacia.");
            ArgumentValidator.isNotNull(fecha, "La fecha no puede ser NULL.");
            Contrato contrato = contratoRepository.findOrFail(contratoId);
            contrato.lineaSolicitarRecogida(ids, fecha);
        } catch (BusinessException ex) {
            throw ex;
        }  finally {
            log.finish();
        }
    }
    
    @Override
    public void cancelarSolicitudRecogidaProductos(Integer contratoId, List<Integer> ids) {
        FunctionLog log = new FunctionLog(Capa.APP_SERVICE);
        try {
            ArgumentValidator.isNotNull(contratoId, "El id de contrato no puede ser NULL.");
            ArgumentValidator.isNotEmpty(ids, "La lista de lineas de contrato no puede estar vacia.");
            Contrato contrato = contratoRepository.findOrFail(contratoId);
            contrato.lineaCancelarSolicitudRecogida(ids);
        } catch (BusinessException ex) {
            throw ex;
        }  finally {
            log.finish();
        }
    }

    @Override
    public void recogerProductos(Integer contratoId, List<Integer> ids, Date fecha) {
        FunctionLog log = new FunctionLog(Capa.APP_SERVICE);
        try {
            ArgumentValidator.isNotNull(contratoId, "El id de contrato no puede ser NULL.");
            ArgumentValidator.isNotEmpty(ids, "La lista de lineas de contrato no puede estar vacia.");
            ArgumentValidator.isNotNull(fecha, "La fecha no puede ser NULL.");
            Contrato contrato = contratoRepository.findOrFail(contratoId);
            contrato.lineaRecoger(ids, fecha, null);
        } catch (BusinessException ex) {
            throw ex;
        }  finally {
            log.finish();
        }
    }

    @Override
    public void modificarFechaRecogida(Integer contratoId, List<Integer> ids, Date fecha) {
        FunctionLog log = new FunctionLog(Capa.APP_SERVICE);
        try {
            ArgumentValidator.isNotNull(contratoId, "El id de contrato no puede ser NULL.");
            ArgumentValidator.isNotEmpty(ids, "La lista de lineas de contrato no puede estar vacia.");
            ArgumentValidator.isNotNull(fecha, "La fecha no puede ser NULL.");
            Contrato contrato = contratoRepository.findOrFail(contratoId);
            contrato.lineaModificarFechaRecogida(ids, fecha, null);
        } catch (BusinessException ex) { 
            throw ex;
        }  finally {
            log.finish();
        }
    }

    @Override
    public void cancelarRecogidaProductos(Integer contratoId, List<Integer> ids) {
        FunctionLog log = new FunctionLog(Capa.APP_SERVICE);
        try {
            ArgumentValidator.isNotNull(contratoId, "El id de contrato no puede ser NULL.");
            ArgumentValidator.isNotEmpty(ids, "La lista de lineas de contrato no puede estar vacia.");
            Contrato contrato = contratoRepository.findOrFail(contratoId);
            contrato.lineaCancelarRecogida(ids);
        } catch (BusinessException ex) {
            throw ex;
        }  finally {
            log.finish();
        }
    }
    
    @Override
    public void eliminarLinea(Integer contratoId, List<Integer> ids) {
        FunctionLog log = new FunctionLog(Capa.APP_SERVICE);
        try {
            ArgumentValidator.isNotNull(contratoId, "El id de contrato no puede ser NULL.");
            ArgumentValidator.isNotEmpty(ids, "La lista de lineas de contrato no puede estar vacia.");
            Contrato contrato = contratoRepository.findOrFail(contratoId);
            contrato.lineaEliminar(ids);
        } catch (BusinessException ex) {
            throw ex;
        }  finally {
            log.finish();
        }
    }

    @Override
    public void anadirLineasContratoAAlbaranEntrega(Integer contratoId, List<Integer> lineasContratoIds, Integer albaranId) {
        FunctionLog log = new FunctionLog(Capa.APP_SERVICE);
        try {
            ArgumentValidator.isNotNull(contratoId, "El id de contrato no puede ser NULL.");
            ArgumentValidator.isNotEmpty(lineasContratoIds, "La lista de lineas de contrato no puede estar vacia.");
            ArgumentValidator.isNotNull(albaranId, "El id de albaran no puede ser NULL.");
            Contrato contrato = contratoRepository.findOrFail(contratoId);
            System.err.println("COJONES 111111111111111111111111");
            Albaran albaran = albaranRepository.findOrFail(albaranId);
            if (albaran instanceof AlbaranEntrega) {
                System.err.println("COJONES 2222222222222222");
                for (Integer lineaContratoId : lineasContratoIds) {
                    contrato.asignarAlbaranEntrega(lineaContratoId, (AlbaranEntrega) albaran);
                }
            } else if (albaran instanceof AlbaranRecogida) {
                System.err.println("COJONES 333333333333333333");
                for (Integer lineaContratoId : lineasContratoIds) {
                    contrato.asignarAlbaranRecogida(lineaContratoId, (AlbaranRecogida) albaran);
                }
            } else {
                System.err.println("COJONES 44444444444444");
                throw new UndefinedBussinesException("De que tipo es teste albaran?");
            }
            System.err.println("COJONES 5555555555555555555555555");
        } catch (BusinessException ex) {
            throw ex;
        }  finally {
            log.finish();
        }
    }
    
    @Override
    public void anadirLineasContratoAAlbaranRecogida(Integer contratoId, List<Integer> lineasContratoIds, Integer albaranId) {
        FunctionLog log = new FunctionLog(Capa.APP_SERVICE);
        try {
            ArgumentValidator.isNotNull(contratoId, "El id de contrato no puede ser NULL.");
            ArgumentValidator.isNotEmpty(lineasContratoIds, "La lista de lineas de contrato no puede estar vacia.");
            ArgumentValidator.isNotNull(albaranId, "El id de albaran no puede ser NULL.");
            Contrato contrato = contratoRepository.findOrFail(contratoId);
            AlbaranRecogida albaranRecogida = albaranRecogidaRepository.findOrFail(albaranId);
            for (Integer lineaContratoId : lineasContratoIds) {
                contrato.asignarAlbaranRecogida(lineaContratoId, albaranRecogida);
            }
        } catch (BusinessException ex) {
            throw ex;
        }  finally {
            log.finish();
        }
    }

    @Override
    public void crearAlbaranEntrega(Integer contratoId, List<Integer> lineasContratoIds) {
        FunctionLog log = new FunctionLog(Capa.APP_SERVICE);
        try {
            ArgumentValidator.isNotNull(contratoId, "El id de contrato no puede ser NULL.");
            ArgumentValidator.isNotEmpty(lineasContratoIds, "La lista de lineas de contrato no puede estar vacia.");
            Contrato contrato = contratoRepository.findOrFail(contratoId);
            contrato.crearAlbaranEntrega(lineasContratoIds);
        } catch (BusinessException ex) {
            throw ex;
        }  finally {
            log.finish();
        }
    }

    @Override
    public void eliminarAlbaran(Integer albaranId) {
        FunctionLog log = new FunctionLog(Capa.APP_SERVICE);
        try {
            ArgumentValidator.isNotNull(albaranId, "El id de albaran no puede ser NULL.");
            AlbaranEntrega albaran = albaranEntregaRepository.findOrFail(albaranId);
            albaran.eliminar();
            albaranEntregaRepository.remove(albaran);
        } catch (BusinessException ex) {
            throw ex;
        }  finally {
            log.finish();
        }
    }
    
    @Override
    public void crearAlbaranRecogida(Integer contratoId, List<Integer> lineasContratoIds) {
        FunctionLog log = new FunctionLog(Capa.APP_SERVICE);
        try {
            ArgumentValidator.isNotNull(contratoId, "El id de contrato no puede ser NULL.");
            ArgumentValidator.isNotEmpty(lineasContratoIds, "La lista de lineas de contrato no puede estar vacia.");
            Contrato contrato = contratoRepository.findOrFail(contratoId);
            contrato.crearAlbaranRecogida(lineasContratoIds);
        } catch (BusinessException ex) {
            throw ex;
        }  finally {
            log.finish();
        }
    }

    @Override
    public void cambiarCliente(Integer contratoId, Integer nuevoClienteId) {
        FunctionLog log = new FunctionLog(Capa.APP_SERVICE);
        try {
            ArgumentValidator.isNotNull(contratoId, "El id de contrato no puede ser NULL.");
            ArgumentValidator.isNotNull(nuevoClienteId, "El id de cliente no puede ser NULL.");
            Contrato contrato = contratoRepository.findOrFail(contratoId);
            Empresa cliente = empresaRepository.findOrFail(nuevoClienteId);
            contrato.cambiarCliente(cliente);
        } catch (BusinessException ex) {
            throw ex;
        }  finally {
            log.finish();
        }
    }

    @Override
    public Contrato traspasar(Integer contratoId, Integer nuevoClienteId, Date fecha) {
        FunctionLog log = new FunctionLog(Capa.APP_SERVICE);
        try {
            ArgumentValidator.isNotNull(contratoId, "El id de contrato no puede ser NULL.");
            ArgumentValidator.isNotNull(nuevoClienteId, "El id de cliente no puede ser NULL.");
            Contrato contrato = contratoRepository.findOrFail(contratoId);            
            Empresa cliente = empresaRepository.findOrFail(nuevoClienteId);
            return contrato.traspasar(cliente, fecha);
        } catch (BusinessException ex) {
            throw ex;
        }  finally {
            log.finish();
        }
    }

    @Override
    public Contrato copiarContrato(Integer contratoId) {
        FunctionLog log = new FunctionLog(Capa.APP_SERVICE);
        try {
            ArgumentValidator.isNotNull(contratoId, "El id de contrato no puede ser NULL.");
            Contrato contrato = contratoRepository.findOrFail(contratoId);
            return contrato.copiar();
        } catch (BusinessException ex) {
            throw ex;
        }  finally {
            log.finish();
        }
    }

    @Override
    public void modificarEstadoPago(Integer contratoId, String estadoPago) {
        FunctionLog log = new FunctionLog(Capa.APP_SERVICE);
        try {
            ArgumentValidator.isNotNull(contratoId, "El id de contrato no puede ser NULL.");
            ArgumentValidator.isNotNull(estadoPago, "El estadoPago no puede ser NULL.");
            Contrato contrato = contratoRepository.findOrFail(contratoId);
            contrato.cambiarEstadoPago(ContratoPagoEstado.fromId(estadoPago));
        } catch (BusinessException ex) {
            throw ex;
        }  finally {
            log.finish();
        }
    }
}