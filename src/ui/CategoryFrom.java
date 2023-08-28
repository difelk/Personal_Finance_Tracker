package ui;

import Category.Category;
import Category.CategoryNode;
import Category.CategoryLinkedList;
import java.time.LocalDateTime;
import java.util.Scanner;

 class CategoryForm {

    private final CategoryLinkedList categoryLinkedList;
    private final Scanner scanner;

    public CategoryForm(CategoryLinkedList categoryLinkedList) {
        this.categoryLinkedList = categoryLinkedList;
        this.scanner = new Scanner(System.in);
    }

     public void hardCodedCategoryForm(CategoryLinkedList categoryLinkedListMain) {

         LocalDateTime creationDateTime = LocalDateTime.now();

         Category category1 = new Category("Groceries", "Grocery expenses", 200, creationDateTime);
         Category category2 = new Category("Entertainment", "Entertainment expenses", 100, creationDateTime);
         Category category3 = new Category("Transportation", "Transportation expenses", 150, creationDateTime);

         if (!categoryLinkedListMain.isCategoryExists(category1.getName())) {
             categoryLinkedListMain.addCategory(category1);
         }

         if (!categoryLinkedListMain.isCategoryExists(category2.getName())) {
             categoryLinkedListMain.addCategory(category2);
         }

         if (!categoryLinkedListMain.isCategoryExists(category3.getName())) {
             categoryLinkedListMain.addCategory(category3);
         }

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
                System.out.print("Invalid budget input. Please enter a valid number.");
            }
        }

        return budget;
    }



    private boolean validateCategory(Category category) {
        if (category.getName().isEmpty()) {
            System.out.print("Category name is mandatory.");
            return false;
        }

        if (category.getDescription().isEmpty()) {
            System.out.print("Category description is mandatory.");
            return false;
        }

        if (category.getBudget() <= 0) {
            System.out.print("Invalid budget value. Budget must be a positive number.");
            return false;
        }

        String categoryName = category.getName().toLowerCase();

        if (categoryLinkedList.isCategoryExists(categoryName)) {
            System.out.println();
            System.out.print("\u001B[31mCategory already exists.\u001B[0m");
            return false;
        }

        return true;
    }



}
