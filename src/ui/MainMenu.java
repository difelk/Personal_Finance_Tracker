package ui;

import Category.CategoryLinkedList;
import transaction.TransactionLinkedList;

import java.util.Scanner;

public class MainMenu {

    private int selectedOption = 0;
    private boolean isMenuOpen = true;
    private final TransactionLinkedList transactionLinkedList = new TransactionLinkedList();
    private final CategoryLinkedList categoryLinkedList = new CategoryLinkedList();
    private final Scanner scanner = new Scanner(System.in);

    public void displayMenu() {
        CategoryForm categoryForm = new CategoryForm(categoryLinkedList);
        categoryForm.hardCodedCategoryForm(categoryLinkedList);


        do {
            System.out.println();
            System.out.println("===========================================================================================");
            System.out.println("\u001B[36m                                   MAIN MENU                            \u001B[0m");
            System.out.println();
            System.out.println("Select an option (by typing the selected option number)");
            System.out.println();
            System.out.println();
            System.out.println("\u001B[33m01. ADD TRANSACTION\u001B[0m");
            System.out.println("\u001B[33m02. UPDATE TRANSACTION\u001B[0m");
            System.out.println("\u001B[33m03. DELETE TRANSACTION\u001B[0m");
            System.out.println("\u001B[32m04. ADD CATEGORY\u001B[0m");
            System.out.println("\u001B[32m05. UPDATE CATEGORY\u001B[0m");
            System.out.println("\u001B[32m06. DELETE CATEGORY\u001B[0m");
            System.out.println("\u001B[34m07. SEARCH\u001B[0m");
            System.out.println("\u001B[34m08. HELP\u001B[0m");
            System.out.println("\u001B[31m99. EXIT\u001B[0m");
            System.out.println();
            System.out.println("===========================================================================================");

            getSelectedOption();

            switch (selectedOption) {
                case 1 -> addTransaction();
                case 2 -> updateTransaction();
                case 3 -> deleteTransaction();
                case 4 -> addCategory();
                case 5 -> updateCategory();
                case 6 -> deleteCategory();
                case 7 -> search();
                case 8 -> help();
                case 99 -> isMenuOpen = false;
                default -> isMenuOpen = true;
            }
        } while (isMenuOpen);
    }

    private void getSelectedOption() {
        try {
            System.out.println();
            System.out.print("Enter selected option number: ");
            String input = scanner.nextLine();

            if (!input.matches("\\d+")) {
                System.out.println();
                System.out.println("\u001B[31mInvalid option. Please enter a valid option number.\u001B[0m");
                System.out.println();
                isMenuOpen = true;
                return;
            }

            selectedOption = Integer.parseInt(input);
            System.out.println();
            System.out.println("===========================================================================================");
            System.out.println();
        } catch (Exception e) {
            System.out.println("Enter a valid option number.");
            scanner.nextLine();
            isMenuOpen = true;
        }
    }





    private void addTransaction() {
        System.out.println("\u001B[33m                                    ADD TRANSACTION                            \u001B[0m");
        System.out.println();
        TransactionForm transactionForm = new TransactionForm(categoryLinkedList, transactionLinkedList);
        transactionForm.displayTransactionForm();
    }


    private void updateTransaction() {
        System.out.println("\u001B[33m                                    UPDATE TRANSACTION                          \u001B[0m");
        System.out.println();
        TransactionForm transactionForm = new TransactionForm(categoryLinkedList, transactionLinkedList);
        int updateOption = -1;;

        do {
            try {
                System.out.println();
                System.out.println("Select an update option:");
                System.out.println();
                System.out.println("1. Search transaction by ID");
                System.out.println("2. Search transaction by date");
                System.out.println("3. Search transaction by date range");
                System.out.println("4. Get all transactions");
                System.out.println("\u001B[34m5. Back to main menu\u001B[0m");
                System.out.println();
                System.out.println();
                System.out.print("Enter your choice: ");


                String input = scanner.nextLine();
                System.out.println();

                if (!input.matches("\\d+")) {
                    System.out.println();
                    System.out.println("\u001B[31mInvalid option. Please enter a valid option number.\u001B[0m");
                    System.out.println();
                    continue;
                }

                updateOption = Integer.parseInt(input);

                switch (updateOption) {
                    case 1:
                        transactionForm.getTransactionById();
                        break;
                    case 2:
                        transactionForm.searchTransactionsByDate();
                        break;
                    case 3:
                        transactionForm.searchTransactionsByDateRange();
                        break;
                    case 4:
//                        transactionForm.getAllTransactions();
                        break;
                    case 5:
                        System.out.println();
                        System.out.println("                                      \u001BReturning to main menu...\u001B[0m");
                        System.out.println();
                        break;
                    default:
                        System.out.println();
                        System.out.println("\u001B[31mInvalid option. Please enter a valid option number.\u001B[0m");
                        System.out.println();
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid option number.");
                updateOption = -1;
            }
        } while (updateOption != 5);
    }

    private void deleteTransaction() {
        System.out.println("\u001B[33m                                    DELETE TRANSACTION                          \u001B[0m");
        System.out.println();
        int deleteOption = -1;

        do {
            try {
                System.out.println();
                System.out.println("Select a delete option:");
                System.out.println();
                System.out.println("1. Search transaction by ID");
                System.out.println("2. Search transaction by date");
                System.out.println("3. Search transaction by date range");
                System.out.println("4. Get all transactions");
                System.out.println("\u001B[34m5. Back to main menu\u001B[0m");
                System.out.println();
                System.out.print("Enter your choice: ");


                String input = scanner.nextLine();
                System.out.println();

                if (!input.matches("\\d+")) {
                    System.out.println();
                    System.out.println("\u001B[31mInvalid option. Please enter a valid option number.\u001B[0m");
                    System.out.println();
                    continue;
                }

                deleteOption = Integer.parseInt(input);

                switch (deleteOption) {
                    case 1:
                        // Implement search transaction by ID and delete
                        break;
                    case 2:
                        // Implement search transaction by date and delete
                        break;
                    case 3:
                        // Implement search transaction by date range and delete
                        break;
                    case 4:
                        // Implement get all transactions and delete
                        break;
                    case 5:
                        System.out.println("                                      \u001BReturning to main menu...\u001B[0m");
                        break;
                    default:
                        System.out.println();
                        System.out.println("\u001B[31mInvalid option. Please enter a valid option number.\u001B[0m");
                        System.out.println();
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid option number.");
                deleteOption = -1;
            }
        } while (deleteOption != 5);
    }


    private void addCategory() {
        System.out.println("\u001B[32m                                    ADD CATEGORY                            \u001B[0m");
        System.out.println();
        CategoryForm categoryFrom = new CategoryForm(categoryLinkedList);
//        CategoryForm categoryFrom = new CategoryForm();
        categoryFrom.displayCategoryForm();
    }

    private void updateCategory() {
        System.out.println("\u001B[32m                                    UPDATE CATEGORY                          \u001B[0m");
        System.out.println();
        int updateOption = -1;

        do {
            try {
                System.out.println();
                System.out.println("Select an update option for categories:");
                System.out.println();
                System.out.println("1. Search categories by name");
                System.out.println("2. Search categories by date");
                System.out.println("3. Search categories by date range");
                System.out.println("4. Get all categories");
                System.out.println("\u001B[34m5. Back to main menu\u001B[0m");
                System.out.println();
                System.out.print("Enter your choice: ");


                String input = scanner.nextLine();
                System.out.println();

                if (!input.matches("\\d+")) {
                    System.out.println();
                    System.out.println("\u001B[31mInvalid option. Please enter a valid option number.\u001B[0m");
                    System.out.println();
                    continue;
                }

                updateOption = Integer.parseInt(input);
                CategoryForm categoryFrom = new CategoryForm(categoryLinkedList);
                switch (updateOption) {
                    case 1:

                        categoryFrom.updateCategoryByName();
                        break;
                    case 2:
                        categoryFrom.updateCategoryByDate();
                        break;
                    case 3:
                        categoryFrom.updateCategoryByDateRange();
                        break;
                    case 4:
                        categoryFrom.updateCategoryAll();
                        break;
                    case 5:
                        System.out.println();
                        System.out.println("                                      \u001BReturning to main menu...\u001B[0m");
                        System.out.println();
                        break;
                    default:
                        System.out.println();
                        System.out.println("\u001B[31mInvalid option. Please enter a valid option number.\u001B[0m");
                        System.out.println();
                }
            } catch (NumberFormatException e) {
                System.out.println();
                System.out.print("Invalid input. Please enter a valid option number. ");
                updateOption = -1;
                System.out.println();
            }
        } while (updateOption != 5);
    }

    private void deleteCategory() {
        System.out.println("\u001B[32m                                    DELETE CATEGORY                          \u001B[0m");
        System.out.println();
        int deleteOption = -1;

        do {
            try {
                System.out.println();
                System.out.println("Select a delete option for categories:");
                System.out.println();
                System.out.println("1. Search categories by ID");
                System.out.println("2. Search categories by date");
                System.out.println("3. Search categories by date range");
                System.out.println("4. Get all categories");
                System.out.println("\u001B[34m5. Back to main menu\u001B[0m");
                System.out.println();
                System.out.print("Enter your choice: ");


                String input = scanner.nextLine();
                System.out.println();

                if (!input.matches("\\d+")) {
                    System.out.println();
                    System.out.println("\u001B[31mInvalid option. Please enter a valid option number.\u001B[0m");
                    System.out.println();
                    continue;
                }

                deleteOption = Integer.parseInt(input);

                switch (deleteOption) {
                    case 1:

                        break;
                    case 2:

                        break;
                    case 3:

                        break;
                    case 4:

                        break;
                    case 5:
                        System.out.println("                                      \u001BReturning to main menu...\u001B[0m");
                        break;
                    default:
                        System.out.println("\u001B[31mInvalid option. Please enter a valid option number.\u001B[0m");
                }
            } catch (NumberFormatException e) {
                System.out.println();
                System.out.println("Invalid input. Please enter a valid option number.");
                System.out.println();
                deleteOption = -1;
            }
        } while (deleteOption != 5);
    }


    private void search() {
        TransactionForm transactionForm = new TransactionForm(categoryLinkedList, transactionLinkedList);
        CategoryForm categoryForm = new CategoryForm(categoryLinkedList);

        System.out.println("\u001B[34m                                    SEARCH TRANSACTION                            \u001B[0m");
        System.out.println();
        System.out.println();

        int searchOption;
        do {
            System.out.println();
            System.out.println("Select a search option:");
            System.out.println();
            System.out.println("\u001B[35m1. Search transactions\u001B[0m");
            System.out.println("\u001B[35m2. Search categories\u001B[0m");
            System.out.println("\u001B[34m5. Back to main menu\u001B[0m");
            System.out.println();
            System.out.print("Enter your choice: ");

            searchOption = scanner.nextInt();
            scanner.nextLine();

            switch (searchOption) {
                case 1:
                    transactionForm.getAllTransaction(transactionLinkedList);
                    break;
                case 2:
                    categoryForm.getAllCategories(categoryLinkedList);
                    break;
                case 5:
                    System.out.println();
                    System.out.println("                                      \u001BReturning to main menu...\u001B[0m");
                    System.out.println();
                    break;
                default:
                    System.out.println("\u001B[31mInvalid option. Please enter a valid option number.\u001B[0m");
            }
        } while (searchOption != 5);
    }

    private void help() {
        System.out.println("\u001B[34m                                    HELP TRANSACTION                            \u001B[0m");
        System.out.println();
        System.out.println("Inside the help");
    }

}
