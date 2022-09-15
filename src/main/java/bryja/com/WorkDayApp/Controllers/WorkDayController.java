package bryja.com.WorkDayApp.Controllers;

import java.util.*;

import bryja.com.WorkDayApp.Classes.TimeEntry;
import bryja.com.WorkDayApp.Classes.WorkDay;
import bryja.com.WorkDayApp.Exceptions.EntryNotFoundException;
import bryja.com.WorkDayApp.Exceptions.WorkDayNotFoundException;
import bryja.com.WorkDayApp.Repository.TimeEntryRepository;
import bryja.com.WorkDayApp.Repository.WorkDayRepository;
import bryja.com.WorkDayApp.Utility.GregorianDateMatcher;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class WorkDayController {

    private final WorkDayRepository repository;
    private final TimeEntryRepository entries_repository;

    WorkDayController(WorkDayRepository repository, TimeEntryRepository entries_repository) {
        this.repository = repository;
        this.entries_repository = entries_repository;
    }


    @GetMapping("/workdays")
    List<WorkDay> all() {
        return repository.findAll();
    }
    @PostMapping("/workdays")
    WorkDay newWorkDay(@RequestBody WorkDay newWorkDay) {
        GregorianDateMatcher datematch = new GregorianDateMatcher();
        if(datematch.matches(newWorkDay.date)){
            return repository.save(newWorkDay);
        }
        else {
            throw new WorkDayNotFoundException(newWorkDay.date);
        }
    }

    @PostMapping("/workdays/{id}/entries")
    void e(@RequestBody TimeEntry entry, @PathVariable Long id) {
            WorkDay WorkDay = repository.findById(id)
                    .orElseThrow(() -> new WorkDayNotFoundException(id));
           // WorkDay.getTimeEntry().add(new TimeEntry(entry.description, entry.time_spent));
          //  WorkDay.date = "abc";
          //  repository.save(WorkDay);

        TimeEntry TimeEntry = repository.findById(id).map(workday -> {
            entry.setWorkday(workday);
            return entries_repository.save(entry);
        }).orElseThrow(() -> new WorkDayNotFoundException(id));
       // return new ResponseEntity<>(comment, HttpStatus.CREATED);

    }

    @PutMapping("/workdays/{id}/entries/{id2}")
    void replaceTimeEntry(@RequestBody TimeEntry entry, @PathVariable Long id,@PathVariable Long id2) {
        repository.findById(id)
                .map(timeentry -> {
                    if(timeentry.getTimeEntry().stream().noneMatch(timeEntry -> Objects.equals(timeEntry.getId(),id2))){
                        throw new EntryNotFoundException(id2);
                    }
                    timeentry.getTimeEntry().get((int) (id2 - 1)).time_spent = entry.time_spent;
                    timeentry.getTimeEntry().get((int) (id2 - 1)).description = entry.description;
                    entry.setWorkday(timeentry);
                    return repository.save(timeentry);
                })
                .orElseThrow(() -> new WorkDayNotFoundException(id));
    }


    @GetMapping("/workdays/{id}")
    EntityModel<WorkDay> one(@PathVariable Long id) {

        WorkDay WorkDay = repository.findById(id) //
                .orElseThrow(() -> new WorkDayNotFoundException(id));

        return EntityModel.of(WorkDay, //
                linkTo(methodOn(WorkDayController.class).one(id)).withSelfRel(),
                linkTo(methodOn(WorkDayController.class).all()).withRel("workdays"));
    }
    @GetMapping("/workdays/{id}/entries")
    List<TimeEntry> b(@PathVariable Long id) {
        WorkDay WorkDay = repository.findById(id) //
                .orElseThrow(() -> new WorkDayNotFoundException(id));
        return WorkDay.getTimeEntry();

        }



    @PutMapping("/workdays/{id}")
    WorkDay replaceWorkDay(@RequestBody WorkDay newWorkDay, @PathVariable Long id) {
        GregorianDateMatcher datematch = new GregorianDateMatcher();
        if(datematch.matches(newWorkDay.date)){
            return repository.findById(id)
                    .map(employee -> {
                        employee.setDate(newWorkDay.getDate());
                        return repository.save(employee);
                    })
                    .orElseGet(() -> {
                        newWorkDay.setId(id);
                        return repository.save(newWorkDay);
                    });
        }
        else{
            throw new WorkDayNotFoundException(newWorkDay.date);
        }

    }

    @DeleteMapping("/workdays/{id}")
    void deleteWorkDay(@PathVariable("id") Long id) {
        WorkDay WorkDay = repository.findById(id) //
                .orElseThrow(() -> new WorkDayNotFoundException(id));
        if(!WorkDay.getTimeEntry().isEmpty()){
          //  WorkDay.getTimeEntry().clear();
        }
        repository.deleteById(id);
    }
}
