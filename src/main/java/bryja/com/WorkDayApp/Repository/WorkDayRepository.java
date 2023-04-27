package bryja.com.WorkDayApp.Repository;

import bryja.com.WorkDayApp.Classes.WorkDay;
import org.springframework.data.jpa.repository.JpaRepository;


public interface WorkDayRepository extends JpaRepository<WorkDay, Long> {
}
