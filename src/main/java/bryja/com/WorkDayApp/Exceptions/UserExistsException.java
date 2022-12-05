package bryja.com.WorkDayApp.Exceptions;

public class UserExistsException extends RuntimeException {
    public UserExistsException(Long id) {
        super("taki user juz istnieje:  " + id);
    }
    public UserExistsException(String text) {
        super(text+" taki user juz istnieje");
    }
}