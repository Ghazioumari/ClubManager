package club.esprit.backend.repository.libary;

import club.esprit.backend.entities.libary.CategoryLibrary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategotyRepo extends JpaRepository<CategoryLibrary, Integer> {
}
