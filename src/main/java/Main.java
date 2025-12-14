import java.time.Instant;

public class Main {
    public static void main(String[] args) {
        DataRetriever dataRetriever = new DataRetriever();
        DateParser dateParser = new DateParser();

        System.out.println(dataRetriever.getAllCategories());
        System.out.println(dataRetriever.getProductList(1, 10));
        System.out.println(dataRetriever.getProductList(1, 5));
        System.out.println(dataRetriever.getProductList(1, 3));
        System.out.println(dataRetriever.getProductList(2, 2));

        System.out.println(dataRetriever.getProductsByCriteria("Dell", null, null, null));
        System.out.println(dataRetriever.getProductsByCriteria(null, "info", null, null));
        System.out.println(dataRetriever.getProductsByCriteria("iPhone", "mobile", null, null));
        System.out.println(dataRetriever.getProductsByCriteria(null, null, dateParser.parseToInstant("2024-02-01"), dateParser.parseToInstant("2024-03-01")));
        System.out.println(dataRetriever.getProductsByCriteria("Samsung", "bureau", null, null));
        System.out.println(dataRetriever.getProductsByCriteria("Sony", "Informatique", null, null));
        System.out.println(dataRetriever.getProductsByCriteria(null, "audio", dateParser.parseToInstant("2024-01-01"), dateParser.parseToInstant("2024-12-01")));
        System.out.println(dataRetriever.getProductsByCriteria(null, null, null,  null));

        System.out.println(dataRetriever.getProductsByCriteria(null, null, null, null, 1, 10));
        System.out.println(dataRetriever.getProductsByCriteria("Dell", null, null, null, 1, 5));
        System.out.println(dataRetriever.getProductsByCriteria(null, "informatique", null, null, 1, 10));
    }
}
