
package ui;

import Category.Category;
import Category.CategoryNode;
import Category.CategoryLinkedList;
import transaction.Transaction;
import transaction.TransactionLinkedList;
import transaction.TransactionNode;
import utils.DataManipulationUtils;
import utils.ValidationUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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


    public void updateTransactionById() {
        System.out.print("Enter Transaction ID to update: ");
        String transactionId = scanner.nextLine();
        System.out.println();

        TransactionNode transactionNodeToUpdate = transactionLinkedList.getTransactionById(transactionId);

        if (transactionNodeToUpdate == null) {
            System.out.println("Transaction not found with the given ID.");
            return;
        }

        Transaction existingTransaction = transactionNodeToUpdate.getData();

        System.out.println("Current Transaction Details:");
        System.out.println("Old Amount: " + existingTransaction.getAmount());
        System.out.println("Old Description: " + existingTransaction.getDescription());
        System.out.println("Old Category: " + existingTransaction.getCategory());
        System.out.println("Old Date and Time: " + existingTransaction.getDateTime());
        System.out.println("Is Income: " + (existingTransaction.isIncome() ? "Yes" : "No"));
        System.out.println();

        System.out.println("Enter updated values (press enter to skip):");

        System.out.print("New Amount: ");
        double updatedAmount = getAmountFromUser();

        System.out.print("New Description: ");
        String updatedDescription = scanner.nextLine().trim();
        if (updatedDescription.isEmpty()) {
            updatedDescription = existingTransaction.getDescription();
        }

        System.out.print("New Category: ");
        Category updatedCategory = getCategoryFromUser();

        LocalDateTime updatedDateTime = getDateTimeFromUser();

        boolean updatedIsIncome = isIncomeTransaction();

        Transaction updatedTransaction = new Transaction(updatedAmount, updatedDescription, updatedCategory, updatedDateTime, updatedIsIncome);

        transactionLinkedList.updateTransactionById(transactionId, updatedTransaction);

        System.out.println("Transaction updated successfully.");
        System.out.println();
    }


    public void updateTransactionByDate() {
        System.out.print("Enter transaction date (yyyy-MM-dd) to update: ");
        String transactionDateStr = scanner.nextLine();
        System.out.println();

        LocalDate transactionDate;
        try {
            transactionDate = LocalDate.parse(transactionDateStr);
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Please use yyyy-MM-dd.");
            return;
        }

        LocalDateTime startOfDay = transactionDate.atStartOfDay();
        List<TransactionNode> matchingTransactions = transactionLinkedList.getTransactionsByDate(LocalDate.from(startOfDay));

        if (matchingTransactions.isEmpty()) {
            System.out.println("No transactions found with the given date.");
            return;
        }

        if (matchingTransactions.size() > 1) {
            System.out.println("Multiple transactions found with the same date. Please choose a specific transaction:");
            for (int i = 0; i < matchingTransactions.size(); i++) {
                System.out.println((i + 1) + ". " + matchingTransactions.get(i).getData().getDescription());
            }

            System.out.print("Enter the number of the transaction to update: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice < 1 || choice > matchingTransactions.size()) {
                System.out.println("Invalid choice.");
                return;
            }

            TransactionNode selectedNode = matchingTransactions.get(choice - 1);
            updateTransactionDetails(selectedNode.getData());
        } else {
            updateTransactionDetails(matchingTransactions.get(0).getData());
        }
    }




    public void updateTransactionByDateRange() {
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

        LocalDateTime startDateInLocalDateTime = startDate.atStartOfDay();
        LocalDateTime endDateInLocalDateTime = endDate.atStartOfDay();
        List<TransactionNode> transactionsInRange = transactionLinkedList.getTransactionsByDateRange(startDateInLocalDateTime, endDateInLocalDateTime);

        if (transactionsInRange.isEmpty()) {
            System.out.println("No transactions found within the specified date range.");
            return;
        }

        System.out.println("Transactions found within the specified date range:");
        for (int i = 0; i < transactionsInRange.size(); i++) {
            System.out.println((i + 1) + ". " + transactionsInRange.get(i).getData().getDescription());
        }

        System.out.print("Enter the number of the transaction to update: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice < 1 || choice > transactionsInRange.size()) {
            System.out.println("Invalid choice.");
            return;
        }

        TransactionNode selectedNode = transactionsInRange.get(choice - 1);
        updateTransactionDetails(selectedNode.getData());
    }



    public void updateAllTransactions() {
        List<TransactionNode> allTransactions = transactionLinkedList.getAllTransactions();

        if (allTransactions.isEmpty()) {
            System.out.println("No transactions found.");
            return;
        }

        System.out.println("All Transactions:");
        for (int i = 0; i < allTransactions.size(); i++) {
            System.out.println((i + 1) + ". " + allTransactions.get(i).getData().getDescription());
        }

        System.out.print("Enter the number of the transaction to update: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice < 1 || choice > allTransactions.size()) {
            System.out.println("Invalid choice.");
            return;
        }

        TransactionNode selectedNode = allTransactions.get(choice - 1);
        updateTransactionDetails(selectedNode.getData());
    }

    public List<TransactionNode> searchTransactionsByDateRange() {
        System.out.print("Enter start date (YYYY-MM-DD): ");
        String startDateString = scanner.nextLine();
        // Validate and convert startDateString if needed

        System.out.print("Enter end date (YYYY-MM-DD): ");
        String endDateString = scanner.nextLine();

        return transactionLinkedList.getTransactionsByDateRange(startDateString, endDateString, "yyyy-MM-dd");
    }


    private void updateTransactionDetails(Transaction existingTransaction) {
        System.out.println("Current Transaction Details:");
        System.out.println("Old Amount: " + existingTransaction.getAmount());
        System.out.println("Old Description: " + existingTransaction.getDescription());
        System.out.println("Old Category: " + existingTransaction.getCategory());
        System.out.println("Old Date and Time: " + existingTransaction.getDateTime());
        System.out.println("Is Income: " + (existingTransaction.isIncome() ? "Yes" : "No"));
        System.out.println();

        System.out.println("Enter updated values (press enter to skip):");

        double updatedAmount = getAmountFromUser();

        System.out.print("New Description: ");
        String updatedDescription = scanner.nextLine().trim();
        if (updatedDescription.isEmpty()) {
            updatedDescription = existingTransaction.getDescription();
        }

        Category updatedCategory = getCategoryFromUser();

        LocalDateTime updatedDateTime = getDateTimeFromUser();

        boolean updatedIsIncome = isIncomeTransaction();

        Transaction updatedTransaction = new Transaction(updatedAmount, updatedDescription, updatedCategory, updatedDateTime, updatedIsIncome);

        transactionLinkedList.updateTransactionById(existingTransaction.getTransactionID(), updatedTransaction);

        System.out.println("Transaction updated successfully.");
        System.out.println();
    }

    public void getAllTransaction(TransactionLinkedList transactionLinkedList){
        List<TransactionNode> transactionNodes = transactionLinkedList.getAllTransactions();

        try{
            if(transactionLinkedList.isEmpty()){
                System.out.println();
                System.out.println("\u001B[31mNo Transaction details to display\u001B[0m");
            }
            else {
                for (int i = 0; i < transactionNodes.size(); i++) {
                    System.out.println((i+1) + ". Description: " + transactionNodes.get(i).getData().getDescription());
                    System.out.println("   Category: " + transactionNodes.get(i).getData().getCategory());
                }
                System.out.println();
                System.out.print("Enter selected Category no: ");
                int selectedNo = scanner.nextInt();

                System.out.println();

                if (selectedNo < 1 || selectedNo > transactionNodes.size()) {
                    System.out.println("Selected number is invalid");
                    return;
                }

                System.out.println();
                System.out.println("Selected Transaction Details");
                System.out.println();

                System.out.println("Transaction Description: " + transactionNodes.get(selectedNo - 1).getData().getDescription());
                System.out.println("Transaction Category: " + transactionNodes.get(selectedNo - 1).getData().getCategory());
                System.out.println("Transaction Date & Time: " + transactionNodes.get(selectedNo - 1).getData().getDateTime());
            }
        }
        catch (NullPointerException e){
            System.out.println(e);
        }
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
