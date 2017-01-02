package ua.university.shared.service;

import ua.university.client.entity.SubjectDTO;
import ua.university.shared.Subject;
import java.util.List;

public interface SubjectService {
    Subject findBySubjectName(String subjectName);
    Subject save(SubjectDTO subjectDTO);
    void delete(SubjectDTO subjectDTO);
    void update(SubjectDTO subjectFrom, int subjectToId);
    List<Subject> findAll();
}
