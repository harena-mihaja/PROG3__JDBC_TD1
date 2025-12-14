import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;

public class DateParser {
    public DateParser() {
    }

    public Instant parseToInstant(String date){
        LocalDate localDate = LocalDate.parse(date);
        // Return an Instant which is zoned to UTC
        return localDate.atStartOfDay(ZoneOffset.UTC).toInstant();
    }
}
