import Category.Category;
import transaction.TransactionLinkedList;
import Category.CategoryLinkedList;
import Category.CategoryNode;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        TransactionLinkedList transactionLinkedList = new TransactionLinkedList();

        Category category1 = new Category("Salary","Test Salary");
        Category category2 = new Category("Kadachoru","Test Kadachoru");
        Category category3 = new Category("Salary","Test Salary 2");
        CategoryLinkedList categoryList = new CategoryLinkedList();

        categoryList.addCategory(category1);
        categoryList.addCategory(category2);
        categoryList.addCategory(category3);

//        CategoryNode currentNode = categoryList.head;
//        while (currentNode != null) {
//            System.out.println("ID: " + currentNode.getData().getCategoryID());
//            System.out.println("Name: " + currentNode.getData().getName());
//            System.out.println("Description: " + currentNode.getData().getDescription());
//            System.out.println("Date: " + currentNode.getData().getCreationDate());
//
//            currentNode = currentNode.getNextRef();
//        }

        String name = "Kadachoru";
        // all
        List<CategoryNode> allCategories = categoryList.getAllCategories();
        // by name
        CategoryNode findName = categoryList.getCategoryByName(name);

        for(int i = 0; i < allCategories.size(); i++){
            CategoryNode node = allCategories.get(i);
            Category category = node.getData();

            if (category.getName() == name) {
                System.out.println("\nID: " + category.getCategoryID());
                System.out.println("Name: " + category.getName());
                System.out.println("Description: " + category.getDescription());
                System.out.println("Date: " + category.getCreationDate());

            }
//            if (findName != null) {
//                Category findCategory = findName.getData();
//                System.out.println("________________________________________");
//                System.out.println("\nID: " + findCategory.getCategoryID());
//                System.out.println("Name: " + findCategory.getName());
//                System.out.println("Description: " + findCategory.getDescription());
//                System.out.println("Date: " + findCategory.getCreationDate());
//            }
            else {
                System.out.println("Category not found.");
            }
        }


    }
}