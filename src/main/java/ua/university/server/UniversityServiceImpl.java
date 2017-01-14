package ua.university.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.context.support.GenericXmlApplicationContext;
import ua.university.client.UniversityService;
import ua.university.client.entity.ClubDTO;
import ua.university.client.entity.StudentDTO;
import ua.university.client.entity.SubjectDTO;
import ua.university.client.entity.TeacherDTO;
import ua.university.shared.Club;
import ua.university.shared.Student;
import ua.university.shared.Subject;
import ua.university.shared.Teacher;
import ua.university.shared.service.ClubService;
import ua.university.shared.service.SubjectService;
import ua.university.shared.service.TeacherService;
import ua.university.utils.HibernateSessionFactory;

import javax.persistence.Query;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class UniversityServiceImpl extends RemoteServiceServlet implements UniversityService {

    private TeacherService teacherService;
    private SubjectService subjectService;
    private ClubService clubService;

    public UniversityServiceImpl() {
        GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
        ctx.load("classpath:spring-config.xml");
        ctx.refresh();

        teacherService = ctx.getBean("jpaTeacherService", TeacherService.class);
        subjectService = ctx.getBean("jpaSubjectService", SubjectService.class);
        clubService = ctx.getBean("jpaClubService", ClubService.class);
    }

    public TeacherDTO createTeacherDTO(Teacher teacher) {
        return new TeacherDTO(teacher.getId(), teacher.getFirstName(),
                teacher.getLastName(), teacher.getBirthday(),
                teacher.getPosition(),
                new SubjectDTO(0, teacher.getSubject().getSubjectName()));
    }

    public StudentDTO createStudentDTO(Student student) {
        Set<SubjectDTO> subjects = new HashSet<SubjectDTO>(student.getSubjects().size());
        Set<ClubDTO> clubs = new HashSet<ClubDTO>(student.getClubs().size());

        for (Subject s : student.getSubjects())
            subjects.add(new SubjectDTO(s.getSubjectId(), s.getSubjectName()));

        for (Club c : student.getClubs())
            clubs.add(new ClubDTO(c.getClubId(), c.getClubName()));

        return new StudentDTO(student.getId(), student.getFirstName(),
                student.getLastName(), student.getBirthday(), subjects, clubs);
    }

    public ClubDTO createClubDTO(Club club) {
        return new ClubDTO(club.getClubId(), club.getClubName());
    }

    public SubjectDTO createSubjectDTO(Subject subject) {
        return new SubjectDTO(subject.getSubjectId(), subject.getSubjectName());
    }

    //searchTeachers - CriteriaBuilder
    public List<TeacherDTO> searchTeachers(TeacherDTO searchTeacherDTO) {
        List<Teacher> teachers = null;
        List<TeacherDTO> teacherDTOs = null;

        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            CriteriaBuilder builder = session.getCriteriaBuilder();

            CriteriaQuery<Teacher> criteria = builder.createQuery(Teacher.class);
            Root<Teacher> teacherRoot = criteria.from(Teacher.class);
            criteria.select(teacherRoot);
            if (!"".equals(searchTeacherDTO.getFirstName()))
                criteria.where(builder.equal(teacherRoot.get("firstName"), searchTeacherDTO.getFirstName()));
            if (!"".equals(searchTeacherDTO.getLastName()))
                criteria.where(builder.equal(teacherRoot.get("lastName"), searchTeacherDTO.getLastName()));
            if (searchTeacherDTO.getBirthday() != null)
                criteria.where(builder.equal(teacherRoot.get("birthday"), searchTeacherDTO.getBirthday()));
            if (!"".equals(searchTeacherDTO.getPosition()))
                criteria.where(builder.equal(teacherRoot.get("position"), searchTeacherDTO.getPosition()));
            if (!"<<ANY SUBJECT>>".equals(searchTeacherDTO.getSubject().getSubjectName()))
                criteria.where(builder.equal(teacherRoot.get("subject"), subjectService.findBySubjectName(searchTeacherDTO.getSubject().getSubjectName())));

            teachers = session.createQuery(criteria).getResultList();
            tx.commit();
        } catch (HibernateException ex) {
            if (tx != null)
                tx.rollback();
            ex.printStackTrace();
        } finally {
            session.close();
        }

        teacherDTOs = new ArrayList<TeacherDTO>(
                teachers != null ? teachers.size() : 0);
        if (teachers != null) {
            for (Teacher teacher : teachers) {
                teacherDTOs.add(createTeacherDTO(teacher));
            }
        }
        return teacherDTOs;
    }


    /* searchTeachers - CriteriaAPI
    public ArrayList<TeacherDTO> searchTeachers(TeacherDTO searchTeacherDTO) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx = null;
        List<Teacher> teachers = null;
        ArrayList<TeacherDTO> teacherDTOs = null;
        try {
            tx = session.beginTransaction();
            Criteria c = session.createCriteria(Teacher.class);
            if (!"".equals(searchTeacherDTO.getLastName()))
                c.add(Restrictions.eq("lastName", searchTeacherDTO.getLastName()));
            if (!"".equals(searchTeacherDTO.getFirstName()))
                c.add(Restrictions.eq("firstName", searchTeacherDTO.getFirstName()));
            if (searchTeacherDTO.getBirthday() != null)
                c.add(Restrictions.eq("birthday", searchTeacherDTO.getBirthday()));
            if (!"".equals(searchTeacherDTO.getPosition()))
                c.add(Restrictions.eq("position", searchTeacherDTO.getPosition()));
            if (!"<<ANY SUBJECT>>".equals(searchTeacherDTO.getSubject().getSubjectName()))
                c.add(Restrictions.eq("subject", getSubjectByName(searchTeacherDTO.getSubject().getSubjectName())));

            teachers = c.list();
            tx.commit();
        } catch (HibernateException ex) {
            if (tx != null)
                tx.rollback();
            ex.printStackTrace();
        } finally {
            session.close();
        }

        teacherDTOs = new ArrayList<TeacherDTO>(
                teachers != null ? teachers.size() : 0);
        if (teachers != null) {
            for (Teacher teacher : teachers) {
                teacherDTOs.add(createTeacherDTO(teacher));
            }
        }
        return teacherDTOs;
    }*/

    public void addTeacher(TeacherDTO teacherDTO) {
        teacherService.save(teacherDTO);
    }

    public void deleteTeacher(TeacherDTO teacherDTO) {
        teacherService.deleteById(teacherDTO.getId());
    }

    public void editTeacher(TeacherDTO teacherDTO) {
        teacherService.update(teacherDTO, teacherDTO.getId());
    }

    public void addStudent(StudentDTO studentDTO) {
        Student student = new Student(studentDTO);

        Set<Subject> subjects = new HashSet<Subject>(studentDTO.getSubjects().size());
        for (SubjectDTO s : studentDTO.getSubjects())
            subjects.add(subjectService.findBySubjectName(s.getSubjectName()));
        student.setSubjects(subjects);

        Set<Club> clubs = new HashSet<Club>(studentDTO.getClubs().size());
        for (ClubDTO c : studentDTO.getClubs())
            clubs.add(clubService.findByClubName(c.getClubName()));
        student.setClubs(clubs);

        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            session.save(student);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null)
                tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public void deleteStudent(StudentDTO studentDTO) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            String hql = "delete from Student where lastName = :last_Name " +
                    "and firstName = :first_Name " +
                    "and birthday = :birthday_val ";
            Query q = session.createQuery(hql);
            q.setParameter("last_Name", studentDTO.getLastName());
            q.setParameter("first_Name", studentDTO.getFirstName());
            q.setParameter("birthday_val", studentDTO.getBirthday());
            int result = q.executeUpdate();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public void editStudent(StudentDTO studentDTO) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Student t = session.get(Student.class, studentDTO.getId());
            t.setLastName(studentDTO.getLastName());
            t.setFirstName(studentDTO.getFirstName());
            t.setBirthday(studentDTO.getBirthday());

            HashSet<Subject> subjects = new HashSet<Subject>(studentDTO.getSubjects().size());
            for (SubjectDTO s : studentDTO.getSubjects())
                subjects.add(subjectService.findBySubjectName(s.getSubjectName()));
            t.setSubjects(subjects);

            HashSet<Club> clubs = new HashSet<Club>(studentDTO.getClubs().size());
            for (ClubDTO c : studentDTO.getClubs())
                clubs.add(clubService.findByClubName(c.getClubName()));
            t.setClubs(clubs);

            session.update(t);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public ArrayList<StudentDTO> searchStudents(StudentDTO searchStudentDTO) {
        List<Student> students = null;
        ArrayList<StudentDTO> studentDTOs = null;

        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            CriteriaBuilder builder = session.getCriteriaBuilder();

            CriteriaQuery<Student> criteria = builder.createQuery(Student.class);
            Root<Student> studentRoot = criteria.from(Student.class);
            criteria.select(studentRoot);
            if (!"".equals(searchStudentDTO.getFirstName()))
                criteria.where(builder.equal(studentRoot.get("firstName"), searchStudentDTO.getFirstName()));
            if (!"".equals(searchStudentDTO.getLastName()))
                criteria.where(builder.equal(studentRoot.get("lastName"), searchStudentDTO.getLastName()));
            if (searchStudentDTO.getBirthday() != null)
                criteria.where(builder.equal(studentRoot.get("birthday"), searchStudentDTO.getBirthday()));
            if (!searchStudentDTO.getSubjects().contains(new SubjectDTO(0, "<<ANY SUBJECT>>"))) {
                /*Subquery<String> subQuery = criteria.subquery(String.class);
                Root<Subject> entityRoot2 = subQuery.from(Subject.class);
                predicates.add(criteriaBuilder.equal(entityRoot2.get(BundesbruderEntity_.userName), username));
                criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()])));*/
            }

            students = session.createQuery(criteria).getResultList();
            tx.commit();
        } catch (HibernateException ex) {
            if (tx != null)
                tx.rollback();
            ex.printStackTrace();
        } finally {
            session.close();
        }

        studentDTOs = new ArrayList<StudentDTO>(
                students != null ? students.size() : 0);
        if (students != null) {
            for (Student s : students) {
                studentDTOs.add(createStudentDTO(s));
            }
        }
        return studentDTOs;
    }

    public void addClub(ClubDTO clubDTO) {
        clubService.save(clubDTO);
    }

    ;

    public void deleteClub(ClubDTO clubDTO) {
        clubService.delete(clubDTO);
    }

    public void editClub(ClubDTO clubDTO) {
        clubService.update(clubDTO, clubDTO.getId());
    }

    public Set<ClubDTO> getClubs() {
        List<Club> clubs = clubService.findAll();
        Set<ClubDTO> clubsDTO = new HashSet<ClubDTO>();

        for (Club club : clubs) {
            clubsDTO.add(createClubDTO(club));
        }
        return clubsDTO;
    }

    public void addSubject(SubjectDTO subjectDTO) {
        subjectService.save(subjectDTO);
    }

    public void deleteSubject(SubjectDTO subjectDTO) {
        subjectService.delete(subjectDTO);
    }

    public void editSubject(SubjectDTO subjectDTO) {
        subjectService.update(subjectDTO, subjectDTO.getId());
    }

    public Set<SubjectDTO> getSubjects() {
        List<Subject> subjects = subjectService.findAll();
        Set<SubjectDTO> subjectsDTO = new HashSet<SubjectDTO>();

        for (Subject s : subjects)
            subjectsDTO.add(createSubjectDTO(s));

        return subjectsDTO;
    }

    public static void main(String[] args) {
        UniversityServiceImpl i = new UniversityServiceImpl();

        Set<SubjectDTO> subjectsDTO = new HashSet<SubjectDTO>();
        subjectsDTO.add(new SubjectDTO(0, "Math"));

        StudentDTO searchStudentDTO = new StudentDTO(0, "asdfasdf", "", null, subjectsDTO, null);
        List<StudentDTO> foundStudents = i.searchStudents(searchStudentDTO);

        for(StudentDTO s : foundStudents)
            System.out.println(s);

    }
}