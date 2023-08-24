import Category.Category;
import transaction.TransactionLinkedList;
import Category.CategoryLinkedList;
import Category.CategoryNode;

public class Main {
    public static void main(String[] args) {
        TransactionLinkedList transactionLinkedList = new TransactionLinkedList();

        Category category = new Category("Salary","Test Salary Transaction");
        CategoryLinkedList categoryList = new CategoryLinkedList();

        categoryList.addCategory(category);

        CategoryNode currentNode = categoryList.head;
        while (currentNode != null) {
            System.out.println("ID: " + currentNode.getData().getCategoryID());
            System.out.println("Name: " + currentNode.getData().getName());
            System.out.println("Description: " + currentNode.getData().getDescription());
            System.out.println("Date: " + currentNode.getData().getCreationDate());

            currentNode = currentNode.getNextRef();
        }
    }
}