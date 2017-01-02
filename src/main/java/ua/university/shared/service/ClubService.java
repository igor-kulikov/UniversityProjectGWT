package ua.university.shared.service;

import ua.university.client.entity.ClubDTO;
import ua.university.shared.Club;

import java.util.List;

public interface ClubService {
    Club findByClubName(String clubName);
    Club save(ClubDTO clubDTO);
    void delete(ClubDTO clubDTO);
    void update(ClubDTO clubFrom, int clubToId);
    List<Club> findAll();
}
