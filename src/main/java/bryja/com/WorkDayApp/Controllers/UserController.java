package bryja.com.WorkDayApp.Controllers;

import java.io.IOException;
import java.util.*;

import bryja.com.WorkDayApp.Classes.Project;
import bryja.com.WorkDayApp.Classes.WorkDay;
import bryja.com.WorkDayApp.Exceptions.UserExistsException;
import bryja.com.WorkDayApp.Repository.ProjectRepository;
import bryja.com.WorkDayApp.Repository.RoleRepository;
import bryja.com.WorkDayApp.Classes.User;
import bryja.com.WorkDayApp.Repository.UserRepository;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@CrossOrigin(maxAge = 3600)
public class UserController {
    private final UserRepository repository;
    private final RoleRepository rolerep;
    private final ProjectRepository projectRepository;

    public UserController(UserRepository repository, RoleRepository rolerep, ProjectRepository pjk) {
        this.repository = repository;
        this.rolerep=rolerep;
        this.projectRepository = pjk;
    }
    @GetMapping(value ="/user/add", consumes = {"*/*"})
    public void registerUser(@AuthenticationPrincipal OAuth2User principal, HttpServletRequest req, HttpServletResponse resp) {
        String n = principal.getAttribute("name");
        String n2 = principal.getAttribute("email");
        if(n2==null){
           // SecurityContextHolder.getContext().setAuthentication(null);
              ///  throw new EmailNullException(n, req, resp);
        }
        User a = new User(n2,n);
        a.setRoles(Arrays.asList(rolerep.findByName("ROLE_USER")));
        if (emailExists(a.getEmail())) {
            try {
                resp.sendRedirect(req.getContextPath() + "/");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else{
            repository.save(a);
            //model.addAttribute("attribute", "forwardWithForwardPrefix");

            try {
                resp.sendRedirect(req.getContextPath() + "/");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }

    @GetMapping(value ="/user/projects", consumes = {"*/*"})
    public List<Project> UserProjects(@AuthenticationPrincipal OAuth2User principal, HttpServletRequest req, HttpServletResponse resp) {
        User usr = repository.findByEmail(principal.getAttribute("email"));


        return usr.projekty;
    }

    @GetMapping(value ="/user/workdays", consumes = {"*/*"})
    public List<WorkDay> UserWorkDays(@AuthenticationPrincipal OAuth2User principal, String hash) {
        User usr = repository.findByEmail(principal.getAttribute("email"));
        Project project = null;
        if(usr!=null){
            if(hashExists(hash)){
                 project =  projectRepository.findByHash(hash);
            }
            else{
                throw new UserExistsException("hash nie istnieje");
            }
        }
        return project.getWorkDay();
    }

    private boolean emailExists(String email) {
            return repository.findByEmail(email) != null;
        }


    private boolean hashExists(String hash) {
        return projectRepository.findByHash(hash) != null;
    }
}


