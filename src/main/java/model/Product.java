package model;

import java.time.Instant;
import java.util.Objects;

public class Product {
    private int id;
    private String name;
    private Instant creationDatetime;
    private Category category;

    public Product(int id, String name, Instant creationDatetime, Category category) {
        this.id = id;
        this.name = name;
        this.creationDatetime = creationDatetime;
        this.category = category;
    }

    public String getCategoryName(){
        return this.category.getName();
    }

    public Category getCategory() {
        return category;
    }

    public Instant getCreationDatetime() {
        return creationDatetime;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setCreationDatetime(Instant creationDatetime) {
        this.creationDatetime = creationDatetime;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return getId() == product.getId() && Objects.equals(getName(), product.getName()) && Objects.equals(getCreationDatetime(), product.getCreationDatetime()) && Objects.equals(getCategory(), product.getCategory());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getCreationDatetime(), getCategory());
    }

    @Override
    public String toString() {
        return "# Product:"
                + "\n - id: " + id
                + "\n - name: " + name
                + "\n - creation: " + creationDatetime
                + "\n *" + category;
    }
}
