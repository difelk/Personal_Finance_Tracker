import Category.Category;
import transaction.TransactionLinkedList;
import Category.CategoryLinkedList;
import Category.CategoryNode;

import java.util.List;
import java.util.Objects;

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


        String name = "Kadachoru";
        // all
        List<CategoryNode> allCategories = categoryList.getAllCategories();
        // by name
        CategoryNode findName = categoryList.getCategoryByName(name);
        Category searchCategory = null;

        for (CategoryNode node : allCategories) {
            Category category = node.getData();

            if (Objects.equals(category.getName(), name)) {
                searchCategory = category;
            }
        }

        if(searchCategory != null){
            System.out.println("\nID: " + searchCategory.getCategoryID());
            System.out.println("Name: " + searchCategory.getName());
            System.out.println("Description: " + searchCategory.getDescription());
            System.out.println("Date: " + searchCategory.getCreationDate());
        }else{
            System.out.println("Category not found.");
        }




    }
}