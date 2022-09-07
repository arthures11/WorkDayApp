package bryja.com.WorkDayApp;

public class EntryNotFoundException extends RuntimeException {
    public EntryNotFoundException(Long id) {
        super("Nie istnieje epis z id:  " + id);
    }
}
