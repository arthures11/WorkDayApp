package bryja.com.WorkDayApp.Classes;

import org.hibernate.jdbc.Work;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Project {
    private @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
    public String nazwa;


    @OneToMany(targetEntity=TimeEntry.class,cascade = CascadeType.ALL,
            fetch = FetchType.LAZY, orphanRemoval = true)
    private List<WorkDay> WorkDay = new ArrayList<WorkDay>();

    public Project(String nazwa) {
        this.nazwa = nazwa;
    }

    public Long getId() {
        return id;
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
