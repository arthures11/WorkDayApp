package bryja.com.WorkDayApp.Repository;

import bryja.com.WorkDayApp.Classes.Privilege;
import bryja.com.WorkDayApp.Classes.TimeEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {
    Privilege findByName(String name);
}
