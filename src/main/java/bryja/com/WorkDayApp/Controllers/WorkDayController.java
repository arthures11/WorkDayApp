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

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class WorkDayController {
    private final WorkDayRepository repository;
    private final TimeEntryRepository entries_repository;
    @Autowired
    WorkDayController(WorkDayRepository repository, TimeEntryRepository entries_repository) {
        this.repository = repository;
        this.entries_repository = entries_repository;
    }


    @GetMapping("/workdays")
    List<WorkDay> showAllWorkdays() {
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
    void addEntryToWorkday(@RequestBody TimeEntry entry, @PathVariable Long id) {
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
    void updateTimeEntry(@RequestBody TimeEntry entry, @PathVariable Long id,@PathVariable Long id2) {

        repository.findById(id).orElseThrow(() -> new WorkDayNotFoundException(id));
        entries_repository.findById(id2).map(timeEntry -> {
            timeEntry.time_spent=entry.time_spent;
            timeEntry.description=entry.description;
            return entries_repository.save(timeEntry);}
                )
                .orElseThrow(() -> new WorkDayNotFoundException(id2));
    }


    @GetMapping("/workdays/{id}")
    EntityModel<WorkDay> showSpecificWorkday(@PathVariable Long id) {

        WorkDay WorkDay = repository.findById(id) //
                .orElseThrow(() -> new WorkDayNotFoundException(id));

        return EntityModel.of(WorkDay, //
                linkTo(methodOn(WorkDayController.class).showSpecificWorkday(id)).withSelfRel(),
                linkTo(methodOn(WorkDayController.class).showAllWorkdays()).withRel("workdays"));
    }
    @GetMapping("/workdays/{id}/entries")
    List<TimeEntry> showSpecificEntries(@PathVariable Long id) {
        WorkDay WorkDay = repository.findById(id) //
                .orElseThrow(() -> new WorkDayNotFoundException(id));
        return WorkDay.getTimeEntry();

        }



    @PutMapping("/workdays/{id}")
    WorkDay updateWorkDay(@RequestBody WorkDay newWorkDay, @PathVariable Long id) {
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
