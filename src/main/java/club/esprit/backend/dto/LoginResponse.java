package club.esprit.backend.dto;

public record LoginResponse(String jwt, String refreshToken, String role) {
}
