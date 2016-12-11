package ua.university.shared;

import org.hibernate.annotations.GeneratorType;
import ua.university.client.entity.ClubDTO;
import ua.university.client.entity.StudentDTO;
import ua.university.client.entity.SubjectDTO;
import ua.university.client.entity.TeacherDTO;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Win7 on 23.10.2016.
 */
@Entity
@Table(name = "student")
@PrimaryKeyJoinColumn(name="person_id")
public class Student extends Person {
    @Id
    @Column(name = "student_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "student_subject",
            joinColumns = {@JoinColumn(name = "student_id")},
            inverseJoinColumns = {@JoinColumn(name = "subject_id")})
    private Set<Subject> subjects = new HashSet<Subject>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "student_club",
            joinColumns = {@JoinColumn(name = "student_id")},
            inverseJoinColumns = {@JoinColumn(name = "club_id")})
    private Set<Club> clubs = new HashSet<Club>();

    public Student() {
    }

    public Student(int id, String firstName, String lastName, Date birthday, Set<Subject> subjects) {
        super(id, firstName, lastName, birthday);
        this.subjects = subjects;
    }

    public Student(StudentDTO studentDTO){
        super(0, studentDTO.getFirstName(), studentDTO.getLastName(), studentDTO.getBirthday());
        //subjects = new HashSet<Subject>(studentDTO.getSubjects().size());
        /*for(SubjectDTO s : studentDTO.getSubjects())
            subjects.add(new Subject(s));*/

        //clubs = new HashSet<Club>(studentDTO.getClubs().size());
        /*for(ClubDTO c : studentDTO.getClubs())
            clubs.add(new Club(c));*/
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public Set<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(Set<Subject> subjects) {
        this.subjects = subjects;
    }

    public Set<Club> getClubs() {
        return clubs;
    }

    public void setClubs(Set<Club> clubs) {
        this.clubs = clubs;
    }
}
