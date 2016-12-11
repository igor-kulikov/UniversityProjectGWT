package ua.university.server;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.gwt.user.server.rpc.core.java.util.ArrayList_ServerCustomFieldSerializer;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import ua.university.client.UniversityService;
import ua.university.client.entity.ClubDTO;
import ua.university.client.entity.StudentDTO;
import ua.university.client.entity.SubjectDTO;
import ua.university.client.entity.TeacherDTO;
import ua.university.shared.Club;
import ua.university.shared.Student;
import ua.university.shared.Subject;
import ua.university.shared.Teacher;
import ua.university.utils.HibernateSessionFactory;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.*;


public class UniversityServiceImpl extends RemoteServiceServlet implements UniversityService {

    public ArrayList<TeacherDTO> searchTeachers(TeacherDTO searchTeacherDTO) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx = null;
        List<Teacher> teachers = null;
        ArrayList<TeacherDTO> teacherDTOs = null;
        try {
            tx = session.beginTransaction();
            /*CriteriaBuilder builder = session.getCriteriaBuilder();

            CriteriaQuery<Teacher> criteria = builder.createQuery(Teacher.class );
            Root<Teacher> teacherRoot = criteria.from(Teacher.class);
            criteria.select(teacherRoot);
            if(!"".equals(searchTeacherDTO.getFirstName()))
                criteria.where(builder.equal(teacherRoot.get("firstName"), searchTeacherDTO.getFirstName()));
            if(!"".equals(searchTeacherDTO.getLastName()))
                criteria.where(builder.equal(teacherRoot.get("lastName"), searchTeacherDTO.getLastName()));
            if(searchTeacherDTO.getBirthday() != null)
                criteria.where(builder.equal(teacherRoot.get("birthday"), searchTeacherDTO.getBirthday()));
            if(!"".equals(searchTeacherDTO.getPosition()))
                criteria.where(builder.equal(teacherRoot.get("position"), searchTeacherDTO.getPosition()));
            if(!"<<ANY SUBJECT>>".equals(searchTeacherDTO.getSubject().getSubjectName()))
                criteria.where(builder.equal(teacherRoot.get("subject"), getSubjectByName(searchTeacherDTO.getSubject().getSubjectName())));

            teachers =  session.createQuery(criteria).getResultList();*/


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
    }

    public void addTeacher(TeacherDTO teacherDTO) {
        Teacher t = new Teacher(teacherDTO);
        t.setSubject(getSubjectByName(t.getSubject().getSubjectName()));

        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(t);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null)
                tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public void deleteTeacher(TeacherDTO teacherDTO) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Teacher t = (Teacher) session.get(Teacher.class, teacherDTO.getId());
            session.delete(t);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public void editTeacher(TeacherDTO teacherDTO) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Teacher t = (Teacher) session.get(Teacher.class, teacherDTO.getId());
            t.setLastName(teacherDTO.getLastName());
            t.setFirstName(teacherDTO.getFirstName());
            t.setBirthday(teacherDTO.getBirthday());
            t.setPosition(teacherDTO.getPosition());
            if(!t.getSubject().getSubjectName().equals(teacherDTO.getSubject().getSubjectName()))
                t.setSubject(getSubjectByName(teacherDTO.getSubject().getSubjectName()));
            session.update(t);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    /*
    //working one
    public void deleteTeacher(TeacherDTO teacherDTO) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            String hql = "delete from Teacher where lastName = :last_Name " +
                    "and firstName = :first_Name " +
                    "and birthday = :birthday_val ";
            Query q = session.createQuery(hql);
            q.setParameter("last_Name", teacherDTO.getLastName());
            q.setParameter("first_Name", teacherDTO.getFirstName());
            q.setParameter("birthday_val", teacherDTO.getBirthday());
            int result = q.executeUpdate();

            tx.commit();
        }
        catch(HibernateException e){
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }
        finally {
            session.close();
        }
    }*/

    public Subject getSubjectByName(String subjectName) {
        List<Subject> subjects = null;
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            String hql = "from Subject s where s.subjectName = :subjet_name order by s.id asc";
            Query q = session.createQuery(hql);
            q.setParameter("subjet_name", subjectName);
            subjects = q.list();
            tx.commit();
        } catch (HibernateException ex) {
            if (null != tx)
                tx.rollback();
            ex.printStackTrace();
        } finally {
            session.close();
        }
        return subjects.get(0);
    }

    public Club getClubByName(String clubName) {
        List<Club> clubs = null;
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            String hql = "from Club c where c.clubName = :club_name order by c.id asc";
            Query q = session.createQuery(hql);
            q.setParameter("club_name", clubName);
            clubs = q.list();
            tx.commit();
        } catch (HibernateException ex) {
            if (null != tx)
                tx.rollback();
            ex.printStackTrace();
        } finally {
            session.close();
        }
        return clubs.get(0);
    }

    public Set<SubjectDTO> getSubjects() {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx = null;
        List<Subject> subjects = null;
        Set<SubjectDTO> subjectsDTO = new HashSet<SubjectDTO>();

        try {
            tx = session.beginTransaction();
            subjects = session.createQuery("from Subject").list();
            tx.commit();
        } catch (HibernateException ex) {
            if (null != tx)
                tx.rollback();
            ex.printStackTrace();
        } finally {
            session.close();
        }

        for (Subject subject : subjects) {
            subjectsDTO.add(createSubjectDTO(subject));
        }
        return subjectsDTO;
    }

    public Set<ClubDTO> getClubs() {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx = null;
        List<Club> clubs = null;
        Set<ClubDTO> clubsDTO = new HashSet<ClubDTO>();

        try {
            tx = session.beginTransaction();
            clubs = session.createQuery("from Club").list();
            tx.commit();
        } catch (HibernateException ex) {
            if (null != tx)
                tx.rollback();
            ex.printStackTrace();
        } finally {
            session.close();
        }

        for (Club club : clubs) {
            clubsDTO.add(createClubDTO(club));
        }
        return clubsDTO;
    }


    public SubjectDTO createSubjectDTO(Subject subject) {
        return new SubjectDTO(subject.getSubjectId(), subject.getSubjectName());
    }

    public ClubDTO createClubDTO(Club club) {
        return new ClubDTO(club.getClubId(), club.getClubName());
    }

    public TeacherDTO createTeacherDTO(Teacher teacher) {
        return new TeacherDTO(teacher.getId(), teacher.getFirstName(),
                teacher.getLastName(), teacher.getBirthday(),
                teacher.getPosition(),
                createSubjectDTO(teacher.getSubject()));
    }

    public StudentDTO createStudentDTO(Student student) {
        HashSet<SubjectDTO> subjects = new HashSet<SubjectDTO>(student.getSubjects().size());
        HashSet<ClubDTO> clubs = new HashSet<ClubDTO>(student.getClubs().size());

        for(Subject s : student.getSubjects())
            subjects.add(new SubjectDTO(s.getSubjectId(), s.getSubjectName()));

        for(Club c : student.getClubs())
            clubs.add(new ClubDTO(c.getClubId(), c.getClubName()));

        return new StudentDTO(student.getId(), student.getFirstName(),
                student.getLastName(), student.getBirthday(), subjects, clubs);
    }


   /* public void addStudent(Student student){
        //t.setSubject(getSubjectByName(t.getSubject().getSubjectName()));
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx = null;

        Set<Subject> new_subject = new HashSet<Subject>();
        if(student.getSubjects() != null)
            for(Subject s : student.getSubjects())
                new_subject.add(getSubjectByName(s.getSubjectName()));
        student.setSubjects(new_subject);

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
    }*/

    public void addStudent(StudentDTO studentDTO) {
        Student student = new Student(studentDTO);

        Set<Subject> subjects = new HashSet<Subject>(studentDTO.getSubjects().size());
        for(SubjectDTO s : studentDTO.getSubjects())
            subjects.add(getSubjectByName(s.getSubjectName()));
        student.setSubjects(subjects);

        Set<Club> clubs = new HashSet<Club>(studentDTO.getClubs().size());
        for(ClubDTO c : studentDTO.getClubs())
            clubs.add(getClubByName(c.getClubName()));
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

    public void deleteStudent(StudentDTO studentDTO){
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Student s = (Student) session.get(Student.class, studentDTO.getId());
            session.delete(s);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public void editStudent(StudentDTO studentDTO){
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Student t = (Student) session.get(Student.class, studentDTO.getId());
            t.setLastName(studentDTO.getLastName());
            t.setFirstName(studentDTO.getFirstName());
            t.setBirthday(studentDTO.getBirthday());

            HashSet<Subject> subjects = new HashSet<Subject>(studentDTO.getSubjects().size());
            for(SubjectDTO s : studentDTO.getSubjects())
                subjects.add(getSubjectByName(s.getSubjectName()));
            t.setSubjects(subjects);

            HashSet<Club> clubs = new HashSet<Club>(studentDTO.getClubs().size());
            for(ClubDTO c : studentDTO.getClubs())
                clubs.add(getClubByName(c.getClubName()));
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

    public ArrayList<StudentDTO> searchStudents(StudentDTO searchStudentDTO){
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx = null;
        List<Student> students = null;
        ArrayList<StudentDTO> studentDTOs = null;
        try {
            tx = session.beginTransaction();
            CriteriaBuilder builder = session.getCriteriaBuilder();

            CriteriaQuery<Student> criteria = builder.createQuery(Student.class);
            Root<Student> studentRoot = criteria.from(Student.class);
            criteria.select(studentRoot);
            if(!"".equals(searchStudentDTO.getFirstName()))
                criteria.where(builder.equal(studentRoot.get("firstName"), searchStudentDTO.getFirstName()));
            if(!"".equals(searchStudentDTO.getLastName()))
                criteria.where(builder.equal(studentRoot.get("lastName"), searchStudentDTO.getLastName()));
            if(searchStudentDTO.getBirthday() != null)
                criteria.where(builder.equal(studentRoot.get("birthday"), searchStudentDTO.getBirthday()));
            /*if(!searchStudentDTO.getSubjects().contains(new SubjectDTO("<<ANY SUBJECT>>"))){
                Set<Subject> s1 = new HashSet<Subject>();
                for(SubjectDTO s : searchStudentDTO.getSubjects()) {
                    s1.add(getSubjectByName(s.getSubjectName()));
                    //criteria.where(builder.equal(studentRoot.get("subjects"), s1));
                    criteria.where(builder.in(studentRoot.get("subjects"), getSubjectByName(s.getSubjectName())));
                }

            }*/
            students =  session.createQuery(criteria).getResultList();


            System.out.println(students);
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

    public void addClub(ClubDTO clubDTO){
        Club club = new Club(clubDTO);
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            session.save(club);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null)
                tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    };

    public void deleteClub(ClubDTO clubDTO){
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Club c = (Club) session.get(Club.class, clubDTO.getId());
            session.delete(c);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    };

    public void editClub(ClubDTO clubDTO){
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Club c = (Club) session.get(Club.class, clubDTO.getId());
            c.setClubName(clubDTO.getClubName());
            session.update(c);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    };

    public void addSubject(SubjectDTO subjectDTO){
        Subject subject = new Subject(subjectDTO);
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            session.save(subject);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null)
                tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    };

    public void deleteSubject(SubjectDTO subjectDTO){
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Subject c = (Subject) session.get(Subject.class, subjectDTO.getId());
            session.delete(c);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    };

    public void editSubject(SubjectDTO subjectDTO){
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Subject t = (Subject) session.get(Subject.class, subjectDTO.getId());
            t.setSubjectName(subjectDTO.getSubjectName());
            session.update(t);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    };


    public static void main(String[] args) {
        UniversityServiceImpl i = new UniversityServiceImpl();

        //Add student
        HashSet<SubjectDTO> ss = new HashSet<SubjectDTO>();
        ss.add(new SubjectDTO(0, "Math"));
        ss.add(new SubjectDTO(0, "History"));

        HashSet<ClubDTO> sc = new HashSet<ClubDTO>();
        sc.add(new ClubDTO(0, "Football"));
        sc.add(new ClubDTO(0, "Chess"));

        StudentDTO s = new StudentDTO(0, "SName1", "SSurname1", new Date(17, 10, 23),
                ss, sc);
        i.addStudent(s);

        // Add teacher
        TeacherDTO t = new TeacherDTO(0, "TName2", "Surname1", new Date (06, 11, 16),
                "Position1", new SubjectDTO(0, "Math"));
        i.addTeacher(t);

        //Add student
        HashSet<SubjectDTO> ss1 = new HashSet<SubjectDTO>();
        ss1.add(new SubjectDTO(0, "Math"));
        ss1.add(new SubjectDTO(0, "History"));

        HashSet<ClubDTO> sc1 = new HashSet<ClubDTO>();
        sc1.add(new ClubDTO(0, "Football"));
        sc1.add(new ClubDTO(0, "Chess"));

        StudentDTO s1 = new StudentDTO(0, "SName2", "SSurname2", new Date(17, 10, 23),
                ss1, sc1);
        i.addStudent(s1);
    }
}