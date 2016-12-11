package ua.university.client.entity;

import com.google.gwt.i18n.shared.DateTimeFormat;
import ua.university.shared.Subject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.Serializable;
import java.util.Random;

/**
 * Created by Win7 on 2016-07-29.
 */

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
                this.getFirstName()+ " " + this.getLastName() + "(" + this.getBirthday() + ") - " +
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

    public SubjectDTO getSubject() {
        return subject;
    }

    public void setSubject(SubjectDTO subject) {
        this.subject = subject;
    }

    public static void main(String[] args) {
        TeacherDTO t = new TeacherDTO(0, "Test1", "Test2",
                new Date(new Random().nextInt(100), new Random().nextInt(12), new Random().nextInt(29)),
                "Dean", new SubjectDTO(0, "IT"));

        //DateTimeFormat.getFormat(DateTimeFormat.PredefinedFormat.DATE_SHORT).format(t.getBirthday());

        /*TeacherDTO teacherDTO = new TeacherDTO(0, "TestName", "TestSurname",
                new Date(new Random().nextInt(100), new Random().nextInt(12), new Random().nextInt(29)),
                "Dean1");
        Teacher teacher = new Teacher(teacherDTO);

        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(teacher);
        session.getTransaction().commit();
        session.close();*/
    }
}
