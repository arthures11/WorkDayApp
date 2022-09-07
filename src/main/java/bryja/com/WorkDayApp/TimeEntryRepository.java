package bryja.com.WorkDayApp;

import org.springframework.data.jpa.repository.JpaRepository;

interface TimeEntryRepository extends JpaRepository<TimeEntry, Long> {

}
