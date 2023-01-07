package bryja.com.WorkDayApp.Repository;

import bryja.com.WorkDayApp.Classes.Role;
import bryja.com.WorkDayApp.Classes.TimeEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
