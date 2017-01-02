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
    void addTeacher(TeacherDTO teacherDTO, AsyncCallback callback);
    void deleteTeacher(TeacherDTO teacherDTO, AsyncCallback callback);
    void editTeacher(TeacherDTO teacherDTO, AsyncCallback callback);
    void searchTeachers(TeacherDTO searchTeacherDTO, AsyncCallback<List<TeacherDTO>> callback);

    void addStudent(StudentDTO StudentDTO, AsyncCallback callback);
    void deleteStudent(StudentDTO StudentDTO, AsyncCallback callback);
    void editStudent(StudentDTO StudentDTO, AsyncCallback callback);
    void searchStudents(StudentDTO searchStudentDTO, AsyncCallback<ArrayList<StudentDTO>> callback);

    void addClub(ClubDTO clubDTO, AsyncCallback callback);
    void deleteClub(ClubDTO clubDTO, AsyncCallback callback);
    void editClub(ClubDTO clubDTO, AsyncCallback callback);
    void getClubs(AsyncCallback<Set<ClubDTO>> callback);

    void addSubject(SubjectDTO subjectDTO, AsyncCallback callback);
    void deleteSubject(SubjectDTO subjectDTO, AsyncCallback callback);
    void editSubject(SubjectDTO subjectDTO, AsyncCallback callback);
    void getSubjects(AsyncCallback<Set<SubjectDTO>> callback);

}
