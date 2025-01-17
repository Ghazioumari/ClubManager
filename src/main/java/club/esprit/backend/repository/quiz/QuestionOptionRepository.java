package club.esprit.backend.repository.quiz;

import club.esprit.backend.entities.quiz.QuestionOption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionOptionRepository extends JpaRepository<QuestionOption,Long> {
}
