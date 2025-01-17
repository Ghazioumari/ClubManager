package club.esprit.backend.repository;

import club.esprit.backend.entities.Ressource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RessourceRepository extends JpaRepository<Ressource, Long> {

}
