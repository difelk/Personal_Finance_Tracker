public class Node {

   private Transaction data;
    private Node nextRef;


    public Node(Transaction data){
        this.data = data;
        this.nextRef = null;
    }

    public Transaction getData() {
        return data;
    }

    public void setData(Transaction data) {
        this.data = data;
    }

    public void setNextRef(Node nextRef){
        this.nextRef = nextRef;
    }

    public Node getNextRef(){
       return this.nextRef;
    }



}
