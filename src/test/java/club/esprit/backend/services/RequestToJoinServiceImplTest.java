package club.esprit.backend.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import club.esprit.backend.entities.Club;
import club.esprit.backend.entities.RequestToJoin;
import club.esprit.backend.entities.User;
import club.esprit.backend.repository.ClubRepository;
import club.esprit.backend.repository.RequestToJoinRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class RequestToJoinServiceImplTest {

    @Mock
    private RequestToJoinRepository requestToJoinRepository;

    @Mock
    private ClubRepository clubRepository;

    @Mock
    private JavaMailSender emailSender;

    @InjectMocks
    private RequestToJoinServiceImpl requestToJoinService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveRequestToJoin() {
        RequestToJoin requestToJoin = new RequestToJoin();
        when(requestToJoinRepository.save(requestToJoin)).thenReturn(requestToJoin);

        RequestToJoin result = requestToJoinService.saveRequestToJoin(requestToJoin);

        assertEquals(requestToJoin, result);
        verify(requestToJoinRepository, times(1)).save(requestToJoin);
    }

    @Test
    void getRequestToJoinById() {
        Long requestId = 1L;
        RequestToJoin requestToJoin = new RequestToJoin();
        when(requestToJoinRepository.findById(requestId)).thenReturn(Optional.of(requestToJoin));

        Optional<RequestToJoin> result = requestToJoinService.getRequestToJoinById(requestId);

        assertTrue(result.isPresent());
        assertEquals(requestToJoin, result.get());
    }

    @Test
    void getAllRequestsToJoin() {
        List<RequestToJoin> requests = new ArrayList<>();
        when(requestToJoinRepository.findAll()).thenReturn(requests);

        List<RequestToJoin> result = requestToJoinService.getAllRequestsToJoin();

        assertEquals(requests, result);
        verify(requestToJoinRepository, times(1)).findAll();
    }

    @Test
    void getRequestsToJoinByUserId() {
        Long userId = 1L;
        RequestToJoin requestToJoin = new RequestToJoin();
        requestToJoin.setClub(new Club());
        requestToJoin.getClub().setId(1L);
        requestToJoin.getClub().setName("Club Name");
        requestToJoin.getClub().setLogo("club-logo.png");

        List<RequestToJoin> requests = List.of(requestToJoin);
        when(requestToJoinRepository.findByUserId(userId)).thenReturn(requests);
        when(clubRepository.findById(1L)).thenReturn(Optional.of(requestToJoin.getClub()));

        List<RequestToJoin> result = requestToJoinService.getRequestsToJoinByUserId(userId);

        assertEquals(1, result.size());
        assertEquals("Club Name", result.get(0).getClubName());
        assertEquals("club-logo.png", result.get(0).getClubImage());
    }

    @Test
    void getRequestsToJoinByClubId() {
        Long clubId = 1L;
        RequestToJoin requestToJoin = new RequestToJoin();
        requestToJoin.setUser(new User());
        requestToJoin.getUser().setName("User Name");
        requestToJoin.getUser().setEmail("user@example.com");
        requestToJoin.getUser().setId(1L);

        List<RequestToJoin> requests = List.of(requestToJoin);
        when(requestToJoinRepository.findByClub_Id(clubId)).thenReturn(requests);

        List<RequestToJoin> result = requestToJoinService.getRequestsToJoinByClubId(clubId);

        assertEquals(1, result.size());
        assertEquals("User Name", result.get(0).getUserName());
        assertEquals("user@example.com", result.get(0).getUserEmail());
        assertEquals(1L, result.get(0).getUserid());
    }

    @Test
    void deleteRequestToJoin() {
        Long requestId = 1L;

        requestToJoinService.deleteRequestToJoin(requestId);

        verify(requestToJoinRepository, times(1)).deleteById(requestId);
    }




}
