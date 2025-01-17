package club.esprit.backend.services.chat;

import club.esprit.backend.entities.chat.ChatEntity;
import club.esprit.backend.entities.chat.MessageEntity;
import club.esprit.backend.repository.chat.ChatRepository;
import club.esprit.backend.repository.chat.MessageRepository;
import club.esprit.backend.services.chat.MessageImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MessageImplTest {

    @Mock
    private MessageRepository messageRepository;
    @Mock
    private ChatRepository chatRepository;

    @InjectMocks
    private MessageImpl messageService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddMessage() {
        MessageEntity message = new MessageEntity();
        when(messageRepository.save(message)).thenReturn(message);

        MessageEntity result = messageService.addMessage(message);

        assertEquals(message, result);
        verify(messageRepository, times(1)).save(message);
    }

    @Test
    void testRetrieveById() {
        MessageEntity message = new MessageEntity();
        when(messageRepository.findById(1L)).thenReturn(Optional.of(message));

        MessageEntity result = messageService.retrieveById(1L);

        assertEquals(message, result);
        verify(messageRepository, times(1)).findById(1L);
    }

    @Test
    void testUpdateMessage() {
        MessageEntity message = new MessageEntity();
        when(messageRepository.save(message)).thenReturn(message);

        MessageEntity result = messageService.updateMessage(message);

        assertEquals(message, result);
        verify(messageRepository, times(1)).save(message);
    }

    @Test
    void testRetrieveAll() {
        List<MessageEntity> messages = List.of(new MessageEntity());
        when(messageRepository.findAll()).thenReturn(messages);

        List<MessageEntity> result = messageService.retrieveAll();

        assertEquals(messages, result);
        verify(messageRepository, times(1)).findAll();
    }

    @Test
    void testDeleteById() {
        messageService.deleteById(1L);

        verify(messageRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteFor() {
        MessageEntity message = new MessageEntity();
        message.setMs_id(1L);
        message.setDeleteForAll(false);
        message.setWhoMakeDelete("user1");

        when(messageRepository.findById(1L)).thenReturn(Optional.of(message));

        messageService.deleteFor(message);

        verify(messageRepository, times(1)).findById(1L);
        verify(messageRepository, times(1)).save(message);
        assertTrue(message.isDeleteForAll());
    }

    @Test
    void testMsgReact() {
        MessageEntity message = new MessageEntity();
        message.setMs_id(1L);
        message.setReaction(false);

        when(messageRepository.findById(1L)).thenReturn(Optional.of(message));

        message.setReaction(true);
        messageService.msgReact(message);

        verify(messageRepository, times(1)).findById(1L);
        verify(messageRepository, times(1)).save(message);
        assertTrue(message.isReaction());
    }

    @Test
    void testFindAllByChat() {
        List<MessageEntity> messages = List.of(new MessageEntity());
        when(messageRepository.findByChatId(1L)).thenReturn(messages);

        List<MessageEntity> result = messageService.findAllByChat(1L);

        assertEquals(messages, result);
        verify(messageRepository, times(1)).findByChatId(1L);
    }

    @Test
    void testGetTheLastMsg() {
        ChatEntity chat = new ChatEntity();
        chat.setChat_id(1L);
        when(chatRepository.findByName("testChat")).thenReturn(chat);

        MessageEntity lastMessage = new MessageEntity();
        when(messageRepository.findFirstByChatIdOrderByCreatedAtDesc(1L)).thenReturn(lastMessage);

        MessageEntity result = messageService.getTheLastMsg("testChat");

        assertEquals(lastMessage, result);
        verify(chatRepository, times(1)).findByName("testChat");
        verify(messageRepository, times(1)).findFirstByChatIdOrderByCreatedAtDesc(1L);
    }
}
