package ua.university.repository;

import org.springframework.data.repository.CrudRepository;
import ua.university.shared.Subject;

import java.util.List;

public interface SubjectRepository extends CrudRepository<Subject, Integer> {
    List<Subject> findBySubjectName(String subjectName);
}
