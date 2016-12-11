package ua.university.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import ua.university.client.entity.ClubDTO;
import ua.university.client.entity.StudentDTO;
import ua.university.client.entity.SubjectDTO;
import ua.university.client.entity.TeacherDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public interface UniversityServiceAsync {
    public void addTeacher(TeacherDTO teacherDTO, AsyncCallback callback);
    public void deleteTeacher(TeacherDTO teacherDTO, AsyncCallback callback);
    public void editTeacher(TeacherDTO teacherDTO, AsyncCallback callback);
    public void searchTeachers(TeacherDTO searchTeacherDTO, AsyncCallback<ArrayList<TeacherDTO>> callback);

    public void addStudent(StudentDTO StudentDTO, AsyncCallback callback);
    public void deleteStudent(StudentDTO StudentDTO, AsyncCallback callback);
    public void editStudent(StudentDTO StudentDTO, AsyncCallback callback);
    public void searchStudents(StudentDTO searchStudentDTO, AsyncCallback<ArrayList<StudentDTO>> callback);

    public void addClub(ClubDTO clubDTO, AsyncCallback callback);
    public void deleteClub(ClubDTO clubDTO, AsyncCallback callback);
    public void editClub(ClubDTO clubDTO, AsyncCallback callback);

    public void addSubject(SubjectDTO subjectDTO, AsyncCallback callback);
    public void deleteSubject(SubjectDTO subjectDTO, AsyncCallback callback);
    public void editSubject(SubjectDTO subjectDTO, AsyncCallback callback);
    
    public void getSubjects(AsyncCallback<Set<SubjectDTO>> callback);
    public void getClubs(AsyncCallback<Set<ClubDTO>> callback);
}
