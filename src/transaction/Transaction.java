package transaction;

import Category.Category;
import Category.CategoryNode;
import utils.DataManipulationUtils;
import utils.ValidationUtils;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Transaction {
    public TransactionLinkedList transactionLinkedList;
    private String transactionID;
    private double amount;
    private String description;
    private Category category;
    private LocalDateTime dateTime;
    private boolean isIncome;


    public Transaction(){}
    public Transaction(double amount, String description, Category category, boolean isIncome) {
        this.amount = amount;
        this.description = description;
        this.category = category;
        this.isIncome = isIncome;
        this.dateTime = ZonedDateTime.from(LocalDateTime.now()).toLocalDateTime();
    }

    public Transaction(double amount, String description, Category category, LocalDateTime dateTime, boolean isIncome) {
        this.amount = amount;
        this.description = description;
        this.category = category;
        this.dateTime = dateTime;
        this.isIncome = isIncome;
        this.transactionID = generateTransactionID(dateTime, category, isIncome);
    }

    DataManipulationUtils dataManipulationUtils = new DataManipulationUtils();
    public Transaction(double amount, String description, CategoryNode category, String dateTimeString, boolean isIncome) {
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
        this.category = category.getData();
        this.dateTime = dateTime.atZone(ZoneId.systemDefault()).toLocalDateTime();
        this.isIncome = isIncome;
        this.transactionID = generateTransactionID(dateTime, category, isIncome);
    }

    public Transaction(double amountValue, String description, CategoryNode data, LocalDateTime fullDateTime, boolean isIncome) {

    this.amount = amountValue;
    this.description = description;
    this.category = data.getData();
    this.dateTime = fullDateTime;
    this.isIncome = isIncome;

    }

    private String generateTransactionID(LocalDateTime dateTime, Object category, boolean isIncome) {
        String transactionType = isIncome ? "INC" : "EXP";
        String categoryName = (category instanceof CategoryNode) ? ((CategoryNode) category).getData().getName() : "";
        return dateTime + categoryName + transactionType;
    }

    public String getTransactionID() {
        return this.transactionID;
    }

    public LocalDateTime getDateTime() {
        return this.dateTime;
    }

    public void setDateTime(ZonedDateTime dateTime) {
        this.dateTime = dateTime.toLocalDateTime();
    }

    public void setDateTime(String dateTime, String formate) {
        this.dateTime = ZonedDateTime.from(dataManipulationUtils.ConvertDateStringToLocalDateTime(dateTime, formate)).toLocalDateTime();
    }
}
