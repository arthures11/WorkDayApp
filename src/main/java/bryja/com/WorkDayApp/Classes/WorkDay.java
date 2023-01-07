package bryja.com.WorkDayApp.Classes;

import bryja.com.WorkDayApp.Classes.TimeEntry;

import java.sql.Time;
import java.util.*;

import javax.persistence.*;
@Entity
public class WorkDay {
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
    public String date;

    @OneToMany(targetEntity=TimeEntry.class,cascade = CascadeType.ALL,
            fetch = FetchType.LAZY, orphanRemoval = true)
    private List<TimeEntry> TimeEntry = new ArrayList<TimeEntry>();
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
