package Category;

public class CategoryLinkedList {
    public CategoryNode head;

    public void addCategory(Category data){
        CategoryNode newCategoryNode = new CategoryNode(data);

        if(!isEmpty()) {
            newCategoryNode.setNextRef(this.head);
        }
        this.head = newCategoryNode;

        CategoryNode currentNode = head;
        while(currentNode.next != null){
            currentNode = currentNode.next;
        }

    }
    public boolean isEmpty(){
        return this.head == null;
    }
}
