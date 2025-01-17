package club.esprit.backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;
    private String postName;
    private String url;
    @Lob
    private String description;
    private Integer voteCount = 0;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id", referencedColumnName = "id")
    private User user;
    private Instant createdDate;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", nullable = false)
    @JsonIgnore
    private Category category;
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
