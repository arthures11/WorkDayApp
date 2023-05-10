package bryja.com.WorkDayApp.Controllers;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.UnexpectedException;
import java.util.*;

import bryja.com.WorkDayApp.Classes.*;
import bryja.com.WorkDayApp.Exceptions.UserExistsException;
import bryja.com.WorkDayApp.Repository.ProjectRepository;
import bryja.com.WorkDayApp.Repository.RoleRepository;
import bryja.com.WorkDayApp.Repository.UserRepository;
import bryja.com.WorkDayApp.Services.CustomUserDetailsService;
import bryja.com.WorkDayApp.dto.BearerToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.jayway.jsonpath.spi.json.JacksonJsonProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import bryja.com.WorkDayApp.Services.jwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@CrossOrigin(maxAge = 3600)
public class UserController {
    private final UserRepository repository;
    private final RoleRepository rolerep;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired PasswordEncoder passwordEncoder;

    public UserController(UserRepository repository, RoleRepository rolerep, ProjectRepository pjk, UserRepository usr) {
        this.repository = repository;
        this.rolerep=rolerep;
        this.projectRepository = pjk;
        this.userRepository = usr;
    }
    @GetMapping(value ="/user/add", consumes = {"*/*"})
    public void registerUser(@AuthenticationPrincipal OAuth2User principal, HttpServletRequest req, HttpServletResponse resp) {
        String n = principal.getAttribute("name");
        String n2 = principal.getAttribute("email");
        if(n2==null){
           // SecurityContextHolder.getContext().setAuthentication(null);
              ///  throw new EmailNullException(n, req, resp);
        }
        User a = new User(n,n2,passwordEncoder.encode("123"));
        a.setRoles(Arrays.asList(rolerep.findByName("ROLE_USER")));
        if (emailExists(a.getEmail())) {
           try {
            Role role = rolerep.findByName("ROLE_USER");
               // String token = generateToken(n2,Collections.singletonList(role.getName()));
               // return new ResponseEntity<>(new BearerToken(token , "Bearer "), HttpStatus.OK);
                resp.sendRedirect(req.getContextPath()+"/");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else{
            repository.save(a);
            Role role = rolerep.findByName("ROLE_USER");
            //String token = generateToken(n2,Collections.singletonList(role.getName()));
            //model.addAttribute("attribute", "forwardWithForwardPrefix");
          //  return new ResponseEntity<>(new BearerToken(token , "Bearer "), HttpStatus.OK);
            try {
                resp.sendRedirect(req.getContextPath()+"/");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }
    public static String generateToken(String mail,List roles) {
        jwtUtils jwtUtils = new jwtUtils();
        String token = jwtUtils.generateToken(mail, (List<String>) roles);
        return token;
    }

    @GetMapping(value="/user", consumes = {"*/*"})
    public User user(Authentication authentication) {
       // Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User usr = userRepository.findByEmail(checkmail(authentication.getPrincipal()));

        return usr;
    }

 public String checkmail(Object authentication){
     if (authentication instanceof DefaultOidcUser) {       //klasa która powstaje przy social loginie
         DefaultOidcUser oauth2User = (DefaultOidcUser) authentication;
         return oauth2User.getAttribute("email");
     } else if (authentication instanceof UserDetails) {    //zwykla klasa posiadająca dane z bazy
         UserDetails userDetails = (UserDetails) authentication;
         return userDetails.getUsername();
     }
     else {
         return "notfound";
     }
 }
    @GetMapping(value ="/user/projects", consumes = {"*/*"})
    public List<Project> UserProjects(@AuthenticationPrincipal OAuth2User principal, HttpServletRequest req, HttpServletResponse resp) {
        User usr = repository.findOptionalByEmail(principal.getAttribute("email")).orElseThrow(()-> new UsernameNotFoundException("User not found !"));

        return usr.projekty;
    }

    @GetMapping(value ="/user/workdays", consumes = {"*/*"})
    public List<WorkDay> UserWorkDays(@AuthenticationPrincipal OAuth2User principal, String hash) {
        User usr = repository.findOptionalByEmail(principal.getAttribute("email")).orElseThrow(()-> new UsernameNotFoundException("User not found !"));
        Project project = null;
        if(usr!=null){
            if(hashExists(hash)){
                 project =  projectRepository.findByHash(hash);
            }
            else{
                throw new UserExistsException("hash nie istnieje");
            }
        }
        return project.getWorkDay();
    }

    @GetMapping(value ="/user/notifs", consumes = {"*/*"})
    public List<Notification> UserNotifs(@AuthenticationPrincipal OAuth2User principal) {
        User usr = repository.findOptionalByEmail(principal.getAttribute("email")).orElseThrow(()-> new UsernameNotFoundException("User not found !"));


        return usr.notyfikacje;
    }

    @GetMapping(value ="/user/raportamount", consumes = {"*/*"})
    public int Userraports(@AuthenticationPrincipal OAuth2User principal) {
        User usr = repository.findOptionalByEmail(principal.getAttribute("email")).orElseThrow(()-> new UsernameNotFoundException("User not found !"));
        return usr.raporty;
    }

    @RequestMapping(path = "/download", method = RequestMethod.GET)
    public ResponseEntity<InputStreamResource> download(@AuthenticationPrincipal OAuth2User user, HttpServletResponse response) throws IOException {
        User usr = repository.findOptionalByEmail(user.getAttribute("email")).orElseThrow(()-> new UsernameNotFoundException("User not found !"));
        Date d = new Date();
        HttpHeaders headers = new HttpHeaders(); headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+d+".txt");

        Files.createDirectories(Paths.get("raporty"));
        File plik = new File("raporty/raport"+usr+".txt");

        BufferedWriter writer = new BufferedWriter(new FileWriter(plik, true));
        writer.write(usr.email+"\n");
        writer.write(usr.name+"\n");
        JsonParser parser = new JsonParser();
        String n = new JacksonJsonProvider().toJson(usr);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonElement el = parser.parse(n);
        n = gson.toJson(el);
        writer.write(n+"\n");
        writer.close();

        FileInputStream fis = new FileInputStream(plik);
        InputStreamResource resource = new InputStreamResource(fis);
        Date date = new Date();
        usr.notyfikacje.add(new Notification("Raport wygenerowany.",date,usr));
        usr.raporty+=1;
        userRepository.save(usr);
        plik.delete();

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(plik.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    private boolean emailExists(String email) {
            return repository.findByEmail(email) != null;
        }


    private boolean hashExists(String hash) {
        return projectRepository.findByHash(hash) != null;
    }
}


