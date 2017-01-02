package ua.university.client.entity;

import java.io.Serializable;

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
}
