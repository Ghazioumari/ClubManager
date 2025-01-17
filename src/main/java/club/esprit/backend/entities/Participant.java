package club.esprit.backend.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@AllArgsConstructor

//One to many relationship between User and Participant
//to indicate that one user can be a participant in many events
public class Participant implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //represents the user who is participating in the event.
    //This is a many to one relationship between User and Participant
    //to indicate that many participant instances can be associated with one user
    @ManyToOne
    private User user;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;
}
