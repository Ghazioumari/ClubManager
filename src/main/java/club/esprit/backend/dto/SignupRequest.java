package club.esprit.backend.dto;


import club.esprit.backend.entities.Role;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Getter
@Setter
public class SignupRequest {
    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    private String email;

    private String name;

    @NotBlank(message = "Password is mandatory")
    @Size(min = 8, message = "Password should have at least 8 characters")
    private String password;

    private Role role;

    // This field will hold the file name after processing
    private String profileImage;
}

