package ua.university.shared;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Win7 on 08.10.2016.
 */
@Entity
@Table(name = "person")
@Inheritance(strategy = InheritanceType.JOINED)
public class Person implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_id", nullable = false)
    private int id;

    @Column(name = "last_name", length = 50)
    private String lastName;

    @Column(name = "first_name", length = 30)
    private String firstName;

    @Column(name = "birthday")
    private Date birthday;

    public Person() {
    }

    public Person(int id, String firstName, String lastName, Date birthday) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
}
