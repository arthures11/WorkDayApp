package bryja.com.WorkDayApp.Exceptions;

import org.springframework.security.core.context.SecurityContextHolder;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EmailNullException extends RuntimeException {

    public EmailNullException(String text, HttpServletRequest req, HttpServletResponse resp) {
        super("zmien ustawienia prywatności github aby udostępnić email w celu zarejestrowania konta");
        try {
            resp.sendRedirect(req.getContextPath() + "/githubprivacyerror.html");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}