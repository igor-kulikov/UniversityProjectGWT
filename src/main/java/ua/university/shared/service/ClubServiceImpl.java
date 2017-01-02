package ua.university.shared.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.university.client.entity.ClubDTO;
import ua.university.repository.ClubRepository;
import ua.university.shared.Club;

import java.util.List;

@Service("jpaClubService")
@Repository
@Transactional
public class ClubServiceImpl implements ClubService {
    @Autowired
    private ClubRepository clubRepository;

    public Club findByClubName(String clubName){
        return clubRepository.findByClubName(clubName).get(0);
    }

    public Club save(ClubDTO clubDTO){
        Club club = new Club(clubDTO);
        return clubRepository.save(club);
    }

    public void delete(ClubDTO clubDTO){
        Club club = new Club(clubDTO);
        clubRepository.delete(club);
    }

    public void update(ClubDTO clubFrom, int clubToId){
        Club club = clubRepository.findOne(clubToId);
        club.setClubName(clubFrom.getClubName());
    }

    public List<Club> findAll(){
        return (List<Club>)clubRepository.findAll();
    }
}
