package ua.university.client.entity;

import java.util.Date;
import java.io.Serializable;


public class TeacherDTO extends PersonDTO implements Serializable {
    private int id;
    private String position;
    private SubjectDTO subject;

    public TeacherDTO() {
    }

    public TeacherDTO(int id, String firstName, String lastName, Date birthday,
                      String position, SubjectDTO subject) {
        super(firstName, lastName, birthday);
        this.id = id;
        this.position = position;
        this.subject = subject;
    }

    @Override
    public String toString() {
        return "id: " + this.getId() + "; " +
                super.toString() +
                "Position: " + position + "; " +
                "Subject: " + subject;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public SubjectDTO getSubject() {
        return subject;
    }

    public void setSubject(SubjectDTO subject) {
        this.subject = subject;
    }
}
