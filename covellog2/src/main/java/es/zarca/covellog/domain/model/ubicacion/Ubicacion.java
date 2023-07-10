package es.zarca.covellog.domain.model.ubicacion;

import es.zarca.covellog.application.exception.AppExceptionHandler;
import es.zarca.covellog.domain.model.adreces.adreça.CoordenadasGeograficas;
import es.zarca.covellog.domain.model.adreces.adreça.Direccion;
import es.zarca.covellog.domain.model.contactos.Contacto;
import es.zarca.covellog.domain.model.empresa.DobleObservacion;
import es.zarca.covellog.domain.model.empresa.Empresa;
import es.zarca.covellog.domain.model.stock.Stock;
import es.zarca.covellog.domain.model.stock.movimientos.AsignacionStock;
import es.zarca.covellog.domain.model.stock.movimientos.Movimiento;
import es.zarca.covellog.domain.model.stock.movimientos.MovimientoLinea;
import es.zarca.covellog.domain.model.stock.movimientos.MovimientoTipo;
import es.zarca.covellog.domain.model.stock.reservas.Reserva;
import es.zarca.covellog.domain.model.stock.reservas.ReservaEstado;
import es.zarca.covellog.domain.model.stock.reservas.ReservaLinea;
import es.zarca.covellog.domain.model.ubicacion.exception.ProductoNoEstaEnUbicacionException;
import es.zarca.covellog.infrastructure.assertions.ArgumentValidator;
import es.zarca.covellog.infrastructure.log.Capa;
import es.zarca.covellog.infrastructure.log.FunctionLog;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
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
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author francisco
 */
@Entity
@Table(name = "ubicacion")
@Inheritance(strategy = InheritanceType.JOINED)
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ubicacion.findAll", query = "SELECT u FROM Ubicacion u"),
    @NamedQuery(name = "Ubicacion.findById", query = "SELECT u FROM Ubicacion u WHERE u.id = :id"),
    @NamedQuery(name = "Ubicacion.findByNombre", query = "SELECT u FROM Ubicacion u WHERE u.nombre = :nombre"),
})
public abstract class Ubicacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="empresa_id")
    private Empresa empresa;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "descripcion")
    private String descripcion;
    private Direccion direccion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "horario")
    private String horario;
    private CoordenadasGeograficas coordenadas;
    private DobleObservacion observaciones;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ubicacion", orphanRemoval = true)
    private List<Movimiento> movimientos;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ubicacion")
    private List<Reserva> reservas;
    
    public Ubicacion() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public CoordenadasGeograficas getCoordenadas() {
        return coordenadas;
    }

    public void setCoordenadas(CoordenadasGeograficas coordenadas) {
        this.coordenadas = coordenadas;
    }

    public DobleObservacion getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(DobleObservacion observaciones) {
        System.err.println("COJONES Ubicacion.setObservaciones " + observaciones.getObsInternas());
        this.observaciones = observaciones;
    }

    public List<Movimiento> getMovimientos() {
        return movimientos;
    }

    public List<Reserva> getReservas() {
        return reservas;
    }

    public abstract List<? extends Contacto> getContactos();
    
    public abstract void setContactos(List<Contacto> contacto);
    
    public abstract void addContacto(Contacto contacto);
    
    public abstract void removeContacto(Contacto contacto);
    
    public abstract void removeContactos();
    
    //public abstract void updateContacto(Contacto contacto);
    
    public void copyFrom(Ubicacion ubicacion) {
        nombre = ubicacion.getNombre();
        descripcion = ubicacion.getDescripcion();
        horario = ubicacion.getHorario();
        direccion = ubicacion.getDireccion();
        coordenadas = ubicacion.getCoordenadas();
        observaciones = ubicacion.getObservaciones();
        removeContactos();
        for (Contacto contacto : ubicacion.getContactos()) {
            addContacto(contacto);
        }
        
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + Objects.hashCode(this.id);
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
        if (obj instanceof Ubicacion) {
            final Ubicacion other = (Ubicacion) obj;
            if (Objects.equals(this.id, other.id)) {
                return true;
            }
        }
        return false;
    }

    public void entrada(
        Date fecha, 
        String booking,
        Empresa cliente, 
        Empresa transportista, 
        String chofer, 
        String matricula, 
        String observaciones, 
        List<AsignacionStock> asignaciones,
        Reserva reserva
    ) {
        FunctionLog log = new FunctionLog(Capa.DOMAIN);
        try {
            Movimiento movimiento = new Movimiento(
                MovimientoTipo.ENTRADA, 
                booking,
                fecha, 
                this, 
                cliente, 
                transportista, 
                chofer, 
                matricula, 
                observaciones, 
                asignaciones,
                reserva
            );
            movimientos.add(movimiento);
            notificarNuevaEntrada(movimiento);
        } catch (Exception e) {
            AppExceptionHandler.handleException(e);
        } finally {
            log.finish();
        }
    }
    
    public void entrada(
        Date fecha, 
        String booking,
        Empresa cliente, 
        String transportistaNombre, 
        String chofer, 
        String matricula, 
        String observaciones, 
        List<AsignacionStock> asignaciones,
        Reserva reserva
    ) {
        FunctionLog log = new FunctionLog(Capa.DOMAIN);
        try {
            Movimiento movimiento = new Movimiento(
                MovimientoTipo.ENTRADA, 
                booking, 
                fecha, 
                this, 
                cliente, 
                transportistaNombre, 
                chofer, 
                matricula, 
                observaciones, 
                asignaciones,
                reserva
            );
            movimientos.add(movimiento);
            notificarNuevaEntrada(movimiento);
        } catch (Exception e) {
            AppExceptionHandler.handleException(e);
        } finally {
            log.finish();
        }
    }
    
    public void anularMovimiento(Movimiento movimiento) {
        FunctionLog log = new FunctionLog(Capa.DOMAIN);
        try {
            ArgumentValidator.isNotNull(movimiento, "No se puede anular el movimiento porque es null.");
            movimiento.anular();
            movimientos.remove(movimiento);
        } catch (Exception e) {
            AppExceptionHandler.handleException(e);
        } finally {
            log.finish();
        }  
    }
    
    private void validarSalida(List<AsignacionStock> asignaciones) {
        for (AsignacionStock asignacion : asignaciones) {
            ArgumentValidator.isNotEmpty(asignacion.getBooking(), "No puede hacer salidas de productos sin booking.");
            ArgumentValidator.isNotNull(asignacion.getStock(), "No puede hacer salidas de productos sin asignar.");
            if (!asignacion.getStock().getUbicacion().equals(this)) {
                throw new ProductoNoEstaEnUbicacionException(asignacion.getStock(), this);
            }
        }
    }
    
    public void salida(
        Date fecha, 
        String booking,
        Empresa cliente, 
        Empresa transportista, 
        String chofer, 
        String matricula, 
        String observaciones, 
        List<AsignacionStock> asignaciones,
        Reserva reserva
    ) {
        FunctionLog log = new FunctionLog(Capa.DOMAIN);        
        try {
            validarSalida(asignaciones);
            Movimiento movimiento = new Movimiento(
                MovimientoTipo.SALIDA, 
                booking, 
                fecha, 
                this, 
                cliente, 
                transportista, 
                chofer, 
                matricula, 
                observaciones, 
                asignaciones,
                reserva
            );
            movimientos.add(movimiento);
            notificarNuevaSalida(movimiento);
        } catch (Exception e) {
            AppExceptionHandler.handleException(e);
        } finally {
            log.finish();
        }
    }
    
    public void salida(
        Date fecha, 
        String booking,
        Empresa cliente, 
        String transportistaNombre, 
        String chofer, 
        String matricula, 
        String observaciones, 
        List<AsignacionStock> asignaciones,
        Reserva reserva
    ) {
        FunctionLog log = new FunctionLog(Capa.DOMAIN);
        try {
            validarSalida(asignaciones);
            Movimiento movimiento = new Movimiento(
                MovimientoTipo.SALIDA, 
                booking, 
                fecha, 
                this, 
                cliente, 
                transportistaNombre, 
                chofer, 
                matricula, 
                observaciones, 
                asignaciones,
                reserva
            );
            movimientos.add(movimiento);
            notificarNuevaSalida(movimiento);
        } catch (Exception e) {
            AppExceptionHandler.handleException(e);
        } finally {
            log.finish();
        }
    }
    
    public void salida(Date fecha, Empresa cliente, String transportistaNombre, String chofer, String matricula, String observaciones, HashMap<String, Stock> stocks) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void notificarNuevaEntrada(Movimiento movimiento) {
        notificarMovimientoRealizado(movimiento);
    }

    public void notificarNuevaSalida(Movimiento movimiento) {
        notificarMovimientoRealizado(movimiento);
    }
 
    private void notificarMovimientoRealizado(Movimiento movimiento) {
        FunctionLog log = new FunctionLog(Capa.DOMAIN);
        movimiento.notificarMovimientoRealizado();
        log.finish();
    }
}