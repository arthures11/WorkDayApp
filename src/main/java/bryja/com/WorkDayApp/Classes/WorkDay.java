package bryja.com.WorkDayApp.Classes;

import bryja.com.WorkDayApp.Classes.TimeEntry;
import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.Parent;

import java.sql.Time;
import java.util.*;

import javax.persistence.*;
@Entity
public class WorkDay {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "workday_id")
    Long id;
    public String date;
    @OneToMany(targetEntity=TimeEntry.class,cascade = CascadeType.ALL,
            fetch = FetchType.LAZY, mappedBy = "workDay")
    public List<TimeEntry> TimeEntry = new ArrayList<TimeEntry>();

    @ManyToOne(targetEntity=Project.class,fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    @JsonBackReference
    private Project project;
    public Project getProject() {
        return project;
    }

    public WorkDay(String date, List<bryja.com.WorkDayApp.Classes.TimeEntry> timeEntry, Project project) {
        this.date = date;
        TimeEntry = timeEntry;
        this.project = project;
    }

    public WorkDay(String date, Project project) {
        this.date = date;
        this.project = project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public WorkDay(){
    }
    public WorkDay(String date) {
        this.date = date;
    }
    public String getDate() {
        return this.date;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDate(String date) {
        this.date = date;
    }
    public void setTimeEntry(List<TimeEntry> entry) {
        this.TimeEntry =  entry;
    }
    public List<TimeEntry> getTimeEntry(){
        return TimeEntry;
    }
}
