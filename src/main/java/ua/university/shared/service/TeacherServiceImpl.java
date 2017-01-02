package ua.university.shared.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.university.client.entity.TeacherDTO;
import ua.university.repository.SubjectRepository;
import ua.university.repository.TeacherRepository;
import ua.university.shared.Teacher;

@Service("jpaTeacherService")
@Repository
@Transactional
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    public Teacher save(TeacherDTO teacherDTO){
        Teacher t = new Teacher(teacherDTO);
        t.setSubject(subjectRepository.findBySubjectName(teacherDTO.getSubject().getSubjectName()).get(0));

        return teacherRepository.save(t);
    }

    public void deleteById(int id){
        teacherRepository.delete(id);
    }

    public void update(TeacherDTO teacherFrom, int teacherToId){
        Teacher teacher = teacherRepository.findOne(teacherToId);

        teacher.setFirstName(teacherFrom.getFirstName());
        teacher.setLastName(teacherFrom.getLastName());
        teacher.setBirthday(teacherFrom.getBirthday());
        teacher.setPosition(teacherFrom.getPosition());
        teacher.setSubject(subjectRepository.findBySubjectName(teacherFrom.getSubject().getSubjectName()).get(0));
    }
}
