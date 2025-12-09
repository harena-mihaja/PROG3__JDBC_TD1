import model.Category;
import model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DataRetrieverTest {
    DataRetriever dataRetriever;

    @BeforeEach
    void setUp() {
        dataRetriever = new DataRetriever();
    }

    @Test
    void getAllCategories() {
        List<Category> categoryList = dataRetriever.getAllCategories();

        assertEquals(7,categoryList.size());
        assertEquals(1, categoryList.getFirst().getId());
        assertEquals(7, categoryList.getLast().getId());
    }

    @Test
    void getProductList() {
        List<Product> productList1 = dataRetriever.getProductList(1,10);
        List<Product> productList2 = dataRetriever.getProductList(1, 5);
        List<Product> productList3 = dataRetriever.getProductList(1, 3);
        List<Product> productList4 = dataRetriever.getProductList(2,2);
        
        assertTrue(productList1.size() <= 10 );
        assertFalse(productList1.isEmpty());
        assertTrue(productList2.size() <= 5);
        assertFalse(productList2.isEmpty());
        assertTrue(productList3.size() <= 3);
        assertFalse(productList3.isEmpty());
        assertTrue(productList4.size() <= 2);
        assertFalse(productList4.isEmpty());
    }

    @Test
    void getProductsByCriteria() {
        List<Product> pr1 = dataRetriever.getProductsByCriteria("Dell", null, null, null);
        List<Product> pr2 = dataRetriever.getProductsByCriteria(null, "info", null, null);
        List<Product> pr3 = dataRetriever.getProductsByCriteria("iPhone", "mobile", null, null);
        List<Product> pr4 = dataRetriever.getProductsByCriteria(null, null, Instant.parse("2024-02-01T00:00:00Z"), Instant.parse("2024-03-01T00:00:00Z"));
        List<Product> pr5 = dataRetriever.getProductsByCriteria("Samsung", "bureau", null, null);
        List<Product> pr6 = dataRetriever.getProductsByCriteria("Sony", "Informatique", null, null);
        List<Product> pr7 = dataRetriever.getProductsByCriteria(null, "audio", Instant.parse("2024-01-01T00:00:00Z"), Instant.parse("2024-12-01T00:00:00Z"));
        List<Product> pr8 = dataRetriever.getProductsByCriteria(null, null, null, null);

        for (Product product : pr1) {
            assertEquals("Laptop Dell XPS", product.getName());
        }
        for (Product product : pr2) {
            assertEquals("Informatique", product.getCategoryName());
        }
        for (Product product : pr3) {
            assertEquals("iPhone 13", product.getName());
            assertEquals("Mobile", product.getCategoryName());
        }
        for (Product product : pr4){
            assertTrue(product.getCreationDatetime().isAfter(Instant.parse("2024-01-31T23:59:59Z")));
            assertTrue(product.getCreationDatetime().isBefore(Instant.parse("2024-03-01T00:00:01Z")));
        }
        for (Product product : pr5) {
            assertEquals("Écran Samsung 27\"", product.getName());
            assertEquals("Bureau", product.getCategoryName());
        }

        assertTrue(pr6.isEmpty());

        for (Product product : pr7){
            assertTrue(product.getCreationDatetime().isAfter(Instant.parse("2023-12-31T23:59:59Z")));
            assertTrue(product.getCreationDatetime().isBefore(Instant.parse("2024-12-01T00:00:01Z")));
            assertEquals("Audio", product.getCategoryName());
        }

        assertEquals(dataRetriever.getProductList(1,1000), pr8);
    }

    @Test
    void testGetProductsByCriteria() {
        List<Product> pr1 = dataRetriever.getProductsByCriteria(null, null, null, null, 1, 10);
        List<Product> pr2 = dataRetriever.getProductsByCriteria("Dell", null, null, null, 1, 5);
        List<Product> pr3 = dataRetriever.getProductsByCriteria(null, "informatique", null, null, 1, 10);

        assertTrue(pr1.size() <= 10);
        assertFalse(pr1.isEmpty());

        assertTrue(pr2.size() <= 5);
        for (Product pr : pr2){
            assertEquals("Laptop Dell XPS", pr.getName());
        }

        assertTrue(pr3.size() <= 10);
        for (Product pr : pr3){
            assertEquals("Informatique", pr.getCategoryName());
        }
    }

}