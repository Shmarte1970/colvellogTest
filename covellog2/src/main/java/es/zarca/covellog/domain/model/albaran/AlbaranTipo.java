package es.zarca.covellog.domain.model.albaran;

/**
 *
 * @author francisco
 */
public enum AlbaranTipo {
    
        ENTREGA("E", "ENTREGA"),
        RECOGIDA("R", "RECOGIDA");
        
        private final String id;
        private final String nombre;

        private AlbaranTipo(String id, String nombre) {
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
        
        public static AlbaranTipo fromId(String id) {
            switch(id) {
                case "E":
                    return ENTREGA;
                case "R":
                    return RECOGIDA;
                default:
                    throw new IllegalArgumentException("El tipo de albaran con Id \"" + id + "\" no existe");
            }
        }
        
        
    }
