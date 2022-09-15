package bryja.com.WorkDayApp.Exceptions;

public class EntryNotFoundException extends RuntimeException {
    public EntryNotFoundException(Long id) {
        super("Nie istnieje epis z id:  " + id);
    }
}
