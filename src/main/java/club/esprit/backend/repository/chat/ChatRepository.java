package club.esprit.backend.repository.chat;

import club.esprit.backend.entities.chat.ChatEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRepository extends JpaRepository<ChatEntity, Long> {
    ChatEntity findByName(String chat);
    //List<ChatEntity> findByPartecipantEmail(String login);
}
