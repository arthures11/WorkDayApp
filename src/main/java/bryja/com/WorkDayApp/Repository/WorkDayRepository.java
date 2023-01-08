package bryja.com.WorkDayApp.Repository;

import bryja.com.WorkDayApp.Classes.WorkDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface WorkDayRepository extends JpaRepository<WorkDay, Long> {
}
