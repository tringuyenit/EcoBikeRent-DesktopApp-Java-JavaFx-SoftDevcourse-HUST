package se.project.util;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class MyUtils {

  // second to h:m:s
  public static final DecimalFormat moneyformat1 = new DecimalFormat("###,###,### vnđ");
  public static final DecimalFormat moneyformat2 = new DecimalFormat("###,###,###");
  

  //String format = decimalFormat.format(123456789.123);
  public static final DateTimeFormatter format = DateTimeFormatter.ofPattern(
	      "yyyy-MM-dd HH:mm:ss");  // create date util

  public static String date(long l) {
    long p1 = l % 60;
    long p2 = l / 60;
    long p3 = p2 % 60;
    p2 = p2 / 60;
    String s = Long.toString(p2) + ":" + Long.toString(p3) + ":" + Long.toString(p1);
    return s;
  }

  public static String presentDate(String date_stringtype_of_object) {
    DateTimeFormatter db_date_stringtype_to_datetype = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    LocalDateTime db_date_datetype = LocalDateTime.parse(date_stringtype_of_object, db_date_stringtype_to_datetype);

    DateTimeFormatter present_string_fomatter = DateTimeFormatter.ofPattern("hh:mm dd/MM/yyyy");
    String date_to_present = present_string_fomatter.format(db_date_datetype);
    return date_to_present;
  }

  public static String presentDate2(String date_stringtype_of_object) {
    DateTimeFormatter db_date_stringtype_to_datetype = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    LocalDateTime db_date_datetype = LocalDateTime.parse(date_stringtype_of_object, db_date_stringtype_to_datetype);

    DateTimeFormatter present_string_fomatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm");
    String date_to_present = present_string_fomatter.format(db_date_datetype);
    return date_to_present;
  }
} 
