package ua.university.shared.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.university.client.entity.SubjectDTO;
import ua.university.shared.Subject;
import ua.university.repository.SubjectRepository;

import java.util.List;

@Service("jpaSubjectService")
@Repository
@Transactional
public class SubjectServiceImpl implements SubjectService {
    @Autowired
    private SubjectRepository subjectRepository;

    public Subject findBySubjectName(String subjectName){
        return subjectRepository.findBySubjectName(subjectName).get(0);
    }

    public Subject save(SubjectDTO subjectDTO){
        Subject subject = new Subject(subjectDTO);
        return subjectRepository.save(subject);
    }

    public void delete(SubjectDTO subjectDTO){
        Subject subject = new Subject(subjectDTO);
        subjectRepository.delete(subject);
    }

    public void update(SubjectDTO subjectFrom, int subjectToId){
        Subject subject = subjectRepository.findOne(subjectToId);
        subject.setSubjectName(subjectFrom.getSubjectName());
    }

    public List<Subject> findAll(){
        return (List<Subject>)subjectRepository.findAll();
    }
}
