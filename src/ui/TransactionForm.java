package ui;

import Category.Category;
import Category.CategoryLinkedList;
import transaction.Transaction;
import transaction.TransactionLinkedList;
import utils.DataManipulationUtils;
import utils.ValidationUtils;
import Category.CategoryNode;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Scanner;

public class TransactionForm {

    private String amount;
    private String description;
    private String category;
    private String date;
    private String time;

    private boolean isIncome;

    private final ValidationUtils validationUtils = new ValidationUtils();

    private  final  DataManipulationUtils dataManipulationUtils = new DataManipulationUtils();

    private final TransactionLinkedList transactionLinkedList = new TransactionLinkedList();

    private Transaction transaction;
    private final CategoryLinkedList categoryLinkedList;
    private final Scanner scanner = new Scanner(System.in);

    public TransactionForm(CategoryLinkedList categoryLinkedList) {
        this.categoryLinkedList = categoryLinkedList;
    }


    public void displayTransactionForm() {
        boolean continueEntering = true;

        while (continueEntering) {
            int invalidInputCounter = 0;

            amount = getInput("* Amount:");
            description = getInput("Description:");
            category = getInput("* Category:");
            date = getInput("Date (YYYY-MM-DD):");
            time = getInput("Time (HH:mm):");
            String transactionType = getInput("Is it an Income or expense? (type \"INC\" for INCOME, \"EXP\" for EXPENSE)");

            boolean isValid = validateInput(amount, description, category, date, time, transactionType);

            if (isValid) {
                invalidInputCounter = 0;
                submitForm(amount, description, category, date, time, transactionType);
            } else {
                invalidInputCounter++;
            }

            if (!isValid) {
                System.out.println("You've entered invalid input(s).");
                if (!askToContinue()) {
                    System.out.println("Exiting.");
                    continueEntering = false;
                }
                invalidInputCounter = 0;
            }
        }
}


    private String getInput(String prompt) {
        System.out.print(prompt + " ");
        return scanner.nextLine();
    }

    private boolean validateInput(String amount, String description, String category, String date, String time, String transactionType) {
        boolean isAmountValid = validationUtils.isITANumber(amount);
        boolean isCategoryValid = !validationUtils.isEmptyInput(category) &&
                categoryLinkedList.isCategoryExists(category);
        if(this.date.equals("") && this.time.equals("")){

        }else{

        }
        boolean isDateValid = validationUtils.isItAValidDate(date);
        boolean isTimeValid = validationUtils.isItAValidTime(time);

        if (!isAmountValid) {
            System.out.println();
            System.out.println("\u001B[31mInvalid amount\u001B[0m");
            System.out.println();
        }
        if (!isCategoryValid) {
            System.out.println();
            System.out.println("\u001B[31mInvalid category\u001B[0m");
            System.out.println();
        }
        if (!isDateValid) {
            System.out.println();
            System.out.println("\u001B[31mInvalid date\u001B[0m");
            System.out.println();
        }
        if (!isTimeValid) {
            System.out.println();
            System.out.println("\u001B[31mInvalid time\u001B[0m");
            System.out.println();
        }

        if ("INC".equalsIgnoreCase(transactionType) || "INCOME".equalsIgnoreCase(transactionType)) {
            this.isIncome = true;
        } else if ("EXP".equalsIgnoreCase(transactionType) || "EXPENSES".equalsIgnoreCase(transactionType) || "EXPENSE".equalsIgnoreCase(transactionType)) {
            this.isIncome = false;
        } else {
            System.out.println("\u001B[31mInvalid transaction type\u001B[0m");
            return false;
        }

        return isAmountValid && isCategoryValid && isDateValid && isTimeValid;
    }

    private void submitForm(String amount, String description, String category, String date, String time, String transactionType) {
        CategoryNode selectedCategory = categoryLinkedList.getCategoryByName(category);

        double amountValue = dataManipulationUtils.convertStringToDouble(amount);
        LocalDateTime dateValue = dataManipulationUtils.ConvertDateStringToLocalDateTime(date);
        LocalTime timeValue = LocalTime.from(dataManipulationUtils.convertStringToTime(time));
        LocalDateTime fullDateTime = dataManipulationUtils.concatDateAndTime(LocalDate.from(dateValue), timeValue);

        Transaction transaction = new Transaction(amountValue, description, selectedCategory, fullDateTime, isIncome);

        transactionLinkedList.addTransaction(transaction);
    }





    private boolean askToContinue() {
        System.out.print("Do you want to continue entering data? (yes/no): ");
        String response = scanner.nextLine().trim().toLowerCase();
        return response.equals("yes") || response.equals("y");
    }
}
