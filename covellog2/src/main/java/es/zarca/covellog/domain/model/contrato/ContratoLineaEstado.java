package es.zarca.covellog.domain.model.contrato;

import es.zarca.covellog.domain.model.base.Estado;

/**
 *
 * @author francisco
 */
public enum ContratoLineaEstado implements Estado {
        PENDIENTE_ENTREGA("PE", "PENDIENTE ENTREGA"),
        EN_ALQUILER("AL", "EN ALQUILER"),
        PENDIENTE_RECOGIDA("PR", "PENDIENTE RECOGIDA"),
        FINALIZADO("FI", "FINALIZADO");

        private final String id;
        private final String nombre;

        private ContratoLineaEstado(String id, String nombre) {
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
        
        public static ContratoLineaEstado fromId(String id) {
            switch(id) {
                case "PE":
                    return PENDIENTE_ENTREGA;
                case "AL":
                    return EN_ALQUILER;
                case "PR":
                    return PENDIENTE_RECOGIDA;
                case "FI":
                    return FINALIZADO;
                default:
                    throw new IllegalArgumentException("Id \"" + id + "\" not defined");
            }
        }
        
        
    }
