package club.esprit.backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class RequestToJoin {

    public enum Status {
        APPROVED,
        REJECTED,
        PENDING
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    @JsonIgnore
    private Club club;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;
    @Transient
    private  String UserName;
    @Transient
    private  String ClubName;
    @Transient
    private String ClubImage;
    @Transient
    private String UserEmail;
    @Transient
    private Long Userid;

    @JsonProperty
    public void setClub(Club club) {
        this.club = club;
    }
    @JsonProperty
    public void setUser(User user) {
        this.user = user;
    }
    @JsonIgnore
    public Club getClub() {
        return club;
    }
    @JsonIgnore
    public User getUser() {
        return user;
    }
}