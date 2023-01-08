package bryja.com.WorkDayApp.Classes;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.jdbc.Work;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Project {
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     @Column(name = "project_id")
    Long id;
    public String nazwa;

    @Column(unique=true)
    public String hash;

    @OneToMany(targetEntity=WorkDay.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "project")
    public List<WorkDay> WorkDay;

    @ManyToOne(targetEntity=User.class,fetch = FetchType.LAZY)
    @JsonBackReference
    private User user;
    public List<WorkDay> getWorkDays() {
        return WorkDay;
    }

    public Project(String nazwa, String hash, User user) {
        this.nazwa = nazwa;
        this.hash = hash;
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setWorkDays(List<WorkDay> workDays) {
        WorkDay = workDays;
    }

    public Project(Long id, String nazwa, String hash) {
        this.id = id;
        this.nazwa = nazwa;
        this.hash = hash;
    }

    public Project(){

    }
    public Project(String nazwa) {
        this.nazwa = nazwa;
    }

    public Long getId() {
        return id;
    }
    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public List<bryja.com.WorkDayApp.Classes.WorkDay> getWorkDay() {
        return WorkDay;
    }

    public void setWorkDay(List<bryja.com.WorkDayApp.Classes.WorkDay> workDay) {
        WorkDay = workDay;
    }
}
