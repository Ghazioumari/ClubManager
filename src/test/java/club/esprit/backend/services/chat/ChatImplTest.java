package club.esprit.backend.services.chat;

import club.esprit.backend.dto.chat.LastMessage;
import club.esprit.backend.entities.User;
import club.esprit.backend.entities.chat.ChatEntity;
import club.esprit.backend.entities.chat.MessageEntity;
import club.esprit.backend.repository.UserRepository;
import club.esprit.backend.repository.chat.ChatRepository;
import club.esprit.backend.repository.chat.MessageRepository;
import club.esprit.backend.services.chat.ChatImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ChatImplTest {

    @Mock
    private ChatRepository chatRepository;
    @Mock
    private MessageRepository messageRepository;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ChatImpl chatService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddChat() {
        ChatEntity chat = new ChatEntity();
        when(chatRepository.save(chat)).thenReturn(chat);

        ChatEntity result = chatService.addChat(chat);

        assertEquals(chat, result);
        verify(chatRepository, times(1)).save(chat);
    }

    @Test
    void testRetrieveById() {
        ChatEntity chat = new ChatEntity();
        when(chatRepository.findById(1L)).thenReturn(Optional.of(chat));

        ChatEntity result = chatService.retrieveById(1L);

        assertEquals(chat, result);
        verify(chatRepository, times(1)).findById(1L);
    }

    @Test
    void testUpdateChat() {
        ChatEntity chat = new ChatEntity();
        when(chatRepository.save(chat)).thenReturn(chat);

        ChatEntity result = chatService.updateChat(chat);

        assertEquals(chat, result);
        verify(chatRepository, times(1)).save(chat);
    }

    @Test
    void testRetrieveAll() {
        List<ChatEntity> chatList = new ArrayList<>();
        when(chatRepository.findAll()).thenReturn(chatList);

        List<ChatEntity> result = chatService.retrieveAll();

        assertEquals(chatList, result);
        verify(chatRepository, times(1)).findAll();
    }

    @Test
    void testDeleteById() {
        chatService.deleteById(1L);

        verify(chatRepository, times(1)).deleteById(1L);
    }

    @Test
    void testFindByName() {
        ChatEntity chat = new ChatEntity();
        when(chatRepository.findByName("testChat")).thenReturn(chat);

        ChatEntity result = chatService.findByName("testChat");

        assertEquals(chat, result);
        verify(chatRepository, times(1)).findByName("testChat");
    }

    @Test
    void testFindUserByEmail() {
        User user = new User();
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));

        Optional<User> result = chatService.findUserByEmail("test@example.com");

        assertTrue(result.isPresent());
        assertEquals(user, result.get());
        verify(userRepository, times(1)).findByEmail("test@example.com");
    }



    @Test
    void testGetChannelName() {
        User user1 = new User();
        user1.setId(1L);
        user1.setEmail("user1@example.com");

        User user2 = new User();
        user2.setId(2L);
        user2.setEmail("user2@example.com");

        String channelName = chatService.getChannelName(user1, user2);

        assertEquals("user1@example.com&user2@example.com", channelName);
    }
}
