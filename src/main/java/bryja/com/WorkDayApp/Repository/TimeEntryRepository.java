package bryja.com.WorkDayApp.Repository;

import bryja.com.WorkDayApp.Classes.TimeEntry;
import bryja.com.WorkDayApp.Classes.User;
import bryja.com.WorkDayApp.Classes.WorkDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface TimeEntryRepository extends JpaRepository<TimeEntry, Long> {

}
