package utils;

import utils.DataManipulationUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ValidationUtils {

    DataManipulationUtils dataManipulationUtils = new DataManipulationUtils();
    public boolean isDateAFutureDate(String dateTime) {
        if(isValidDateTimeFormat(dateTime, "mm/dd/yy 00:00")){
            return  false;

        }else{
            return dataManipulationUtils.ConvertDateStringToLocalDateTime(dateTime, "mm/dd/yy 00:00").isAfter(LocalDateTime.now());
        }
    }


    public boolean isDateAFutureDate(LocalDateTime dateTime) {
        return dateTime.isAfter(LocalDateTime.now());
    }

    public boolean isValidDateTimeFormat(String dateTimeString, String formatPattern) {
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

}
