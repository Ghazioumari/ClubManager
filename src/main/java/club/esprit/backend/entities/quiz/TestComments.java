package club.esprit.backend.entities.quiz;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level=AccessLevel.PRIVATE)
public class TestComments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    //the question asked
    String body;
    //the id of the question
    String questionId;
    // the user information so i dont have to get them again
    String userimage;
    String username;
    String userId;
    //date to be beautiful
    @Temporal(TemporalType.DATE)
    Date createdAt;
    //reply of the question
    String response;
    //date of response
    @Temporal(TemporalType.DATE)
    Date repliedAt;
    }
