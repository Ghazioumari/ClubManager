package club.esprit.backend.repository.chat;

import club.esprit.backend.entities.chat.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface MessageRepository extends JpaRepository<MessageEntity, Long>{
    List<MessageEntity> findByChatId(long chat_id);
    MessageEntity findFirstByChatIdOrderByCreatedAtDesc(long chat_id);
}
