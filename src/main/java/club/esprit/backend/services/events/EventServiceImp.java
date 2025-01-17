package club.esprit.backend.services.events;

import club.esprit.backend.entities.Event;
import club.esprit.backend.entities.EventType;
import club.esprit.backend.entities.User;
import club.esprit.backend.repository.EventRepository;
import club.esprit.backend.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EventServiceImp  implements IEvent{

    private EventRepository eventRepository;


    private UserRepository userRepository;
    @Override
    public Event addEvent(Event e) {

        return eventRepository.save(e);
    }
    @Override
    public Optional<User> findUserByEmail(String email){
        return userRepository.findByEmail(email);
    }


    @Override
    public List<Event> retrieveAllEvents() {

        return eventRepository.findAll();
    }

    @Override
    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);

    }

    @Override
    public Event updateEvent(Event e) {

        return eventRepository.save(e);
    }
    @Override
    public Event getEvent(Long id) {
        return eventRepository.findById(id).orElse(null);
    }

    @Override
    public List<Event> getEventsByeventType(EventType eventType) {
        return eventRepository.findByEventType(eventType);
    }

}
