package bryja.com.WorkDayApp.Exceptions;

public class WorkDayNotFoundException extends RuntimeException {
    public WorkDayNotFoundException(Long id) {
        super("Nie istnieje dzien z id:  " + id);
    }
    public WorkDayNotFoundException(String text) {
        super(text+" jest nieprawidlowa data - wprowadz w nastepujacym patternie: YYYY-MM-DD");
    }
}