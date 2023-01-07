package bryja.com.WorkDayApp.Repository;

import bryja.com.WorkDayApp.Classes.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
