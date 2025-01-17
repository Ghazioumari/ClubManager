package club.esprit.backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import jakarta.persistence.*;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
public class BehaviorScore {
    public enum BehaviorType {
        POSITIVE,
        NEGATIVE,
        NEUTRAL,
        ATTENDANCE,
        PARTICIPATION,
        PERFORMANCE,
        OTHER
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Membership membership;

    @Enumerated(EnumType.STRING)
    @Column(name = "behavior_type", nullable = false)
    private BehaviorType behaviorType;

    @Column(nullable = false)
    private int score;

    @Column(name = "date_recorded")
    private LocalDate dateRecorded;

    @Column
    private String description;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    private Event event;
   @JsonIgnore
    public Membership getMembership() {
        return membership;
    }
@JsonProperty
    public void setMembership(Membership membership) {
        this.membership = membership;
    }
    @JsonIgnore
    public Event getEvent() {
        return event;
    }

    @JsonProperty
    public void setEvent(Event event) {
        this.event = event;
    }
}
