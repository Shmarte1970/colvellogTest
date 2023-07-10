package es.zarca.covellog.domain.model.albaran;

import es.zarca.covellog.domain.model.base.Estado;

/**
 *
 * @author francisco
 */
public enum AlbaranEstado implements Estado {
    
        BORRADOR("BO", "BORRADOR"),
        ACTIVO("AC", "ACTIVO"),
        ANULADO("AN", "ANULADO"),
        FINALIZADO("FI", "FINALIZADO");

        private final String id;
        private final String nombre;

        private AlbaranEstado(String id, String nombre) {
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
        
        public static AlbaranEstado fromId(String id) {
            switch(id) {
                case "BO":
                    return BORRADOR;
                case "AC":
                    return ACTIVO;
                case "AN":
                    return ANULADO;
                case "FI":
                    return FINALIZADO;
                default:
                    throw new IllegalArgumentException("El estado de albaran con Id \"" + id + "\" no existe");
            }
        }
        
        
    }
