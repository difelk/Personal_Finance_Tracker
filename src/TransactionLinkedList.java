public class TransactionLinkedList {
    TransactionNode head;
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


    public boolean isEmpty(){
        return this.head == null;
    }

}
