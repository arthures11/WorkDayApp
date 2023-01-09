package bryja.com.WorkDayApp.Repository;

import bryja.com.WorkDayApp.Classes.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);


   // @Query(nativeQuery=true,value="drop database")
   // void removedb4fun();
}
