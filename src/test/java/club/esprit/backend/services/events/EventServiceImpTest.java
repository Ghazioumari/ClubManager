package club.esprit.backend.services.events;

import club.esprit.backend.entities.Event;
import club.esprit.backend.entities.EventType;
import club.esprit.backend.entities.User;
import club.esprit.backend.repository.EventRepository;
import club.esprit.backend.repository.UserRepository;
import club.esprit.backend.services.events.EventServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EventServiceImpTest {

    @Mock
    private EventRepository eventRepository;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private EventServiceImp eventService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddEvent() {
        Event event = new Event();
        when(eventRepository.save(event)).thenReturn(event);

        Event result = eventService.addEvent(event);

        assertEquals(event, result);
        verify(eventRepository, times(1)).save(event);
    }

    @Test
    void testFindUserByEmail() {
        User user = new User();
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));

        Optional<User> result = eventService.findUserByEmail("test@example.com");

        assertTrue(result.isPresent());
        assertEquals(user, result.get());
        verify(userRepository, times(1)).findByEmail("test@example.com");
    }

    @Test
    void testRetrieveAllEvents() {
        List<Event> events = List.of(new Event());
        when(eventRepository.findAll()).thenReturn(events);

        List<Event> result = eventService.retrieveAllEvents();

        assertEquals(events, result);
        verify(eventRepository, times(1)).findAll();
    }

    @Test
    void testDeleteEvent() {
        eventService.deleteEvent(1L);

        verify(eventRepository, times(1)).deleteById(1L);
    }

    @Test
    void testUpdateEvent() {
        Event event = new Event();
        when(eventRepository.save(event)).thenReturn(event);

        Event result = eventService.updateEvent(event);

        assertEquals(event, result);
        verify(eventRepository, times(1)).save(event);
    }

    @Test
    void testGetEvent() {
        Event event = new Event();
        when(eventRepository.findById(1L)).thenReturn(Optional.of(event));

        Event result = eventService.getEvent(1L);

        assertEquals(event, result);
        verify(eventRepository, times(1)).findById(1L);
    }


}
