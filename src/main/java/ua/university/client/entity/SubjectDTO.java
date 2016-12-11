package ua.university.client.entity;

import com.google.gwt.user.client.rpc.IsSerializable;
import ua.university.client.entity.TeacherDTO;
import ua.university.shared.Subject;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Win7 on 06.10.2016.
 */

public class SubjectDTO implements Serializable {
    private int id;
    private String subjectName;

    public SubjectDTO() {
    }

    public SubjectDTO(int id, String subjectName) {
        this.id = id;
        this.subjectName = subjectName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    /*public Set<TeacherDTO> getTeachers() {
        return teachers;
    }

    public void setTeachers(Set<TeacherDTO> teachers) {
        this.teachers = teachers;
    }*/

    @Override
    public String toString() {
        return "SubjectDTO{" +
                "subjectId=" + id +
                "; subjectName='" + subjectName + '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        SubjectDTO s = (SubjectDTO) obj;
        if (!this.subjectName.equals(s.subjectName))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        return subjectName.hashCode();
    }

    public static void main(String[] args) {
        Set<SubjectDTO> set = new HashSet<SubjectDTO>();
        SubjectDTO s1 = new SubjectDTO(0, "<<ANY SUBJECT>>");
        SubjectDTO s2 = new SubjectDTO(0, "<<ANY SUBJECT>>");

        set.add(s1);
        System.out.println(set);
        //System.out.println(s1.equals(s2));
        System.out.println(set.contains(s2));
    }
}
