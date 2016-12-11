package ua.university.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import ua.university.client.entity.ClubDTO;
import ua.university.client.entity.StudentDTO;
import ua.university.client.entity.SubjectDTO;
import ua.university.client.entity.TeacherDTO;

import java.util.ArrayList;
import java.util.Set;

@RemoteServiceRelativePath("UniversityService")
public interface UniversityService extends RemoteService {
     void addTeacher(TeacherDTO teacherDTO);
     void deleteTeacher(TeacherDTO teacherDTO);
     void editTeacher(TeacherDTO teacherDTO);
     ArrayList<TeacherDTO> searchTeachers(TeacherDTO searchTeacherDTO);

     void addStudent(StudentDTO studentDTO);
     void deleteStudent(StudentDTO studentDTO);
     void editStudent(StudentDTO studentDTO);
     ArrayList<StudentDTO> searchStudents(StudentDTO searchStudentDTO);

     void addClub(ClubDTO clubDTO);
     void deleteClub(ClubDTO clubDTO);
     void editClub(ClubDTO clubDTO);

     void addSubject(SubjectDTO subjectDTO);
     void deleteSubject(SubjectDTO subjectDTO);
     void editSubject(SubjectDTO subjectDTO);
     
     Set<SubjectDTO> getSubjects();
     Set<ClubDTO> getClubs();
}
