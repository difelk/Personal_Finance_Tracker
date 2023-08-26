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
        do {
            System.out.println();
            System.out.println();
            System.out.println("\u001B[36m                                   MAIN MENU                            \u001B[0m");
            System.out.println();
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
            selectedOption = scanner.nextInt();
            System.out.println();
            System.out.println();

            System.out.println();
            System.out.println("----------------------------------------------");
            System.out.println();
        } catch (Exception e) {
            System.out.println("Enter a valid option number.");
            scanner.nextLine();
            isMenuOpen = true;
        }
    }

    private void addTransaction() {
        System.out.println("                                    ADD TRANSACTION                            ");
        System.out.println();
        TransactionForm transactionForm = new TransactionForm(categoryLinkedList);
        transactionForm.displayTransactionForm();
    }

    private void updateTransaction() {
        System.out.println("                                    UPDATE TRANSACTION                            ");
        System.out.println();
        System.out.println("Inside the update transaction");
    }

    private void deleteTransaction() {
        System.out.println("                                    DELETE TRANSACTION                            ");
        System.out.println();
        System.out.println("Inside the delete transaction");
    }

    private void addCategory() {
        System.out.println("                                    ADD CATEGORY                            ");
        System.out.println();
        System.out.println("Inside the add category");
    }

    private void updateCategory() {
        System.out.println("                                    UPDATE TRANSACTION                            ");
        System.out.println();
        System.out.println("Inside the update category");
    }

    private void deleteCategory() {
        System.out.println("                                    DELETE TRANSACTION                            ");
        System.out.println();
        System.out.println("Inside the delete category");
    }

    private void search() {
        System.out.println("                                    SEARCH TRANSACTION                            ");
        System.out.println();
        System.out.println("Inside the search");
    }

    private void help() {
        System.out.println("                                    HELP TRANSACTION                            ");
        System.out.println();
        System.out.println("Inside the help");
    }

}
