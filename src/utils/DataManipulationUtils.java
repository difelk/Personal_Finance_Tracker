package utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DataManipulationUtils {

    public LocalDateTime convertStringToDate(String date) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate parsedDate = LocalDate.parse(date, dateFormatter);
        return parsedDate.atStartOfDay();
    }

    public static LocalDateTime convertStringToTime(String time) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTime parsedTime = LocalTime.parse(time, timeFormatter);
        return parsedTime.atDate(LocalDate.now());
    }

    public LocalDateTime concatDateAndTime(LocalDate date, LocalTime time) {
        return LocalDateTime.of(date, time);
    }
    public LocalDateTime ConvertDateStringToLocalDateTime(String dateTimeString, String formatPattern) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatPattern);
            return LocalDateTime.parse(dateTimeString, formatter);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date and time format.");
        }
    }

    public static LocalDateTime ConvertDateStringToLocalDateTime(String dateTimeString) {
        System.out.println("dateTimeString - " + dateTimeString);
        String date = dateTimeString.trim();
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            System.out.println("formatter - " + formatter);
            return LocalDateTime.parse(date, formatter);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date and time format.");
        }
    }

    public double convertStringToDouble(String amount) {
        try {
            return Double.parseDouble(amount);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid amount format.");
        }
    }

}
