package club.esprit.backend.controllers;


import club.esprit.backend.services.IAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ResetPasswordController {

    private IAuth IAuth;

    @Autowired
    public ResetPasswordController(IAuth IAuth) {
        this.IAuth = IAuth;
    }

    @GetMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestParam String email) {
        return new ResponseEntity<>(IAuth.forgotPassword(email), HttpStatus.OK);
    }

    @PutMapping("/set-password")
    public ResponseEntity<String> setPassword(@RequestParam String email, @RequestHeader String newPassword) {
        return new ResponseEntity<>(IAuth.setPassword(email, newPassword), HttpStatus.OK);
    }
    @GetMapping("/verify-account")
    public ResponseEntity<String> verifyAccount(@RequestParam String email,
                                                @RequestParam String otp) {
        return new ResponseEntity<>(IAuth.verifyAccount(email, otp), HttpStatus.OK);
    }
    @PutMapping("/regenerate-otp")
    public ResponseEntity<String> regenerateOtp(@RequestParam String email) {
        return new ResponseEntity<>(IAuth.regenerateOtp(email), HttpStatus.OK);
    }








}
