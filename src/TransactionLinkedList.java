import java.time.ZonedDateTime;
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
        if (isEmpty()) {
            return null;
        } else {
            TransactionNode tempNode = this.head;
            while (tempNode != null) {
                Transaction data = tempNode.getData();
                if (data.getTransactionID().equals(transactionId)) {
                    return tempNode;
                }
                tempNode = tempNode.getNextRef();
            }
            return null;
        }
    }

    public List<TransactionNode> getTransactionByDate(String dateTime, String formate){
        if (isEmpty()){
            return null;
        }else{
            List<TransactionNode> transactionNodes = new ArrayList<>();
            TransactionNode tempNode = this.head;
            ZonedDateTime paramDateTime = ZonedDateTime.from(dataManipulationUtils.ConvertDateStringToLocalDateTime(dateTime, formate));
            while (tempNode != null) {
                if (tempNode.getData().getDateTime() == paramDateTime) {
                    transactionNodes.add(tempNode);
                    tempNode = tempNode.getNextRef();
                }
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
                ZonedDateTime transactionDateTime = tempNode.getData().getDateTime();
                if (!transactionDateTime.isBefore(startDateTime) && !transactionDateTime.isAfter(endDateTime)) {
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

    public boolean isEmpty(){
        return this.head == null;
    }

}
