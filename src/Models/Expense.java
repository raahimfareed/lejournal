package Models;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Table(name = "addrecord")
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String food;
    private String petrol;
    private String credit;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date", updatable = false)
    private Date date;

    public Expense() {
    }

    public Expense(String food, String petrol, String credit) {
        this.food = food;
        this.petrol = petrol;
        this.credit = credit;
    }

    public Expense(int id, String food, String petrol, String credit, Date date) {
        this.id = id;
        this.food = food;
        this.petrol = petrol;
        this.credit = credit;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public String getPetrol() {
        return petrol;
    }

    public void setPetrol(String petrol) {
        this.petrol = petrol;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
