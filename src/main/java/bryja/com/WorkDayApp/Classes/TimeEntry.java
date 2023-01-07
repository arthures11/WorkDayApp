package bryja.com.WorkDayApp.Classes;

import javax.persistence.*;

@Entity
public class TimeEntry {
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
    public String description;
    public int time_spent;

  //  public WorkDay getWorkday() {
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
