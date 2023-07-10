package es.zarca.covellog.infrastructure.persistence.csv;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVUtils {

    private static final char DEFAULT_SEPARATOR = ',';
    private static final char DEFAULT_QUOTE = '"';
    
    public static List<List<String>> parseFile(String csvFile) throws Exception {
        System.out.println("parseFile: " + csvFile);
        List<List<String>> tabla = new ArrayList<>();
        //Scanner scanner = new Scanner(new File(csvFile));
        String line;
        BufferedReader br = new BufferedReader(new FileReader(csvFile));
        while ((line = leerLinea(br)) != null) {
            //System.err.println("LINEA: " + line);
            tabla.add(parseLine(line));
        }
            /*if (!scanner.hasNextLine()) System.out.println("vaya tru;avco: " + csvFile);
            while (scanner.hasNextLine()) {
                System.out.println("nanai: **************************************************");
                List<String> line = parseLine(scanner.nextLine());
                System.out.println("Country [id= " + line.get(0) + ", code= " + line.get(1) + " , name=" + line.get(2) + "]");
            }
        scanner.close();*/
            return tabla;

    }
    
    private static String leerLinea(BufferedReader br) throws IOException {
        StringBuilder linea = new StringBuilder();
        int c = 0;
        boolean inQuotes = false;
        while((c = br.read()) != -1) {
            char character = (char)c;
            if (character == '"') {
                inQuotes = !inQuotes;
            }
            if (character == '\n' && !inQuotes) {
                if (linea.toString().startsWith("292")) {
                    System.err.println("sds");
                }
                return linea.toString();
            }
            linea = linea.append(character);
        }
        return null;
    }

    public static List<String> parseLine(String cvsLine) {
        return parseLine(cvsLine, DEFAULT_SEPARATOR, DEFAULT_QUOTE);
    }

    public static List<String> parseLine(String cvsLine, char separators) {
        return parseLine(cvsLine, separators, DEFAULT_QUOTE);
    }

    public static List<String> parseLine(String cvsLine, char separators, char customQuote) {

        List<String> result = new ArrayList<>();

        //if empty, return!
        if (cvsLine == null || cvsLine.isEmpty()) {
            return result;
        }

        if (customQuote == ' ') {
            customQuote = DEFAULT_QUOTE;
        }

        if (separators == ' ') {
            separators = DEFAULT_SEPARATOR;
        }

        StringBuffer curVal = new StringBuffer();
        boolean inQuotes = false;
        boolean startCollectChar = false;
        boolean doubleQuotesInColumn = false;

        char[] chars = cvsLine.toCharArray();

        for (char ch : chars) {

            if (inQuotes) {
                startCollectChar = true;
                if (ch == customQuote) {
                    inQuotes = false;
                    doubleQuotesInColumn = false;
                } else {

                    //Fixed : allow "" in custom quote enclosed
                    if (ch == '\"') {
                        if (!doubleQuotesInColumn) {
                            curVal.append(ch);
                            doubleQuotesInColumn = true;
                        }
                    } else {
                        curVal.append(ch);
                    }
                }
            } else {
                if (ch == customQuote) {

                    inQuotes = true;

                    //Fixed : allow "" in empty quote enclosed
                    if (chars[0] != '"' && customQuote == '\"') {
                        //curVal.append('"');
                    }

                    //double quotes in column will hit this!
                    if (startCollectChar) {
                        curVal.append('"');
                    }

                } else if (ch == separators) {

                    result.add(curVal.toString());

                    curVal = new StringBuffer();
                    startCollectChar = false;

                } else if (ch == '\r') {
                    //ignore LF characters
                    continue;
                } else if (ch == '\n') {
                    //the end, break!
                    break;
                } else {
                    curVal.append(ch);
                }
            }
        }

        result.add(curVal.toString());

        return result;
    }

}

