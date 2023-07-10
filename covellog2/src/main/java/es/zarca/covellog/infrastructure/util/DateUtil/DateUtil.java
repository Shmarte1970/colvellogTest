package es.zarca.covellog.infrastructure.util.DateUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 *
 * @author f.torralbo
 */
public class DateUtil {
    public static final String MYSQL_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String ISO_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss. SSSZ";
    public static final String DATE_FORMAT = "dd-MM-yyyy";
    public static final String DATETIME_FORMAT = "dd-MM-yyyy HH:mm:ss";
    public static final String FOX_DATETIME_FORMAT = "dd/MM/yyyy HH:mm:ss";
    public static final String ISO8601_DATE_FORMAT = "yyyy-MM-dd";
    public static final String ISO8601_DATETIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    public static final String DATE_ID_FORMAT = "yyyyMMdd";
    public static final String DATETIME_ID_FORMAT = "yyyyMMdd_HHmmss";

    public static Date stringToDate(String stringDate, String dateFormat) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        Date date = formatter.parse(stringDate);
        return date;
    }
    
    public static Date stringGmtToDateLocal(String stringDate, String dateFormat) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
        Date date = formatter.parse(stringDate);
        return date;
    }
    
    public static String dateToString(Date date, String dateFormat) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        String stringDate = formatter.format(date);
        return stringDate;
    }
    
    public static String dateToStringGMT(Date date, String dateFormat) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
        String stringDate = formatter.format(date);
        return stringDate;
    }
    
    public static Date stringToDate(String stringDate) throws ParseException {
        return stringToDate(stringDate, DATE_FORMAT);
    }

    public static String dateToString(Date date) {
        return dateToString(date, DATE_FORMAT);
    }

    public static Date stringToDatetime(String stringDate) throws ParseException {
        return stringToDate(stringDate, DATETIME_FORMAT);
    }

    public static String dateTimeToString(Date date) {
        return dateToString(date, DATETIME_FORMAT);
    }
    
    public static Date iso8601ToDate(String stringDate) throws ParseException {
        return stringToDate(stringDate, ISO8601_DATE_FORMAT);
    }

    public static String dateToIso8601(Date date) {
        return dateToString(date, ISO8601_DATE_FORMAT);
    }
    
    public static Date iso8601GmtToDatetime(String stringDate) throws ParseException {
        return stringGmtToDateLocal(stringDate, ISO8601_DATETIME_FORMAT);
    }

    public static String dateTimeToIso8601Gmt(Date date) {
        return dateToStringGMT(date, ISO8601_DATETIME_FORMAT);
    }
    
    public static String dateToId(Date date) {
        return dateToString(date, DATE_ID_FORMAT);
    }
    
    public static String dateTimeToId(Date date) {
        return dateToString(date, DATETIME_ID_FORMAT);
    }

    public static Date fusionarFechaHora(Date fecha, Date hora) {
        Calendar calendarFecha = Calendar.getInstance();
        calendarFecha.setTime(fecha);
        Calendar calendarHora = Calendar.getInstance();
        calendarHora.setTime(hora);
        calendarFecha.set(Calendar.HOUR, calendarHora.get(Calendar.HOUR));
        calendarFecha.set(Calendar.MINUTE, calendarHora.get(Calendar.MINUTE));
        calendarFecha.set(Calendar.SECOND, calendarHora.get(Calendar.SECOND));
        return calendarFecha.getTime();
    }
    
}