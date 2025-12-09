package model;

import java.time.Instant;
import java.util.Objects;

public class Product {
    private final int id;
    private final String name;
    private final Instant creationDatetime;
    private final Category category;

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
        return "model.Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", creationDatetime=" + creationDatetime +
                ", category=" + category +
                '}';
    }
}
