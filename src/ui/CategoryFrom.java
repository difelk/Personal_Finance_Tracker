package ui;

import Category.Category;
import Category.CategoryNode;
import Category.CategoryLinkedList;
import transaction.Transaction;
import transaction.TransactionLinkedList;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

 class CategoryForm {

    private final CategoryLinkedList categoryLinkedList;
    private final Scanner scanner;

    public CategoryForm(CategoryLinkedList categoryLinkedList) {
        this.categoryLinkedList = categoryLinkedList;
        this.scanner = new Scanner(System.in);
    }

     public void hardCodedCategoryForm(CategoryLinkedList categoryLinkedListMain, TransactionLinkedList TransactionLinkedlistHC) {

         LocalDateTime creationDateTime = LocalDateTime.now();

         Category category1 = new Category("Groceries", "Grocery expenses", 20000, creationDateTime);
         Category category2 = new Category("Entertainment", "Entertainment expenses", 10000, creationDateTime);
         Category category3 = new Category("Transportation", "Transportation expenses", 15000, creationDateTime);

         if (!categoryLinkedListMain.isCategoryExists(category1.getName())) {
             categoryLinkedListMain.addCategory(category1);
         }

         if (!categoryLinkedListMain.isCategoryExists(category2.getName())) {
             categoryLinkedListMain.addCategory(category2);
         }

         if (!categoryLinkedListMain.isCategoryExists(category3.getName())) {
             categoryLinkedListMain.addCategory(category3);
         }

        Transaction transaction1 = new Transaction(5000, "Once a month grocery", categoryLinkedListMain.getCategoryByName("Groceries").getData(),false);
        Transaction transaction2 = new Transaction(8500, "Watched a Movie", categoryLinkedListMain.getCategoryByName("Entertainment").getData(), false);
        Transaction transaction3 = new Transaction(550, "Transportation expenses for office", categoryLinkedListMain.getCategoryByName("Transportation").getData(), false);

        TransactionLinkedlistHC.addTransaction(transaction1);
         TransactionLinkedlistHC.addTransaction(transaction2);
         TransactionLinkedlistHC.addTransaction(transaction3);


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


     public void updateCategoryByName() {
         System.out.print("Enter category name to update: ");
         String categoryName = scanner.nextLine();
         System.out.println();

         CategoryNode categoryNodeToUpdate = categoryLinkedList.getCategoryByName(categoryName);

         if (categoryNodeToUpdate == null) {
             System.out.println("Category not found with the given name.");
             return;
         }

         Category existingCategory = categoryNodeToUpdate.getData();

         System.out.println("Current Category Details:");
         System.out.println("Old Name: " + existingCategory.getName());
         System.out.println("Old Description: " + existingCategory.getDescription());
         System.out.println("Old Budget: " + existingCategory.getBudget());
         System.out.println();

         System.out.println("Enter updated values (press enter to skip):");

         System.out.print("New Name: ");
         String updatedCategoryName = scanner.nextLine().trim();
         if (updatedCategoryName.isEmpty()) {
             updatedCategoryName = existingCategory.getName();
         }

         System.out.print("New Description: ");
         String updatedDescription = scanner.nextLine().trim();
         if (updatedDescription.isEmpty()) {
             updatedDescription = existingCategory.getDescription();
         }

         double updatedBudget = getBudgetFromUser();

         Category updatedCategory = new Category(updatedCategoryName, updatedDescription, updatedBudget, existingCategory.getCreationDate());

         categoryLinkedList.updateCategoryByName(categoryName, updatedCategory);

         System.out.println("\u001B[34mCategory updated successfully.\u001B[0m");
         System.out.println();
     }


     public void updateCategoryByDate() {
         System.out.print("Enter category creation date (yyyy-MM-dd) to update: ");
         String creationDateStr = scanner.nextLine();
         System.out.println();

         LocalDate creationDate;
         try {
             creationDate = LocalDate.parse(creationDateStr);
         } catch (DateTimeParseException e) {
             System.out.println("Invalid date format. Please use yyyy-MM-dd.");
             return;
         }

         List<CategoryNode> matchingCategories = categoryLinkedList.getCategoriesByDay(creationDate);

         if (matchingCategories.isEmpty()) {
             System.out.println("No categories found with the given creation date.");
             return;
         }

         if (matchingCategories.size() > 1) {
             System.out.println("Multiple categories found with the same creation date. Please choose a specific category:");
             for (int i = 0; i < matchingCategories.size(); i++) {
                 System.out.println((i + 1) + ". " + matchingCategories.get(i).getData().getName());
             }

             System.out.print("Enter the number of the category to update: ");
             int choice = scanner.nextInt();
             scanner.nextLine();

             if (choice < 1 || choice > matchingCategories.size()) {
                 System.out.println("Invalid choice.");
                 return;
             }

             CategoryNode selectedNode = matchingCategories.get(choice - 1);
             updateCategoryDetails(selectedNode.getData());
         } else {
             updateCategoryDetails(matchingCategories.get(0).getData());
         }
     }

     public void updateCategoryByDateRange() {
         System.out.print("Enter start date (yyyy-MM-dd) for the date range: ");
         String startDateStr = scanner.nextLine();
         System.out.println();

         System.out.print("Enter end date (yyyy-MM-dd) for the date range: ");
         String endDateStr = scanner.nextLine();
         System.out.println();

         LocalDate startDate, endDate;
         try {
             startDate = LocalDate.parse(startDateStr);
             endDate = LocalDate.parse(endDateStr);
         } catch (DateTimeParseException e) {
             System.out.println("Invalid date format. Please use yyyy-MM-dd.");
             return;
         }

         if (startDate.isAfter(endDate)) {
             System.out.println("Start date must be before the end date.");
             return;
         }

         List<CategoryNode> categoriesInRange = categoryLinkedList.getCategoriesByDateRange(startDate, endDate);

         if (categoriesInRange.isEmpty()) {
             System.out.println("No categories found within the specified date range.");
             return;
         }

         System.out.println("Categories found within the specified date range:");
         for (int i = 0; i < categoriesInRange.size(); i++) {
             System.out.println((i + 1) + ". " + categoriesInRange.get(i).getData().getName());
         }

         System.out.print("Enter the number of the category to update: ");
         int choice = scanner.nextInt();
         scanner.nextLine();

         if (choice < 1 || choice > categoriesInRange.size()) {
             System.out.println("Invalid choice.");
             return;
         }

         CategoryNode selectedNode = categoriesInRange.get(choice - 1);
         updateCategoryDetails(selectedNode.getData());
     }

     public void updateCategoryAll() {
         List<CategoryNode> allCategories = categoryLinkedList.getAllCategories();

         if (allCategories.isEmpty()) {
             System.out.println("No categories found.");
             return;
         }

         System.out.println("All Categories:");
         for (int i = 0; i < allCategories.size(); i++) {
             System.out.println((i + 1) + ". " + allCategories.get(i).getData().getName());
         }

         System.out.print("Enter the number of the category to update: ");
         int choice = scanner.nextInt();
         scanner.nextLine();

         if (choice < 1 || choice > allCategories.size()) {
             System.out.println("Invalid choice.");
             return;
         }

         CategoryNode selectedNode = allCategories.get(choice - 1);
         updateCategoryDetails(selectedNode.getData());
     }


     private void updateCategoryDetails(Category existingCategory) {
         System.out.println("Current Category Details:");
         System.out.println("Old Name: " + existingCategory.getName());
         System.out.println("Old Description: " + existingCategory.getDescription());
         System.out.println("Old Budget: " + existingCategory.getBudget());
         System.out.println();

         System.out.println("Enter updated values (press enter to skip):");

         System.out.print("New Name: ");
         String updatedCategoryName = scanner.nextLine().trim();
         if (updatedCategoryName.isEmpty()) {
             updatedCategoryName = existingCategory.getName();
         }

         System.out.print("New Description: ");
         String updatedDescription = scanner.nextLine().trim();
         if (updatedDescription.isEmpty()) {
             updatedDescription = existingCategory.getDescription();
         }

         double updatedBudget = getBudgetFromUser();

         Category updatedCategory = new Category(updatedCategoryName, updatedDescription, updatedBudget, existingCategory.getCreationDate());

         categoryLinkedList.updateCategoryByName(existingCategory.getName(), updatedCategory);

         System.out.println("\u001B[34mCategory updated successfully.\u001B[0m");
         System.out.println();
     }

     public void getAllCategories(CategoryLinkedList categoryLinkedList){
        List<CategoryNode> categoryNodes = categoryLinkedList.getAllCategories();

        try{
            if(categoryLinkedList.isEmpty()){
                System.out.println();
                System.out.println("\u001B[31mNo Category details to display\u001B[0m");
            }
            else{
                System.out.println();
                for (int i = 0; i < categoryNodes.size();i++) {
                    System.out.println((i+1) + ". " + categoryNodes.get(i).getData().getName());
                }
                System.out.println();
                System.out.print("Enter selected Category no: ");
                int selectedNo = scanner.nextInt();

                System.out.println();

                if(selectedNo < 1 || selectedNo > categoryNodes.size()){
                    System.out.println("Selected number is invalid");
                    return;
                }
                System.out.println();
                System.out.println("Selected Category Details");
                System.out.println();

                System.out.println("Category Name: " + categoryNodes.get(selectedNo - 1).getData().getName());
                System.out.println("Category Description: " + categoryNodes.get(selectedNo - 1).getData().getDescription());
                System.out.println("Category Budget: " + categoryNodes.get(selectedNo - 1).getData().getBudget());
                System.out.println("Category Created Date: " + categoryNodes.get(selectedNo - 1).getData().getCreationDate());
            }
        }
        catch(Exception e){
            System.out.println(e);
        }
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
