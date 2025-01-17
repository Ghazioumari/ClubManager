package club.esprit.backend.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
public class Ressource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titre;

    @Enumerated(EnumType.STRING)
    private Categorie categorie;

    @Temporal(TemporalType.DATE)
    private Date dateCreation;

    @ManyToOne
    private Favoris favoris;

    private String filePath;
}
