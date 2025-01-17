package club.esprit.backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.time.Instant;

import static jakarta.persistence.FetchType.EAGER;
import static jakarta.persistence.FetchType.LAZY;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idComment;
    @NotEmpty
    private String text;
    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "postId", referencedColumnName = "postId")
    @JsonIgnore
    private Post post;
    private Instant createdDate;
    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "id", referencedColumnName = "id")
    private User user;
    @Transient
    private String userName;
    public String getUserName() {
        return user != null ? user.getName() : null;
    }
    @PrePersist
    public void prePersist() {
        createdDate = Instant.now();
        if (user != null) {
            userName = user.getName();
        }
    }
}