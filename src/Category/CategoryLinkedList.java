package Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryLinkedList {
    public CategoryNode head;

    public void addCategory(Category data){
        CategoryNode newCategoryNode = new CategoryNode(data);

        if(!isEmpty()){
            newCategoryNode.setNextRef(this.head);
        }
        this.head = newCategoryNode;

        CategoryNode currentNode = head;
        while(currentNode.next != null){
            currentNode = currentNode.next;
        }
    }
    public CategoryNode getCategoryByName(String categoryName) {
        CategoryNode currentNode = head;

        if(!isEmpty()){
            while (currentNode != null) {
                if (currentNode.getData().getName().equals(categoryName)) {
                    return currentNode;
                }
                currentNode = currentNode.getNextRef();
            }
        }
        return null;
    }
    public List<CategoryNode> getAllCategories() {
        if (!isEmpty()){
            List<CategoryNode> categoryNodes = new ArrayList<>();
            CategoryNode currentNode = head;

            while (currentNode != null) {
                categoryNodes.add(currentNode);
                currentNode = currentNode.getNextRef();
            }
            return categoryNodes;
        }
        return null;
    }
    public boolean isEmpty(){
        return this.head == null;
    }
}
