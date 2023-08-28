import Category.Category;
import transaction.TransactionLinkedList;
import Category.CategoryLinkedList;
import Category.CategoryNode;
import ui.MainMenu;

import java.util.List;
import java.util.Objects;

public class Main {
    public static void main(String[] args) {


        MainMenu mainMenu = new MainMenu();
        Main.preBuildData();
        mainMenu.displayMenu();


    }


    public static void preBuildData(){
        //create hardcoded 3 categories (water bill(expenses), electricity bill(expenses), house rent(income),
        //create hardcoded 3 transaction for above each category,
    }
}