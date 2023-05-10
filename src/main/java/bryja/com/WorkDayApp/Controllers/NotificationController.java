
        package bryja.com.WorkDayApp.Controllers;

        import bryja.com.WorkDayApp.Classes.Notification;
        import bryja.com.WorkDayApp.Classes.Project;
        import bryja.com.WorkDayApp.Classes.User;
        import bryja.com.WorkDayApp.Repository.NotificationRepository;
        import bryja.com.WorkDayApp.Repository.UserRepository;
        import org.springframework.security.core.annotation.AuthenticationPrincipal;
        import org.springframework.security.core.userdetails.UsernameNotFoundException;
        import org.springframework.security.oauth2.core.user.OAuth2User;
        import org.springframework.web.bind.annotation.PostMapping;
        import org.springframework.web.bind.annotation.RequestBody;
        import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotificationController {
    private final NotificationRepository notificationRepository;

    private final UserRepository userRepository;

    public NotificationController(NotificationRepository notificationRepository, UserRepository userRepository) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
    }

    @PostMapping(value ="/user/addnotification", consumes = {"*/*"})
    public void UserProjects(@AuthenticationPrincipal OAuth2User principal, @RequestBody Notification notif) {
        User usr = userRepository.findOptionalByEmail(principal.getAttribute("email")).orElseThrow(()-> new UsernameNotFoundException("User not found !"));
        usr.notyfikacje.add(new Notification(notif.opis,notif.date,usr));
        userRepository.save(usr);
        // projectRepositoryrepository.save(usr.projekty.get(usr.projekty.size()-1));
    }
}