package bryja.com.WorkDayApp;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class TimeEntry {
    private @Id
    @GeneratedValue
    Long id;
    String desc;
    int time_spent;
}
