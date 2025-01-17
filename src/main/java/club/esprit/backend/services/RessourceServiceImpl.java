package club.esprit.backend.services;


import club.esprit.backend.entities.Favoris;
import club.esprit.backend.entities.Ressource;
import club.esprit.backend.repository.FavorisRepository;
import club.esprit.backend.repository.RessourceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.Optional;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@AllArgsConstructor
public class RessourceServiceImpl implements IRessource  {

    private RessourceRepository ressourceRepository;
    private FavorisRepository favorisRepository;

//    @Override
//    public Ressource addRessource(Ressource ressource) {
//        return ressourceRepository.save(ressource);
//    }

    @Override
    public Ressource addRessource(Ressource ressource, MultipartFile file) {
        String filePath = uploadFile(file);
        ressource.setFilePath(filePath);
        return ressourceRepository.save(ressource);
    }

    private String uploadFile(MultipartFile file) {
        try {
            Path path = Paths.get("uploads/" + file.getOriginalFilename());
            Files.write(path, file.getBytes());
            return path.toString();
        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
    }

    @Override
    public Favoris addRessourceToFavoris(Long favorisId, Ressource ressource) {
        Optional<Favoris> favorisOptional = favorisRepository.findById(favorisId);
        if (favorisOptional.isPresent()) {
            Favoris favoris = favorisOptional.get();
            ressource.setFavoris(favoris);
            ressourceRepository.save(ressource);
            favoris.getRessources().add(ressource);
            return favorisRepository.save(favoris);
        } else {
            throw new RuntimeException("Favoris not found with id: " + favorisId);
        }
    }

    @Override
    public void deleteRessource(Long ressourceId) {
        Optional<Ressource> ressourceOptional = ressourceRepository.findById(ressourceId);
        if (ressourceOptional.isPresent()) {
            Ressource ressource = ressourceOptional.get();
            ressourceRepository.delete(ressource);
        } else {
            throw new RuntimeException("Ressource not found with id: " + ressourceId);
        }
    }

//    @Override
//    public String uploadFile(MultipartFile file) {
//        try {
//            // This will save the file to a directory. You can change the directory as needed.
//            Path path = Paths.get("uploads/" + file.getOriginalFilename());
//            Files.write(path, file.getBytes());
//            return path.toString();
//        } catch (Exception e) {
//            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
//        }
//    }


}
