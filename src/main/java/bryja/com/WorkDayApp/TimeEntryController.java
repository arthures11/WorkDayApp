package bryja.com.WorkDayApp;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class TimeEntryController {
    private final WorkDayRepository repository;
    private final TimeEntryRepository entries_repository;

    TimeEntryController(WorkDayRepository repository, TimeEntryRepository entries_repository) {
        this.repository = repository;
        this.entries_repository = entries_repository;
    }


}
