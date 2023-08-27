package ui;

import Category.Category;
import Category.CategoryLinkedList;
import utils.DataManipulationUtils;
import utils.ValidationUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

class CategoryForm {

    private final CategoryLinkedList categoryLinkedList;
    private final Scanner scanner;

    public CategoryForm(CategoryLinkedList categoryLinkedList) {
        this.categoryLinkedList = categoryLinkedList;
        this.scanner = new Scanner(System.in);
    }

    public void displayCategoryForm() {
        System.out.print("Enter category name: ");
        String categoryName = scanner.nextLine();
        System.out.println();

        System.out.print("Enter category description: ");
        String description = scanner.nextLine();
        System.out.println();

        double budget = getBudgetFromUser();

        LocalDateTime creationDateTime = LocalDateTime.now();

        Category newCategory = new Category(categoryName, description, budget, creationDateTime);

        if (validateCategory(newCategory)) {
            categoryLinkedList.addCategory(newCategory);
            System.out.println();
            System.out.println("Category added successfully.");
            System.out.println();

            System.out.println("            Entered Category Details:");
            System.out.println("                                        Category Name: " + newCategory.getName());
            System.out.println("                                        Description: " + newCategory.getDescription());
            System.out.println("                                        Budget: " + newCategory.getBudget() + "/=");
            System.out.println("                                        Creation Date and Time: " + newCategory.getCreationDate());
             } else {
            System.out.println();
            System.out.println("Category validation failed.");
            System.out.println();
        }
    }


    private double getBudgetFromUser() {
        double budget = -1;
        boolean isValid = false;

        while (!isValid) {
            System.out.print("Enter category budget: ");
            String budgetInput = scanner.next();

            try {
                budget = Double.parseDouble(budgetInput);
                isValid = true;
            } catch (NumberFormatException e) {
                System.out.println("Invalid budget input. Please enter a valid number.");
            }
        }

        return budget;
    }

    private boolean validateCategory(Category category) {
        if (category.getName().isEmpty()) {
            System.out.println("Category name is mandatory.");
            return false;
        }

        if (category.getDescription().isEmpty()) {
            System.out.println("Category description is mandatory.");
            return false;
        }

        if (category.getBudget() <= 0) {
            System.out.println("Invalid budget value. Budget must be a positive number.");
            return false;
        }

        String categoryName = category.getName().toLowerCase();

        if (categoryLinkedList.isCategoryExists(categoryName)) {
            System.out.println();
            System.out.println("\u001B[31mCategory already exists.\u001B[0m");
            return false;
        }

        return true;
    }



}
