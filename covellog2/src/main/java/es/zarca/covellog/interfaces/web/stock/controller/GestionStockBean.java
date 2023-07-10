package es.zarca.covellog.interfaces.web.stock.controller;

import es.zarca.covellog.infrastructure.assertions.ArgumentValidator;
import es.zarca.covellog.infrastructure.log.Capa;
import es.zarca.covellog.infrastructure.log.FunctionLog;
import es.zarca.covellog.interfaces.facade.stock.StockFacade;
import es.zarca.covellog.interfaces.facade.stock.dto.StockDto;
import es.zarca.covellog.interfaces.web.app.model.DataTableColumn;
import es.zarca.covellog.interfaces.web.helpers.ExceptionHandler;
import es.zarca.covellog.interfaces.web.helpers.JsfUtil;
import es.zarca.covellog.interfaces.web.helpers.OrdreAssembler;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.PrimeFaces;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
/**
 *
 * @author francisco
 */
@Named("gestionStockBean")
@ViewScoped
public class GestionStockBean implements Serializable {
    int cojones = 0;
    private boolean mostrarBajas = false;
    private static final Logger LOGGER = Logger.getLogger(GestionStockBean.class.getName());
    @Inject
    private StockFacade facade;
    @Inject
    private NuevoStockBean nuevoStockBean;
    @Inject
    private ModificarStockBean modificarStockBean;
    @Inject
    private BajaStockBean bajaStockBean;
    @Inject
    private AnularBajaStockBean anularBajaStockBean;
    @Inject
    private TrasladoStockBean trasladoStockBean;
    @Inject
    private BloquearStockBean bloquearStockBean;
    @Inject
    private DesbloquearStockBean desbloquearStockBean;
    @Inject
    private CambiarCondicionStockBean cambiarCondicionStockBean;
    @Inject
    private CambiarPropietarioStockBean cambiarPropietarioStockBean;
    @Inject
    private CambiarFlotaStockBean cambiarFlotaStockBean;
    @Inject
    private DetallePropietariosBean detallePropietariosBean;
    @Inject
    private HistoricoEstadosBean historicoEstadosBean;
    @Inject
    private HistoricoFlotasBean historicoFlotasBean;
    private List<StockDto> selecteds = new ArrayList();
    private StockDtoLazyDataModel items;
    private String filtro = "";
    private AccionPanel accionActual = AccionPanel.LISTA;
    private AccionDialogo dialogoActual = AccionDialogo.NONE;
    private List<DataTableColumn> dataTableSelectedColumns;
    private List<DataTableColumn> dataTableColumns;
    private String filtroNumSerie = "";
    
    
    public enum AccionPanel {
        LISTA("lista", "Gestion Stock"),
        CREAR("crear", "Crear Stock"),
        MODIFICAR("modificar", "Modificar Stock");

        private final String id;
        private final String titulo;

        private AccionPanel(String id, String titulo) {
            this.id = id;
            this.titulo = titulo;
        }
        
        public String getId() {
            return id;
        }
        
        public String getTitulo() {
            return titulo;
        }
    }
    
    public enum AccionDialogo {
        NONE("NONE", "NONE"),
        BAJA("baja", "Baja"),
        ANULAR_BAJA("anularBaja", "Anular Baja"),
        CAMBIAR_CONDICION("cambiarCondicion", "Cambiar Condicion"),
        BLOQUEAR("bloquear", "Bloquear"),
        DESBLOQUEAR("desbloquear", "Desbloquear"),
        CAMBIAR_PROPIETARIO("cambiarPropietario", "Cambiar Propietario"),
        DETALLE_PROPIETARIO("detallePropietarios", "Detalle Propietario"),
        HISTORICO_ESTADOS("historicoEstados", "Historico Estados"),
        CAMBIAR_FLOTA("cambiarFlota", "Cambiar Flota"),
        TRASLADO("traslado", "Traslado"), 
        HISTORICO_FLOTA("historicoFlotas", "Historico Flotas");

        private final String id;
        private final String titulo;

        private AccionDialogo(String id, String titulo) {
            this.id = id;
            this.titulo = titulo;
        }
        
        public String getId() {
            return id;
        }
        
        public String getTitulo() {
            return titulo;
        }
    }

    public class StockDtoLazyDataModel extends LazyDataModel<StockDto> {
        private List<StockDto> list;
        
        public StockDtoLazyDataModel(){
            this.setRowCount(facade.findTotalCount());
        }
        
        @Override
        public List<StockDto> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
            FunctionLog log = new FunctionLog(Capa.CONTROLLER);
            try {
                if (!mostrarBajas) {
                    filters.put("ocultarBajas", "");
                }
                list = facade.find(first, pageSize, OrdreAssembler.fromSortOrder(sortField, sortOrder), filters);
                this.setRowCount(facade.findTotalCount(filters));
                return list;
            } catch (Exception ex) {
                //ExceptionHandler.handleException(ex);
                log.info(ex.getMessage());
            } finally {
                log.finish();
            }
            return null;
            
        }
        
        @Override
	public Object getRowKey(StockDto dto) {
            return dto.getId();
	}

	@Override
	public StockDto getRowData(String rowKey) {
            for (StockDto dto : list) {
                if (dto.getId().equals(Integer.parseInt(rowKey))) return dto;
            }
            return null;
        }
    }

    public StockDto getSelected() {
        if ((selecteds != null) && (selecteds.size() > 0)) {
            return selecteds.get(0);
        }
        return null;
    }

    public List<StockDto> getSelecteds() {
        System.err.println("GET SELECTED STOCK ");
        return selecteds;
    }

    public void setSelecteds(List<StockDto> selecteds) {
        System.err.println("SET SELECTED STOCK");
        this.selecteds = selecteds;
    }

    public StockDtoLazyDataModel getItems() {
        System.err.println("GET ITEMS ");
        if (items == null) {
            items = new StockDtoLazyDataModel();
        }  
        return items;
    }

    public void setItems(StockDtoLazyDataModel items) {
        this.items = items;
    }

    public void dataTableColumnsChanged() {
        JsfUtil.showErrorDialog("dataTableColumnsChanged");
    }

    public Boolean dataTableColumnVisible(String columnId) {
        cojones++;
        return true;
    }
    
    public List<DataTableColumn> getDataTableSelectedColumns() {
        if (dataTableSelectedColumns == null) {
            selectMostrarTodasColumnas();
        }
        return dataTableSelectedColumns;  
    }

    public void setDataTableSelectedColumns(List<DataTableColumn> dataTableSelectedColumns) {
        this.dataTableSelectedColumns = dataTableSelectedColumns;
    }
    
    public DataTableColumn getDataTableColumn(String columnId) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            if (columnId != null) {
                for (DataTableColumn dataTableColumn : dataTableColumns) {
                    if (columnId.equals(dataTableColumn.getId())) {
                        return dataTableColumn;
                    }
                }
            }
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
        return null;
    }
        
    public List<DataTableColumn> getDataTableColumns() {
        if (dataTableColumns == null) {
            dataTableColumns = new ArrayList();
            DataTableColumn column = new DataTableColumn();
            column.setId("PRODUCTO");
            column.setCaption("Producto");
            column.setRendered(true);
            column.setResizable(true);
            column.setWidth(80);
            column.setStyle("");
            column.setPriority(1);
            column.setFilter("tipoProducto.id");
            column.setFilterMatchMode("contains");
            column.setSortBy("tipoProducto.id");
            column.setProperty("tipoProducto");
            column.setView("PRODUCTO");
            dataTableColumns.add(column);
            
            column = new DataTableColumn();
            column.setId("FAMILIA");
            column.setCaption("Familia");
            column.setRendered(true);
            column.setResizable(true);
            column.setWidth(80);
            column.setStyle("");
            column.setPriority(2);
            column.setFilter("familia");
            column.setFilterMatchMode("contains");
            column.setSortBy("tipoProducto.familia");
            column.setProperty("tipoProducto");
            column.setView("FAMILIA");
            dataTableColumns.add(column);
            
            column = new DataTableColumn();
            column.setId("LOTE");
            column.setCaption("Lote");
            column.setRendered(true);
            column.setResizable(true);
            column.setWidth(80);
            column.setStyle("");
            column.setPriority(1);
            column.setFilter("lote");
            column.setFilterMatchMode("contains");
            column.setSortBy("lote");
            column.setProperty("lote");
            column.setView("TEXTO");
            dataTableColumns.add(column);
            
            column = new DataTableColumn();
            column.setId("NUM_SERIE");
            column.setCaption("Num.Serie");
            column.setRendered(true);
            column.setResizable(true);
            column.setWidth(80);
            column.setStyle("");
            column.setPriority(1);
            column.setFilter("numSerie");
            column.setFilterMatchMode("contains");
            column.setSortBy("numSerie");
            column.setProperty("numSerie");
            column.setView("NUM_SERIE");
            dataTableColumns.add(column);
            
            column = new DataTableColumn();
            column.setId("FLOTA");
            column.setCaption("Flota");
            column.setRendered(true);
            column.setResizable(true);
            column.setWidth(80);
            column.setStyle("");
            column.setPriority(1);
            column.setFilter("flota");
            column.setFilterMatchMode("contains");
            column.setSortBy("flota");
            column.setProperty("flota");
            column.setView("FLOTA");
            dataTableColumns.add(column);
            
            column = new DataTableColumn();
            column.setId("ESTADO");
            column.setCaption("Estado");
            column.setRendered(true);
            column.setResizable(true);
            column.setWidth(80);
            column.setStyle("");
            column.setPriority(1);
            column.setFilter("estado");
            column.setFilterMatchMode("contains");
            column.setSortBy("estado");
            column.setProperty("estado");
            column.setView("ESTADO");
            dataTableColumns.add(column);
            
            column = new DataTableColumn();
            column.setId("ESTADO_CONTRATO");
            column.setCaption("Contrato");
            column.setRendered(true);
            column.setResizable(true);
            column.setWidth(80);
            column.setStyle("text-align:center");
            column.setPriority(1);
            column.setFilter("estadoContratoId");
            column.setFilterMatchMode("contains");
            column.setSortBy("estadoContratoId");
            column.setProperty("estadoContratoId");
            column.setView("ESTADO_CONTRATO");
            dataTableColumns.add(column);
            
            column = new DataTableColumn();
            column.setId("ESTADO_FECHA");
            column.setCaption("Estado Fecha");
            column.setRendered(true);
            column.setResizable(true);
            column.setWidth(80);
            column.setStyle("");
            column.setPriority(2);
            column.setFilter("estadoFecha");
            column.setFilterMatchMode("contains");
            column.setSortBy("estadoFecha");
            column.setProperty("estadoFecha");
            column.setView("FECHA");
            dataTableColumns.add(column);
            
            column = new DataTableColumn();
            column.setId("ESTADO_FECHA_EFECTIVA");
            column.setCaption("Estado Fecha Efectiva");
            column.setRendered(true);
            column.setResizable(true);
            column.setWidth(80);
            column.setStyle("");
            column.setPriority(2);
            column.setFilter("estadoFechaEfectiva");
            column.setFilterMatchMode("contains");
            column.setSortBy("estadoFechaEfectiva");
            column.setProperty("estadoFechaEfectiva");
            column.setView("FECHA");
            dataTableColumns.add(column);
            
            column = new DataTableColumn();
            column.setId("UBICACION");
            column.setCaption("Ubicacion");
            column.setRendered(true);
            column.setResizable(true);
            column.setWidth(80);
            column.setStyle("");
            column.setPriority(1);
            column.setFilter("ubicacion.any");
            column.setFilterMatchMode("contains");
            column.setSortBy("ubicacion");
            column.setProperty("ubicacion");
            column.setView("UBICACION");
            dataTableColumns.add(column);
            
            column = new DataTableColumn();
            column.setId("UBICACION_FECHA");
            column.setCaption("Ubicacion Fecha");
            column.setRendered(true);
            column.setResizable(true);
            column.setWidth(80);
            column.setStyle("");
            column.setPriority(1);
            column.setFilter("ubicacionFecha");
            column.setFilterMatchMode("contains");
            column.setSortBy("ubicacionFecha");
            column.setProperty("ubicacionFecha");
            column.setView("FECHA");
            dataTableColumns.add(column);
            
            column = new DataTableColumn();
            column.setId("CONDICION");
            column.setCaption("Condicion");
            column.setRendered(true);
            column.setResizable(true);
            column.setWidth(80);
            column.setStyle("");
            column.setPriority(1);
            column.setFilter("condicion");
            column.setFilterMatchMode("contains");
            column.setSortBy("condicion");
            column.setProperty("condicion");
            column.setView("CONDICION");
            dataTableColumns.add(column);
            
            column = new DataTableColumn();
            column.setId("CONDICION_FECHA");
            column.setCaption("Condicion Fecha");
            column.setRendered(true);
            column.setResizable(true);
            column.setWidth(80);
            column.setStyle("");
            column.setPriority(1);
            column.setFilter("condicionFecha");
            column.setFilterMatchMode("contains");
            column.setSortBy("condicionFecha");
            column.setProperty("condicionFecha");
            column.setView("FECHA");
            dataTableColumns.add(column);
            
            column = new DataTableColumn();
            column.setId("PROPIETARIO");
            column.setCaption("Propietario");
            column.setRendered(true);
            column.setResizable(true);
            column.setWidth(80);
            column.setStyle("");
            column.setPriority(1);
            column.setFilter("propietario");
            column.setFilterMatchMode("contains");
            column.setSortBy("propietario");
            column.setProperty("propietario");
            column.setView("EMPRESA");
            dataTableColumns.add(column);
            
            column = new DataTableColumn();
            column.setId("PROPIETARIO_FECHA");
            column.setCaption("Propietario Desde");
            column.setRendered(true);
            column.setResizable(true);
            column.setWidth(80);
            column.setStyle("");
            column.setPriority(2);
            column.setFilter("propietarioFecha");
            column.setFilterMatchMode("contains");
            column.setSortBy("propietarioFecha");
            column.setProperty("propietarioFecha");
            column.setView("FECHA");
            dataTableColumns.add(column);
            
          /*  column = new DataTableColumn();
            column.setId("FECHA");
            column.setCaption("Fecha");
            column.setRendered(true);
            column.setResizable(true);
            column.setWidth(80);
            column.setStyle("");
            column.setPriority(1);
            column.setFilter("fecha");
            column.setFilterMatchMode("contains");
            column.setSortBy("fecha");
            column.setProperty("fecha");
            column.setView("FECHA");
            dataTableColumns.add(column);*/
            
        }
        System.err.println("GET COLMS 2");
        return dataTableColumns;
     }
    
    /*public void baja() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            selected = facade.baja(selected.getId());
            JsfUtil.addSuccessMessage("Stock dado de baja correctamente.");
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    } 
    
    public void reincorporarBaja() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            selected = facreincorporarBajaBaja(selected.getId());
            JsfUtil.addSuccessMessage("Stock recuperado correctamente.");
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    } */
    
    public void selectMostrarNingunaColumna() {
        dataTableSelectedColumns = new ArrayList();
    }
    
    public void selectMostrarTodasColumnas() {
        dataTableSelectedColumns = new ArrayList();
        for (DataTableColumn dataTableColumn : getDataTableColumns()) {
            if (dataTableColumn.getPriority() <= 1) {
                dataTableSelectedColumns.add(dataTableColumn);
            }
        }
    }
    
    public void cambiarSeleccionColumnas() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            ArgumentValidator.isNotEmpty(getDataTableSelectedColumns(), "Seleccione almenos una columna.");
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void prepareNuevo() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            nuevoStockBean.prepare();
            nuevoStockBean.setOnGuardarListener( (event) -> {
                items = null;
                accionActual = AccionPanel.LISTA;
                PrimeFaces.current().ajax().update(":mi-layout-contenido");
            });
            nuevoStockBean.setOnCancelarListener( (event) -> {
                accionActual = AccionPanel.LISTA;
                PrimeFaces.current().ajax().update(":mi-layout-contenido");
            });
            accionActual = AccionPanel.CREAR;
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    } 
    
    public void prepareModificar() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            modificarStockBean.prepare(getSelected().getId());
            modificarStockBean.setOnGuardarListener( (event) -> {
                items = null;
                accionActual = AccionPanel.LISTA;
                PrimeFaces.current().ajax().update(":mi-layout-contenido");
            });
            modificarStockBean.setOnCancelarListener( (event) -> {
                accionActual = AccionPanel.LISTA;
                PrimeFaces.current().ajax().update(":mi-layout-contenido");
            });
            accionActual = AccionPanel.MODIFICAR;
        } catch (Exception ex) {            
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void prepareBaja() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            bajaStockBean.prepare(selecteds);
            bajaStockBean.setOnGuardarListener( (event) -> {
                items = null;
                dialogoActual = AccionDialogo.NONE;
                PrimeFaces.current().ajax().update(":mi-layout-contenido");
            });
            bajaStockBean.setOnCancelarListener( (event) -> {
                dialogoActual = AccionDialogo.NONE;
                PrimeFaces.current().ajax().update(":mi-layout-contenido");
            });
            dialogoActual = AccionDialogo.BAJA;
        } catch (Exception ex) {            
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void prepareAnularBaja() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            anularBajaStockBean.prepare(selecteds);
            anularBajaStockBean.setOnGuardarListener( (event) -> {
                items = null;
                dialogoActual = AccionDialogo.NONE;
                PrimeFaces.current().ajax().update(":mi-layout-contenido");
            });
            anularBajaStockBean.setOnCancelarListener( (event) -> {
                dialogoActual = AccionDialogo.NONE;
                PrimeFaces.current().ajax().update(":mi-layout-contenido");
            });
            dialogoActual = AccionDialogo.ANULAR_BAJA;
        } catch (Exception ex) {            
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void prepareBloquear() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            bloquearStockBean.prepare(selecteds);
            bloquearStockBean.setOnGuardarListener( (event) -> {
                items = null;
                dialogoActual = AccionDialogo.NONE;
                PrimeFaces.current().ajax().update(":mi-layout-contenido");
            });
            bloquearStockBean.setOnCancelarListener( (event) -> {
                dialogoActual = AccionDialogo.NONE;
                PrimeFaces.current().ajax().update(":mi-layout-contenido");
            });
            dialogoActual = AccionDialogo.BLOQUEAR;
        } catch (Exception ex) {            
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void prepareDesbloquear() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            desbloquearStockBean.prepare(selecteds);
            desbloquearStockBean.setOnGuardarListener( (event) -> {
                items = null;
                dialogoActual = AccionDialogo.NONE;
                PrimeFaces.current().ajax().update(":mi-layout-contenido");
            });
            desbloquearStockBean.setOnCancelarListener( (event) -> {
                dialogoActual = AccionDialogo.NONE;
                PrimeFaces.current().ajax().update(":mi-layout-contenido");
            });
            dialogoActual = AccionDialogo.DESBLOQUEAR;
        } catch (Exception ex) {            
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void prepareCambiarCondicion() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            cambiarCondicionStockBean.prepare(selecteds);
            cambiarCondicionStockBean.setOnGuardarListener( (event) -> {
                items = null;
                dialogoActual = AccionDialogo.NONE;
                PrimeFaces.current().ajax().update(":mi-layout-contenido");
            });
            cambiarCondicionStockBean.setOnCancelarListener( (event) -> {
                dialogoActual = AccionDialogo.NONE;
                PrimeFaces.current().ajax().update(":mi-layout-contenido");
            });
            dialogoActual = AccionDialogo.CAMBIAR_CONDICION;
        } catch (Exception ex) {            
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void prepareTraslado() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            trasladoStockBean.prepare(selecteds);
            trasladoStockBean.setOnGuardarListener( (event) -> {
                items = null;
                dialogoActual = AccionDialogo.NONE;
                PrimeFaces.current().ajax().update(":mi-layout-contenido");
            });
            trasladoStockBean.setOnCancelarListener( (event) -> {
                dialogoActual = AccionDialogo.NONE;
                PrimeFaces.current().ajax().update(":mi-layout-contenido");
            });
            dialogoActual = AccionDialogo.TRASLADO;
        } catch (Exception ex) {            
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void prepareVerHistoricoPropietarios() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            ArgumentValidator.isNotEmpty(selecteds, "Seleccione alguna linea.");
            detallePropietariosBean.prepare(getSelected().getId());
            cambiarPropietarioStockBean.setOnGuardarJsfUpdate(":mi-layout-contenido");
            detallePropietariosBean.setOnGuardarListener( (event) -> {
                items = null;
                dialogoActual = AccionDialogo.NONE;
                //PrimeFaces.current().ajax().update(":mi-layout-contenido");
            });
            cambiarPropietarioStockBean.setOnCancelarJsfUpdate(":mi-layout-contenido");
            detallePropietariosBean.setOnCancelarListener( (event) -> {
                dialogoActual = AccionDialogo.NONE;
                //PrimeFaces.current().ajax().update(":mi-layout-contenido");
            });
            dialogoActual = AccionDialogo.DETALLE_PROPIETARIO;
        } catch (Exception ex) {            
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void prepareVerHistoricoEstados() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            ArgumentValidator.isNotEmpty(selecteds, "Seleccione alguna linea.");
            historicoEstadosBean.prepare(getSelected().getId());
            dialogoActual = AccionDialogo.HISTORICO_ESTADOS;
        } catch (Exception ex) {            
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void prepareVerHistoricoFlotas() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            ArgumentValidator.isNotEmpty(selecteds, "Seleccione alguna linea.");
            historicoFlotasBean.prepare(getSelected().getId());
            cambiarFlotaStockBean.setOnGuardarJsfUpdate(":mi-layout-contenido");
            historicoFlotasBean.setOnGuardarListener( (event) -> {
                items = null;
                dialogoActual = AccionDialogo.NONE;
            });
            cambiarFlotaStockBean.setOnCancelarJsfUpdate(":mi-layout-contenido");
            historicoFlotasBean.setOnCancelarListener( (event) -> {
                dialogoActual = AccionDialogo.NONE;
            });
            dialogoActual = AccionDialogo.HISTORICO_FLOTA;
        } catch (Exception ex) {            
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void prepareCambiarFlota() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            cambiarFlotaStockBean.prepare(selecteds);
            cambiarFlotaStockBean.setOnGuardarListener( (event) -> {
                items = null;
                dialogoActual = AccionDialogo.NONE;
                PrimeFaces.current().ajax().update(":mi-layout-contenido");
            });
            cambiarFlotaStockBean.setOnCancelarListener( (event) -> {
                dialogoActual = AccionDialogo.NONE;
                PrimeFaces.current().ajax().update(":mi-layout-contenido");
            });
            dialogoActual = AccionDialogo.CAMBIAR_FLOTA;
        } catch (Exception ex) {            
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    
    public void prepareCambiarPropietario() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            cambiarPropietarioStockBean.prepare(selecteds);
            cambiarPropietarioStockBean.setOnGuardarListener( (event) -> {
                items = null;
                dialogoActual = AccionDialogo.NONE;
                PrimeFaces.current().ajax().update(":mi-layout-contenido");
            });
            cambiarPropietarioStockBean.setOnCancelarListener( (event) -> {
                dialogoActual = AccionDialogo.NONE;
                PrimeFaces.current().ajax().update(":mi-layout-contenido");
            });
            dialogoActual = AccionDialogo.CAMBIAR_PROPIETARIO;
        } catch (Exception ex) {            
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    
    public void deshacerUltimoCambioPropietario() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            List<Integer> ids = new ArrayList();
            for (StockDto selected : selecteds) {
                ids.add(selected.getId());
            }
            facade.deshacerUltimoCambioPropietario(ids);
        } catch (Exception ex) {            
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void prepareMostrarHistorico() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            
        } catch (Exception ex) {            
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    public boolean isMostrarBajas() {
        return mostrarBajas;
    }

    public void invertirMostrarBajas() {
        this.mostrarBajas = !this.mostrarBajas;
        items = null;  
    }
    
    public String getFiltro() {
        return filtro;
    }

    public void setFiltro(String filtro) {
        this.filtro = filtro;
    }

    public String getFiltroNumSerie() {
        return filtroNumSerie;
    }

    public void setFiltroNumSerie(String filtroNumSerie) {
        this.filtroNumSerie = filtroNumSerie;
    }
    
    public String getAccionActual() {
        return accionActual.getId();
    }

    public AccionDialogo getDialogoActual() {
        return dialogoActual;
    }
    
    @FacesConverter(value="gestionStockColumnConverter")
    public static class GestionStockColumnConverter implements Converter {
        
        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            GestionStockBean bean = (GestionStockBean) facesContext.getApplication().getELResolver().
                        getValue(facesContext.getELContext(), null, "gestionStockBean");
            return bean.getDataTableColumn(value);
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            
            if (object == null) {
                return null;
            }
            if (object instanceof DataTableColumn) {
                DataTableColumn o = (DataTableColumn) object;
                return o.getId();
            } else {
                return null;
            }
        }
    }
    
}