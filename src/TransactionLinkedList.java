public class TransactionLinkedList {
    Node head;


    public void addTransaction(Transaction data){
        Node newNode = new Node(data);

        if(head == null){
            this.head = newNode;
        }else{
            newNode.setNextRef(this.head);
            this.head = newNode;
        }
    }

}
