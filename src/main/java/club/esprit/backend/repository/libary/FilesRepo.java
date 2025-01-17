package club.esprit.backend.repository.libary;

import club.esprit.backend.entities.libary.Files;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilesRepo extends JpaRepository<Files, Integer> {

    public Files findByName(String filename);
}
