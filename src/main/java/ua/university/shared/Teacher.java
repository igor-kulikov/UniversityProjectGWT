package ua.university.shared;

import ua.university.client.entity.TeacherDTO;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

/**
 * Created by Win7 on 2016-07-29.
 */

@Entity
@Table(name = "teacher")
@PrimaryKeyJoinColumn(name="person_id")
@SuppressWarnings("serial")
public class Teacher extends Person implements Serializable {

    @Id
    @Column(name = "teacher_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "position", length = 50)
    private String position;

    @ManyToOne
    @JoinColumn(name="subject_id")
    private Subject subject;

    public Teacher() {
    }

    public Teacher(String firstName, String lastName, Date birthday,
                   String position, Subject subject) {
        super(0, firstName, lastName, birthday);
        this.position = position;
        this.subject = subject;
    }

    public Teacher(TeacherDTO teacherDTO){
        super(0, teacherDTO.getFirstName(), teacherDTO.getLastName(), teacherDTO.getBirthday());
        position = teacherDTO.getPosition();
        subject = new Subject(teacherDTO.getSubject().getSubjectName());
    }

    @Override
    public String toString() {
        return this.getFirstName() + " " + this.getLastName() + "(" + this.getBirthday() + ") - " +
                "position: " + position + "; " +
                "subject: " + subject;
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

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public static void main(String[] args) {
        ArrayList<Teacher> t = new ArrayList<Teacher>();
        ArrayList<TeacherDTO> t1 = new ArrayList<TeacherDTO>();

        /*t.add(new Teacher("TestName1", "TestSurname",
                new Date(new Random().nextInt(100), new Random().nextInt(12), new Random().nextInt(29)),
                "Dean1"));
        t.add(new Teacher("TestName2", "TestSurname",
                new Date(new Random().nextInt(100), new Random().nextInt(12), new Random().nextInt(29)),
                "Dean2"));
        t.add(new Teacher("TestName3", "TestSurname",
                new Date(new Random().nextInt(100), new Random().nextInt(12), new Random().nextInt(29)),
                "Dean3"));*/
        System.out.println(t);

        /*for(Teacher ti : t){
            t1.add(new TeacherDTO((ti)));
        }*/
        System.out.println(t1);
    }
}
