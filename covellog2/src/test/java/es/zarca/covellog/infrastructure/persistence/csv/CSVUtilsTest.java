package es.zarca.covellog.infrastructure.persistence.csv;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;


/**
 *
 * @author francisco
 */
public class CSVUtilsTest {

    private static final Logger LOG = Logger.getLogger(CSVUtilsTest.class.getName());
    
    public CSVUtilsTest() {
    }

   
    /**
     * Test of parseFile method, of class CSVUtils.
     */
    @Test
    public void testParseFile() throws Exception {
        /*String csvFile = "/home/DATOS-RAID1/Informatica/Proyectos/Covellog2/covellog2/BD/Migracion Covellog/empresa.txt";
        LOG.log(Level.SEVERE, "vamos: {0}", csvFile);
        List<List<String>> tabla = CSVUtils.parseFile(csvFile);
        for (List<String> tupla : tabla) {
            for (String atributo : tupla) {
                LOG.log(Level.SEVERE, "atributo: {0}", atributo);
            }
            
        }*/
    }

    /**
     * Test of parseLine method, of class CSVUtils.
     */
    @Test
    public void testParseLine_String() {
    }

    /**
     * Test of parseLine method, of class CSVUtils.
     */
    @Test
    public void testParseLine_String_char() {
    }

    /**
     * Test of parseLine method, of class CSVUtils.
     */
    @Test
    public void testParseLine_3args() {
    }

}
