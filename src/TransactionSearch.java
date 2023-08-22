public class TransactionSearch {

    TransactionLinkedList transactionLinkedList = new TransactionLinkedList();
    public TransactionNode searchByTransactionId(String transactionId) {
       return transactionLinkedList.getTransactionById(transactionId);
    }

    public TransactionNode searchByTransactionDate(String date) {
        return  null;
    }

    public TransactionNode searchByTransactionDateRange(String startDate, String endDate) {
        return  null;
    }

    public TransactionNode searchAllTransactions() {
        return  null;
    }
}
