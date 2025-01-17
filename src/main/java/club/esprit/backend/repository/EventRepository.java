package club.esprit.backend.repository;

import club.esprit.backend.entities.Event;
import club.esprit.backend.entities.EventType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByEventType(EventType type);
    // List<Event> findByEventTitle(String title);
    // List<Event> findByEventCreator(User creator);
    // List<Event> findByEventParticipants(User participant);


}
