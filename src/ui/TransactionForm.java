
package ui;

import Category.Category;
import Category.CategoryLinkedList;
import transaction.Transaction;
import transaction.TransactionLinkedList;
import transaction.TransactionNode;
import utils.DataManipulationUtils;
import utils.ValidationUtils;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class TransactionForm {

    private final CategoryLinkedList categoryLinkedList;
    private final TransactionLinkedList transactionLinkedList;
    private final Scanner scanner;

     private double amount = 0;
    public TransactionForm(CategoryLinkedList categoryLinkedList, TransactionLinkedList transactionLinkedList) {
        this.categoryLinkedList = categoryLinkedList;
        this.transactionLinkedList = transactionLinkedList;
        this.scanner = new Scanner(System.in);
    }

    public void displayTransactionForm() {
        System.out.print("Enter amount: ");
        amount = getAmountFromUser();

        System.out.print("Enter description: ");
        String description = scanner.nextLine();

        System.out.print("Enter category name: ");
        Category category = getCategoryFromUser();

        LocalDateTime dateTime = getDateTimeFromUser();

        boolean isIncome = isIncomeTransaction();

        Transaction newTransaction = new Transaction(amount, description, category, dateTime, isIncome);

        transactionLinkedList.addTransaction(newTransaction);

        System.out.println("Transaction added successfully.");
        displayPreviousTransactions();
    }

    private double getAmountFromUser() {
        double amount = -1;
        boolean isValid = false;

        while (!isValid) {
            String amountInput = scanner.nextLine();
            try {
                amount = Double.parseDouble(amountInput);
                isValid = true;
            } catch (NumberFormatException e) {
                System.out.println("Invalid amount input. Please enter a valid number.");
            }
        }

        return amount;
    }

    private Category getCategoryFromUser() {
        Category category = null;

        while (category == null) {
            String categoryName = scanner.nextLine().toLowerCase();

            if (categoryLinkedList.isCategoryExists(categoryName)) {
                if(this.amount > categoryLinkedList.getCategoryByName(categoryName).getData().getBudget()){
                    System.out.println("amount is exceeding than allocated budget in " + categoryLinkedList.getCategoryByName(categoryName).getData().getName() + " category.");

                }else{
                    category = categoryLinkedList.getCategoryByName(categoryName).getData();
                }

            } else {
                System.out.println("Category does not exist. Please enter a valid category name.");
            }
        }

        return category;
    }

    private LocalDateTime getDateTimeFromUser() {
        LocalDateTime defaultDateTime = LocalDateTime.now();

        System.out.print("Enter date (YYYY-MM-DD): ");
        String dateString = scanner.nextLine();

        System.out.print("Enter time (HH:mm): ");
        String timeString = scanner.nextLine();

        if (dateString.isEmpty() && timeString.isEmpty()) {
            return defaultDateTime;
        }

        LocalDateTime dateTime;

        if (!dateString.isEmpty() && !timeString.isEmpty()) {
            ValidationUtils validationUtils = new ValidationUtils();

            while (!validationUtils.isItAValidDate(dateString)) {
                System.out.println("Invalid date format. Please enter a valid date (YYYY-MM-DD): ");
                dateString = scanner.nextLine();
            }

            while (!validationUtils.isItAValidTime(timeString)) {
                System.out.println("Invalid time format. Please enter a valid time (HH:mm): ");
                timeString = scanner.nextLine();
            }

            String dateTimeString = dateString + " " + timeString;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            dateTime = LocalDateTime.parse(dateTimeString);
        } else if (!dateString.isEmpty()) {
            ValidationUtils validationUtils = new ValidationUtils();

            while (!validationUtils.isItAValidDate(dateString)) {
                System.out.println("Invalid date format. Please enter a valid date (YYYY-MM-DD): ");
                dateString = scanner.nextLine();
            }

            dateTime = DataManipulationUtils.ConvertDateStringToLocalDateTime(dateString).with(LocalTime.MIN);
        } else if (!timeString.isEmpty()) {
            ValidationUtils validationUtils = new ValidationUtils();

            while (!validationUtils.isItAValidTime(timeString)) {
                System.out.println("Invalid time format. Please enter a valid time (HH:mm): ");
                timeString = scanner.nextLine();
            }

            LocalDateTime time = DataManipulationUtils.convertStringToTime(timeString);
            dateTime = defaultDateTime.withHour(time.getHour()).withMinute(time.getMinute());
        } else {
            dateTime = defaultDateTime;
        }

        return dateTime;
    }


    private void displayPreviousTransactions() {
        if (!transactionLinkedList.isEmpty()) {
            System.out.println("Previous Transactions:");
            for (TransactionNode transaction : transactionLinkedList.getAllTransactions()) {
                System.out.println(transaction);
            }
        }
    }

    private boolean isIncomeTransaction() {
        System.out.print("Is it an income or expense? (type \"INC\" for income, \"EXP\" for expense): ");
        String transactionType = scanner.nextLine();

        return transactionType.equalsIgnoreCase("INC");
    }
}