package club.esprit.backend.entities;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@AllArgsConstructor

public class Event implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String eventTitle;
    private String eventDescription;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "eventImage_id")
    private EventImage eventImage;
    private int MaxParticipants;
    @Enumerated(EnumType.STRING)
    private EventType eventType;
    private String location;
    private Date startDate;
    private Date endDate;
    private int price;
    private Boolean isPublic;

    @OneToMany(mappedBy = "event", cascade = CascadeType.REMOVE)
    private Set<Participant> participants;
    @ManyToOne
    @JoinColumn(name = "Creator_id")
    private User creator;

}

