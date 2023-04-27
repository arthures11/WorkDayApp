package bryja.com.WorkDayApp.Classes;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.*;

import jakarta.persistence.*;
@Entity
public class WorkDay {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "workday_id")
    Long id;
    public String date;
    @OneToMany(targetEntity=TimeEntry.class,cascade = CascadeType.ALL,
            fetch = FetchType.LAZY, mappedBy = "workDay")
    public List<TimeEntry> TimeEntry;

    @ManyToOne(targetEntity=Project.class,fetch = FetchType.EAGER)
    @JoinColumn(name = "project_id")
    @JsonBackReference
    @JsonIgnore
    private Project project;
    @JsonIgnore
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
