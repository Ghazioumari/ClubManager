package club.esprit.backend.entities.chat;

import club.esprit.backend.entities.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name="Chat")
@Table(name = "CHATS")
public class ChatEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_id")
    private long chat_id;

    @Column(name = "name")
    private String name;

    public ChatEntity(String name) {
        this.name = name;
    }
}