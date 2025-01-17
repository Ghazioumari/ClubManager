package club.esprit.backend.controllers;

import club.esprit.backend.entities.Event;
import club.esprit.backend.entities.Participant;
import club.esprit.backend.entities.User;
import club.esprit.backend.repository.EventRepository;
import club.esprit.backend.repository.ParticipantRepository;
import club.esprit.backend.repository.UserRepository;
import club.esprit.backend.services.events.IParticipant;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

//@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/participant")
@AllArgsConstructor
public class ParticipantRestController {

    private IParticipant iParticipant;

    private UserRepository userRepository;


    private EventRepository eventRepository;


    private ParticipantRepository participantRepository;
    @PostMapping("/addParticipant")
    public Participant addParticipant(@RequestBody Map<String, Long> body){
        Long userId = body.get("userId");
        Long eventId = body.get("eventId");

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with id " + eventId));

        Participant participant = new Participant();
        participant.setUser(user);
        participant.setEvent(event);

        return iParticipant.addParticipant(participant);
    }
    @GetMapping("/getParticipant/{id}")
    public Participant getParticipant(@PathVariable Long id){
        return participantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Participant not found with id " + id));
    }

    @GetMapping("/getParticipantsByUser/{userId}")
    public List<Participant> getParticipantsByUser(@PathVariable Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));
        return participantRepository.findByUser(user);
    }

    @GetMapping("/getParticipantsByEvent/{eventId}")
    public List<Participant> getParticipantsByEvent(@PathVariable Long eventId){
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with id " + eventId));
        return participantRepository.findByEvent(event);
    }

}
