package bryja.com.WorkDayApp.Controllers;
import bryja.com.WorkDayApp.Repository.WorkDayRepository;

import bryja.com.WorkDayApp.Repository.TimeEntryRepository;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
public class TimeEntryController {
    private final WorkDayRepository repository;
    private final TimeEntryRepository entries_repository;

    TimeEntryController(WorkDayRepository repository, TimeEntryRepository entries_repository) {
        this.repository = repository;
        this.entries_repository = entries_repository;
    }


}
