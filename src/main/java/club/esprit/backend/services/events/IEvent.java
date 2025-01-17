package club.esprit.backend.services.events;

import club.esprit.backend.entities.Event;
import club.esprit.backend.entities.EventType;
import club.esprit.backend.entities.User;

import java.util.List;
import java.util.Optional;

public interface IEvent {
    public Event addEvent(Event e);
    public List<Event> retrieveAllEvents();
    public void deleteEvent(Long id);
    public Event updateEvent(Event e);
    public Optional<User> findUserByEmail(String email);

    public Event getEvent(Long id);
    public List<Event> getEventsByeventType(EventType eventType);
}
