package utils;

import utils.DataManipulationUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

public class ValidationUtils {

    static DataManipulationUtils dataManipulationUtils = new DataManipulationUtils();
    public static boolean isDateAFutureDate(String dateTime) {
        if(isValidDateTimeFormat(dateTime, "mm/dd/yy 00:00")){
            return  false;

        }else{
            return dataManipulationUtils.ConvertDateStringToLocalDateTime(dateTime, "mm/dd/yy 00:00").isAfter(LocalDateTime.now());
        }
    }


    public boolean isDateAFutureDate(LocalDateTime dateTime) {
        return dateTime.isAfter(LocalDateTime.now());
    }

    public static boolean isValidDateTimeFormat(String dateTimeString, String formatPattern) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatPattern);
            LocalDateTime.parse(dateTimeString, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public boolean isStartDateLessThanOrEqualToEndDate(String startDate, String endDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd[ HH:mm:ss]");
        try {
            LocalDateTime startDateTime = LocalDateTime.parse(startDate, formatter);
            LocalDateTime endDateTime = LocalDateTime.parse(endDate, formatter);

            return !isDateAFutureDate(startDateTime) && !endDateTime.isBefore(startDateTime);
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public static boolean isITANumber(String value){
       return value.matches("\\d+");
    }

    public boolean isItAValidDate(String date) {
        try {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate.parse(date, dateFormatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
    public boolean isItAValidTime(String time) {
        try {
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
            LocalTime.parse(time, timeFormatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }


    public boolean isEmptyInput(String value){
        return Objects.equals(value, "");
    }

}
