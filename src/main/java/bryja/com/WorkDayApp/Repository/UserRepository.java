package bryja.com.WorkDayApp.Repository;

import bryja.com.WorkDayApp.Classes.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    Optional<User> findOptionalByEmail(String email);
    Boolean existsByEmail(String email);

   // @Query(nativeQuery=true,value="drop database")
   // void removedb4fun();
}
