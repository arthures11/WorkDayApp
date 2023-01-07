package bryja.com.WorkDayApp.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UrlController {

    @RequestMapping(value="/dashboard", method= RequestMethod.GET)
    public String getDashboard() {
        return "/dashboard";
    }
    @RequestMapping(value="/profile", method= RequestMethod.GET)
    public String getProfile() {
        return "/profile";
    }
    @RequestMapping(value="/projects", method= RequestMethod.GET)
    public String getProjects() {
        return "/projects";
    }
    @RequestMapping(value="/projekt", method= RequestMethod.GET)
    public String getProject() {
        return "/projekt";
    }
}
