package Category;

public class CategoryNode {
    private Category data;
    CategoryNode next;

    public CategoryNode(Category data){
        this.data = data;
        this.next = null;
    }
    public Category getData() {
        return data;
    }

    public void setData(Category data) {
        this.data = data;
    }

    public void setNextRef(CategoryNode next){
        this.next = next;
    }

    public CategoryNode getNextRef(){
        return this.next;
    }
}
