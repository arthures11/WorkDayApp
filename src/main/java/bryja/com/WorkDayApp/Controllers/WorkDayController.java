package bryja.com.WorkDayApp.Controllers;

import java.sql.Time;
import java.util.*;

import bryja.com.WorkDayApp.Classes.TimeEntry;
import bryja.com.WorkDayApp.Classes.WorkDay;
import bryja.com.WorkDayApp.Exceptions.EntryNotFoundException;
import bryja.com.WorkDayApp.Exceptions.WorkDayNotFoundException;
import bryja.com.WorkDayApp.Repository.TimeEntryRepository;
import bryja.com.WorkDayApp.Repository.WorkDayRepository;
import bryja.com.WorkDayApp.Utility.GregorianDateMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
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
    void deleteWorkDay(@PathVariable("id") Long id) {
         workDayService.deleteWorkDay(id);
    }
}
