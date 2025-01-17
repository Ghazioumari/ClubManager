package club.esprit.backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import static jakarta.persistence.FetchType.EAGER;
import static jakarta.persistence.FetchType.LAZY;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Vote {
    public enum VoteType {
        UPVOTE(1), DOWNVOTE(-1),
        ;

        VoteType(int direction) {
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long voteId;
    private VoteType voteType;
    @NotNull
    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "postId", referencedColumnName = "postId")
    @JsonIgnore
    private Post post;
    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "id", referencedColumnName = "id")
    private User user;
}

