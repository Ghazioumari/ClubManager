package club.esprit.backend.entities;

import club.esprit.backend.entities.quiz.Test;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class Club  implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Size(max = 255)
    private String name;

    @Column
    private String description;

    @Column(name = "contact_info")
    private String contactInfo;
    @Column
    private String logo;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "club",cascade = CascadeType.ALL )
    private List<Department> departments;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "club",cascade = CascadeType.ALL )
    private List<Membership> memberships;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "club",cascade = CascadeType.ALL )
    private List<Finance> finance;
    @JsonCreator
    public Club(@JsonProperty("name") String name,
                @JsonProperty("description") String description,
                @JsonProperty("contactInfo") String contactInfo,
                @JsonProperty("logo") String logo) {
        this.name = name;
        this.description = description;
        this.contactInfo = contactInfo;
        this.logo = logo;
    }
    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<RequestToJoin> requestsToJoin;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Test> Tests;
}
