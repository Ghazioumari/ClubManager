package club.esprit.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import club.esprit.backend.entities.Category;

import java.util.Optional;
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String categoryName  );

}
