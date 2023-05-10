package bryja.com.WorkDayApp.Services;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface IuserService {

    void login(String email, String pass, HttpServletRequest request, HttpServletResponse response);
}
