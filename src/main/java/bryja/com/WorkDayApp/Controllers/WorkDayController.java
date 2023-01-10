package bryja.com.WorkDayApp.Controllers;

import java.sql.Time;
import java.util.*;

import bryja.com.WorkDayApp.Classes.*;
import bryja.com.WorkDayApp.Exceptions.EntryNotFoundException;
import bryja.com.WorkDayApp.Exceptions.UserExistsException;
import bryja.com.WorkDayApp.Exceptions.WorkDayNotFoundException;
import bryja.com.WorkDayApp.Repository.*;
import bryja.com.WorkDayApp.Utility.GregorianDateMatcher;
import org.apache.commons.lang3.RandomStringUtils;
import org.hibernate.jdbc.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.BeanUtils;
import bryja.com.WorkDayApp.Services.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@CrossOrigin(maxAge = 3600)
public class WorkDayController {

    @Autowired
    private WorkDayService workDayService;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final WorkDayRepository workDayRepository;
    private final TimeEntryRepository entries;
    private final NotificationRepository notificationRepository;
    WorkDayController(WorkDayRepository repository, UserRepository usr_re, ProjectRepository project_rep, TimeEntryRepository entry, NotificationRepository rep) {
        this.userRepository=usr_re;
        this.projectRepository = project_rep;
        this.workDayRepository = repository;
        this.entries = entry;
        this.notificationRepository=rep;
    }

    @GetMapping(value="/workdays", consumes = {"*/*"})
    public List<WorkDay> showAllWorkdays() {
        return workDayService.showAllWorkdays();
    }
    @PostMapping(value="/workdays", consumes = {"application/json"})
    WorkDay newWorkDay(@RequestBody WorkDay newWorkDay) {
        return workDayService.newWorkDay(newWorkDay);
    }

    @PostMapping(value="/workdays/{id}/entries", consumes = {"*/*"})
    public void addEntryToWorkday(@RequestBody TimeEntry entry, @PathVariable Long id) {
        workDayService.addEntryToWorkday(entry,id);
    }

    @PutMapping(value="/workdays/{id}/entries/{id2}", consumes = {"*/*"})
    void updateTimeEntry(@RequestBody TimeEntry entry, @PathVariable Long id,@PathVariable Long id2) {
        workDayService.updateTimeEntry(entry,id,id2);
    }


    @GetMapping(value="/workdays/{id}", consumes = {"*/*"})
    public EntityModel<WorkDay> showSpecificWorkday(@PathVariable Long id) {
        return workDayService.showSpecificWorkday(id);
    }
    @GetMapping(value="/workdays/{id}/entries", consumes = {"*/*"})
    List<TimeEntry> showSpecificEntries(@PathVariable Long id) {
        return workDayService.showSpecificEntries(id);
        }



    @PutMapping(value="/workdays/{id}", consumes = {"*/*"})
    WorkDay updateWorkDay(@RequestBody WorkDay newWorkDay, @PathVariable Long id) {
        return workDayService.updateWorkDay(newWorkDay,id);

    }

    @DeleteMapping(value="/workdays/{id}", consumes = {"*/*"})
    void deleteWorkDay(@AuthenticationPrincipal OAuth2User principal,@PathVariable("id") Long id) {
        User usr = userRepository.findByEmail(principal.getAttribute("email"));
         workDayService.deleteWorkDay(id);
        Date date = new Date();
        usr.notyfikacje.add(new Notification("Usunięto dzień pracy.",date,usr));
        userRepository.save(usr);
    }
    @DeleteMapping(value="/notif/{id}", consumes = {"*/*"})
    void deletenotif(@AuthenticationPrincipal OAuth2User principal,@PathVariable("id") Long id) {
        User usr = userRepository.findByEmail(principal.getAttribute("email"));
        notificationRepository.deleteById(id);
    }

    @DeleteMapping(value="/timeentry/{id}", consumes = {"*/*"})
    void deleteTimeEntry(@AuthenticationPrincipal OAuth2User principal,@PathVariable("id") Long id) {
        User usr = userRepository.findByEmail(principal.getAttribute("email"));
        workDayService.deleteTimeEntry(id);
        Date date = new Date();
        usr.notyfikacje.add(new Notification("Usunięto wpis.",date,usr));
        userRepository.save(usr);
    }


    @PostMapping(value ="/user/addworkday/{hash}", consumes = {"*/*"})
    public void addWorkDaytoUser(@AuthenticationPrincipal OAuth2User principal, @RequestBody WorkDay workday,@PathVariable String hash) {
        User usr = userRepository.findByEmail(principal.getAttribute("email"));
        if(usr==null){
            throw new UserExistsException("");
        }
        GregorianDateMatcher datematch = new GregorianDateMatcher();
        if(!datematch.matches(workday.date)){
            throw new WorkDayNotFoundException(workday.date);
        }
        Project project = projectRepository.findByHash(hash);
        //workDayRepository.save(workday);
        project.getWorkDay().add(new WorkDay(workday.date,project));
        //projectRepository.save(project);

        projectRepository.save(project);
        Date date = new Date();
        usr.notyfikacje.add(new Notification("Dodanie dnia zakończone pomyślnie.",date,usr));
        userRepository.save(usr);
        // projectRepositoryrepository.save(usr.projekty.get(usr.projekty.size()-1));
    }

    @PostMapping(value ="/user/addtimeentry/{hash}/{id}", consumes = {"*/*"})
    public void addTimeEntrytoUser(@AuthenticationPrincipal OAuth2User principal, @RequestBody TimeEntry entry,@PathVariable String hash,@PathVariable int id) {
        User usr = userRepository.findByEmail(principal.getAttribute("email"));
        if(usr==null){
            throw new UserExistsException("");
        }
        WorkDay day = workDayRepository.findById((long)id)
                .orElseThrow(() -> new WorkDayNotFoundException(""));

        day.getTimeEntry().add(new TimeEntry(entry.description,entry.time_spent,day));
        workDayRepository.save(day);
        Date date = new Date();
        usr.notyfikacje.add(new Notification("Dodanie dnia z czasem: '"+entry.time_spent+"' zostało zakończone pomyślnie.",date,usr));
        userRepository.save(usr);
    }

    @PutMapping(value ="/user/edittimeentry/{id}", consumes = {"*/*"})
    public void editTimeEntryinUser(@AuthenticationPrincipal OAuth2User principal, @RequestBody TimeEntry entry,@PathVariable int id) {
        User usr = userRepository.findByEmail(principal.getAttribute("email"));
        if(usr==null){
            throw new UserExistsException("");
        }

        TimeEntry en = entries.findById((long)id)
                .orElseThrow(() -> new WorkDayNotFoundException(""));

        en.time_spent = entry.time_spent;
        en.description = entry.description;

        Date date = new Date();
        usr.notyfikacje.add(new Notification("Edytowanie wpisu czasu na: '"+entry.time_spent+"' zostało zakończone pomyślnie.",date,usr));
        userRepository.save(usr);
    }

}
