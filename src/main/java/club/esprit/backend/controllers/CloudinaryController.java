package club.esprit.backend.controllers;

import club.esprit.backend.entities.EventImage;
import club.esprit.backend.services.events.CloudinaryService;
import club.esprit.backend.services.events.EventImageService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.imageio.ImageIO;

@RestController
@RequestMapping("/cloudinary")
//@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
public class CloudinaryController {

    CloudinaryService cloudinaryService;


    EventImageService eventImageService;

    @GetMapping("/list")
    public ResponseEntity<List<EventImage>> list(){
        List<EventImage> list = eventImageService.list();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
    @GetMapping("/imageurl/{id}")
    public ResponseEntity<String> getImageUrl(@PathVariable("id") int id){
        Optional<EventImage> eventImageOptional = eventImageService.getOne(id);
        if (eventImageOptional.isPresent()) {
            String imageUrl = eventImageOptional.get().getImageUrl();
            return new ResponseEntity<>(imageUrl, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/upload")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> upload(@RequestParam MultipartFile multipartFile) throws IOException {
        BufferedImage bi = ImageIO.read(multipartFile.getInputStream());
        if (bi == null) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", "Invalid image!");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
        Map result = cloudinaryService.upload(multipartFile);
        EventImage eventImage = new EventImage((String) result.get("original_filename"),
                (String) result.get("url"),
                (String) result.get("public_id"));
        eventImageService.save(eventImage);

        Map<String, Object> successResponse = new HashMap<>();
        successResponse.put("message", "Image successfully added!");
        successResponse.put("name", eventImage.getName());
        successResponse.put("url", eventImage.getImageUrl());
        successResponse.put("public_id", eventImage.getImageId());
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") int id) {
        Optional<EventImage> imageOptional = eventImageService.getOne(id);
        if (imageOptional.isEmpty()) {
            return new ResponseEntity<>("n'existe pas", HttpStatus.NOT_FOUND);
        }
        EventImage eventImage = imageOptional.get();
        String cloudinaryImageId = eventImage.getImageId();
        try {
            cloudinaryService.delete(cloudinaryImageId);
        } catch (IOException e) {
            return new ResponseEntity<>("Failed to delete image from Cloudinary", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        eventImageService.delete(id);
        return new ResponseEntity<>("image supprimée !", HttpStatus.OK);
    }

}