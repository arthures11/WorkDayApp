package bryja.com.WorkDayApp.Controllers;

import bryja.com.WorkDayApp.Classes.Project;
import bryja.com.WorkDayApp.Classes.TimeEntry;
import bryja.com.WorkDayApp.Classes.User;
import bryja.com.WorkDayApp.Repository.ProjectRepository;
import bryja.com.WorkDayApp.Repository.TimeEntryRepository;
import bryja.com.WorkDayApp.Repository.UserRepository;
import bryja.com.WorkDayApp.Repository.WorkDayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
public class ProjectController {
    private final WorkDayRepository repository;

    private final ProjectRepository projectRepositoryrepository;

    private final UserRepository userRepository;
    private final TimeEntryRepository entries_repository;

    @Autowired
    ProjectController(WorkDayRepository repository, TimeEntryRepository entries_repository, UserRepository usr_re, ProjectRepository project_rep) {
        this.repository = repository;
        this.entries_repository = entries_repository;
        this.userRepository=usr_re;
        this.projectRepositoryrepository=project_rep;
    }

    @PostMapping(value ="/user/addproject", consumes = {"*/*"})
    public void UserProjects(@AuthenticationPrincipal OAuth2User principal, @RequestBody Project project) {
        User usr = userRepository.findByEmail(principal.getAttribute("email"));
        usr.projekty.add(project);
        userRepository.save(usr);
       // projectRepositoryrepository.save(usr.projekty.get(usr.projekty.size()-1));
    }
}
