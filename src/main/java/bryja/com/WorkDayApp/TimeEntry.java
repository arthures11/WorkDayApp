package bryja.com.WorkDayApp;

import javax.persistence.*;

@Entity
public class TimeEntry {
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
    public String description;
    public int time_spent;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="workday_id")
    private WorkDay workday;
    TimeEntry(){

    }
    TimeEntry(String description,int time_spent){
        this.description=description;
        this.time_spent=time_spent;
    }

    public void setWorkday(WorkDay workday) {
        this.workday = workday;
    }

}
