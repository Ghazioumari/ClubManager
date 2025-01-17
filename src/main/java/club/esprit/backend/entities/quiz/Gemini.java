package club.esprit.backend.entities.quiz;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level=AccessLevel.PRIVATE)
public class Gemini {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    String question;
    @Column(length = 1000)
    String reponse;
    long iduser;
    Date date;
    }