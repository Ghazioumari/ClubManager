package club.esprit.backend.repository.quiz;

import club.esprit.backend.entities.quiz.UserTest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserTestRepository extends JpaRepository<UserTest,Long> {
    public List<UserTest> findByTestIdOrderByScoreDesc(Long id);
}
