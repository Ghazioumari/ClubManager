package club.esprit.backend.services;

import club.esprit.backend.entities.Finance;

import java.util.List;

public interface IFinanceService {
    Finance addFinance(Finance finance);
    Finance updateFinance(Finance finance);
    void deleteFinance(Long id);
    Finance getFinance(Long id);
    List<Finance> getAllFinances();
    List<Finance> getFinanceByClub(Long id);
}
