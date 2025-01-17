package club.esprit.backend.entities;

import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Membership")

@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class Membership implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "membership",cascade = CascadeType.ALL ,orphanRemoval = true)

    private List<BehaviorScore> behaviorscores;

    @ManyToOne
    @JoinColumn(name = "user_id")

    private User user;

    @ManyToOne
    @JoinColumn(name = "club_id")

    private Club club;

    @Column(nullable = false)
    private boolean president;
    @Column(nullable = false)
    private boolean responsable;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;
   @JsonIgnore
    public Club getClub() {
        return club;
    }
    @JsonProperty
    public void setClub(Club club) {
        this.club = club;
    }

    @JsonIgnore
    public User getUser() {
        return user;
    }
    @JsonProperty
    public void setUser(User user) {
        this.user = user;
    }
    @JsonIgnore
    public Department getDepartment() {
        return department;
    }
    @JsonProperty
    public void setDepartment(Department department) {
        this.department = department;
    }
}
