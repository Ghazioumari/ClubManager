package club.esprit.backend.services;

import club.esprit.backend.entities.Finance;
import club.esprit.backend.repository.FinanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FinanceServiceImpl implements IFinanceService {

    @Autowired
    private FinanceRepository financeRepository;

    @Override
    public Finance addFinance(Finance finance) {
        return financeRepository.save(finance);
    }

    @Override
    public Finance updateFinance(Finance newFinance) {
        if (newFinance.getId() == null) {
            return null;
        }

        return financeRepository.findById(newFinance.getId())
                .map(oldFinance -> {
                    if (newFinance.getTransactionType() != null) {
                        oldFinance.setTransactionType(newFinance.getTransactionType());
                    }


                    return financeRepository.save(oldFinance);
                })
                .orElseThrow(() -> new RuntimeException("Finance not found with id " + newFinance.getId()));
    }

    @Override
    public void deleteFinance(Long id) {
        financeRepository.deleteById(id);
    }

    @Override
    public Finance getFinance(Long id) {
        return financeRepository.findById(id).orElse(null);
    }

    @Override
    public List<Finance> getAllFinances() {
        return financeRepository.findAll();
    }

    @Override
    public List<Finance> getFinanceByClub(Long id) {
        return financeRepository.findFinancesByClub_Id(id);
    }
}