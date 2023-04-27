
        package bryja.com.WorkDayApp.Repository;

        import bryja.com.WorkDayApp.Classes.Notification;
        import org.springframework.data.jpa.repository.JpaRepository;
        import org.springframework.data.repository.NoRepositoryBean;
        import org.springframework.stereotype.Repository;


public interface NotificationRepository extends JpaRepository<Notification, Long> {
}