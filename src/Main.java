import Category.CategoryLinkedList;
import ui.MainMenu;


public class Main {

    private static final CategoryLinkedList categoryLinkedList = new CategoryLinkedList();
    public static void main(String[] args) {
        MainMenu mainMenu = new MainMenu();
        mainMenu.displayMenu();

    }



}