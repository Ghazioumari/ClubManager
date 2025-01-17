package club.esprit.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import club.esprit.backend.entities.Comment;
import club.esprit.backend.entities.Post;
import club.esprit.backend.entities.User;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost(Post post);
    List<Comment> findAllByUser(User user);

    List<Comment> findByPostPostId(Long postId);
}
