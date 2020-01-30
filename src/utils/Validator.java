package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Validator {
  public static boolean isValidText(String s) {
    return s.length() != 0 && !s.trim().isEmpty();
  }

  public static boolean isANumber(String s) {
    try {
      Double.parseDouble(s);
    } catch (NumberFormatException e) {
      return false;
    }
    return true;
  }

  public static boolean isDateValid(String s) {
    if(s.length() != 0) {
      SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
      sdf.setLenient(false);
      try {
        Date date = sdf.parse(s);
        return true;
      } catch (ParseException e) {
        return false;
      }
    }
    return false;
  }
}
