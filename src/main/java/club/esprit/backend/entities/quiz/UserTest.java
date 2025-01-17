package club.esprit.backend.entities.quiz;

import club.esprit.backend.entities.User;
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
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserTest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    int score;
    @Temporal(TemporalType.DATE)
    Date date;

    @ManyToOne
    User user;

    @ManyToOne
    Test test;

}
