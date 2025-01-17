package club.esprit.backend.services;

import club.esprit.backend.dto.SignupRequest;
import club.esprit.backend.entities.User;


public interface IAuth {
    User createUser(SignupRequest signupRequest);
    public String verifyAccount(String email, String otp);

    public String regenerateOtp(String email);

    String forgotPassword(String email);

    String setPassword(String email, String newPassword);
}
