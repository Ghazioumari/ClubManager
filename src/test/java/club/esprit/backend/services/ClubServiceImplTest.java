package club.esprit.backend.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import club.esprit.backend.entities.Club;
import club.esprit.backend.repository.ClubRepository;
import club.esprit.backend.services.ClubServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClubServiceImplTest {

    @Mock
    private ClubRepository clubRepository;

    @InjectMocks
    private ClubServiceImpl clubService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddClub() {
        Club club = new Club();
        when(clubRepository.save(club)).thenReturn(club);

        Club result = clubService.addClub(club);

        assertEquals(club, result);
        verify(clubRepository, times(1)).save(club);
    }

    @Test
    void testUpdateClub() {
        Club club = new Club();
        club.setId(1L);
        club.setName("Updated Club Name");
        club.setDescription("Updated Description");

        Club existingClub = new Club();
        existingClub.setId(1L);

        when(clubRepository.findById(club.getId())).thenReturn(Optional.of(existingClub));
        when(clubRepository.save(existingClub)).thenReturn(existingClub);

        Club result = clubService.updateClub(club);

        assertEquals(club.getName(), result.getName());
        assertEquals(club.getDescription(), result.getDescription());
        verify(clubRepository, times(1)).save(existingClub);
    }



    @Test
    void testDeleteClub() {
        Long clubId = 1L;
        doNothing().when(clubRepository).deleteById(clubId);

        clubService.deleteClub(clubId);

        verify(clubRepository, times(1)).deleteById(clubId);
    }

    @Test
    void testGetClub() {
        Club club = new Club();
        club.setId(1L);

        when(clubRepository.findById(club.getId())).thenReturn(Optional.of(club));

        Club result = clubService.getClub(club.getId());

        assertEquals(club, result);
        verify(clubRepository, times(1)).findById(club.getId());
    }

    @Test
    void testGetAllClubs() {
        List<Club> clubs = List.of(new Club(), new Club());
        when(clubRepository.findAll()).thenReturn(clubs);

        List<Club> result = clubService.getAllClubs();

        assertEquals(clubs.size(), result.size());
        verify(clubRepository, times(1)).findAll();
    }

    @Test
    void testGetClubByUser() {
        Long userId = 1L;
        List<Club> clubs = List.of(new Club(), new Club());

        when(clubRepository.findClubsByUserId(userId)).thenReturn(clubs);

        List<Club> result = clubService.getClubByUser(userId);

        assertEquals(clubs, result);
        verify(clubRepository, times(1)).findClubsByUserId(userId);
    }

    @Test
    void testFindClubsByUserIdAndPresident() {
        Long userId = 1L;
        Club club = new Club();
        List<Club> clubs = List.of(club);

        when(clubRepository.findClubsByUserIdAndPresident(userId)).thenReturn(clubs);

        Club result = clubService.findClubsByUserIdAndPresident(userId);

        assertEquals(club, result);
        verify(clubRepository, times(1)).findClubsByUserIdAndPresident(userId);
    }
}
