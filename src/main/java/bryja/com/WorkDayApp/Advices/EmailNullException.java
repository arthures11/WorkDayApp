package bryja.com.WorkDayApp.Advices;
import bryja.com.WorkDayApp.Exceptions.EmailNullException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class EmailNullAdvice {

    @ResponseBody
    @ExceptionHandler(EmailNullException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String EmailNullHandler(EmailNullException ex) {
        return ex.getMessage();
    }
}
