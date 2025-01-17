package club.esprit.backend.controllers.chat;

import club.esprit.backend.dto.ChatMessage;
import club.esprit.backend.dto.chat.LastMessage;
import club.esprit.backend.entities.User;
import club.esprit.backend.entities.chat.ChatEntity;
import club.esprit.backend.entities.chat.MessageEntity;
import club.esprit.backend.services.chat.IChat;
import club.esprit.backend.services.chat.IMessage;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.nio.file.Files.copy;
import static java.nio.file.Paths.get;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class WebSocketController {
    private IChat chatDAO;
    private IMessage messageDAO;

    public static final String DIRECTORY = System.getProperty("user.home") + "/Downloads/uploads/";


    @MessageMapping("/chat/{to}")
    @SendTo("/topic/{to}")
    public ChatMessage chat(@DestinationVariable String to, MessageEntity message) {
        if (message.getWhoMakeDelete() != null){
            this.messageDAO.deleteFor(message);
            return new ChatMessage();
        }else if (message.getMs_id() != 0){
            this.messageDAO.msgReact(message);
            return new ChatMessage();
        }

        message.setChatId(createAndOrGetChat(to));
        message.setT_stamp(generateTimeStamp());
        message = messageDAO.addMessage(message);
        return new ChatMessage(message.getContent(), message.getSender());
    }
    //returns an empty list if the chat doesn't exist
    @PostMapping("/getMessages")
    public List<MessageEntity> getMessages(@RequestBody String chat) {
        ChatEntity ce = chatDAO.findByName(chat);

        if(ce != null) {
            return messageDAO.findAllByChat(ce.getChat_id());
        }
        else{
            return new ArrayList<MessageEntity>();
        }
    }

    @PostMapping("/chat/upload")
    public ResponseEntity<List<String>> uploadFiles(@RequestParam("files")List<MultipartFile> multipartFiles) throws IOException {
        List<String> filenames = new ArrayList<>();
        for(MultipartFile file : multipartFiles) {
            String filename = StringUtils.cleanPath(file.getOriginalFilename());
            Path fileStorage = get(DIRECTORY, filename).toAbsolutePath().normalize();
            copy(file.getInputStream(), fileStorage, REPLACE_EXISTING);
            filenames.add(filename);
        }
        return ResponseEntity.ok().body(filenames);
    }
    @GetMapping("/download/{filename}")
    public ResponseEntity<Resource> downloadFiles(@PathVariable("filename") String filename) throws IOException {
        Path filePath = get(DIRECTORY).toAbsolutePath().normalize().resolve(filename);
        if(!java.nio.file.Files.exists(filePath)) {
            throw new FileNotFoundException(filename + " was not found on the server");
        }
        Resource resource = new UrlResource(filePath.toUri());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("File-Name", filename);
        httpHeaders.add(CONTENT_DISPOSITION, "attachment;File-Name=" + resource.getFilename());
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(Files.probeContentType(filePath)))
                .headers(httpHeaders).body(resource);
    }

    @GetMapping("/getfile/{filename}")
    public ResponseEntity<Resource> getFile(@PathVariable("filename") String filename) throws IOException{

        Path filePath = get(DIRECTORY).toAbsolutePath().normalize().resolve(filename);
        if(!java.nio.file.Files.exists(filePath)) {
            throw new FileNotFoundException(filename + " was not found on the server");
        }
        Resource resource = new UrlResource(filePath.toUri());
        if (resource.exists() || resource.isReadable()) {
            return ResponseEntity.ok().contentType(MediaType.parseMediaType(Files.probeContentType(filePath)))
                    .body(resource);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/getAllUsers/{email}")
    public List<LastMessage> getAllUsers(@PathVariable String email) {
        return chatDAO.findAllUsers(email);
    }
    @GetMapping("/findByEmail/{email}")
    public Optional<User> getUserByEmail(@PathVariable String email){
        return chatDAO.findUserByEmail(email) ;
    }

    @DeleteMapping("/deletemsg/{id}")
    public void deletemsg(@PathVariable int id){
        messageDAO.deleteById(id); ;
    }

    @GetMapping("/getlastmsg/{channelname}")
    public MessageEntity getlastmsg(@PathVariable String channelname) {
        return messageDAO.getTheLastMsg(channelname);
    }
    private Long createAndOrGetChat(String name) {
        ChatEntity ce = chatDAO.findByName(name);

        if (ce != null) {
            return ce.getChat_id();
        }
        else {
            ChatEntity newChat = new ChatEntity(name);
            return chatDAO.addChat(newChat).getChat_id();
        }
    }

    private String generateTimeStamp() {
        Instant i = Instant.now();
        String date = i.toString();
        int endRange = date.indexOf('T');
        date = date.substring(0, endRange);
        date = date.replace('-', '/');
        String time = Integer.toString(i.atZone(ZoneOffset.UTC).getHour() + 1);
        time += ":";

        int minutes = i.atZone(ZoneOffset.UTC).getMinute();
        if (minutes > 9) {
            time += Integer.toString(minutes);
        } else {
            time += "0" + Integer.toString(minutes);
        }

        String timeStamp = date + "-" + time;
        return timeStamp;
    }
}
