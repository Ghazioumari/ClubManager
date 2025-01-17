package club.esprit.backend.services.chat;


import club.esprit.backend.dto.chat.LastMessage;
import club.esprit.backend.entities.User;
import club.esprit.backend.entities.chat.ChatEntity;

import java.util.List;
import java.util.Optional;

public interface IChat {
    public ChatEntity addChat(ChatEntity chat);
    public ChatEntity retrieveById(long id);
    public ChatEntity updateChat(ChatEntity chat);
    public List<ChatEntity> retrieveAll();
    public void deleteById(long id);
    //public List<ChatEntity> findByPartecipantLogin(String login);

    public ChatEntity findByName(String name);

    public Optional<User> findUserByEmail(String email);

    public List<LastMessage> findAllUsers(String email);
}
