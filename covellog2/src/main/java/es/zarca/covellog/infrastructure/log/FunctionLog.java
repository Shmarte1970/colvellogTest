package es.zarca.covellog.infrastructure.log;

import es.zarca.covellog.infrastructure.util.crono.Crono;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

/**
 *
 * @author francisco
 */
public class FunctionLog {
    private static final Logger LOGGER = Logger.getLogger(FunctionLog.class.getName());
    private final Crono crono;
    private Capa capa;  
    private Level level = Level.SEVERE;
    static private Integer minPadding = null;
    
    public FunctionLog() {
        crono = new Crono();
        LOGGER.log(level, getMessage());
    }
    
    public FunctionLog(Capa capa) {
        crono = new Crono();
        this.capa = capa;
        LOGGER.log(level, getMessage());
    }
    
    private String getPadding(int padding) {
        if ((minPadding == null) || (minPadding > padding)) {
            minPadding = padding;
        }
        padding = padding - minPadding;
        StringBuilder sb = new StringBuilder();        
        for (int i = 0; i < padding; i++) {
            sb.append(' ');
        }
        return sb.toString();
    }
    
    private String getUltimaParte(String className) {
        String[] parts = className.split(Pattern.quote("."));
        if (parts.length == 0) {
            return className;
        }
        return parts[parts.length - 1];
    }
    
    private String getMessage() {
        StackTraceElement stackTraceElements[] = (new Throwable()).getStackTrace();
        String prefijoCapa = "[X]" + getPadding(stackTraceElements.length) + getPrefijoCapa();
        if (!prefijoCapa.isEmpty()) {
            prefijoCapa = prefijoCapa + ": ";
        }
        return String.format(
            "%s%s.%s",
            prefijoCapa,
            getUltimaParte(stackTraceElements[2].getClassName()),
            stackTraceElements[2].getMethodName()
        );
    }
    
    private String getPrefijoCapa() {
        if (capa == null) {
            return "";
        }
        switch(capa) {
            case WEB:
                return "WEB";
            case CONTROLLER:
                return "CONTROLLER";
            case FACADE:
                return "FACADE";
            case APP_SERVICE:
                return "APP_SERVICE";
            case REPOSITORY:
                return "REPOSITORY";
            case DOMAIN_SERVICE:
                return "DOMAIN_SERVICE";
            case DOMAIN:
                return "DOMAIN";
        }
        return "";
    }
    
    public void info(String message) {
        LOGGER.log(Level.INFO, "{0} INFO: {1}", new Object[]{getMessage(), message});
    }
        
    public void finish() {
        LOGGER.log(level, getMessage() + " FIN (Elapsed: {0}s)", String.valueOf(crono.getSegons()));
    }
    
}
