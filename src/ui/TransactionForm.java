
package ui;

import Category.Category;
import Category.CategoryNode;
import Category.CategoryLinkedList;
import transaction.Transaction;
import transaction.TransactionLinkedList;
import transaction.TransactionNode;
import utils.DataManipulationUtils;
import utils.ValidationUtils;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class TransactionForm {

    private final CategoryLinkedList categoryLinkedList;
    private final TransactionLinkedList transactionLinkedList;

//    private final DataManipulationUtils dataManipulationUtils;
    private final Scanner scanner;

     private double amount = 0;

     private String categoryName = null;
    private boolean isIncome;

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
        System.out.println();
        System.out.println("Transaction added successfully.");
        displayPreviousTransactions();
    }


    public TransactionNode selectTransaction(List<TransactionNode> transactions) {
        if (transactions != null && !transactions.isEmpty()) {
            System.out.print("Select a transaction by entering its number: ");
            int selectedNumber = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (selectedNumber >= 1 && selectedNumber <= transactions.size()) {
                return transactions.get(selectedNumber - 1);
            } else {
                System.out.println("Invalid selection. Please enter a valid number.");
            }
        } else {
            System.out.println("No transactions found.");
        }

        return null;
    }


    public TransactionNode getTransactionById(){
        System.out.print("Enter Transaction ID: ");
        String id = scanner.next();
        System.out.println();
        if(id.isEmpty()){
            System.out.println("you enter Empty Transaction ID");
        }else{
            TransactionNode transactionNode = transactionLinkedList.getTransactionById(id);
            if(transactionNode == null){
                System.out.println("invalid Transaction id");
            }else{
                return transactionNode;
            }
        }

       return null;
    }


    public List<TransactionNode> searchTransactionsByDate() {
        System.out.print("Enter date to search (YYYY-MM-DD): ");
        String dateString = scanner.nextLine();
        // Validate and convert dateString if needed

        return transactionLinkedList.getTransactionByDate(dateString, "yyyy-MM-dd");
    }


    public List<TransactionNode> searchTransactionsByDateRange() {
        System.out.print("Enter start date (YYYY-MM-DD): ");
        String startDateString = scanner.nextLine();
        // Validate and convert startDateString if needed

        System.out.print("Enter end date (YYYY-MM-DD): ");
        String endDateString = scanner.nextLine();
        // Validate and convert endDateString if needed

        return transactionLinkedList.getTransactionsByDateRange(startDateString, endDateString, "yyyy-MM-dd");
    }


    private void updateTransaction() {
        System.out.println("                                    UPDATE TRANSACTION                            ");
        System.out.println();
        TransactionForm transactionForm = new TransactionForm(categoryLinkedList, transactionLinkedList);

        int updateOption = -1;

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
                        TransactionNode transactionNode = transactionForm.getTransactionById();
                        if (transactionNode != null) {
                            System.out.println();
                            System.out.println("selected Transaction by ID details:");
                            System.out.println();
                            System.out.println("Transaction ID: " +transactionNode.getData().getTransactionID());
                            System.out.println("Transaction Description: " +transactionNode.getData().getDescription());
                            System.out.println("Transaction Category: " +transactionNode.getData().getAmount());
                            System.out.println("Transaction Category: " +transactionNode.getData().getCategory());
                            System.out.println("Transaction Category: " +transactionNode.getData().getDateTime());
                            System.out.println();
                        }
                        break;

                    case 2:
                        List<TransactionNode> transactionsByDate = transactionForm.searchTransactionsByDate();
                        if (!transactionsByDate.isEmpty()) {
                            TransactionNode selectedTransactionByDate = transactionForm.selectTransaction(transactionsByDate);
                            if (selectedTransactionByDate != null) {
                                // Perform update operation on selectedTransactionByDate.getData()
                            }
                        }
                        break;

                    case 3:
                        List<TransactionNode> transactionsByDateRange = transactionForm.searchTransactionsByDateRange();
                        if (!transactionsByDateRange.isEmpty()) {
                            TransactionNode selectedTransactionByDateRange = transactionForm.selectTransaction(transactionsByDateRange);
                            if (selectedTransactionByDateRange != null) {
                                // Perform update operation on selectedTransactionByDateRange.getData()
                            }
                        }
                        break;

                    case 4:
                        List<TransactionNode> allTransactions = transactionLinkedList.getAllTransactions();
                        if (!allTransactions.isEmpty()) {
                            TransactionNode selectedTransaction = transactionForm.selectTransaction(allTransactions);
                            if (selectedTransaction != null) {
                                // Perform update operation on selectedTransaction.getData()
                            }
                        }
                        break;

                    case 5:
                        System.out.println();
                        System.out.println("                                      \u001B[34mReturning to main menu...\u001B[0m");
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


    private double getAmountFromUser() {
        double amount = -1;
        boolean isValid = false;

        while (!isValid) {
            String amountInput = scanner.nextLine();
            if (amountInput.equalsIgnoreCase("exit")) {
                System.out.println("Exiting...");

                return 0;
            }
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
            String categoryName = scanner.nextLine().toLowerCase().trim();

            if (categoryLinkedList.isCategoryExists(categoryName)) {
                if(this.amount > categoryLinkedList.getCategoryByName(categoryName).getData().getBudget()){
                    System.out.println("amount is exceeding than allocated budget in " + categoryLinkedList.getCategoryByName(categoryName).getData().getName() + " category.");
                    System.out.println("remaining budget for " + categoryLinkedList.getCategoryByName(categoryName).getData().getName() + " is : " + categoryLinkedList.getCategoryByName(categoryName).getData().getBudget());

                }else{
                    categoryLinkedList.getCategoryByName(categoryName).getData().setBudget(categoryLinkedList.getCategoryByName(categoryName).getData().getBudget() - this.amount);
                    category = categoryLinkedList.getCategoryByName(categoryName).getData();
                }

            } else {
                System.out.print("Category does not exist. Please enter a valid category name.");
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
            dateTime = LocalDateTime.parse(dateTimeString, formatter);

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
            System.out.println();
            System.out.println("            Previous Transactions:");
            System.out.println();
            for (TransactionNode transaction : transactionLinkedList.getAllTransactions()) {
                System.out.println("                              Transaction ID: " + transaction.getData().getTransactionID());
                System.out.println("                              Transaction Description: " + transaction.getData().getDescription());
                System.out.println("                              Transaction Amount: " + transaction.getData().getAmount());
                System.out.println("                              Transaction Date: " + transaction.getData().getCategory());
                System.out.println("                              Transaction Date: " + transaction.getData().getDateTime());
                System.out.println();
            }
        }
    }

    private void handleCategoryBudget() {
        if(this.isIncome){
            categoryLinkedList.getCategoryByName(categoryName).getData().setBudget(categoryLinkedList.getCategoryByName(categoryName).getData().getBudget() + this.amount);
        }else{
            categoryLinkedList.getCategoryByName(categoryName).getData().setBudget(categoryLinkedList.getCategoryByName(categoryName).getData().getBudget() - this.amount);
        }
    }

    private boolean isIncomeTransaction() {
        System.out.print("Is it an income or expense? (type \"INC\" for income, \"EXP\" for expense): ");
        String transactionType = scanner.nextLine();
        this.isIncome = transactionType.equalsIgnoreCase("INC");
        return transactionType.equalsIgnoreCase("INC");
    }
}
