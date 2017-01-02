package ua.university.client.entity;

import java.io.Serializable;
import java.util.Date;


public class PersonDTO implements Serializable {
    private String lastName;
    private String firstName;
    private Date birthday;

    public PersonDTO() {
    }

    public PersonDTO(String firstName, String lastName, Date birthday) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
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

    @Override
    public int hashCode() {
        int result = (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (birthday != null ? birthday.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;

        PersonDTO p = (PersonDTO) obj;
        if (!this.firstName.equals(p.firstName)) return false;
        if (!this.lastName.equals(p.lastName)) return false;
        if (!this.birthday.equals(p.birthday)) return false;

        return true;
    }
}
