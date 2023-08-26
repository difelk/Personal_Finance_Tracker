package Category;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import utils.ValidationUtils;

public class Category {
    private String categoryName;
    private double budget;
    private String description;
    private LocalDateTime creationDate;
    private String categoryID;
    //    private List<Transaction> associatedTransactions; // List of   transactions related to this category

    public Category(String categoryName, String description){
        this.categoryName = categoryName;
        this.description = description;
        this.creationDate = LocalDateTime.now();
        this.categoryID = generateCategoryID(categoryName, creationDate);

//        System.out.println("ID: " + categoryID);
//        System.out.println("Name: " + categoryName);
//        System.out.println("Description: " + description);
//        System.out.println("Date: " + creationDate);
    }
    public Category(double budget){
        this.budget = budget;
    }
    public void setName(String name){
        this.categoryName = name;
    }
    public String getName(){
        return this.categoryName;
    }
    public String getDescription(){
        return this.description;
    }
    public String getCategoryID(){
        return this.categoryID;
    }
    public LocalDateTime getCreationDate(){
        return this.creationDate;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public String generateCategoryID(String categoryName, LocalDateTime datetime){
        ValidationUtils validationUtils = new ValidationUtils();
        String formattedDatetime;

        // Creating Category ID combining today's date n time
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
            formattedDatetime = datetime.format(formatter);
        }
        catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date and time format.");
        }

//        if (!formattedDatetime.toLocalTime().equals(LocalTime.of(0, 0))) {
//            formattedDatetime = formattedDatetime.with(LocalTime.MIN);
//        }

//        if (validationUtils.isDateAFutureDate(formattedDatetime)) {
//            throw new IllegalArgumentException("Creation date cannot be in the future.");
//        }

        return categoryName + "_" + formattedDatetime;
    }
}
