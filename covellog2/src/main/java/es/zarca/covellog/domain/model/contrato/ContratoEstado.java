package es.zarca.covellog.domain.model.contrato;

import es.zarca.covellog.domain.model.base.Estado;
/**
 *
 * @author francisco
 */
public enum ContratoEstado implements Estado {
    
        BORRADOR("BO", "BORRADOR"),
        ACTIVO("AC", "ACTIVO"),
        PENDIENTE("PE", "PENDIENTE"),
        ANULADO("AN", "ANULADO"),
        FINALIZADO("FI", "FINALIZADO");

        private final String id;
        private final String nombre;

        private ContratoEstado(String id, String nombre) {
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
        
        public static ContratoEstado fromId(String id) {
            switch(id) {
                case "BO":
                    return BORRADOR;
                case "AC":
                    return ACTIVO;
                case "PE":
                    return PENDIENTE;
                case "AN":
                    return ANULADO;
                case "FI":
                    return FINALIZADO;
                default:
                    throw new IllegalArgumentException("Id \"" + id + "\" not defined");
            }
        }
        
        
    }
