package bryja.com.WorkDayApp;

import bryja.com.WorkDayApp.TimeEntry;

import java.sql.Time;
import java.util.*;

import javax.persistence.*;
@Entity
public class WorkDay {
    private @Id @GeneratedValue Long id;
    private String date;
    public @ElementCollection List<TimeEntry> TimeE = new ArrayList<>();
    WorkDay(){

    }
    WorkDay(String date) {
        this.date = date;
    }
    public Long getId() {
        return this.id;
    }
    public String getDate() {
        return this.date;
    }
}
