package bryja.com.WorkDayApp.Repository;

import bryja.com.WorkDayApp.Classes.Project;
import bryja.com.WorkDayApp.Classes.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    Project findByHash(String hash);
}
