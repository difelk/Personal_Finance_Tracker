
package ui;

import Category.Category;
import Category.CategoryNode;
import Category.CategoryLinkedList;
import transaction.Transaction;
import transaction.TransactionLinkedList;
import transaction.TransactionNode;
import utils.ValidationUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class TransactionForm {

    private final CategoryLinkedList categoryLinkedList;
    private final TransactionLinkedList transactionLinkedList;

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

        if (Double.isNaN(amount)) {
            return;
        }

        System.out.print("Enter description: ");
        String description = scanner.nextLine();

        System.out.print("Enter category name: ");
        Category category = getCategoryFromUserInput();

        if (category == null) {
            return;
        }

        categoryName = category.getName();

        LocalDateTime dateTime = getDateTimeFromUser();


        if (dateTime == null) {
            return;
        }

        boolean isIncome = isIncomeTransaction();


        if (!isIncome) {
            if (checkTransactionAmountExceedThanCategory(category, amount, isIncome)) {
                System.out.println("Error: Amount exceeds allocated budget for the selected category.");
                return;
            } else {
                updateCategoryIncAndExp(category, amount, isIncome);
            }
        } else {
            updateCategoryIncAndExp(category, amount, isIncome);
        }

        Transaction newTransaction = new Transaction(amount, description, category, dateTime, isIncome);
        transactionLinkedList.addTransaction(newTransaction);
        System.out.println();
        System.out.println("\u001B[34m=============================================================================================\u001B[0m");
        System.out.println("\u001B[34mTransaction added successfully.\u001B[0m");
        System.out.println("\u001B[34m=============================================================================================\u001B[0m");
        System.out.println();
        displayPreviousTransactions();
        System.out.println();
    }


    public Category getCategoryFromUserInput(){

        Category category = null;

        while (category == null) {
            String categoryName = scanner.nextLine().toLowerCase().trim();

            if (categoryName.equalsIgnoreCase("exit")) {
                System.out.println("Exiting to main menu...");
                return null;
            }

            if (categoryLinkedList.isCategoryExists(categoryName)) {
                    category = categoryLinkedList.getCategoryByName(categoryName).getData();
            } else {
                System.out.print("Category does not exist. Please enter a valid category name.");
            }
        }
        return category;
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

        System.out.println();
        System.out.println("Current Transaction Details:");
        System.out.println("Old Amount: " + existingTransaction.getAmount());
        System.out.println("Old Description: " + existingTransaction.getDescription());
        System.out.println("Old Category: " + existingTransaction.getCategory());
        System.out.println("Old Date and Time: " + existingTransaction.getDateTime());
        System.out.println("Is Income: " + (existingTransaction.isIncome() ? "Yes" : "No"));
        System.out.println();

        System.out.println("Enter updated values (press enter to skip):");

        System.out.print("New Amount: ");
        double updatedAmount = getUpdateAmountFromUser(existingTransaction.getAmount());

        System.out.print("New Description: ");
        String updatedDescription = scanner.nextLine().trim();
        if (updatedDescription.isEmpty()) {
            updatedDescription = existingTransaction.getDescription();
        }

        System.out.print("New Category: ");
        Category updatedCategory = getCategoryFromUser();

        LocalDateTime updatedDateTime = getDateTimeFromUser();

        if(updatedDateTime == null){
            return;
        }

        boolean updatedIsIncome = isIncomeTransaction();



        Transaction updatedTransaction = new Transaction(updatedAmount, updatedDescription, updatedCategory, updatedDateTime, updatedIsIncome);

        transactionLinkedList.updateTransactionById(transactionId, updatedTransaction);


        System.out.println();
        System.out.println("\u001B[34m=============================================================================================\u001B[0m");
        System.out.println("\u001B[34mTransaction updated successfully.\u001B[0m");
        System.out.println("\u001B[34m=============================================================================================\u001B[0m");
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

            System.out.println();
            System.out.print("Enter the number of the transaction to update: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            System.out.println();

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
        System.out.println();
        System.out.print("Enter the number of the transaction to update: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        System.out.println();
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

        System.out.print("Enter end date (YYYY-MM-DD): ");
        String endDateString = scanner.nextLine();

        return transactionLinkedList.getTransactionsByDateRange(startDateString, endDateString, "yyyy-MM-dd");
    }


    private void updateTransactionDetails(Transaction existingTransaction) {
        System.out.println("Current Transaction Details:");
        System.out.println();
        System.out.println("Old Amount: " + existingTransaction.getAmount());
        System.out.println("Old Description: " + existingTransaction.getDescription());
        System.out.println("Old Category: " + existingTransaction.getCategory());
        System.out.println("Old Date and Time: " + existingTransaction.getDateTime());
        System.out.println("Is Income: " + (existingTransaction.isIncome() ? "Yes" : "No"));
        System.out.println();

        System.out.println("Enter updated values below (press enter to skip):");
        System.out.print("Enter New Amount:");

        double updatedAmount = getUpdateAmountFromUser(existingTransaction.getAmount());

        System.out.print("New Description: ");
        String updatedDescription = scanner.nextLine().trim();

        if (updatedDescription.isEmpty()) {
            updatedDescription = existingTransaction.getDescription();
        }
        System.out.print("Enter Category:");
        Category updatedCategory = getCategoryFromUserInput();


        if(updatedCategory == null){
            return;
        }


        categoryName = updatedCategory.getName();

        LocalDateTime updatedDateTime = getDateTimeFromUser();

        boolean updatedIsIncome = isIncomeTransaction();

        boolean isItOldCategory = existingTransaction.getCategory().equals(categoryName);
        boolean isItOldIsIncome = existingTransaction.isIncome() == updatedIsIncome;
        boolean isItSameAmount = existingTransaction.getAmount() == updatedAmount;

        if (!isItSameAmount || !isItOldCategory || !isItOldIsIncome) {
            updateCategoryBudget(existingTransaction, updatedCategory, updatedAmount, updatedIsIncome);

            if (!updatedIsIncome && checkTransactionAmountExceedThanCategory(updatedCategory, updatedAmount, updatedIsIncome)) {
                System.out.println("Error: Amount exceeds allocated budget for the selected category.");
                return;
            }
        }



        Transaction newTransaction = new Transaction(updatedAmount, updatedDescription, updatedCategory, updatedDateTime, updatedIsIncome);
        transactionLinkedList.updateTransactionById(existingTransaction.getTransactionID(),newTransaction);
        System.out.println();
        System.out.println("\u001B[34m=============================================================================================\u001B[0m");
        System.out.println("\u001B[34mTransaction Updated successfully.\u001B[0m");
        System.out.println("\u001B[34m=============================================================================================\u001B[0m");
        System.out.println();
        displayPreviousTransactions();
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
                System.out.println();
                for (int i = 0; i < transactionNodes.size(); i++) {
                    System.out.println((i+1) + ". Description: " + transactionNodes.get(i).getData().getDescription());
                    System.out.println("   Category: " + transactionNodes.get(i).getData().getCategory());
                    System.out.println("   Transaction Date & Time: " + transactionNodes.get(i).getData().getDateTime());
                    System.out.println();
                }
                System.out.println();
                int selectedNo = 0;
                do {
                    System.out.print("Enter selected Transaction no: ");

                    String input = scanner.nextLine();

                    if (!ValidationUtils.isITANumber(input)) {
                        System.out.println();
                        System.out.println("\u001B[31mInvalid option. Please enter a valid option number.\u001B[0m");
                        System.out.println();
                        continue;
                    }
                    if(ValidationUtils.isITANumber(input)){
                        selectedNo = Integer.parseInt(input);
                    }
                    if (selectedNo < 1 || selectedNo > transactionNodes.size()) {
                        System.out.println();
                        System.out.println("\u001B[31mSelected number is invalid.\u001B[0m");
                        System.out.println();
                        continue;
                    }
                }while (selectedNo < 1 || selectedNo > transactionNodes.size());

                System.out.println();
                System.out.println("Selected Transaction Details");
                System.out.println();

                String defaultTransactionId = "202301011030transactionEXP";

                if(transactionNodes.get(selectedNo - 1).getData().getTransactionID() == null){
                    System.out.println("Transaction ID: " + defaultTransactionId);
                    System.out.println("Transaction Category: " + transactionNodes.get(selectedNo - 1).getData().getCategory());
                    System.out.println("Transaction Description: " + transactionNodes.get(selectedNo - 1).getData().getDescription());
                    System.out.println("Transaction Amount: " + transactionNodes.get(selectedNo - 1).getData().getAmount());
                    System.out.println("Transaction Date & Time: " + transactionNodes.get(selectedNo - 1).getData().getDateTime());
                    System.out.println("Type: " + (transactionNodes.get(selectedNo - 1).getData().isIncome() ? "INCOME" : "EXPENSES"));
                }
                else {
                    System.out.println("Transaction ID: " + transactionNodes.get(selectedNo - 1).getData().getTransactionID());
                    System.out.println("Transaction Category: " + transactionNodes.get(selectedNo - 1).getData().getCategory());
                    System.out.println("Transaction Description: " + transactionNodes.get(selectedNo - 1).getData().getDescription());
                    System.out.println("Transaction Amount: " + transactionNodes.get(selectedNo - 1).getData().getAmount());
                    System.out.println("Transaction Date & Time: " + transactionNodes.get(selectedNo - 1).getData().getDateTime());
                    System.out.println("Type: " + (transactionNodes.get(selectedNo - 1).getData().isIncome() ? "INCOME" : "EXPENSES"));
                }
            }
        }
        catch (NullPointerException e){
            System.out.println(e);
        }
    }

    public void getTransactionsIncExpByDate(String type){
        boolean isItAnIncome = type.equals("Incomes");
            System.out.printf("Enter date (yyyy-MM-dd) to get %s: ", type);
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
            List<TransactionNode> matchingTransactions = transactionLinkedList.getTransactionsByDate(startOfDay.toLocalDate());
            matchingTransactions =   matchingTransactions.stream().filter(node -> node.getData().isIncome() == isItAnIncome).collect(Collectors.toList());

            displayAllTransactionDetailsToDelete(matchingTransactions);
            if (matchingTransactions.isEmpty()) {
                System.out.println("\u001B[31m*******************************************************************************************************\u001B[0m");
                System.out.println("\u001B[31mNo transactions found with the given date.\u001B[0m");
                System.out.println("\u001B[31m*******************************************************************************************************\u001B[0m");
                System.out.println();
                return;
            }
    }

    public void getTransactionsIncExpByDateRange(String type){
        boolean isItAnIncome = type.equals("Incomes");

        System.out.print("Enter START date (yyyy-MM-dd) for the date range: ");
        String startDateStr = scanner.nextLine();

        System.out.print("Enter END date (yyyy-MM-dd) for the date range: ");
        String endDateStr = scanner.nextLine();

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
        transactionsInRange =   transactionsInRange.stream().filter(node -> node.getData().isIncome() == isItAnIncome).collect(Collectors.toList());

        displayAllTransactionDetailsToDelete(transactionsInRange);
        if (transactionsInRange.isEmpty()) {
            System.out.println("\u001B[31m*******************************************************************************************************\u001B[0m");
            System.out.println("\u001B[31mNo transactions found with the given date.\u001B[0m");
            System.out.println("\u001B[31m*******************************************************************************************************\u001B[0m");
            System.out.println();
            return;
        }

    }


    public void deleteTransactionById() {
        System.out.print("Enter Transaction ID to delete: ");
        String transactionId = scanner.nextLine();
        System.out.println();

        TransactionNode transactionNodeToDelete = transactionLinkedList.getTransactionById(transactionId);

        if (transactionNodeToDelete == null) {
            System.out.println();
            System.out.println("\u001B[31m*******************************************************************************************************\u001B[0m");
            System.out.println("\u001B[31mTransaction not found with the given ID.\u001B[0m");
            System.out.println("\u001B[31m*******************************************************************************************************\u001B[0m");
            System.out.println();
            return;
        }

        Transaction transactionToDelete = transactionNodeToDelete.getData();

        System.out.println("Transaction Details to Delete:");
        System.out.println();
        System.out.println("Amount: " + transactionToDelete.getAmount());
        System.out.println("Description: " + transactionToDelete.getDescription());
        System.out.println("Category: " + transactionToDelete.getCategory());
        System.out.println("Date and Time: " + transactionToDelete.getDateTime());
        System.out.println("Is Income: " + (transactionToDelete.isIncome() ? "Yes" : "No"));
        System.out.println();

        if (confirmDeletion(transactionToDelete)) {
            transactionLinkedList.deleteTransactionById(transactionId);
            System.out.println();
            System.out.println("\u001B[34m=============================================================================================\u001B[0m");
            System.out.println("\u001B[34mTransaction deleted successfully.\u001B[0m");
            System.out.println("\u001B[34m=============================================================================================\u001B[0m");
            System.out.println();
        } else {
            System.out.println("Transaction not deleted.");
        }
        System.out.println();
    }


    public void deleteTransactionByDate() {
        if(transactionLinkedList.getAllTransactions() != null){
            System.out.print("Enter transaction date (yyyy-MM-dd): ");
            String transactionDateStr = scanner.nextLine();
            LocalDate transactionDate;
            System.out.println();
            try {
                transactionDate = LocalDate.parse(transactionDateStr);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please use yyyy-MM-dd.");
                return;
            }

            LocalDateTime startOfDay = transactionDate.atStartOfDay();
            List<TransactionNode> matchingTransactions = transactionLinkedList.getTransactionsByDate(startOfDay.toLocalDate());

            displayAllTransactionDetailsToDelete(matchingTransactions);
            if (matchingTransactions.isEmpty()) {
                System.out.println("\u001B[31m*******************************************************************************************************\u001B[0m");
                System.out.println("\u001B[31mNo transactions found with the given date.\u001B[0m");
                System.out.println("\u001B[31m*******************************************************************************************************\u001B[0m");
                System.out.println();
                return;
            }

            TransactionNode selectedNode = selectTransaction(matchingTransactions);
            if (selectedNode != null) {
                System.out.println();
                System.out.println("Transaction Details:");
                System.out.println();
                displayTransactionDetails(selectedNode.getData());

                if (confirmDeletion(selectedNode.getData())) {
                    transactionLinkedList.deleteTransactionById(selectedNode.getData().getTransactionID());
                    System.out.println();
                    System.out.println("\u001B[34m=============================================================================================\u001B[0m");
                    System.out.println("\u001B[34mTransaction deleted successfully.\u001B[0m");
                    System.out.println("\u001B[34m=============================================================================================\u001B[0m");
                    System.out.println();
                } else {
                    System.out.println("Deletion canceled.");
                }
            }
        }
        else {
            System.out.println();
            System.out.println("\u001B[31m*******************************************************************************************************\u001B[0m");
            System.out.println("\u001B[31mNo transactions to display\u001B[0m");
            System.out.println("\u001B[31m*******************************************************************************************************\u001B[0m");
            System.out.println();
        }

    }



    public void deleteTransactionByDateRange() {
        System.out.print("Enter START date (yyyy-MM-dd) for the date range: ");
        String startDateStr = scanner.nextLine();

        System.out.print("Enter END date (yyyy-MM-dd) for the date range: ");
        String endDateStr = scanner.nextLine();

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
            System.out.println("\u001B[31m*******************************************************************************************************\u001B[0m");
            System.out.println("\u001B[31mNo transactions found within the specified date range.\u001B[0m");
            System.out.println("\u001B[31m*******************************************************************************************************\u001B[0m");
            System.out.println();

            return;
        }

        TransactionNode selectedNode = selectTransaction(transactionsInRange);
        if (selectedNode != null) {
            System.out.println();
            System.out.println("Transaction Details:");
            displayTransactionDetails(selectedNode.getData());

            if (confirmDeletion(selectedNode.getData())) {
                transactionLinkedList.deleteTransactionById(selectedNode.getData().getTransactionID());
                System.out.println();
                System.out.println("\u001B[34m=============================================================================================\u001B[0m");
                System.out.println("\u001B[34mTransaction deleted successfully.\u001B[0m");
                System.out.println("\u001B[34m=============================================================================================\u001B[0m");
                System.out.println();
            } else {
                System.out.println();
                System.out.println("Deletion canceled.");
                System.out.println();
            }
        }
    }



    public void deleteAllTransactions() {

        if(transactionLinkedList.getAllTransactions() != null) {

            List<TransactionNode> allTransactions = transactionLinkedList.getAllTransactions();

            if (allTransactions.isEmpty()) {
                System.out.println();
                System.out.println("No transactions found.");
                System.out.println();
                return;
            }
            displayAllTransactionDetailsToDelete(allTransactions);
                deleteSelectedTransaction(allTransactions);
        }
        else {
            System.out.println();
            System.out.println("\u001B[31m*******************************************************************************************************\u001B[0m");
            System.out.println("\u001B[31mNo transactions to display\u001B[0m");
            System.out.println("\u001B[31m*******************************************************************************************************\u001B[0m");
            System.out.println();
        }
    }

    public void deleteSelectedTransaction(List<TransactionNode> SelectedNode){
        System.out.println();
        int selectedNo = 0;
        do {
            System.out.print("Enter selected Transaction no to delete: ");

            String input = scanner.nextLine();
            System.out.println();
            if (!ValidationUtils.isITANumber(input)) {
                System.out.println();
                System.out.println("\u001B[31mInvalid option. Please enter a valid option number.\u001B[0m");
                System.out.println();
                continue;
            }
            if(ValidationUtils.isITANumber(input)){
                selectedNo = Integer.parseInt(input);
            }
            if (selectedNo < 1 || selectedNo > SelectedNode.size()) {
                System.out.println();
                System.out.println("\u001B[31mSelected number is invalid.\u001B[0m");
                System.out.println();
                continue;
            }
        }while (selectedNo < 1 || selectedNo > SelectedNode.size());

        if (SelectedNode != null) {
            System.out.println("Transaction Details:");
            System.out.println();

            if (confirmDeletion(SelectedNode.get(selectedNo-1).getData())) {
                transactionLinkedList.deleteTransactionById(SelectedNode.get(selectedNo-1).getData().getTransactionID());

                if(SelectedNode.get(selectedNo-1).getData().isIncome()){
                    removeAmountFromBudget(categoryLinkedList.getCategoryByName(SelectedNode.get(selectedNo-1).getData().getCategory()) , SelectedNode.get(selectedNo-1).getData().getAmount());
                }else{
                    addAmountToBudget(categoryLinkedList.getCategoryByName(SelectedNode.get(selectedNo-1).getData().getCategory()) , SelectedNode.get(selectedNo-1).getData().getAmount());
                }

                System.out.println();
                System.out.println("\u001B[34m=============================================================================================\u001B[0m");
                System.out.println("\u001B[34mTransaction deleted successfully.\u001B[0m");
                System.out.println("\u001B[34m=============================================================================================\u001B[0m");
                System.out.println();
            } else {
                System.out.println("Deletion canceled.");
            }
        }

    }


    private boolean confirmDeletion(Transaction transaction) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Are you sure you want to delete this transaction? (yes/no): ");
        String userInput = scanner.nextLine().trim().toLowerCase();
        return userInput.equals("yes") || userInput.equals("y");
    }


    public void displayTransactionDetails(Transaction transaction){
        System.out.println("Amount: " + transaction.getAmount());
        System.out.println("Description: " + transaction.getDescription());
        System.out.println("Category: " + transaction.getCategory());
        System.out.println("Date and Time: " + transaction.getDateTime());
        System.out.println("Is Income: " + (transaction.isIncome() ? "INCOME" : "EXPENSES"));
        System.out.println();

    }

    private double getAmountFromUser() {
        double amount = -1;
        boolean isValid = false;

        while (!isValid) {
            String amountInput = scanner.nextLine();
            if (amountInput.equalsIgnoreCase("exit")) {
                System.out.println("Exiting...");

                return Double.NaN;
            }
            try {
                amount = Double.parseDouble(amountInput);
                isValid = true;
            } catch (NumberFormatException e) {
                System.out.print("Invalid amount input. Please enter a valid number.");
            }
        }
        return amount;
    }
    private double getUpdateAmountFromUser(Double oldAmount) {
        double amount = -1;
        boolean isValid = false;

        while (!isValid) {
            String amountInput = scanner.nextLine();
            if (amountInput.equalsIgnoreCase("exit")) {
                System.out.println("Exiting...");
                return Double.NaN;
            }
            if(amountInput.isEmpty()){
                return oldAmount;
            }
            try {
                amount = Double.parseDouble(amountInput);
                isValid = true;
            } catch (NumberFormatException e) {
                System.out.print("Invalid amount input. Please enter a valid number.");
            }
        }
        return amount;
    }

    private Category getCategoryFromUser() {
        Category category = null;

        while (category == null) {
            String categoryName = scanner.nextLine().toLowerCase().trim();

            if (categoryName.equalsIgnoreCase("exit")) {
                System.out.println("Exiting to main menu...");
                return null;
            }

            if (categoryLinkedList.isCategoryExists(categoryName)) {
                if (this.amount > categoryLinkedList.getCategoryByName(categoryName).getData().getBudget()) {
                    // Handle budget exceeding error
                    System.out.println("\u001B[31m*******************************************************************************************************\u001B[0m");
                    System.out.println("\u001B[31mAmount exceeds allocated budget in " + categoryLinkedList.getCategoryByName(categoryName).getData().getName() + " category.\u001B[0m");
                    System.out.println("Remaining budget for " + categoryLinkedList.getCategoryByName(categoryName).getData().getName() + " is : " + categoryLinkedList.getCategoryByName(categoryName).getData().getBudget());
                    System.out.println("\u001B[31m*******************************************************************************************************\u001B[0m");
                    System.out.println();
                } else {
                    categoryLinkedList.getCategoryByName(categoryName).getData().setBudget(categoryLinkedList.getCategoryByName(categoryName).getData().getBudget() - this.amount);
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

        LocalDateTime dateTime = null;

        while (true) {
            if (!dateString.isEmpty()) {
                ValidationUtils validationUtils = new ValidationUtils();

                while (!validationUtils.isItAValidDate(dateString)) {
                    System.out.println();
                    System.out.print("Invalid date format. Please enter a valid date (YYYY-MM-DD): ");
                    dateString = scanner.nextLine();
                    System.out.println();
                }

                LocalDate date = LocalDate.parse(dateString);
                if (date.isAfter(LocalDate.now())) {
                    System.out.println();
                    System.out.print("Future date is not allowed. Please enter a valid date (YYYY-MM-DD): ");
                    dateString = scanner.nextLine();

                    if ("exit".equalsIgnoreCase(dateString.trim())) {
                        return null;
                    }
                } else {
                    if (!timeString.isEmpty()) {
                        ValidationUtils timeValidationUtils = new ValidationUtils();
                        while (!timeValidationUtils.isItAValidTime(timeString)) {
                            System.out.println();
                            System.out.print("Invalid time format. Please enter a valid time (HH:mm): ");
                            timeString = scanner.nextLine();
                            System.out.println();
                        }

                        LocalTime time = LocalTime.parse(timeString);
                        dateTime = LocalDateTime.of(date, time);
                    } else {
                        dateTime = date.atStartOfDay();
                    }
                    break;
                }
            } else if (!timeString.isEmpty()) {
                ValidationUtils validationUtils = new ValidationUtils();

                while (!validationUtils.isItAValidTime(timeString)) {
                    System.out.println();
                    System.out.print("Invalid time format. Please enter a valid time (HH:mm): ");
                    timeString = scanner.nextLine();
                    System.out.println();
                }

                LocalTime time = LocalTime.parse(timeString);
                dateTime = defaultDateTime.withHour(time.getHour()).withMinute(time.getMinute());
                break;
            }
        }

        return dateTime;
    }

    private void displayPreviousTransactions() {
        if (!transactionLinkedList.isEmpty()) {
            System.out.println();
            System.out.println("            Latest Transactions:");
            System.out.println();
            TransactionNode transaction = transactionLinkedList.getAllTransactions().get(0);
                System.out.println("                              Transaction ID: " + transaction.getData().getTransactionID());
                System.out.println("                              Transaction Description: " + transaction.getData().getDescription());
                System.out.println("                              Transaction Amount: " + transaction.getData().getAmount());
                System.out.println("                              Transaction Category: " + transaction.getData().getCategory());
                System.out.println("                              Transaction Date: " + transaction.getData().getDateTime());
                System.out.println();

        }
    }

    private boolean isIncomeTransaction() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Is it an income or expense? (type \"INC\" for income, \"EXP\" for expense): ");
            String transactionType = scanner.nextLine();

            if (transactionType.equalsIgnoreCase("INC")) {
                this.isIncome = true;
                return true;
            } else if (transactionType.equalsIgnoreCase("EXP")) {
                this.isIncome = false;
                return false;
            } else {
                System.out.println("Invalid input. Please enter \"INC\" for income or \"EXP\" for expense.");
            }
        }
    }


    public boolean checkTransactionAmountExceedThanCategory(Category cat, Double amount, boolean isIncome){
        if(!isIncome){
          return   amount > categoryLinkedList.getCategoryByName(cat.getName().toLowerCase().trim()).getData().getBudget();
        }
        return  false;
    }



    private void updateCategoryBudget(Transaction existingTransaction, Category updatedCategory, double updatedAmount, boolean updatedIsIncome) {
        String oldCategoryName = existingTransaction.getCategory();

        // Calculate the change in amount
        double amountChange = updatedAmount - existingTransaction.getAmount();

        // Update the old category's budget (subtract the old amount if it was an income, add if it was an expense)
        double oldCategoryBudgetChange = existingTransaction.isIncome() ? -existingTransaction.getAmount() : existingTransaction.getAmount();
        categoryLinkedList.getCategoryByName(oldCategoryName).getData().setBudget(
                categoryLinkedList.getCategoryByName(oldCategoryName).getData().getBudget() + oldCategoryBudgetChange
        );

        // Update the new category's budget (add the updated amount if it's an income, subtract if it's an expense)
        double newCategoryBudgetChange = updatedIsIncome ? updatedAmount : -updatedAmount;
        categoryLinkedList.getCategoryByName(updatedCategory.getName()).getData().setBudget(
                categoryLinkedList.getCategoryByName(updatedCategory.getName()).getData().getBudget() + newCategoryBudgetChange
        );

        // Check if the new category's budget exceeds the allocated budget for an expense
        if (!updatedIsIncome && checkTransactionAmountExceedThanCategory(updatedCategory, updatedAmount, updatedIsIncome)) {
            System.out.println("1Error: Amount exceeds allocated budget for the selected category.");
            return;
        }
    }



    public void removeAmountFromBudget(CategoryNode catLink, double amount){
        catLink.getData().setBudget(catLink.getData().getBudget() - amount);
    }

    public void addAmountToBudget(CategoryNode catLink, double amount){
        catLink.getData().setBudget(catLink.getData().getBudget() + amount);
    }


    public void updateCategoryIncAndExp(Category cat, double amount, boolean isIncome){
        if(isIncome){
            categoryLinkedList.getCategoryByName(cat.getName()).getData().setBudget(categoryLinkedList.getCategoryByName(categoryName).getData().getBudget() + amount);
        }else{
            categoryLinkedList.getCategoryByName(cat.getName()).getData().setBudget(categoryLinkedList.getCategoryByName(categoryName).getData().getBudget() - amount);
        }

    }

    public void displayAllTransactionDetailsToDelete(List<TransactionNode> allTransactions){

        for (int i = 0; i < allTransactions.size(); i++) {
            System.out.println((i + 1) + ". Transaction ID: " + allTransactions.get(i).getData().getTransactionID());
            System.out.println("   Transaction Amount: " + allTransactions.get(i).getData().getAmount());
            System.out.println("   Transaction Category: " + allTransactions.get(i).getData().getCategory());
            System.out.println("   Transaction Description: " + allTransactions.get(i).getData().getDescription());
            System.out.println("   Transaction Date & Time: " + allTransactions.get(i).getData().getDateTime());
            System.out.println();
        }
    }

    public void displayAllTransactionDetailsForSummary(List<TransactionNode> allTransactions) {
        if (allTransactions.isEmpty()) {
            System.out.println("No transactions to display.");
            return;
        }

        double totalIncome = 0.0;
        double totalExpense = 0.0;

        System.out.println("+----+--------------+--------------------+-----------------------------+------------------------+----------------+");
        System.out.println("| #  | Transaction  | Amount (Rs.)       | Category                    | Description            | Date & Time    |");
        System.out.println("+----+--------------+--------------------+-----------------------------+------------------------+----------------+");

        for (int i = 0; i < allTransactions.size(); i++) {
            TransactionNode node = allTransactions.get(i);
            Transaction data = node.getData();
            String transactionType = data.isIncome() ? "Income" : "Expense";

            System.out.printf("| %-2d | %-10s   | Rs. %-14.2f | %-28s| %-24s| %s |\n",
                    (i + 1),
                    transactionType,
                    data.getAmount(),
                    data.getCategory(),
                    data.getDescription(),
                    data.getDateTime().truncatedTo(ChronoUnit.MINUTES)
            );

            if (data.isIncome()) {
                totalIncome += data.getAmount();
            } else {
                totalExpense += data.getAmount();
            }
        }

        System.out.println("+----+--------------+--------------------+-----------------------------+------------------------+----------------+");

        double netTotal = totalIncome - totalExpense;
        System.out.printf("Net Total: Rs. %.2f\n", netTotal);
    }




    public void getSummary(){
        System.out.print("Enter START date (yyyy-MM-dd) for the date range: ");
        String startDateStr = scanner.nextLine();

        System.out.print("Enter END date (yyyy-MM-dd) for the date range: ");
        String endDateStr = scanner.nextLine();


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
            System.out.println("\u001B[31m*******************************************************************************************************\u001B[0m");
            System.out.println("\u001B[31mNo transactions found within the specified date range.\u001B[0m");
            System.out.println("\u001B[31m*******************************************************************************************************\u001B[0m");
            System.out.println();

            return;
        }

            System.out.println();
            displayAllTransactionDetailsForSummary(transactionsInRange);

            System.out.println();


    }

}
