package es.zarca.covellog.infrastructure.persistence.migracion;

import es.zarca.covellog.domain.model.migracion.Empresa;
import es.zarca.covellog.infrastructure.persistence.csv.CSVUtils;
import es.zarca.covellog.infrastructure.util.DateUtil.DateUtil;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author francisco
 */
public class EmpresaFileRepository {
    private final String csvFile = "/home/DATOS-RAID1/Informatica/Proyectos/Covellog2/covellog2/BD/Migracion Covellog/empresa.txt";
    
    public Empresa find(Integer id) throws ParseException, Exception {
        List<Empresa> lista = new ArrayList<>();
        List<List<String>> tabla = CSVUtils.parseFile(csvFile);
        for (List<String> list : tabla) {
            if (Integer.valueOf(list.get(0)).equals(id)) {
                return toEmpresa(list);
            }
        }
        return null;
    }
    
    public List<Empresa> findAll() throws Exception {
        List<Empresa> lista = new ArrayList<>();
        List<List<String>> tabla = CSVUtils.parseFile(csvFile);
        for (List<String> list : tabla) {
            lista.add(toEmpresa(list));
        }
        return lista;
    }
    
    private Empresa toEmpresa(List<String> tupla) throws ParseException {
        LOG.log(Level.SEVERE, "Tupla: {0}", tupla);
        Empresa empresa = new Empresa();
        empresa.setId(Integer.parseInt(tupla.get(0)));
        if (Integer.parseInt(tupla.get(0)) == 292) {
            System.err.println("kiko");
        }
        empresa.setCodiClient(tupla.get(1));
        empresa.setPotencialId(Integer.parseInt(tupla.get(2)));
        empresa.setComercialId(Integer.parseInt(tupla.get(3)));
        empresa.setNom(tupla.get(4));
        empresa.setCif(tupla.get(5));
        empresa.setTipusViaId(Integer.parseInt(tupla.get(6)));
        empresa.setDireccio(tupla.get(7));
        empresa.setDireccio2(tupla.get(8));
        empresa.setLocalitat(tupla.get(9));
        empresa.setCodiPostal(tupla.get(10));
        empresa.setProvincia(tupla.get(11));
        empresa.setPaisId(tupla.get(12));
        empresa.setAtencioc(tupla.get(13));
        empresa.setcTipusViaId(Integer.parseInt(tupla.get(14)));
        empresa.setcDireccio(tupla.get(15));
        empresa.setcDireccio2(tupla.get(16));
        empresa.setcLocalitat(tupla.get(17));
        empresa.setcCodiPostal(tupla.get(18));
        empresa.setcProvincia(tupla.get(19));
        empresa.setcPaisId(tupla.get(20));
        empresa.setTelefon1(tupla.get(21));
        empresa.setTelefon2(tupla.get(22));
        empresa.setFax(tupla.get(23));
        empresa.setEmail(tupla.get(24));
        empresa.setEmailFact(tupla.get(25));
        empresa.setSwift(tupla.get(26));
        empresa.setIban(tupla.get(27));
        empresa.setCcorrent(tupla.get(29));
        empresa.setCompteTransfer(tupla.get(30));
        empresa.setObservacions(tupla.get(31));
        empresa.setFormaPagllogId(Integer.parseInt(tupla.get(32)));
        empresa.setVenciLlogId(Integer.parseInt(tupla.get(33)));
        empresa.setDiaVenciLlog(Integer.parseInt(tupla.get(34)));
        empresa.setEntAvancat("T".equals(tupla.get(35)));
        empresa.setDevAvancat("T".equals(tupla.get(36)));
        empresa.setMesosAvancat(Integer.parseInt(tupla.get(37)));
        empresa.setMunAvancat("T".equals(tupla.get(38)));
        empresa.setDesAvancat("T".equals(tupla.get(39)));
        empresa.setTipusFacturacioId(Integer.parseInt(tupla.get(40)));
        empresa.setFormaPagCompId(Integer.parseInt(tupla.get(41)));
        empresa.setVenciCompId(Integer.parseInt(tupla.get(42)));
        empresa.setDiaVenciComp(Integer.parseInt(tupla.get(43)));
        empresa.setTipusTarifa(tupla.get(44));
        empresa.setTipus(tupla.get(45));
        empresa.setEsClient("T".equals(tupla.get(46)));
        empresa.setEsProveidor("T".equals(tupla.get(47)));
        empresa.setEsDepot("T".equals(tupla.get(48)));
        empresa.setEsTransportista("T".equals(tupla.get(49)));
        empresa.setContacte(tupla.get(50));
        if (tupla.get(51).startsWith("/  /")) {
            empresa.setDataSincro(null);
        } else {
            //empresa.setDataSincro(DateUtil.stringToDate(tupla.get(51), DateUtil.FOX_DATETIME_FORMAT));
        }
        empresa.setUsuarioCreadorId(Integer.parseInt(tupla.get(52)));
        if (tupla.get(53).startsWith("/  /")) {
            empresa.setDataCreacio(null);
        } else {
            //empresa.setDataCreacio(DateUtil.stringToDate(tupla.get(53), DateUtil.FOX_DATETIME_FORMAT));
        }
        empresa.setUsuarioModificadorId(Integer.parseInt(tupla.get(54)));
        if (tupla.get(53).startsWith("/  /")) {
            empresa.setDataModificacio(null);
        } else {
           // empresa.setDataModificacio(DateUtil.stringToDate(tupla.get(55), DateUtil.FOX_DATETIME_FORMAT));
        }
        return empresa;
    }
    private static final Logger LOG = Logger.getLogger(EmpresaFileRepository.class.getName());
}
