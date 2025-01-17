package club.esprit.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import club.esprit.backend.entities.Post;
import club.esprit.backend.entities.User;
import club.esprit.backend.entities.Vote;

import java.util.Optional;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    Optional<Vote> findTopByPostAndUserOrderByVoteIdDesc(Post post, User currentUser);
    Optional<Vote> findVoteByPostAndUser(Post post, User currentUser); // Modify this line
    int countByPost(Post post);
}
