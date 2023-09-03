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

    public Category(String categoryName, String description){
        this.categoryName = categoryName;
        this.description = description;
        this.creationDate = LocalDateTime.now();
        this.categoryID = generateCategoryID(categoryName, creationDate);

    }
    public Category(double budget){
        this.budget = budget;
    }

    public Category(String categoryName, String description, double budgetValue, LocalDateTime fullDateTime) {
        this.categoryName = categoryName.toLowerCase().trim();
        this.description = description;
        this.budget = budgetValue;
        this.creationDate = fullDateTime;
        this.categoryID = generateCategoryID(categoryName, creationDate);

    }

    public void setName(String name){
        this.categoryName = name.toLowerCase().trim();
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

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
            formattedDatetime = datetime.format(formatter);
        }
        catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date and time format.");
        }

        return categoryName + "" + formattedDatetime.replace(":", "").replace("-", "");
    }
}
