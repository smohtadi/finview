package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateFormatter {
  public static String format(String date, String formatFrom, String formatTo) throws ParseException {
    SimpleDateFormat sdf = new SimpleDateFormat(formatFrom);
    sdf.setLenient(false);
    Date d = sdf.parse(date);
    sdf = new SimpleDateFormat(formatTo);
    return sdf.format(d);
  }

  public static String addDays(String date, int days) {
    try {
      SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
      Date d = sdf.parse(date);
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(d);
      calendar.add(Calendar.DATE, days);
      return sdf.format(calendar.getTime());
    } catch (ParseException e) {
      e.printStackTrace();
      return null;
    }
  }

  public static String getCurrentDay() {
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    LocalDateTime localDateTime = LocalDateTime.now();
    return dateTimeFormatter.format(localDateTime);
  }

  public static String getLastDate(List<String> dates) {
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    sdf.setLenient(false);
    long max = 0;
    for (String date : dates) {
      try {
        Date d = sdf.parse(date);
        long time = d.getTime();
        if (time > max) max = time;
      } catch (ParseException e) {
        e.printStackTrace();
        return "";
      }
    }
    Calendar.getInstance().setTimeInMillis(max);
    return sdf.format(Calendar.getInstance().getTime());
  }

  public static String subtract(String fechaVcto, String fechaInicial) {
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    try {
      Date d1 = sdf.parse(fechaVcto);
      Date d2 = sdf.parse(fechaInicial);
      Date dif = new Date(d1.getTime() - d2.getTime());
      return sdf.format(dif);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return "";
  }
}
