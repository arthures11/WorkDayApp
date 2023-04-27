package bryja.com.WorkDayApp.Classes;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
public class TimeEntry {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
    public String description;
    public int time_spent;
    @ManyToOne(targetEntity=WorkDay.class,fetch = FetchType.EAGER)
    @JoinColumn(name = "workday_id")
    @JsonBackReference
    @JsonIgnore
    private WorkDay workDay;
    @JsonIgnore
    public WorkDay getWorkDay() {
        return workDay;
    }


    public void setWorkDay(WorkDay workDay) {
        this.workDay = workDay;
    }

    public TimeEntry(String description, int time_spent, WorkDay workDay) {
        this.description = description;
        this.time_spent = time_spent;
        this.workDay = workDay;
    }
//     return workday;
  //  }

   // public void setWorkday(WorkDay workday) {
   //     this.workday = workday;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTime_spent() {
        return time_spent;
    }

    public void setTime_spent(int time_spent) {
        this.time_spent = time_spent;
    }
    //}

    public TimeEntry(){

    }
    public TimeEntry(String description, int time_spent){
        this.description=description;
        this.time_spent=time_spent;
    }

  //  public void setWorkday(WorkDay workday) {
  //      this.workday = workday;
   // }
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
