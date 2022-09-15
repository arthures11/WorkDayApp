package bryja.com.WorkDayApp;

import java.util.*;

import javax.persistence.*;
@Entity
public class WorkDay {
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
    public String date;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "workday", cascade = CascadeType.ALL)
    private List<TimeEntry> TimeEntry;
    WorkDay(){
    }
    WorkDay(String date) {
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
    public void setTimeEntry(TimeEntry entry) {
        this.TimeEntry = (List<TimeEntry>) entry;
    }
    public List<TimeEntry> getTimeEntry(){
        return TimeEntry;
    }
}
