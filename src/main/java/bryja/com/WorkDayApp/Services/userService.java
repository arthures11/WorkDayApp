package bryja.com.WorkDayApp.Services;

import bryja.com.WorkDayApp.Classes.User;
import bryja.com.WorkDayApp.Repository.RoleRepository;
import bryja.com.WorkDayApp.Repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
@Service
@Transactional
@RequiredArgsConstructor
public class userService implements IuserService {

    private final AuthenticationManager authenticationManager ;
    private final UserRepository iUserRepository ;
    private final RoleRepository iRoleRepository ;
    private final PasswordEncoder passwordEncoder ;
    private final jwtUtils jwtUtilities ;

    private SecurityContextRepository securityContextRepository =
            new HttpSessionSecurityContextRepository();
    private final SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();


    @Override
    public void login(String email, String pass, HttpServletRequest request, HttpServletResponse response) {
      //  try {
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(email, pass);
            Authentication authentication = authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            SecurityContext context = securityContextHolderStrategy.createEmptyContext();

            context.setAuthentication(authentication);
            //  User user = iUserRepository.findOptionalByEmail(authentication.getName()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
            //   List<String> rolesNames = new ArrayList<>();
            //   user.getRoles().forEach(r -> rolesNames.add(r.getName()));
            //  String tkn = generateToken(email, rolesNames);
            securityContextHolderStrategy.setContext(context);
            securityContextRepository.saveContext(context, request, response);
            //     return tkn;
            // return new RedirectView("/?success");
            // } catch (AuthenticationException e) {
            //     e.printStackTrace();
            //  return ResponseEntity.status(HttpStatus.FOUND)
            //         .header(HttpHeaders.LOCATION, "/?error")
            //        .build();
            // return "error";
            // }


    }

    public static String generateToken(String mail, List roles) {
        System.out.println(mail + roles);
        jwtUtils ji = new jwtUtils(); // utworzenie instancji klasy JwtUtils
        String token = ji.generateToken(mail, roles);
        System.out.println(token.toString());
        return token;

    }
}
