package club.esprit.backend.services;

import club.esprit.backend.entities.Favoris;
import club.esprit.backend.entities.Ressource;
import org.springframework.web.multipart.MultipartFile;


public interface IRessource {
    Ressource addRessource(Ressource ressource, MultipartFile file);
    Favoris addRessourceToFavoris(Long favorisId, Ressource ressource);
    void deleteRessource(Long ressourceId);

//    String uploadFile(MultipartFile file);
}
