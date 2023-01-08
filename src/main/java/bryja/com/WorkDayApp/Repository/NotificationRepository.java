package bryja.com.WorkDayApp.Repository;

import bryja.com.WorkDayApp.Classes.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
