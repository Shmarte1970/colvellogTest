package es.zarca.covellog.interfaces.web.helpers;

import java.util.Random;

/**
 *
 * @author usuario
 */
public class StringUtil {
    
    public static String textoAleatorio(int longitud) {
        char [] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        int charsLength = chars.length;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder();
        for (int i=0;i<longitud;i++){
           buffer.append(chars[random.nextInt(charsLength)]);
        }
        return buffer.toString();
    }
    
        
    public static String padLeftZeros(String inputString, int length) {
        if (inputString.length() >= length) {
            return inputString;
        }
        StringBuilder sb = new StringBuilder();
        while (sb.length() < length - inputString.length()) {
            sb.append('0');
        }
        sb.append(inputString);

        return sb.toString();
    }
}
