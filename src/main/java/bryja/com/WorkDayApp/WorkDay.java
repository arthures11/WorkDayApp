package bryja.com.WorkDayApp;

import bryja.com.WorkDayApp.TimeEntry;

import java.sql.Time;
import java.util.*;

import javax.persistence.*;
@Entity
@Table(name="WORKDAY")
public class WorkDay {
    private @Id @GeneratedValue Long id;
    public String date;
    @OneToMany(mappedBy="workday")
    public List<TimeEntry> TimeEntry = new ArrayList<>();
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
}
