package transaction;
import transaction.TransactionLinkedList;
import utils.ValidationUtils;

import java.util.List;

public class TransactionSearch {

    TransactionLinkedList transactionLinkedList = new TransactionLinkedList();
    ValidationUtils validationUtils = new ValidationUtils();

    public TransactionNode searchByTransactionId(String transactionId) {
        return transactionLinkedList.getTransactionById(transactionId);
    }

    public TransactionNode searchByTransactionDate(String date) {
        if (!validationUtils.isDateAFutureDate(date)) {
            return null;
        }
        List<TransactionNode> transactions = transactionLinkedList.getTransactionByDate(date, "MM/dd/yy 00:00");
        return transactions.isEmpty() ? null : transactions.get(0);
    }

    public List<TransactionNode> searchByTransactionDateRange(String startDate, String endDate) {
        if (!validationUtils.isStartDateLessThanOrEqualToEndDate(startDate, endDate)) {
            return null;
        } else {
            return transactionLinkedList.getTransactionsByDateRange(startDate, endDate, "MM/dd/yy 00:00");
        }
    }

    public List<TransactionNode> searchAllTransactions() {
        return transactionLinkedList.getAllTransactions();
    }
}
