package club.esprit.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import club.esprit.backend.entities.Category;
import club.esprit.backend.entities.Post;
import club.esprit.backend.entities.User;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByCategory(Category category);
    List<Post> findByCategory_IdCategory(Long idCategory);
    List<Post> findByUser(User user);


}
