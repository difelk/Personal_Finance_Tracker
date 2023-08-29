package transaction;

import utils.DataManipulationUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TransactionLinkedList {
    TransactionNode head;

    DataManipulationUtils dataManipulationUtils = new DataManipulationUtils();
    public void addTransaction(Transaction data){
        TransactionNode newTransactionNode = new TransactionNode(data);

        if(isEmpty()){
            this.head = newTransactionNode;
            return;
        }
        newTransactionNode.setNextRef(this.head);
        this.head = newTransactionNode;
    }

    public TransactionNode getTransactionById(String transactionId) {
        if (!isEmpty()) {
            TransactionNode tempNode = this.head;
            while (tempNode != null) {
                Transaction data = tempNode.getData();
                if (data.getTransactionID().equals(transactionId)) {
                    return tempNode;
                }
                tempNode = tempNode.getNextRef();
            }
        }
        return null;
    }

    public void updateTransactionById(String transactionId, Transaction updatedTransaction) {
        TransactionNode currentNode = head;
        while (currentNode != null) {
            if (currentNode.getData().getTransactionID().equals(transactionId)) {
                currentNode.getData().setAmount(updatedTransaction.getAmount());
                currentNode.getData().setDescription(updatedTransaction.getDescription());
                currentNode.getData().setCategory(updatedTransaction.getCategory());
                currentNode.getData().setDateTime(updatedTransaction.getDateTime());
                currentNode.getData().setIncome(updatedTransaction.isIncome());
                return;
            }
            currentNode = currentNode.getNextRef();
        }
    }


    public List<TransactionNode> getTransactionByDate(String dateTime, String formate){
        if (isEmpty()){
            return null;
        }else{
            List<TransactionNode> transactionNodes = new ArrayList<>();
            TransactionNode tempNode = this.head;
            LocalDateTime paramDateTime = LocalDateTime.from(dataManipulationUtils.ConvertDateStringToLocalDateTime(dateTime, formate));
            while (tempNode != null) {
                if (tempNode.getData().getDateTime() == paramDateTime) {
                    transactionNodes.add(tempNode);
                    tempNode = tempNode.getNextRef();
                }
            }
            return transactionNodes;
        }
    }

    public List<TransactionNode> getTransactionsByDate(LocalDate date) {
        if (isEmpty()) {
            return null;
        } else {
            List<TransactionNode> transactionNodes = new ArrayList<>();
            TransactionNode tempNode = this.head;

            LocalDateTime startOfDay = date.atStartOfDay();
            LocalDateTime endOfDay = date.plusDays(1).atStartOfDay();

            while (tempNode != null) {
                LocalDateTime transactionDateTime = tempNode.getData().getDateTime();
                if (transactionDateTime.isAfter(startOfDay) && transactionDateTime.isBefore(endOfDay)) {
                    transactionNodes.add(tempNode);
                }
                tempNode = tempNode.getNextRef();
            }

            return transactionNodes;
        }
    }
    public List<TransactionNode> getTransactionsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        if (isEmpty()) {
            return null;
        } else {
            List<TransactionNode> transactionNodes = new ArrayList<>();
            TransactionNode tempNode = this.head;

            while (tempNode != null) {
                LocalDateTime transactionDateTime = tempNode.getData().getDateTime();
                if (!transactionDateTime.isBefore(startDate) && !transactionDateTime.isAfter(endDate)) {
                    transactionNodes.add(tempNode);
                }
                tempNode = tempNode.getNextRef();
            }

            return transactionNodes;
        }
    }

    public List<TransactionNode> getTransactionsByDateRange(String startDate, String endDate, String formatPattern) {
        if (isEmpty()) {
            return null;
        } else {
            List<TransactionNode> transactionNodes = new ArrayList<>();
            TransactionNode tempNode = this.head;

            ZonedDateTime startDateTime = ZonedDateTime.from(dataManipulationUtils.ConvertDateStringToLocalDateTime(startDate, formatPattern));
            ZonedDateTime endDateTime = ZonedDateTime.from(dataManipulationUtils.ConvertDateStringToLocalDateTime(endDate, formatPattern));

            while (tempNode != null) {
                LocalDateTime transactionDateTime = tempNode.getData().getDateTime();
                if (!transactionDateTime.isBefore(ChronoLocalDateTime.from(startDateTime)) && !transactionDateTime.isAfter(ChronoLocalDateTime.from(endDateTime))) {
                    transactionNodes.add(tempNode);
                }
                tempNode = tempNode.getNextRef();
            }

            return transactionNodes;
        }
    }
    public List<TransactionNode> getAllTransactions() {
        if (isEmpty()) {
            return null;
        }
        List<TransactionNode> transactionNodes = new ArrayList<>();
        TransactionNode tempNode = this.head;

        while (tempNode != null) {
            transactionNodes.add(tempNode);
            tempNode = tempNode.getNextRef();
        }
        return transactionNodes;
    }


    public void deleteTransactionById(String transactionId) {
        if (isEmpty()) {
            System.out.println("No transactions to delete.");
            return;
        }

        if (head.getData().getTransactionID().equals(transactionId)) {
            head = head.getNextRef();
            System.out.println("Transaction deleted successfully.");
            return;
        }

        TransactionNode currentNode = head;
        TransactionNode prevNode = currentNode;

        while (currentNode != null) {
            if (currentNode.getData().getTransactionID().equals(transactionId)) {
                prevNode.setNextRef(currentNode.getNextRef());
                System.out.println("Transaction deleted successfully.");
                return;
            }
            prevNode = currentNode;
            currentNode = currentNode.getNextRef();
        }

        // Transaction not found with the given ID
        System.out.println("Transaction not found with the given ID.");
    }


    public void deleteAllTransactions() {
        if (isEmpty()) {
            System.out.println("No transactions to delete.");
            return;
        }

        head = null;
        System.out.println("All transactions deleted successfully.");
    }


    public boolean isEmpty(){
        return this.head == null;
    }

}
