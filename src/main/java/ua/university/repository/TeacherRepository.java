package ua.university.repository;

import org.springframework.data.repository.CrudRepository;
import ua.university.shared.Teacher;

public interface TeacherRepository extends CrudRepository<Teacher, Integer> {
    /*@Query("select t from Teacher t where t.firstName = :firstname and " +
            "t.lastName = :lastname and " +
            "t.birthday = :birthday and " +
            "t.position = :position"
    )
    List<Teacher> findAll1(@Param("firstname") String firstname,
                           @Param("lastname") String lastName,
                           @Param("birthday") Date birthday,
                           @Param("position") String position);*/
}
