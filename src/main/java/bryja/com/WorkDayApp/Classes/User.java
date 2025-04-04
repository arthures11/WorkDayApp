package bryja.com.WorkDayApp.Classes;

import bryja.com.WorkDayApp.Utility.ValidEmail;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sun.istack.NotNull;
import org.aspectj.weaver.ast.Not;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "id")Long id;
  //  @Column(name = "user_id")public Long id2;
    public String name;
    @Column(unique=true)
    @NotNull
    @NotEmpty
    @ValidEmail
    public String email;
    public String password;
    public int raporty=0;

    @ManyToMany
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    @JsonIgnore
    private Collection<Role> roles;
    @OneToMany(targetEntity=Project.class,cascade = CascadeType.ALL,
            fetch = FetchType.LAZY, mappedBy = "user")
    public List<Project> projekty = new ArrayList<Project>();
    @OneToMany(targetEntity=Notification.class,cascade = CascadeType.ALL,
            fetch = FetchType.LAZY, mappedBy = "user")
    public List<Notification> notyfikacje = new ArrayList<Notification>();


    public User(){
    }

    public int getRaporty() {
        return raporty;
    }

    public void setRaporty(int raporty) {
        this.raporty = raporty;
    }

    public List<Notification> getNotyfikacje() {
        return notyfikacje;
    }

    public void setNotyfikacje(List<Notification> notyfikacje) {
        this.notyfikacje = notyfikacje;
    }

    public User(String email, String name) {
        this.email = email;
        this.name = name;
    }


   // public Long getId2() {
    //    return id2;
   // }

    //public void setId2(Long id2) {
    //    this.id2 = id2;
    //}

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public List<Project> getProjekty() {
        return projekty;
    }

    public void setProjekty(List<Project> projekty) {
        this.projekty = projekty;
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

    public void setPassword(String password) {
        this.password = password;
    }


}
