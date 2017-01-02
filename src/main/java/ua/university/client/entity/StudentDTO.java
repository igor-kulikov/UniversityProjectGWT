package ua.university.client.entity;

import java.util.Date;
import java.util.Set;

public class StudentDTO extends PersonDTO {
    private int id;

    private Set<SubjectDTO> subjects;
    private Set<ClubDTO> clubs;

    public StudentDTO() {
    }

    public StudentDTO(int id, String firstName, String lastName, Date birthday, Set<SubjectDTO> subjects, Set<ClubDTO> clubs) {
        super(firstName, lastName, birthday);
        this.id = id;
        this.subjects = subjects;
        this.clubs = clubs;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Set<SubjectDTO> getSubjects() {
        return subjects;
    }

    public void setSubjects(Set<SubjectDTO> subjects) {
        this.subjects = subjects;
    }

    public Set<ClubDTO> getClubs() {
        return clubs;
    }

    public void setClubs(Set<ClubDTO> clubs) {
        this.clubs = clubs;
    }

    @Override
    public String toString() {
        return "StudentDTO: ID = " + id +
                "; Subject = " + subjects +
                "; Clubs = " + clubs;
    }
}
