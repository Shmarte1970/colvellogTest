package es.zarca.covellog.domain.model.stock.movimientos;

import es.zarca.covellog.domain.model.empresa.Empresa;
import es.zarca.covellog.domain.model.stock.Stock;
import es.zarca.covellog.domain.model.stock.reservas.Reserva;
import es.zarca.covellog.domain.model.ubicacion.Ubicacion;
import es.zarca.covellog.infrastructure.assertions.ArgumentValidator;
import es.zarca.covellog.infrastructure.log.Capa;
import es.zarca.covellog.infrastructure.log.FunctionLog;
import es.zarca.covellog.interfaces.web.helpers.StringUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author francisco
 */
@Entity
@Table(name = "movimiento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Movimiento.findAll", query = "SELECT m FROM Movimiento m"),
    @NamedQuery(name = "Movimiento.findById", query = "SELECT m FROM Movimiento m WHERE m.id = :id"),
    @NamedQuery(name = "Movimiento.findByTipo", query = "SELECT m FROM Movimiento m WHERE m.tipo = :tipo"),
    @NamedQuery(name = "Movimiento.findByFecha", query = "SELECT m FROM Movimiento m WHERE m.fecha = :fecha"),
    @NamedQuery(name = "Movimiento.findByClienteNombre", query = "SELECT m FROM Movimiento m WHERE m.clienteNombre = :clienteNombre"),
    @NamedQuery(name = "Movimiento.findByTransportistaNombre", query = "SELECT m FROM Movimiento m WHERE m.transportistaNombre = :transportistaNombre"),
    @NamedQuery(name = "Movimiento.findByChofer", query = "SELECT m FROM Movimiento m WHERE m.chofer = :chofer"),
    @NamedQuery(name = "Movimiento.findByMatricula", query = "SELECT m FROM Movimiento m WHERE m.matricula = :matricula")})
public class Movimiento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "tipo_id")
    private MovimientoTipo tipo;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="reserva_id")
    private Reserva reserva;
    @Basic(optional = false)
    @NotNull
    @Size(max = 50)
    @Column(name = "booking")
    private String booking;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado_id")
    private MovimientoEstado estado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="almacen_id")
    private Ubicacion ubicacion;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="cliente_id")
    private Empresa cliente;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 80)
    @Column(name = "cliente_nombre")
    private String clienteNombre;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="transportista_id")
    private Empresa transportista;
    @NotNull
    @Size(min = 2, max = 80)
    @Column(name = "transportista_nombre")
    private String transportistaNombre;
    @Basic(optional = false)
    @NotNull
    @Size(max = 80)
    @Column(name = "chofer")
    private String chofer;
    @Basic(optional = false)
    @NotNull
    @Size(max = 20)
    @Column(name = "matricula")
    private String matricula;
    @Lob
    @Column(name = "observaciones")
    private String observaciones;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "movimiento", orphanRemoval = true)
    private List<MovimientoLinea> lineas;

    public Movimiento() {
    }

    public Movimiento(
        MovimientoTipo tipo, 
        String booking,
        Date fecha, 
        Ubicacion ubicacion, 
        Empresa cliente, 
        Empresa transportista, 
        String chofer, 
        String matricula, 
        String observaciones, 
        List<AsignacionStock> asignaciones,
        Reserva reserva
    ) {
        ArgumentValidator.isNotNull(cliente, "El cliente es obligatorio.");
        ArgumentValidator.isNotNull(transportista, "El transportista es obligatorio.");
        ArgumentValidator.isNotNull(ubicacion, "El almacen es obligatorio.");
        this.tipo = tipo;
        this.booking = booking;
        this.fecha = fecha;
        this.ubicacion = ubicacion;
        this.cliente = cliente;
        this.clienteNombre = cliente.getNombre();
        this.transportista= transportista;
        this.transportistaNombre = transportista.getNombre();
        this.chofer = chofer;
        this.matricula = matricula;
        this.observaciones = observaciones;
        this.reserva = reserva;
        this.lineas = new ArrayList();
        for (AsignacionStock asignacion : asignaciones) {
            this.lineas.add(new MovimientoLinea(this, asignacion.getBooking(), asignacion.getStock()));
        }
        /*for (Map.Entry<String, Stock> entry : stocks.entrySet()) {
            String key = entry.getKey();
            Stock value = entry.getValue();
            this.lineas.add(new MovimientoLinea(this, key, value));
        }*/
        estado = MovimientoEstado.FINALIZADO;
    }
    
    public Movimiento(
        MovimientoTipo tipo, 
        String booking,
        Date fecha, 
        Ubicacion ubicacion, 
        Empresa cliente, 
        String transportistaNombre, 
        String chofer, 
        String matricula, 
        String observaciones, 
        List<AsignacionStock> asignaciones,
        Reserva reserva
    ) {
        ArgumentValidator.isNotNull(cliente, "El cliente es obligatorio.");
        ArgumentValidator.isNotNull(ubicacion, "El almacen es obligatorio.");
        this.tipo = tipo;
        this.booking = booking;
        this.fecha = fecha;
        this.ubicacion = ubicacion;
        this.cliente = cliente;
        this.cliente = cliente;
        this.clienteNombre = cliente.getNombre();
        this.transportistaNombre = transportistaNombre;
        this.chofer = chofer;
        this.matricula = matricula;
        this.observaciones = observaciones;
        this.reserva = reserva;
        this.lineas = new ArrayList();
        for (AsignacionStock asignacion : asignaciones) {
            this.lineas.add(new MovimientoLinea(this, asignacion.getBooking(), asignacion.getStock()));
        }
        /*
        for (Map.Entry<String, Stock> entry : stocks.entrySet()) {
            String key = entry.getKey();
            Stock value = entry.getValue();
            this.lineas.add(new MovimientoLinea(this, key, value));
        }*/
        estado = MovimientoEstado.FINALIZADO;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFriendlyId() {
        return "MOV" + StringUtil.padLeftZeros(getId().toString(), 10);
    }
    
    public MovimientoTipo getTipo() {
        return tipo;
    }

    public void setTipo(MovimientoTipo tipo) {
        this.tipo = tipo;
    }

    public Reserva getReserva() {
        return reserva;
    }

    public void setReserva(Reserva reserva) {
        this.reserva = reserva;
    }

    public String getBooking() {
        return booking;
    }

    public void setBooking(String booking) {
        this.booking = booking;
    }

    public MovimientoEstado getEstado() {
        return estado;
    }

    public void setEstado(MovimientoEstado estado) {
        this.estado = estado;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Ubicacion getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
    }
    
    public Empresa getCliente() {
        return cliente;
    }

    public void setCliente(Empresa cliente) {
        this.cliente = cliente;
    }

    public String getClienteNombre() {
        return clienteNombre;
    }

    public void setClienteNombre(String clienteNombre) {
        this.clienteNombre = clienteNombre;
    }

    public Empresa getTransportista() {
        return transportista;
    }

    public void setTransportista(Empresa transportista) {
        this.transportista = transportista;
    }
    
    public String getTransportistaNombre() {
        return transportistaNombre;
    }

    public void setTransportistaNombre(String transportistaNombre) {
        this.transportistaNombre = transportistaNombre;
    }

    public String getChofer() {
        return chofer;
    }

    public void setChofer(String chofer) {
        this.chofer = chofer;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public List<MovimientoLinea> getLineas() {
        return lineas;
    }

    public void setLineas(List<MovimientoLinea> lineas) {
        this.lineas = lineas;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Movimiento other = (Movimiento) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Movimiento{" + "id=" + id + ", tipo=" + tipo + ", fecha=" + fecha + '}';
    }

    public MovimientoLinea getLinea(String booking) {
        for (MovimientoLinea linea : lineas) {
            if (linea.getBooking().equals(booking)) {
                return linea;
            }
        }
        return null;
    }    

    public String getNumSerieAsignado(String booking) {
        MovimientoLinea linea = getLinea(booking);
        if ((linea != null) && (linea.getStock() != null)) {
            return linea.getStock().getNumeroSerie();
        }
        return null;
    }

    public Stock getStock(String booking) {
        MovimientoLinea movimientoLinea = getLinea(booking);
        if (movimientoLinea != null) {
            return movimientoLinea.getStock();
        }
        return null;
    }

    public void anular() {
        if (reserva != null) {
            for (MovimientoLinea linea : lineas) {
                reserva.onMovimientoAnulado(linea.getBooking());
            }
        }
    }

    public void notificarMovimientoRealizado() {
        FunctionLog log = new FunctionLog(Capa.DOMAIN);
        if (reserva != null) {
            for (MovimientoLinea linea : lineas) {                
                reserva.onMovimientoRealizado(linea.getBooking(), fecha, linea.getStock(), this);
            }
        }
        log.finish();
    }
}