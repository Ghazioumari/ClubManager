package club.esprit.backend.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import club.esprit.backend.entities.Role;
import club.esprit.backend.entities.User;
import club.esprit.backend.repository.UserRepository;
import club.esprit.backend.services.EmailService;
import club.esprit.backend.services.IUserImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class IUserImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private EmailService emailService;

    @InjectMocks
    private IUserImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSetNewRole() {
        User user = new User();
        user.setRole(Role.MEMBRE);
        when(userRepository.save(user)).thenReturn(user);

        userService.setNewRole(user, Role.RESPONSABLE);

        assertEquals(Role.RESPONSABLE, user.getRole());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testGetAllUsers() {
        userService.getAllUsers();
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testFindUserByEmail_UserExists() {
        User user = new User();
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));

        Optional<User> result = userService.findUserByEmail("test@example.com");

        assertTrue(result.isPresent());
        assertEquals(user, result.get());
    }

    @Test
    void testSetEtatToAccepted() {
        User user = new User();
        user.setEmail("test@example.com");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        boolean result = userService.setEtatToAccepted(1L);

        assertTrue(result);
        assertEquals("ACCEPTED", user.getEtat());
        assertEquals(Role.RESPONSABLE, user.getRole());
        verify(emailService, times(1)).sendEmail(
                eq("test@example.com"),
                anyString(),
                anyString()
        );
        verify(userRepository, times(1)).save(user);
    }


    @Test
    void testSetEtatToRejected() {
        User user = new User();
        user.setEmail("test@example.com");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        userService.setEtatToRejected(1L);

        assertEquals("REJECTED", user.getEtat());
        verify(emailService, times(1)).sendEmail(
                eq("test@example.com"),
                anyString(),
                anyString()
        );
        verify(userRepository, times(1)).save(user);
    }
}
