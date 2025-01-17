package club.esprit.backend.repository;

import club.esprit.backend.entities.Event;
import club.esprit.backend.entities.Participant;
import club.esprit.backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {
    List<Participant> findByUser(User user);
    List<Participant> findByEvent(Event event);
}