package club.esprit.backend.services;

import club.esprit.backend.entities.Finance;
import club.esprit.backend.repository.FinanceRepository;
import club.esprit.backend.services.FinanceServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FinanceServiceImplTest {

    @Mock
    private FinanceRepository financeRepository;

    @InjectMocks
    private FinanceServiceImpl financeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddFinance() {
        Finance finance = new Finance();
        when(financeRepository.save(finance)).thenReturn(finance);

        Finance result = financeService.addFinance(finance);

        assertEquals(finance, result);
        verify(financeRepository, times(1)).save(finance);
    }



    @Test
    void testUpdateFinance_NotFound() {
        Finance newFinance = new Finance();
        newFinance.setId(2L);

        when(financeRepository.findById(newFinance.getId())).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> financeService.updateFinance(newFinance));
        assertEquals("Finance not found with id 2", exception.getMessage());
    }

    @Test
    void testDeleteFinance() {
        Long financeId = 1L;

        financeService.deleteFinance(financeId);

        verify(financeRepository, times(1)).deleteById(financeId);
    }

    @Test
    void testGetFinance_Found() {
        Long financeId = 1L;
        Finance finance = new Finance();
        finance.setId(financeId);

        when(financeRepository.findById(financeId)).thenReturn(Optional.of(finance));

        Finance result = financeService.getFinance(financeId);

        assertEquals(finance, result);
        verify(financeRepository, times(1)).findById(financeId);
    }

    @Test
    void testGetFinance_NotFound() {
        Long financeId = 1L;

        when(financeRepository.findById(financeId)).thenReturn(Optional.empty());

        Finance result = financeService.getFinance(financeId);

        assertNull(result);
    }

    @Test
    void testGetAllFinances() {
        List<Finance> finances = List.of(new Finance(), new Finance());

        when(financeRepository.findAll()).thenReturn(finances);

        List<Finance> result = financeService.getAllFinances();

        assertEquals(finances, result);
        verify(financeRepository, times(1)).findAll();
    }

    @Test
    void testGetFinanceByClub() {
        Long clubId = 1L;
        List<Finance> finances = List.of(new Finance(), new Finance());

        when(financeRepository.findFinancesByClub_Id(clubId)).thenReturn(finances);

        List<Finance> result = financeService.getFinanceByClub(clubId);

        assertEquals(finances, result);
        verify(financeRepository, times(1)).findFinancesByClub_Id(clubId);
    }
}
