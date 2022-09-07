package bryja.com.WorkDayApp;

import java.util.*;

import org.hibernate.jdbc.Work;
import org.springframework.web.bind.annotation.*;

@RestController
class WorkDayController {

    private final WorkDayRepository repository;

    WorkDayController(WorkDayRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/workdays")
    List<WorkDay> all() {
        return repository.findAll();
    }
    @PostMapping("/workdays")
    WorkDay newWorkDay(@RequestBody WorkDay newWorkDay) {
        return repository.save(newWorkDay);
    }
    @GetMapping("/workdays/{id}")
    WorkDay one(@PathVariable Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new WorkDayNotFoundException(id));
    }

    @PutMapping("/workdays/{id}")
    WorkDay replaceEmployee(@RequestBody WorkDay newWorkDay, @PathVariable Long id) {

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

    @DeleteMapping("/workdays/{id}")
    void deleteEmployee(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
