package ua.university.shared;

import com.google.gwt.user.client.rpc.IsSerializable;
import ua.university.client.entity.SubjectDTO;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Win7 on 06.10.2016.
 */

@Entity
@Table(name="ref_subjects")
public class Subject implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subject_id", nullable = false)
    private int subjectId;

    @Column(name = "subject_name", nullable = false, length = 50)
    private String subjectName;


    public Subject() {

    }

    public Subject(int subjectId, String subjectName) {
        this.subjectId = subjectId;
        this.subjectName = subjectName;
    }

    public Subject(SubjectDTO subjectDTO) {
        this(subjectDTO.getId(), subjectDTO.getSubjectName());
    }

    public Subject(String subjectName) {
        this.subjectName = subjectName;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    @Override
    public String toString() {
        return "subjectName: " + subjectName /*+ "; teachers: " + teachers*/;
    }

    public static void main(String[] args) {
        SubjectDTO s = new SubjectDTO(0, "Math");
        Subject s1 = new Subject(s);
        System.out.println(s1);
    }
}
