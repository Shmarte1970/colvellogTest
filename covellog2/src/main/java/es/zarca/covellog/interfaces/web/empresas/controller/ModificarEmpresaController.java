package es.zarca.covellog.interfaces.web.empresas.controller;

import es.zarca.covellog.domain.model.empresa.cliente.DetalleFacturacion;
import es.zarca.covellog.interfaces.web.empresas.empresas.controller.EmpresaController;
import es.zarca.covellog.interfaces.web.empresas.direccionesenvio.controller.EmpresaDireccionesEnvioController;
import es.zarca.covellog.interfaces.web.empresas.potencial.controller.PotencialController;
import es.zarca.covellog.interfaces.web.empresas.contactos.controller.EmpresaContactosController;
import es.zarca.covellog.infrastructure.log.Capa;
import es.zarca.covellog.infrastructure.log.FunctionLog;
import es.zarca.covellog.interfaces.facade.adreces.facade.dto.DireccionDto;
import es.zarca.covellog.interfaces.facade.adreces.facade.dto.DireccionPostalDto;
import es.zarca.covellog.interfaces.facade.empresas.facade.EmpresaServiceFacade;
import es.zarca.covellog.interfaces.facade.empresas.facade.dto.ClienteDto;
import es.zarca.covellog.interfaces.facade.empresas.facade.dto.DetalleFacturacionDto;
import es.zarca.covellog.interfaces.facade.empresas.facade.dto.EmpresaDto;
import es.zarca.covellog.interfaces.facade.empresas.facade.dto.PotencialDto;
import es.zarca.covellog.interfaces.facade.empresas.facade.dto.ProveedorDto;
import es.zarca.covellog.interfaces.web.empresas.cliente.controller.ClienteContratacionController;
import es.zarca.covellog.interfaces.web.empresas.cliente.controller.ClienteFacturacionController;
import es.zarca.covellog.interfaces.web.empresas.cliente.controller.ClienteGeneralController;
import es.zarca.covellog.interfaces.web.empresas.model.ModificarEmpresaModel;
import es.zarca.covellog.interfaces.web.empresas.potencial.exception.EmpresaNoTieneRolPotencialPresentationException;
import es.zarca.covellog.interfaces.web.empresas.proveedor.controller.ProveedorController;
import es.zarca.covellog.interfaces.web.helpers.JsfUtil;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.PrimeFaces;
import org.primefaces.event.TabChangeEvent;

/**
 *
 * @author francisco
 */
@Named("modificarEmpresaController")
@ViewScoped
public class ModificarEmpresaController implements Serializable {
    private static final Logger LOGGER = Logger.getLogger(ModificarEmpresaController.class.getName());
    
    @Inject
    private EmpresaServiceFacade facade;
    @Inject
    private EmpresaController empresaController;
    @Inject
    private EmpresaContactosController empresaContactosController;
    @Inject
    private EmpresaDireccionesEnvioController empresaDireccionesEnvioController;
    @Inject
    private PotencialController potencialController;
    @Inject
    private ClienteGeneralController clienteGeneralController;
    @Inject
    private ClienteContratacionController clienteContratacionController;
    @Inject
    private ClienteFacturacionController clienteFacturacionController;
    @Inject
    private ProveedorController proveedorController;
    @Inject
    private ListadoEmpresasController listadoEmpresasController;
    
    private ModificarEmpresaModel model = new ModificarEmpresaModel();
    
    /**
     * Crea un ModificarEmpresaController
     */
    public ModificarEmpresaController() {
        
    }

    /**
     * Devuelve el modelo que esta usando el controlador
     * @return
     */
    public ModificarEmpresaModel getModel() {
        return model;
    }

    /**
     * Asigna el modelo que usara el controlador
     * @param model
     */
    public void setModel(ModificarEmpresaModel model) {
        this.model = model;
        activarDesactivarPestanas();
    }
    
    public void prepareListado() {
        
        if (model.getTipoListado() != null) {
            switch(model.getTipoListado()) {
                case "potenciales":
                    preparePotencialesListado();
                    break;
                case "clientes":
                    prepareClientesListado();
                    break;
                case "proveedores":
                    prepareProveedoresListado();
                    break;
            }
        } 
    }
    
    public void preparePotencialesListado() {
        model.setEstadoInicial(ModificarEmpresaEstado.POTENCIALES_LISTADO);
        setEstado(ModificarEmpresaEstado.POTENCIALES_LISTADO);
    }
    
    public void prepareClientesListado() {
        model.setEstadoInicial(ModificarEmpresaEstado.CLIENTES_LISTADO);
        setEstado(ModificarEmpresaEstado.CLIENTES_LISTADO);
    }
    
    public void prepareProveedoresListado() {
        model.setEstadoInicial(ModificarEmpresaEstado.PROVEEDORES_LISTADO);
        setEstado(ModificarEmpresaEstado.PROVEEDORES_LISTADO);
    }
    
    /**
     * Inicializa el modelo poniendo como empresa la empresa cuyo id se pasa por parametros e inicia el estado EMPRESA
     * @param empresaId
     */
    public void prepareUpdate(Integer empresaId) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            System.err.println("COJONES: prepareupdate: " + empresaId.toString());
            model.setEmpresaId(empresaId);
            prepareUpdate();
        } catch (Exception ex) {
            JsfUtil.addException(ex);
        } finally {
            log.finish();
        }  model.setEmpresaId(empresaId);
        
    }
    
    /**
     * Inicializa el modelo poniendo como empresa la empresa cuyo id es igual al que se pasa por URL e inicia el estado EMPRESA
     */
    public void prepareUpdateInicial() {
        System.err.println("COJONES: prepareUpdateInicial [EMPRESA] => " + (model.getEmpresaId() == null ? "NULLL" : model.getEmpresaId()));
        System.err.println("COJONES: prepareUpdateInicial [TAB] => " + (model.getTabIndex() == null ? "NULLL" : model.getTabIndex()));
        model.setEsUrlWithParameters(true);
        //prepareUpdate(model.getEmpresaId()); 
        switch(model.getTabIndex()) {
            case 1:
                setEstado(ModificarEmpresaEstado.CONTACTOS);
                break;
            case 2:
                setEstado(ModificarEmpresaEstado.DIRECCIONES_ENVIO);
                break;
            case 3:
                setEstado(ModificarEmpresaEstado.POTENCIAL);
                break;
            case 4:
                setEstado(ModificarEmpresaEstado.CLIENTE_GENERAL);
                break;
            case 5:
                setEstado(ModificarEmpresaEstado.PROVEEDOR);
                break;
            default:
                setEstado(ModificarEmpresaEstado.EMPRESA);
        }
        
    }
    
    public void prepareUpdate() {
        System.err.println("COJONES: prepareUpdate     TAB => " + (model.getTabIndex()== null ? "NULLL" : model.getTabIndex()));
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            setEstado(ModificarEmpresaEstado.EMPRESA);
        } catch (Exception ex) {
            JsfUtil.addException(ex);
        } finally {
            log.finish();
        }       
    }
    
    /**
     * Carga una EmpresaDto de la base de datos si existe, sino devuelve una EmpresaDto vacia
     * @param model
     */
    private EmpresaDto cargarEmpresa(Integer id) {
        LOGGER.log(Level.INFO, "cargarEmpresa: {0}", id == null ? "null" : id.toString());
        EmpresaDto empresa = facade.buscarPorIdDto(id);
        if (empresa == null) {
            empresa = new EmpresaDto();
        }
        return empresa;
    }
    
    public void setEstado(ModificarEmpresaEstado estado, EmpresaDto empresa) {
        cargarEstado(estado, empresa);
    }
    
    public void setEstado(ModificarEmpresaEstado estado) {
        cargarEstado(estado, null);
    }
    
    private void cargarEstado(ModificarEmpresaEstado estado, EmpresaDto empresa) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        LOGGER.log(Level.INFO, "Estado: {0}", estado);
        model.setEstado(estado);
        if (empresa == null) {
            empresa = cargarEmpresa(model.getEmpresaId());
        }
        Map<String, Object> defautFilters = new HashMap<>();
        actualizarEmpresa(empresa);
        switch(estado) {
            case CREAR_EMPRESA:
               model.setEmpresa(new EmpresaDto());
                empresaController.actualizarEmpresa(model.getEmpresa());
                model.setTabIndex(0);
                break;
            case EMPRESA:
                empresaController.actualizarEmpresa(model.getEmpresa());
                model.setTabIndex(0);
                break;
            case CONTACTOS:
                empresaContactosController.actualizarEmpresa(model.getEmpresa());
                model.setTabIndex(1);
                break;
            case DIRECCIONES_ENVIO:
                empresaDireccionesEnvioController.actualizarEmpresa(model.getEmpresa());
                model.setTabIndex(2);
                break;
            case CREAR_POTENCIAL:
                model.getEmpresa().setPotencial(new PotencialDto());
                potencialController.actualizarEmpresa(model.getEmpresa());
                model.setTabIndex(0);
                break;
            case POTENCIAL:
                potencialController.actualizarEmpresa(model.getEmpresa());
                model.setTabIndex(3);
                break;
            case CONVERTIR_EN_CLIENTE:
                if (model.getEmpresa().getPotencial() == null) {
                    throw new EmpresaNoTieneRolPotencialPresentationException(model.getEmpresa());
                }
                //Prepara el cliente el cliente con la info del potencial
                ClienteDto cliente = new ClienteDto();
                cliente.setComerciales(model.getEmpresa().getPotencial().getComerciales());
                cliente.setContactos(model.getEmpresa().getPotencial().getContactos());
                cliente.setObservaciones(model.getEmpresa().getPotencial().getObservaciones());
                DireccionDto direccion = model.getEmpresa().getDireccionFiscal();
                if (direccion != null) {
                    DetalleFacturacionDto detalleFacturacion = new DetalleFacturacionDto();
                    detalleFacturacion.setDireccionPostal(new DireccionPostalDto("", direccion.getDireccion(), direccion.getDireccion2(), direccion.getCodigoPostal(), direccion.getPoblacion()));
                    cliente.setDetalleFacturacion(detalleFacturacion);
                }
                model.getEmpresa().setCliente(cliente);
                clienteGeneralController.actualizarEmpresa(model.getEmpresa());
                break;
            case CREAR_CLIENTE:
                model.getEmpresa().setCliente(new ClienteDto());
                clienteGeneralController.actualizarEmpresa(model.getEmpresa());
                break;
            case CLIENTE_GENERAL:
                clienteGeneralController.actualizarEmpresa(model.getEmpresa());
                model.setTabIndex(3);
                break;
            case CLIENTE_CONTRATACION:
                clienteContratacionController.actualizarEmpresa(model.getEmpresa());
                model.setTabIndex(3);
                break;
            case CLIENTE_FACTURACION:
                clienteFacturacionController.actualizarEmpresa(model.getEmpresa());
                model.setTabIndex(3);
                break;
            case CREAR_PROVEEDOR:
                model.getEmpresa().setProveedor(new ProveedorDto());
                proveedorController.actualizarEmpresa(model.getEmpresa());
                break;
            case PROVEEDOR:
                proveedorController.actualizarEmpresa(model.getEmpresa());
                if (model.getEmpresa().getPotencial() != null || model.getEmpresa().getCliente() != null) {
                    model.setTabIndex(4);
                } else {
                    model.setTabIndex(3);
                }               
                break;
            case POTENCIALES_LISTADO:
                defautFilters.put("tipo", "POTENCIAL");
                listadoEmpresasController.getModel().setDefautFilters(defautFilters);
                break;
            case CLIENTES_LISTADO:
                defautFilters.put("tipo", "CLIENTE");
                listadoEmpresasController.getModel().setDefautFilters(defautFilters);
                break;
            case PROVEEDORES_LISTADO:
                defautFilters.put("tipo", "PROVEEDOR");
                listadoEmpresasController.getModel().setDefautFilters(defautFilters);
                break;
        }
        log.finish();
    }
      
    /**
     * Actualiza la empresa del modelo recargandola de la base de datos
     */
    public void actualizarEmpresa() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            actualizarEmpresa(cargarEmpresa(model.getEmpresaId()));
        } catch (Exception ex) {
            JsfUtil.addException(ex);
        } finally {
            log.finish();
        }
    }
    
    /**
     * Actualiza la empresa del modelo con la empresa pasada por parametros.
     * @param empresa
     */
    public void actualizarEmpresa(EmpresaDto empresa) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            model.setEmpresaId(empresa.getId());
            model.setEmpresa(empresa);
            activarDesactivarPestanas();
        } catch (Exception ex) {
            JsfUtil.addException(ex);
        } finally {
            log.finish();
        }
    }
    
    private void activarDesactivarPestanas() {
        LOGGER.log(Level.INFO, "activarDesactivarPestanas");
        EmpresaDto empresa = model.getEmpresa();
        model.setMostrarTabEmpresa(false);
        model.setMostrarTabContactos(false);
        model.setMostrarTabDireccionesEnvio(false);
        model.setMostrarTabPotencial(false);
        model.setMostrarTabCliente(false);
        model.setMostrarTabProveedor(false);
        if (empresa != null) {
            model.setMostrarTabEmpresa(true);
            if (empresa.getId() != null) {
                model.setMostrarTabEmpresa(true);
                model.setMostrarTabContactos(true);
                model.setMostrarTabDireccionesEnvio(true);
                if (empresa.getPotencial() != null) {
                    model.setMostrarTabPotencial(true);
                }
                if (empresa.getCliente()!= null) {
                    model.setMostrarTabCliente(true);
                }
                if (empresa.getProveedor()!= null) {
                    model.setMostrarTabProveedor(true);
                }
            }
        }
    }
    
    private void cambiarEstadoSegunPestanaSeleccionada(String id) {
        LOGGER.log(Level.INFO, "Cargar Pestana: {0}", id);
        switch(id) {
            case "EmpresaTab":
                setEstado(ModificarEmpresaEstado.EMPRESA);
                break;
            case "ContactosTab":
                setEstado(ModificarEmpresaEstado.CONTACTOS);
                PrimeFaces.current().ajax().update(":Pestanas:ContactosForm");
                break;
            case "DireccionesEnvioTab":
                setEstado(ModificarEmpresaEstado.DIRECCIONES_ENVIO);
                PrimeFaces.current().ajax().update(":Pestanas:DireccionesEnvioForm");
                break;
            case "PotencialTab":
                setEstado(ModificarEmpresaEstado.POTENCIAL);
                PrimeFaces.current().ajax().update(":Pestanas:PotencialForm");
                break;
            case "ClienteTab":
                setEstado(ModificarEmpresaEstado.CLIENTE_GENERAL);
                PrimeFaces.current().ajax().update(":Pestanas:Cliente:ClienteGeneralForm");
                break;
            case "ProveedorTab":
                setEstado(ModificarEmpresaEstado.PROVEEDOR);
                PrimeFaces.current().ajax().update(":Pestanas:ProveedorForm");
                break;
        }
        PrimeFaces.current().ajax().update(":growl");
        //PrimeFaces.current().ajax().update(":mi-layout-contenido");
    }
    
    /**
     *
     * @param event
     */
    public void onTabChange(TabChangeEvent event) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {            
            LOGGER.log(Level.INFO, "Selected Tab: {0}", event.getTab().getId());
            cambiarEstadoSegunPestanaSeleccionada(event.getTab().getId());
            //JsfUtil.showErrorDialog(event.getTab().getId() + "  " + model.getTabIndex().toString());
        } catch (Exception ex) {
            JsfUtil.addException(ex);
        } finally {
            log.finish();
        }
    }

    /**
     *
     */
    public void prepareCreate() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            setEstado(ModificarEmpresaEstado.CREAR_EMPRESA);
        } catch (Exception ex) {
            JsfUtil.addException(ex);
        } finally {
            log.finish();
        }
    }
    
    /**
     *
     */
    public void volver() {
        setEstado(model.getEstadoInicial());
    }
    
}