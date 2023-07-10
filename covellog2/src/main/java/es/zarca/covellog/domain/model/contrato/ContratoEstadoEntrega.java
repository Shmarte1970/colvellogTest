package es.zarca.covellog.domain.model.contrato;

/**
 *
 * @author francisco
 */
public enum ContratoEstadoEntrega {
    
        PENDIENTE("PE", "PENDIENTE"),
        PAGADO("EN", "PAGADO"),
        PAGADO_50("50", "PAGADO 50%");

        private final String id;
        private final String nombre;

        private ContratoEstadoEntrega(String id, String nombre) {
            this.id = id;
            this.nombre = nombre;
        }
        
        public String getId() {
            return id;
        }
        
        public String getNombre() {
            return nombre;
        }

        @Override
        public String toString() {
            return id;
        }
        
        public static ContratoEstadoEntrega fromId(String id) {
            switch(id) {
                case "PE":
                    return PENDIENTE;
                case "PA":
                    return PAGADO;
                case "50":
                    return PAGADO_50;
                default:
                    throw new IllegalArgumentException("Id \"" + id + "\" not defined");
            }
        }
        
    }
