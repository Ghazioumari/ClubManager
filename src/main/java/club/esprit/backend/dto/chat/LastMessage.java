package club.esprit.backend.dto.chat;

import club.esprit.backend.entities.User;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
public class LastMessage{
    public String lastMsg;
    public User user;
    public String sender;
    public String tstamp;
}
