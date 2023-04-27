package bryja.com.WorkDayApp.Controllers;

import bryja.com.WorkDayApp.Classes.Notification;
import bryja.com.WorkDayApp.Classes.Project;
import bryja.com.WorkDayApp.Classes.TimeEntry;
import bryja.com.WorkDayApp.Classes.User;
import bryja.com.WorkDayApp.Repository.ProjectRepository;
import bryja.com.WorkDayApp.Repository.TimeEntryRepository;
import bryja.com.WorkDayApp.Repository.UserRepository;
import bryja.com.WorkDayApp.Repository.WorkDayRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.Date;
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
        String generatedString = RandomStringUtils.randomAlphanumeric(50);
        project.setHash(generatedString);
        usr.projekty.add(new Project(project.nazwa, project.hash, usr));
        Date date = new Date();
        usr.notyfikacje.add(new Notification("Dodanie nowego projektu o nazwie '"+project.nazwa+"' zakończone pomyślnie.",date,usr));
        userRepository.save(usr);
       // projectRepositoryrepository.save(usr.projekty.get(usr.projekty.size()-1));
    }
}
