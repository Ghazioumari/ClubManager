package club.esprit.backend.services;

import club.esprit.backend.dto.SignupRequest;
import club.esprit.backend.entities.User;
import club.esprit.backend.repository.UserRepository;
import club.esprit.backend.services.EmailService;
import club.esprit.backend.services.IAuthImpl;
import club.esprit.backend.utils.OtpUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.mail.MessagingException;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class IAuthImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private OtpUtil otpUtil;
    @Mock
    private EmailService emailService;

    @InjectMocks
    private IAuthImpl authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }



    @Test
    void testCreateUser_EmailAlreadyExists() {
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail("test@example.com");

        when(userRepository.existsByEmail(signupRequest.getEmail())).thenReturn(true);

        Exception exception = assertThrows(RuntimeException.class, () -> authService.createUser(signupRequest));
        assertEquals("Email already exists", exception.getMessage());
    }

    @Test
    void testVerifyAccount_Success() {
        User user = new User();
        user.setOtp("123456");
        user.setOtpGeneratedTime(LocalDateTime.now());

        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));

        String result = authService.verifyAccount("test@example.com", "123456");

        assertEquals("OTP verified you can login", result);
        assertTrue(user.isActive());
    }

    @Test
    void testVerifyAccount_InvalidOtp() {
        User user = new User();
        user.setOtp("654321");
        user.setOtpGeneratedTime(LocalDateTime.now());

        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));

        String result = authService.verifyAccount("test@example.com", "123456");

        assertEquals("Please regenerate otp and try again", result);
        assertFalse(user.isActive());
    }





    @Test
    void testSetPassword_Success() {
        User user = new User();
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.encode("newPassword123")).thenReturn("hashedPassword");

        String result = authService.setPassword("test@example.com", "newPassword123");

        assertEquals("Password set successfully", result);
        assertEquals("hashedPassword", user.getPassword());
        verify(userRepository, times(1)).save(user);
    }
}
