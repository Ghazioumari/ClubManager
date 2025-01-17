package club.esprit.backend.repository;

import club.esprit.backend.entities.Favoris;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavorisRepository extends JpaRepository<Favoris, Long> {

}
