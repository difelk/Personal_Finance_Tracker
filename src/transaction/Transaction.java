package transaction;

import Category.Category;
import utils.DataManipulationUtils;
import utils.ValidationUtils;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Transaction {
    private String transactionID;
    private double amount;
    private String description;
    private Category category;
    private ZonedDateTime dateTime;
    private boolean isIncome;

    public Transaction(double amount, String description, Category category, boolean isIncome) {
        this.amount = amount;
        this.description = description;
        this.category = category;
        this.isIncome = isIncome;
        this.dateTime = ZonedDateTime.from(LocalDateTime.now());
    }


    DataManipulationUtils dataManipulationUtils = new DataManipulationUtils();
    public Transaction(double amount, String description, Category category, String dateTimeString, boolean isIncome) {
        ValidationUtils validationUtils = new ValidationUtils();

        LocalDateTime dateTime;

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd[ HH:mm:ss]");
            dateTime = LocalDateTime.parse(dateTimeString, formatter);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date and time format.");
        }

        if (!dateTime.toLocalTime().equals(LocalTime.of(0, 0))) {
            dateTime = dateTime.with(LocalTime.MIN);
        }

        if (validationUtils.isDateAFutureDate(dateTime)) {
            throw new IllegalArgumentException("Transaction date cannot be in the future.");
        }

        this.amount = amount;
        this.description = description;
        this.category = category;
        this.dateTime = dateTime.atZone(ZoneId.systemDefault());
        this.isIncome = isIncome;
        this.transactionID = generateTransactionID(dateTime, category, isIncome);
    }

    private String generateTransactionID(LocalDateTime dateTime, Category category, boolean isIncome) {
        String transactionType = isIncome ? "INC" : "EXP";
        return dateTime + (category != null ? category.getName() : "") + transactionType;
    }

    public String getTransactionID() {
        return this.transactionID;
    }

    public ZonedDateTime getDateTime() {
        return this.dateTime;
    }

    public void setDateTime(ZonedDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setDateTime(String dateTime, String formate) {
        this.dateTime = ZonedDateTime.from(dataManipulationUtils.ConvertDateStringToLocalDateTime(dateTime, formate));
    }
}
