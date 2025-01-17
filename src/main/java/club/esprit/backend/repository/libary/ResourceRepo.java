package club.esprit.backend.repository.libary;

import club.esprit.backend.entities.libary.Resource;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResourceRepo extends JpaRepository<Resource, Integer> {
}
