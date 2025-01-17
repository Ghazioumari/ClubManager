package club.esprit.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import club.esprit.backend.entities.BehaviorScore;

import java.util.List;

public interface BehaviorScoreRepository extends JpaRepository<BehaviorScore, Long> {
List<BehaviorScore> getBehaviorScoreByMembership_Id(Long id);
}
