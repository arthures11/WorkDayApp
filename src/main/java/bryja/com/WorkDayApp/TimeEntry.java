package bryja.com.WorkDayApp;

import javax.persistence.*;

@Entity
@Table(name="TIMEENTRY")
public class TimeEntry {
    private @Id @GeneratedValue Long id;
    public String description;
    public int time_spent;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "workday_id", nullable = false)
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
