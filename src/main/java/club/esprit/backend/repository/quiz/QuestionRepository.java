package club.esprit.backend.repository.quiz;

import club.esprit.backend.entities.quiz.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question,Long> {
}
