package bryja.com.WorkDayApp.Repository;

import bryja.com.WorkDayApp.Classes.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
