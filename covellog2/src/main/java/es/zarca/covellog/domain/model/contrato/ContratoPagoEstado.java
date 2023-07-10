package es.zarca.covellog.domain.model.contrato;

import es.zarca.covellog.domain.model.base.Estado;

/**
 *
 * @author francisco
 */
public enum ContratoPagoEstado implements Estado {
    
        PENDIENTE("PE", "PENDIENTE"),
        PAGADO("PA", "PAGADO"),
        PAGADO_25("25", "PAGADO 25%"),
        PAGADO_50("50", "PAGADO 50%"),
        PAGADO_75("75", "PAGADO 75%");

        private final String id;
        private final String nombre;

        private ContratoPagoEstado(String id, String nombre) {
            this.id = id;
            this.nombre = nombre;
        }
        
        @Override
        public String getId() {
            return id;
        }
        
        @Override
        public String getNombre() {
            return nombre;
        }

        @Override
        public String toString() {
            return id;
        }
        
        public static ContratoPagoEstado fromId(String id) {
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
