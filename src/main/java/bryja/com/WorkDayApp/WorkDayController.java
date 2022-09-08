package bryja.com.WorkDayApp;

import java.sql.Time;
import java.util.*;
import java.util.stream.Collectors;

import org.hibernate.jdbc.Work;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
class WorkDayController {

    private final WorkDayRepository repository;
    private final TimeEntryRepository entries_repository;

    WorkDayController(WorkDayRepository repository, TimeEntryRepository entries_repository) {
        this.repository = repository;
        this.entries_repository = entries_repository;
    }


    @GetMapping("/workdays")
    CollectionModel<EntityModel<WorkDay>> all() {

        List<EntityModel<WorkDay>> workdays = repository.findAll().stream()
                .map(workday -> EntityModel.of(workday,
                        linkTo(methodOn(WorkDayController.class).one(workday.getId())).withSelfRel(),
                        linkTo(methodOn(WorkDayController.class).all()).withRel("workdays")))
                .collect(Collectors.toList());

        return CollectionModel.of(workdays, linkTo(methodOn(WorkDayController.class).all()).withSelfRel());
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
            WorkDay.TimeEntry.add(new TimeEntry(entry.description, entry.time_spent));
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
                    if(id2>timeentry.TimeEntry.size()){  //nie dziala idk why
                        throw new EntryNotFoundException(id);
                    }
                    timeentry.TimeEntry.get((int) (id2 - 1)).time_spent = entry.time_spent;
                    timeentry.TimeEntry.get((int) (id2 - 1)).description = entry.description;
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
        List<TimeEntry> entries = WorkDay.TimeEntry;
        return entries;

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
    void deleteWorkDay(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
