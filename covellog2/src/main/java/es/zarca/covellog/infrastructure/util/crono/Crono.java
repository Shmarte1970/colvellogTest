/*
 * Proyecto Covellog2
 * 
 * Este archivo ha sido creado para ZARCA y no se puede usar sin su consentimiento.
 */
package es.zarca.covellog.infrastructure.util.crono;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public class Crono {
    private long inici;
    
    public Crono() {
        inici = System.nanoTime();
    }
    
    public void iniciar() {
        inici = System.nanoTime();
    }   
    
    public long getNanoSegons() {
        return System.nanoTime()-inici;
    }
    
    public long getMiliSegons() {
        return (System.nanoTime()-inici)/1000000l;
    }
    
    public double getSegons() {
        return (System.nanoTime()-inici)/1000000000.0d;
    }
    
}
