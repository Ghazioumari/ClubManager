package club.esprit.backend.services.chat;

import club.esprit.backend.dto.chat.LastMessage;
import club.esprit.backend.entities.User;
import club.esprit.backend.entities.chat.ChatEntity;
import club.esprit.backend.entities.chat.MessageEntity;
import club.esprit.backend.repository.UserRepository;
import club.esprit.backend.repository.chat.ChatRepository;
import club.esprit.backend.repository.chat.MessageRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ChatImpl implements IChat {

    private ChatRepository chatRepository;

    private MessageRepository messageRepository;

    private UserRepository userRepository;


    @Override
    public ChatEntity addChat(ChatEntity chat) {
        return chatRepository.save(chat);
    }

    @Override
    public ChatEntity retrieveById(long id) {
        return chatRepository.findById(id).get();
    }

    @Override
    public ChatEntity updateChat(ChatEntity chat) {
        return chatRepository.save(chat);
    }

    @Override
    public List<ChatEntity> retrieveAll() {
        return chatRepository.findAll();
    }

    @Override
    public void deleteById(long id) {
        chatRepository.deleteById(id);
    }

//    @Override
//    public List<ChatEntity> findByPartecipantLogin(String login) {
//        return chatRepository.findByPartecipantEmail(login);
//    }

    @Override
    public ChatEntity findByName(String name) {
        return chatRepository.findByName(name);
    }

    @Override
    public Optional<User> findUserByEmail(String email){
        return userRepository.findByEmail(email);
    }

    @Override
    public List<LastMessage> findAllUsers(String email) {
        List<User> users = userRepository.findAll();
        Optional<User> curUser = userRepository.findByEmail(email);
        List<LastMessage> newUsersList = new ArrayList<>();
        for (User user :
                users) {
            if (!user.getEmail().equals(email)){
                String channelName = this.getChannelName(user, curUser.get());
                ChatEntity chat = chatRepository.findByName(channelName);
                MessageEntity lastMessage = null;
                if (chat != null){
                    lastMessage = messageRepository.findFirstByChatIdOrderByCreatedAtDesc(chat.getChat_id());

                }
                if (lastMessage == null){
                    newUsersList.add(new LastMessage("Start the conversation...", user, "", ""));
                }else {
                    newUsersList.add(new LastMessage(lastMessage.getContent(), user, lastMessage.getSender(), lastMessage.getT_stamp()));
                }
            }


        }
        
        return newUsersList;
    }

    public String getChannelName(User otherUser, User thisUser){
        long id1 = thisUser.getId();
        String nick1 = thisUser.getEmail();

        long id2 = otherUser.getId();
        String nick2 = otherUser.getEmail();

        if (id1 > id2) {
            return nick1 + '&' + nick2;
        }

        return nick2 + '&' + nick1;
    }
}
