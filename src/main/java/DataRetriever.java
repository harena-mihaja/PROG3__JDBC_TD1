import model.Category;
import model.Product;

import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class DataRetriever {
    private final DBConnection dbConnection = new DBConnection();

    public List<Category> getAllCategories(){
        String query;
        List<Category> categoryList;
        Category category;

        query = "SELECT id, name FROM product_category ORDER BY id";
        categoryList = new ArrayList<>();
        try (Connection conn = dbConnection.getDBConnection()) {
            PreparedStatement st = conn.prepareStatement(query);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    category = new Category(id, name);
                    categoryList.add(category);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return (categoryList);
    }

    public List<Product> getProductList (int page, int size) {
        String query;
        int offset;
        List<Product> productList;

        query = "SELECT pr.id, pr.name, pr.creation_datetime, pc.id AS pci, pc.name as pcn FROM product pr INNER JOIN product_category pc on pr.id = pc.product_id ORDER BY pr.id LIMIT ? OFFSET ?";
        offset = (page - 1) * size;
        productList = new ArrayList<>();
        try (Connection conn = dbConnection.getDBConnection()) {
            PreparedStatement st = conn.prepareStatement(query);
            st.setInt(1, size);
            st.setInt(2, offset);
            productMapping(productList, st);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return productList;
    }

    public List<Product> getProductsByCriteria(String productName, String categoryName, Instant creationMin, Instant creationMax){
        List<Object> parameters;
        StringBuilder sb;

        parameters = new ArrayList<>();
        sb = getStringBuilder(productName, categoryName, creationMin, creationMax, parameters);
        return (getProducts(sb, parameters));
    }

    public List<Product> getProductsByCriteria(String productName, String categoryName, Instant creationMin, Instant creationMax, int page, int size){
        int offset;
        List<Object> parameters;
        StringBuilder sb;

        parameters = new ArrayList<>();
        sb = getStringBuilder(productName, categoryName, creationMin, creationMax, parameters);
        sb.append(" LIMIT ? OFFSET ?");
        offset = (page - 1) * size;
        parameters.add(size);
        parameters.add(offset);
        return (getProducts(sb, parameters));
    }

    private StringBuilder getStringBuilder(String productName, String categoryName, Instant creationMin, Instant creationMax, List<Object> parameters){
        StringBuilder sb;

        sb = new StringBuilder("SELECT pr.id, pr.name, pr.creation_datetime, pc.id , pc.name FROM product pr INNER JOIN product_category pc on pr.id = pc.product_id WHERE 1=1");
        if (productName != null){
            sb.append(" AND pr.name ILIKE ?");
            parameters.add("%" + productName + "%");
        }
        if (categoryName != null){
            sb.append( " AND pc.name ILIKE ?");
            parameters.add("%" + categoryName + "%");
        }
        if(creationMax != null && creationMin != null){
            sb.append(" AND creation_datetime >= ? AND creation_datetime <= ?");
            parameters.add(Timestamp.from(creationMin));
            parameters.add(Timestamp.from(creationMax));
        }
        sb.append(" ORDER BY pr.id");
        return sb;
    }

    private List<Product> getProducts(StringBuilder sb, List<Object> parameters) {
        String query;
        List<Product> productList;

        query = sb.toString();
        productList = new ArrayList<>();
        try(Connection conn = dbConnection.getDBConnection()) {
            PreparedStatement st = conn.prepareStatement(query);
            for (int i = 0; i < parameters.size(); i++) {
                st.setObject(i + 1, parameters.get(i));
            }
            productMapping(productList, st);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return productList;
    }

    private void productMapping(List<Product> productList, PreparedStatement st) throws SQLException {
        Category category;
        Product product;

        try (ResultSet rs = st.executeQuery()){
            while (rs.next()){
                int id = rs.getInt(1);
                String name = rs.getString(2);
                Instant creationDatetime = rs.getTimestamp(3)
                        .toInstant();
                int pci = rs.getInt(4);
                String pcn = rs.getString(5);
                category = new Category(pci, pcn);
                product = new Product(id, name, creationDatetime, category);
                productList.add(product);
            }
        }
    }
}
