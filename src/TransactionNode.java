public class TransactionNode {

   private Transaction data;
    private TransactionNode nextRef;


    public TransactionNode(Transaction data){
        this.data = data;
        this.nextRef = null;
    }

    public Transaction getData() {
        return data;
    }

    public void setData(Transaction data) {
        this.data = data;
    }

    public void setNextRef(TransactionNode nextRef){
        this.nextRef = nextRef;
    }

    public TransactionNode getNextRef(){
       return this.nextRef;
    }




}
