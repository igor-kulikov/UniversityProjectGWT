package ua.university.client.entity;

import java.io.Serializable;

/**
 * Created by ikulikov on 26.10.2016.
 */
public class ClubDTO implements Serializable {
    private int id;
    private String clubName;

    public ClubDTO() {
    }

    public ClubDTO(int id, String clubName) {
        this.id = id;
        this.clubName = clubName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ClubDTO c = (ClubDTO) obj;
        if (!this.clubName.equals(c.clubName))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        return clubName.hashCode();
    }

    @Override
    public String toString() {
        return "ClubDTO{"+
                "ClubId=" + id +
                "; ClubName=" + clubName + "}";
    }
}
