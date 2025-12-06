import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class DataRetriever {
    private final DBConnection dbConnection = new DBConnection();

    public List<Category> getAllCategories(){
        String query;
        List<Category> categoryList;
        Category category;

        query = "SELECT id, name FROM product_category;";
        categoryList = new ArrayList<>();
        category = null;
        try(Connection conn = dbConnection.getDBConnection()) {
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
        Product product;
        Category category;

        query = "SELECT pr.id, pr.name, pr.creation_datetime, pc.id AS pci, pc.name as pcn FROM product pr INNER JOIN product_category pc on pr.id = pc.product_id LIMIT ? OFFSET ?;";
        offset = (page - 1) * size;
        productList = new ArrayList<>();
        product = null;
        category = null;
        try(Connection conn = dbConnection.getDBConnection()) {
            PreparedStatement st = conn.prepareStatement(query);
            st.setInt(1, size);
            st.setInt(2, offset);
           try (ResultSet rs = st.executeQuery()) {
               while (rs.next()) {
                   int id = rs.getInt("id");
                   String name = rs.getString("name");
                   Instant creationDatetime = rs.getTimestamp("creation_datetime")
                           .toInstant();
                   int pci = rs.getInt("pci");
                   String pcn = rs.getString("pcn");
                   category = new Category(pci, pcn);
                   product = new Product(id, name, creationDatetime, category);
                   productList.add(product);
               }
           }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return productList;
    }
}
