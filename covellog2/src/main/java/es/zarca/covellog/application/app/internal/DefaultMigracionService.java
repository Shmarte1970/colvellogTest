package es.zarca.covellog.application.app.internal;

import es.zarca.covellog.application.app.MigracionService;
import es.zarca.covellog.domain.model.empresa.Cif;
import es.zarca.covellog.domain.model.empresa.EmpresaRepository;
import es.zarca.covellog.domain.model.empresa.EmpresaTipoRolRepository;
import es.zarca.covellog.domain.model.empresa.cliente.Cliente;
import es.zarca.covellog.domain.model.empresa.cliente.CodigoCliente;
import es.zarca.covellog.domain.model.idiomas.idioma.CodigoIdioma;
import es.zarca.covellog.domain.model.idiomas.idioma.Idioma;
import es.zarca.covellog.domain.model.idiomas.idioma.IdiomaRepository;
import es.zarca.covellog.domain.model.migracion.Empresa;
import es.zarca.covellog.infrastructure.persistence.migracion.EmpresaFileRepository;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author francisco
 */
@Stateless
public class DefaultMigracionService implements MigracionService {

    @Inject
    EmpresaRepository empresaRepository;
    @Inject
    IdiomaRepository idiomaRepository;
    @Inject
    EmpresaTipoRolRepository empresaTipoRolRepository;
    
    @Override
    public void migrar() {
        EmpresaFileRepository repository = new EmpresaFileRepository();
        List<Empresa> empresas;
        try {
            empresas = repository.findAll();
            for (Empresa empresa : empresas) {   
                es.zarca.covellog.domain.model.empresa.Empresa empresa2 = new es.zarca.covellog.domain.model.empresa.Empresa();
                empresa2.setId(empresa.getId());
                
                if (empresa.getEsClient()) {
                    Cliente cliente = new Cliente();
                    if (!empresa.getCodiClient().isEmpty()) {
                        cliente.setCodigoCliente(new CodigoCliente(empresa.getCodiClient()));
                    }
                    empresa2.setCliente(cliente);
                    
                }
                
                try {
                    empresa2.setCif(new Cif(empresa.getCif()));
                } catch (Exception e) {
                    empresa2.setCif(null);
                }
               
                empresa2.setNombre(empresa.getNom());
                Idioma idioma = idiomaRepository.find(new CodigoIdioma("es_ES"));
                empresa2.setIdioma(idioma);
                empresaRepository.store(empresa2);
            }
        } catch (Exception ex) {
            Logger.getLogger(DefaultMigracionService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
