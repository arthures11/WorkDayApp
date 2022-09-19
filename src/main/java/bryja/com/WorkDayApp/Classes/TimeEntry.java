package bryja.com.WorkDayApp.Classes;

import javax.persistence.*;

@Entity
public class TimeEntry {
    private @Id @GeneratedValue(strategy = GenerationType.AUTO) Long id;
    public String description;
    public int time_spent;
   // @ManyToOne
    //@JoinColumn(name = "workday_id")
    //private WorkDay workday;

   // public WorkDay getWorkday() {
    //    return workday;
   // }

   // public void setWorkday(WorkDay workday) {
   //     this.workday = workday;
   // }

    TimeEntry(){

    }
    TimeEntry(String description,int time_spent){
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
