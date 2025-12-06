import java.time.Instant;

public class Product {
    private int id;
    private String name;
    private Instant creationDatetime;
    private Category category;

    public Product(){}
    
    public Product(Category category, Instant creationDatetime, int id, String name) {
        this.category = category;
        this.creationDatetime = creationDatetime;
        this.id = id;
        this.name = name;
    }

    public String getCategoryName(){
        return this.category.getName();
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Instant getCreationDatetime() {
        return creationDatetime;
    }

    public void setCreationDatetime(Instant creationDatetime) {
        this.creationDatetime = creationDatetime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Product{" +
                "category=" + category +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", creationDatetime=" + creationDatetime +
                '}';
    }
}
