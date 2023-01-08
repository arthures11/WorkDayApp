
        package bryja.com.WorkDayApp.Classes;

        import com.fasterxml.jackson.annotation.JsonBackReference;

        import javax.persistence.*;

@Entity
public class Notification {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
    private String opis;
    private boolean odczyt;

    @ManyToOne(targetEntity=User.class,fetch = FetchType.LAZY)
    @JsonBackReference
    private User usr;

    public User getUser() {
        return usr;
    }

    public void setUser(User user) {
        this.usr = usr;
    }

    public boolean isOdczyt() {
        return odczyt;
    }

    public User getUsr() {
        return usr;
    }

    public void setUsr(User usr) {
        this.usr = usr;
    }

    public Notification() {

    }
    public Notification (String opis, boolean odczyt) {
        this.opis = opis;
        this.odczyt = odczyt;
    }

    public void setOpis(String opis) {this.opis = opis;}

    public void setOdczyt(boolean odczyt) {this.odczyt = odczyt;}

    public String getOpis() {return opis;}
    public boolean getOdczyt(){return odczyt;}

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}