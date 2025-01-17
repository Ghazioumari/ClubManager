package club.esprit.backend.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import club.esprit.backend.entities.Membership;
import club.esprit.backend.entities.User;
import club.esprit.backend.repository.MembershipRepository;
import club.esprit.backend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceImpTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private MembershipRepository membershipRepository;

    @InjectMocks
    private UserServiceImp userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addUser() {
        User user = new User();
        when(userRepository.save(user)).thenReturn(user);

        User result = userService.addUser(user);

        assertEquals(user, result);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void updateUser() {
        User user = new User();
        when(userRepository.save(user)).thenReturn(user);

        User result = userService.updateUser(user);

        assertEquals(user, result);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void deleteUser() {
        Long userId = 1L;

        userService.deleteUser(userId);

        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    void getUser() {
        Long userId = 1L;
        User user = new User();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        User result = userService.getUser(userId);

        assertNotNull(result);
        assertEquals(user, result);
    }

    @Test
    void getAllUsers() {
        List<User> users = new ArrayList<>();
        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.getAllUsers();

        assertEquals(users, result);
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void getById() {
        Long userId = 1L;
        User user = new User();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        User result = userService.getById(userId);

        assertNotNull(result);
        assertEquals(user, result);
    }

    @Test
    void getMembersForUser() {
        Long userId = 1L;
        User user = new User();
        List<Membership> memberships = new ArrayList<>();
        user.setMemberships(memberships);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        List<Membership> result = userService.getMembersForUser(userId);

        assertEquals(memberships, result);
    }

    @Test
    void getUsersByClub() {
        Long clubId = 1L;
        List<User> users = new ArrayList<>();
        when(userRepository.findUsersByClubId(clubId)).thenReturn(users);

        List<User> result = userService.getUsersByClub(clubId);

        assertEquals(users, result);
        verify(userRepository, times(1)).findUsersByClubId(clubId);
    }

    @Test
    void findUsersNotClub() {
        Long clubId = 1L;
        List<User> users = new ArrayList<>();
        when(userRepository.findUsersNotInClub(clubId)).thenReturn(users);

        List<User> result = userService.findUsersNotClub(clubId);

        assertEquals(users, result);
        verify(userRepository, times(1)).findUsersNotInClub(clubId);
    }

    @Test
    void findUsersByDepartmentId() {
        Long departmentId = 1L;
        List<User> users = new ArrayList<>();
        when(userRepository.findUsersByDepartmentId(departmentId)).thenReturn(users);

        List<User> result = userService.findUsersByDepartmentId(departmentId);

        assertEquals(users, result);
        verify(userRepository, times(1)).findUsersByDepartmentId(departmentId);
    }

    @Test
    void findMembersWithoutDepartment() {
        Long clubId = 1L;
        List<User> users = new ArrayList<>();
        when(userRepository.findMembersWithoutDepartment(clubId)).thenReturn(users);

        List<User> result = userService.findMembersWithoutDepartment(clubId);

        assertEquals(users, result);
        verify(userRepository, times(1)).findMembersWithoutDepartment(clubId);
    }



    @Test
    void getByEmail() {
        String email = "user@example.com";
        User user = new User();
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        User result = userService.getByEmail(email);

        assertEquals(user, result);
        verify(userRepository, times(1)).findByEmail(email);
    }
}
