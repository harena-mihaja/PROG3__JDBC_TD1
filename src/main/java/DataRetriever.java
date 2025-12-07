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

        query = "SELECT id, name FROM product_category";
        categoryList = new ArrayList<>();
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

        query = "SELECT pr.id, pr.name, pr.creation_datetime, pc.id AS pci, pc.name as pcn FROM product pr INNER JOIN product_category pc on pr.id = pc.product_id LIMIT ? OFFSET ?";
        offset = (page - 1) * size;
        productList = new ArrayList<>();
        try(Connection conn = dbConnection.getDBConnection()) {
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
        StringBuilder sb;
        String query;
        List<Product> productList;
        List<Object> parameters = new ArrayList<>();

        sb = new StringBuilder("SELECT pr.id, pr.name, pr.creation_datetime, pc.id AS pci, pc.name as pcn FROM product pr INNER JOIN product_category pc on pr.id = pc.product_id WHERE 1=1");
        if (productName != null){
            sb.append(" AND pr.name ILIKE ?");
            parameters.add("%" + productName + "%");
        }
        if (categoryName != null){
            sb.append( "AND pcn ILIKE ?");
            parameters.add("%" + categoryName + "%");
        }
        if(creationMax != null && creationMin != null){
            sb.append(" AND creation_datetime >= ? AND creation_datetime <= ?");
            parameters.add(creationMin);
            parameters.add(creationMax);
        }
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
    }
}
