package bryja.com.WorkDayApp.Advices;
import bryja.com.WorkDayApp.Exceptions.WorkDayNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class EntryNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(WorkDayNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String WorkDayNotFoundHandler(WorkDayNotFoundException ex) {
        return ex.getMessage();
    }
}
