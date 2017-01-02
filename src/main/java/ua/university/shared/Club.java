package ua.university.shared;

import ua.university.client.entity.ClubDTO;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="ref_clubs")
public class Club implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "club_id", nullable = false)
    private int clubId;

    @Column(name = "club_name", nullable = false, length = 50)
    private String clubName;

    public Club() {
    }

    public Club(int clubId, String clubName) {
        this.clubId = clubId;
        this.clubName = clubName;
    }

    public Club(ClubDTO clubDTO){
        this(clubDTO.getId(), clubDTO.getClubName());
    }

    public int getClubId() {
        return clubId;
    }

    public void setClubId(int clubId) {
        this.clubId = clubId;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    @Override
    public String toString() {
        return "clubId: " + clubId + "; clubName: " + clubName;
    }

    @Override
    public int hashCode() {
        return clubName.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Club c = (Club) obj;
        if (!this.clubName.equals(c.clubName))
            return false;
        return true;
    }
}
