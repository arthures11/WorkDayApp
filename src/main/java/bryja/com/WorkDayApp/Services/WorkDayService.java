package bryja.com.WorkDayApp.Services;
import java.sql.Time;
import java.util.*;

import bryja.com.WorkDayApp.Classes.Project;
import bryja.com.WorkDayApp.Classes.TimeEntry;
import bryja.com.WorkDayApp.Classes.WorkDay;
import bryja.com.WorkDayApp.Controllers.WorkDayController;
import bryja.com.WorkDayApp.Exceptions.EntryNotFoundException;
import bryja.com.WorkDayApp.Exceptions.WorkDayNotFoundException;
import bryja.com.WorkDayApp.Repository.ProjectRepository;
import bryja.com.WorkDayApp.Repository.TimeEntryRepository;
import bryja.com.WorkDayApp.Repository.WorkDayRepository;
import bryja.com.WorkDayApp.Utility.GregorianDateMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.BeanUtils;

import javax.transaction.Transactional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@Service
public class WorkDayService {

    private final WorkDayRepository repository;
    private final TimeEntryRepository entries_repository;
    private final ProjectRepository projectRepository;

    @Autowired
    WorkDayService(WorkDayRepository repository, TimeEntryRepository entries_repository, ProjectRepository prj) {
        this.repository = repository;
        this.entries_repository = entries_repository;
        this.projectRepository = prj;
    }

    public List<WorkDay> showAllWorkdays() {
        return repository.findAll();
    }

    public WorkDay newWorkDay(@RequestBody WorkDay newWorkDay) {
        GregorianDateMatcher datematch = new GregorianDateMatcher();
        if(datematch.matches(newWorkDay.date)){
            return repository.save(newWorkDay);
        }
        else {
            throw new WorkDayNotFoundException(newWorkDay.date);
        }
    }
    public void addEntryToWorkday(@RequestBody TimeEntry entry, @PathVariable Long id) {

        WorkDay WorkDay = repository.findById(id)
                .orElseThrow(() -> new WorkDayNotFoundException(id));
       // entries_repository.save(new TimeEntry(entry.description, entry.time_spent));
        //  WorkDay.date = "abc";
          //repository.save(WorkDay);

        TimeEntry TimeEntry = repository.findById(id).map(workday -> {
            //entry.setWorkday(workday);
            TimeEntry abc = new TimeEntry(entry.description, entry.time_spent);
            WorkDay.getTimeEntry().add(abc);
            return entries_repository.save(abc);
        }).orElseThrow(() -> new WorkDayNotFoundException(id));
        // return new ResponseEntity<>(comment, HttpStatus.CREATED);

    }

    public void updateTimeEntry(@RequestBody TimeEntry entry, @PathVariable Long id,@PathVariable Long id2) {

        repository.findById(id).orElseThrow(() -> new WorkDayNotFoundException(id));
        entries_repository.findById(id2).map(timeEntry -> {
                    timeEntry.time_spent=entry.time_spent;
                    timeEntry.description=entry.description;
                    return entries_repository.save(timeEntry);}
                )
                .orElseThrow(() -> new WorkDayNotFoundException(id2));
    }
    public EntityModel<WorkDay> showSpecificWorkday(@PathVariable Long id) {

        WorkDay WorkDay = repository.findById(id) //
                .orElseThrow(() -> new WorkDayNotFoundException(id));

        return EntityModel.of(WorkDay, //
                linkTo(methodOn(WorkDayController.class).showSpecificWorkday(id)).withSelfRel(),
                linkTo(methodOn(WorkDayController.class).showAllWorkdays()).withRel("workdays"));
    }

    public List<TimeEntry> showSpecificEntries(@PathVariable Long id) {
        WorkDay WorkDay = repository.findById(id) //
                .orElseThrow(() -> new WorkDayNotFoundException(id));
        return WorkDay.getTimeEntry();

    }
    public WorkDay updateWorkDay(@RequestBody WorkDay newWorkDay, @PathVariable Long id) {
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
    public void deleteWorkDay(@PathVariable("id") Long id) {
        WorkDay WorkDay = repository.findById(id) //
                .orElseThrow(() -> new WorkDayNotFoundException(id));
        if(!WorkDay.getTimeEntry().isEmpty()){
            //  WorkDay.getTimeEntry().clear();
        }
       // repository.deleteById(id);
        //projectRepository.delete(prj);
        repository.delete(WorkDay);
    }

    public void deleteTimeEntry(@PathVariable("id")Long id) {
        TimeEntry TimeEntry = entries_repository.findById(id) //
                .orElseThrow(() -> new WorkDayNotFoundException(id));
        // repository.deleteById(id);
        //projectRepository.delete(prj);
        entries_repository.delete(TimeEntry);
    }
}
