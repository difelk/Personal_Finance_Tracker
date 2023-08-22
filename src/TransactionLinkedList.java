public class TransactionLinkedList {
    TransactionNode head;


    public void addTransaction(Transaction data){
        TransactionNode newTransactionNode = new TransactionNode(data);

        if(this.head == null){
            this.head = newTransactionNode;
            return;
        }
        newTransactionNode.setNextRef(this.head);
        this.head = newTransactionNode;
    }

}
