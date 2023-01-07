package bryja.com.WorkDayApp.Classes;

import bryja.com.WorkDayApp.Utility.ValidEmail;
import com.sun.istack.NotNull;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Collection;

@Entity
public class User {
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
    public String name;
    @NotNull
    @NotEmpty
    @ValidEmail
    public String email;
    public String password;

    @ManyToMany
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    private Collection<Role> roles;

    public User(){
    }
    public User(String email, String name) {
        this.email = email;
        this.name = name;
    }

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
