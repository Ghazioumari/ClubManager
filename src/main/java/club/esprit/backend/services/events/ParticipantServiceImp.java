package club.esprit.backend.services.events;

import club.esprit.backend.entities.Event;
import club.esprit.backend.entities.Participant;
import club.esprit.backend.entities.User;
import club.esprit.backend.repository.ParticipantRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor
public class ParticipantServiceImp implements IParticipant{


    private ParticipantRepository participantRepository;
    @Override
    public Participant addParticipant(Participant p) {
        return participantRepository.save(p);
    }

    @Override
    public List<Participant> getParticipantsByUser(User user) {
        return participantRepository.findByUser(user);
    }

    @Override
    public List<Participant> getParticipantsByEvent(Event event) {
        return participantRepository.findByEvent(event);
    }


}