import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ValidationUtils {


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

}
