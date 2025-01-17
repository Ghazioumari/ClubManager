package club.esprit.backend.controllers;

import club.esprit.backend.entities.Favoris;
import club.esprit.backend.entities.Ressource;
import club.esprit.backend.services.IRessource;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.databind.ObjectMapper;


@RestController
public class RessourceController {

    private final IRessource iRessource;


    @Autowired
    public RessourceController(IRessource iRessource) {
        this.iRessource = iRessource;
    }

//    @PostMapping("/addRessource")
//    public Ressource addRessource(@RequestBody Ressource ressource) {
//        return iRessource.addRessource(ressource);
//    }

    @PostMapping("/addRessource")
    public Ressource addRessource(@RequestPart("ressource") String ressourceStr, @RequestPart("file") MultipartFile file) {
        ObjectMapper objectMapper = new ObjectMapper();
        Ressource ressource = null;
        try {
            ressource = objectMapper.readValue(ressourceStr, Ressource.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return iRessource.addRessource(ressource, file);
    }




    @PostMapping("/addRessourceToFavoris/{favorisId}")
    public Favoris addRessourceToFavoris(@PathVariable Long favorisId, @RequestBody Ressource ressource) {
        return iRessource.addRessourceToFavoris(favorisId, ressource);
    }


    @DeleteMapping("/deleteRessource/{ressourceId}")
    public void deleteRessource(@PathVariable Long ressourceId) {
        iRessource.deleteRessource(ressourceId);
    }

//    @PostMapping("/uploadFile")
//    public String uploadFile(@RequestParam("file") MultipartFile file) {
//        return iRessource.uploadFile(file);
//    }
}
