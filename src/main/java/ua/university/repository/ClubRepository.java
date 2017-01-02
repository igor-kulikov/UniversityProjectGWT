package ua.university.repository;

import org.springframework.data.repository.CrudRepository;
import ua.university.shared.Club;

import java.util.List;

public interface ClubRepository extends CrudRepository<Club, Integer> {
    List<Club> findByClubName(String clubName);
}
