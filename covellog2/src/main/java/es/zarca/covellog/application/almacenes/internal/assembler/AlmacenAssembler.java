package es.zarca.covellog.application.almacenes.internal.assembler;

import es.zarca.covellog.application.almacenes.exception.AlmacenNotExistException;
import es.zarca.covellog.application.almacenes.form.AlmacenForm;
import es.zarca.covellog.application.empresas.exception.EmpresaNotExistException;
import es.zarca.covellog.domain.model.almacen.Almacen;
import es.zarca.covellog.domain.model.almacen.AlmacenRepository;
import es.zarca.covellog.domain.model.empresa.Empresa;
import es.zarca.covellog.domain.model.empresa.EmpresaRepository;
import javax.inject.Inject;

/**
 *
 * @author francisco
 */
public class AlmacenAssembler {
    @Inject
    AlmacenRepository almacenRepository;
    @Inject
    EmpresaRepository empresaRepository;
    
    private Empresa cargarEmpresa(Integer empresaId) {
        System.err.println("mira " + empresaId);
        System.err.println("mira2 " + empresaRepository);
        Empresa empresa = empresaRepository.find(empresaId);
        if (empresa == null) {
            throw new EmpresaNotExistException(empresaId);
        }
        return empresa;
    }
    
    private Almacen formToAlmacen(Almacen almacen, AlmacenForm form) {
        almacen.setEmpresa(cargarEmpresa(form.getEmpresaId()));
        almacen.setNombre(form.getNombre());
        //List<Contacto> contactos = cargarContactos(almacen.getEmpresa(), form.getContactosIds())
        //almacen.setContactos(new ArrayList());
        almacen.setDescripcion(form.getDescripcion());
        almacen.setHorario(form.getHorario());
        //almacen.setDireccion( DireccionAssembler.  form.getDireccion());
        //almacen.setObservaciones(form.getObservaciones());
        
        return almacen;
    }
    
    /*private List<Contacto> cargarContactos(Empresa empresa, List<Integer> contactoIds) {
        List<Contacto> contactos = new ArrayList<>();
        if ((contactoIds != null) && ( empresa != null)) {
            contactoIds.forEach(contactoId -> {         
                contactos.add(empresa.getContacto(contactoId));
            });
        }
        return contactos;
    }*/
    
    public Almacen createAlmacen(AlmacenForm form) {
        if (form == null) {
            return null;
        }
        return formToAlmacen(new Almacen(), form);
    }
    
    public Almacen updateAlmacen(Integer almacenId, AlmacenForm form) {
        if (form == null) {
            return null;
        }
        Almacen almacen = almacenRepository.find(almacenId);
        if (almacen == null) {
            throw new AlmacenNotExistException(almacenId);
        }
        return formToAlmacen(almacen, form);
    }
    
}
