package club.esprit.backend.repository;

import club.esprit.backend.entities.Department;
import club.esprit.backend.entities.Finance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FinanceRepository extends JpaRepository<Finance, Long> {
List<Finance> findFinancesByClub_Id(Long id);
}
