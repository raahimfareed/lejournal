package Models;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
@Entity
@Table(name = "calendar_data")
public class CalendarData {
    @Id
    @Column(name = "date")
    private int date;

    @Column(name = "todo")
    private String todo;

    public CalendarData() {
        // Default constructor required by Hibernate
    }

    public CalendarData(int date, String todo) {
        this.date = date;
        this.todo = todo;
    }

    public int getDate() {
        return date;
    }

    public String getTodo() {
        return todo;
    }
}

