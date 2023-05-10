package bryja.com.WorkDayApp.Controllers;

import bryja.com.WorkDayApp.Classes.User;
import bryja.com.WorkDayApp.Repository.RoleRepository;
import bryja.com.WorkDayApp.Repository.UserRepository;
import bryja.com.WorkDayApp.Services.jwtUtils;
import bryja.com.WorkDayApp.Services.userService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class LoginController {
    @Autowired
    private AuthenticationManager authenticationManager;
    private final UserRepository iUserRepository;
    private final RoleRepository iRoleRepository;
    private final jwtUtils jwtUtilities;
    private final userService iUserService ;

    @PostMapping("/login")
    public ModelAndView login(@RequestParam String email, @RequestParam String password , HttpServletRequest request, HttpServletResponse response) {
        iUserService.login(email,password, request, response);
        return new ModelAndView("index");
    }

    public static String generateToken(String mail, List roles) {
        System.out.println(mail + roles);
        jwtUtils ji = new jwtUtils();
        String token = ji.generateToken(mail, roles);
        System.out.println(token.toString());
        return token;

    }


}