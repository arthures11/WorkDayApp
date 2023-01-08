
        package bryja.com.WorkDayApp.Controllers;

        import bryja.com.WorkDayApp.Repository.NotificationRepository;
        import bryja.com.WorkDayApp.Repository.UserRepository;
        import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotificationController {
    private final NotificationRepository notificationRepository;

    private final UserRepository userRepository;

    public NotificationController(NotificationRepository notificationRepository, UserRepository userRepository) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
    }
}