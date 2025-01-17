package club.esprit.backend.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;


import java.util.List;


@Entity
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String password;
    private boolean active;
    private String etat;
    private String otp;
    private LocalDateTime otpGeneratedTime;

    @Enumerated(EnumType.STRING)
    private Role role;


    private String profileImage;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user",cascade = CascadeType.ALL ,orphanRemoval = true)
    private List<Membership> memberships;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RequestToJoin> requestsToJoin;


}
