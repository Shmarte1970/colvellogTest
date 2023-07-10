package es.zarca.covellog.infrastructure.assertions;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Objects;

/**
 *
 * @author francisco
 */
public class ArgumentValidator {
      
    public static void fail(String message) {
        throw new IllegalArgumentException(message);
    }

    public static void isEqual(Object object1, Object object2, String message) {
        if (!Objects.equals(object1, object2)) {
            throw new IllegalArgumentException(message);
        }
    }
    
    public static void isNotEqual(Object object1, Object object2, String message) {
        if (Objects.equals(object1, object2)) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void isFalse(boolean condition, String message) {
        if (condition) {
            throw new IllegalArgumentException(message);
        }
    }
    
    public static void isTrue(boolean condition, String message) {
        if (!condition) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void isNotNull(Object object, String message) {
        if (object == null) {
            throw new IllegalArgumentException(message);
        }
    }
    
    public static void isNull(Object object, String message) {
        if (object != null) {
            throw new IllegalArgumentException(message);
        }
    }
    
    public static void isNotNullOrEmpty(Integer integer, String message) {
        if ((integer == null) || (integer == 0)) {
            throw new IllegalArgumentException(message);
        }
    }
    
    public static void isEmpty(String object, String message) {
        if (!object.isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }
    
    public static void isEmpty(Collection object, String message) {
        if (!object.isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }
    
    public static void isNotEmpty(String object, String message) {
        if (object == null || object.isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }
    
    public static void isNotEmpty(Collection object, String message) {
        if (object == null || object.isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void isValid(Object object, String message, Object[] choices) {
        throw new IllegalArgumentException("TODO");
    }

    public static void isPositiveNonZero(int object, String message) {
        if (object < 1) {
            throw new IllegalArgumentException(message);
        }
    }
    
    public static void notIsPositiveNonZero(int object, String message) {
        if (object > 0) {
            throw new IllegalArgumentException(message);
        }
    }
    
    public static void isPositiveNonZero(Integer object, String message) {
        if (object == null || object < 1) {
            throw new IllegalArgumentException(message);
        }
    }
    
    public static void notIsPositiveNonZero(Integer object, String message) {
        if (object != null && object > 0) {
            throw new IllegalArgumentException(message);
        }
    }
    
    public static void isPositive(BigDecimal object, String message) {
        if (object == null || object.compareTo(BigDecimal.ZERO) == -1) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void required(Object object, String field) {
        if (object == null) {
            throw new IllegalArgumentException("El campo " + field + " es obligatorio.");
        }
    }
    
    
}
