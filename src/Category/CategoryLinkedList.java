package Category;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CategoryLinkedList {
    public CategoryNode head;

    public void addCategory(Category data) {
        String categoryName = data.getName().toLowerCase();

        if (isEmpty() || !isCategoryExists(categoryName)) {
            CategoryNode newCategoryNode = new CategoryNode(data);

            if (!isEmpty()){
                newCategoryNode.setNextRef(this.head);
            }
            this.head = newCategoryNode;
        }
    }

    public void updateCategory(Category updatedCategory) {
        CategoryNode categoryNodeToUpdate = getCategoryByName(updatedCategory.getName());

        if (categoryNodeToUpdate != null) {
            Category existingCategory = categoryNodeToUpdate.getData();


            if (updatedCategory.getBudget() >= 0) {
                existingCategory.setBudget(updatedCategory.getBudget());
            }
            if (updatedCategory.getDescription() != null) {
                existingCategory.setDescription(updatedCategory.getDescription());
            }


            if (!existingCategory.getName().equalsIgnoreCase(updatedCategory.getName()) &&
                    !isCategoryExists(updatedCategory.getName())) {
                existingCategory.setName(updatedCategory.getName());
            }
        }
    }

    public void deleteCategory(Category categoryToDelete) {
        CategoryNode categoryNodeToDelete = getCategoryByName(categoryToDelete.getName());

        if (categoryNodeToDelete != null) {
            Category existingCategory = categoryNodeToDelete.getData();

            if (categoryToDelete.getCategoryID().equals(existingCategory.getCategoryID())) {

                if (head == categoryNodeToDelete) {
                    head = head.getNextRef();
                } else {
                    CategoryNode currentNode = head;
                    while (currentNode.getNextRef() != null) {
                        if (currentNode.getNextRef() == categoryNodeToDelete) {
                            currentNode.setNextRef(categoryNodeToDelete.getNextRef());
                            break;
                        }
                        currentNode = currentNode.getNextRef();
                    }
                }
            }
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

    public List<CategoryNode> getCategoriesByDay(LocalDate day) {
        List<CategoryNode> matchingCategories = new ArrayList<>();

        CategoryNode currentNode = head;
        while (currentNode != null) {
            LocalDate categoryCreationDate = currentNode.getData().getCreationDate().toLocalDate();
            if (categoryCreationDate.equals(day)) {
                matchingCategories.add(currentNode);
            }
            currentNode = currentNode.getNextRef();
        }

        return matchingCategories;
    }


    public List<CategoryNode> getCategoriesByDateRange(LocalDate startDate, LocalDate endDate) {
        List<CategoryNode> categoriesInRange = new ArrayList<>();

        CategoryNode currentNode = head;
        while (currentNode != null) {
            LocalDate categoryCreationDate = currentNode.getData().getCreationDate().toLocalDate();
            if (!categoryCreationDate.isBefore(startDate) && !categoryCreationDate.isAfter(endDate)) {
                categoriesInRange.add(currentNode);
            }
            currentNode = currentNode.getNextRef();
        }

        return categoriesInRange;
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


    public boolean isCategoryExists(String categoryName) {
        CategoryNode currentNode = head;

        while (currentNode != null) {
            if (currentNode.getData().getName().toLowerCase().equals(categoryName)) {
                return true;
            }
            currentNode = currentNode.getNextRef();
        }
        return false;
    }
    public boolean isEmpty(){
        return this.head == null;
    }
}
