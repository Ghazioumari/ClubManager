package club.esprit.backend.services.chat;


import club.esprit.backend.entities.chat.MessageEntity;

import java.util.List;

public interface IMessage {
    public MessageEntity addMessage(MessageEntity message);
    public MessageEntity retrieveById(long id);
    public MessageEntity updateMessage(MessageEntity message);
    public List<MessageEntity> retrieveAll();
    public void deleteById(long id);
    public void deleteFor(MessageEntity id);
    public void msgReact(MessageEntity id);
    public List<MessageEntity> findAllByChat(long chat_id);

    public MessageEntity getTheLastMsg(String channulName);
}
