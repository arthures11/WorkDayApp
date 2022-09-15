package bryja.com.WorkDayApp.Repository;

import bryja.com.WorkDayApp.Classes.TimeEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeEntryRepository extends JpaRepository<TimeEntry, Long> {

}
