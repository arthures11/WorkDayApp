package bryja.com.WorkDayApp;

public class WorkDayNotFoundException extends RuntimeException {
    public WorkDayNotFoundException(Long id) {
        super("Nie istnieje dzien z id:  " + id);
    }
}