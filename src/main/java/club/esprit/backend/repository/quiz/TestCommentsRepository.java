package club.esprit.backend.repository.quiz;

import club.esprit.backend.entities.quiz.TestComments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestCommentsRepository extends JpaRepository<TestComments,Long> {
    TestComments findByQuestionIdAndId(String id,Long idd);
}
