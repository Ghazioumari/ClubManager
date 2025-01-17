package club.esprit.backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.Instant;
import java.util.List;

import static jakarta.persistence.FetchType.EAGER;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Category {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long idCategory;
    @NotBlank(message = "Community name is required")
    private String name;
    @NotBlank(message = "Description is required")
    private String description;
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = EAGER)
    @JsonIgnore
    private List<Post> posts;
    private Instant createdDate;
    @ManyToOne(fetch = EAGER)
    private User user;

    @PrePersist
    public void prePersist() {
        createdDate = Instant.now();
    }
}