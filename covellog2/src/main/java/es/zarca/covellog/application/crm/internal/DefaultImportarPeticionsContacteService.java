/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.zarca.covellog.application.crm.internal;

import es.zarca.covellog.application.crm.ImportarPeticionsContacteService;
import es.zarca.covellog.application.crm.event.NovaOportunitatEvent;
import es.zarca.covellog.application.crm.exception.CalculUltimaPeticioException;
import es.zarca.covellog.application.crm.exception.ImportarPeticionsContactePendentsLockedException;
import es.zarca.covellog.domain.model.adreces.exception.AppNotFoundException;
import es.zarca.covellog.domain.model.app.App;
import es.zarca.covellog.domain.model.app.AppRepository;
import es.zarca.covellog.domain.model.comercials.comercial.ComercialRepository;
import es.zarca.covellog.domain.model.crm.oportunitat.EstatOportunitatRepository;
import es.zarca.covellog.domain.model.crm.oportunitat.Oportunitat;
import es.zarca.covellog.domain.model.crm.oportunitat.OportunitatRepository;
import es.zarca.covellog.domain.model.crm.oportunitat.OrigenOportunitatRepository;
import es.zarca.covellog.domain.model.clients.potencial.Potencial2;
import es.zarca.covellog.domain.model.comercials.divisiocomercial.DivisioComercialRepository;
import es.zarca.covellog.domain.model.crm.peticiocontacte.PeticioContacte;
import es.zarca.covellog.domain.model.crm.peticiocontacte.PeticioContacteRepository;
import es.zarca.covellog.domain.model.clients.potencial.PotencialRepository;
import es.zarca.covellog.domain.model.comercials.comercial.Comercial;
import es.zarca.covellog.domain.model.crm.repartidoroportunitats.RepartidorOportunitats;
import es.zarca.covellog.domain.model.crm.repartidoroportunitats.RepartidorOportunitatsRepository;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;

/**
 *
 * @author francisco
 */
@Stateless
public class DefaultImportarPeticionsContacteService implements ImportarPeticionsContacteService {
    
    private static final Logger LOGGER = Logger.getLogger(DefaultImportarPeticionsContacteService.class.getName());
    
    @Inject
    private AppRepository appRepository;
    @Inject
    private PeticioContacteRepository peticioContacteRepository;
    @Inject
    private OportunitatRepository oportunitatRepository;
    @Inject
    private EstatOportunitatRepository estatOportunitatRepository;
    @Inject
    private DivisioComercialRepository divisioComercialRepository;
    @Inject
    private OrigenOportunitatRepository origenOportunitatRepository;
    @Inject
    private ComercialRepository comercialRepository;
    @Inject
    private PotencialRepository potencialRepository;
    @Inject
    private RepartidorOportunitatsRepository repartidorOportunitatsRepository;
    @Inject
    Event<NovaOportunitatEvent> novaOportunitatEvent;
        
    @Override
    public Integer importarPeticionsContactePendents() {
        List<Comercial> comercials = comercialRepository.findAll();
        for (Comercial comercial : comercials) {
            System.out.println("**************" + comercial.getNombreCompleto());
        }
        int quantitat = -1;
        App app = appRepository.find("COVELLOG-2");        
        if (app == null) {
            throw new AppNotFoundException("COVELLOG-2");
        }        
        if ("true".equals(app.getPropietat("importarPeticionsContactePendentsLocked"))) {
            throw new ImportarPeticionsContactePendentsLockedException();
        }
        app.setPropietat("importarPeticionsContactePendentsLocked", "true");
        try {
            LOGGER.log(Level.SEVERE,"----- empiezo");
            List<PeticioContacte> peticions = peticioContacteRepository.findBySubmitTimeMajor(
                new BigDecimal(app.getPropietat("UltimaPeticioContacteConvertida")), 
                0, 
                100
            );
            LOGGER.log(Level.SEVERE,"----- tengo 100");
            if ( peticions != null && !peticions.isEmpty() ) {
                quantitat = peticions.size();
                LOGGER.log(Level.SEVERE,"----- importar");
                importarPeticionsContacte(peticions);
                LOGGER.log(Level.SEVERE,"----- in importar");
                BigDecimal ultima =  peticions.get(peticions.size() - 1).getSubmitTime();
                if (ultima == null) {
                    throw new CalculUltimaPeticioException(peticions, peticions.size() - 1);
                }
                app.setPropietat("UltimaPeticioContacteConvertida", ultima.toPlainString());
                LOGGER.log(Level.SEVERE,"----- termino");
            } else {
                return 0;
            }
        } finally {
            app.setPropietat("importarPeticionsContactePendentsLocked", "false");
        }
        return quantitat;
    }
    
    private void importarPeticionsContacte(List<PeticioContacte> peticions) {
        if (peticions != null) {
            for (PeticioContacte peticio : peticions) {
                System.out.println(peticio.getSubmitTime());
                System.out.println("Nom: " + peticio.getNom());
                System.out.println("Email: " + peticio.getEmail());
                System.out.println("Empresa: " + peticio.getEmpresa());
                //System.out.println("Assumpte: " + peticio.getAssumpte());
                //System.out.println("Missatje: " + peticio.getMissatje());
                System.out.println("IP: " + peticio.getIp());   
                Oportunitat oportunitat = new Oportunitat();
                if (peticio.getAssumpte().isEmpty()) {
                    oportunitat.setConcepte("SIN TITULO");
                } else {
                    oportunitat.setConcepte(peticio.getAssumpte());
                }

                oportunitat.setDetall(peticio.getMissatje());
                oportunitat.setDataOportunitat(peticio.getSubmitTimeDate());
                oportunitat.setDivisioComercial(divisioComercialRepository.find("VCONT"));
                oportunitat.setEstat(estatOportunitatRepository.find("NOGEST"));
                RepartidorOportunitats repartidorOportunitats = repartidorOportunitatsRepository.find("DEFAULT");
                repartidorOportunitats.repartirOportunitat(oportunitat);
                oportunitat.setOrigen(origenOportunitatRepository.find("EMAIL"));
                Potencial2 potencial = new Potencial2();
                if (!peticio.getEmpresa().isEmpty()) {
                    potencial.setNom(peticio.getEmpresa());                        
                } else if (!peticio.getNom().isEmpty()) {
                    potencial.setNom(peticio.getNom());                        
                } else {
                    potencial.setNom("DESCONOCIDO");
                }
                Potencial2 potencialExistent = potencialRepository.findByNom(potencial.getNom());
                if (potencialExistent == null) {
                    potencialRepository.store(potencial);
                    oportunitat.setPotencial(potencial);
                } else {
                    oportunitat.setPotencial(potencialExistent);
                }
                oportunitatRepository.store(oportunitat);
                novaOportunitatEvent.fire(new NovaOportunitatEvent(oportunitat));
            }
        }
    }

    @Override
    public BigDecimal getUltimaPeticioContacteConvertida() {
        App app = appRepository.find("COVELLOG-2");        
        if (app == null) {
            throw new AppNotFoundException("COVELLOG-2");
        }
        String valor = app.getPropietat("UltimaPeticioContacteConvertida");       
        LOGGER.log(Level.FINE, "getUltimaPeticioContacteConvertida() => {0}", valor);
        BigDecimal result;
        if (valor != null) {
            result = new BigDecimal(valor);
        } else {
            app.setPropietat("UltimaPeticioContacteConvertida", "0");
            result = new BigDecimal(BigInteger.ZERO);
        }
        return result;
    }
    
}
