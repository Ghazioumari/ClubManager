package club.esprit.backend.repository;

import club.esprit.backend.entities.EventImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventImageRepository extends JpaRepository<EventImage, Integer>{
    List<EventImage> findByOrderById();


}
