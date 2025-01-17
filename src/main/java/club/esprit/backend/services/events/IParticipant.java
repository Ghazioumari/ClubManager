package club.esprit.backend.services.events;

import club.esprit.backend.entities.Event;
import club.esprit.backend.entities.Participant;
import club.esprit.backend.entities.User;

import java.util.List;

public interface IParticipant {
    public Participant addParticipant(Participant p);
    public List<Participant> getParticipantsByUser(User user);
    public List<Participant> getParticipantsByEvent(Event event);



}