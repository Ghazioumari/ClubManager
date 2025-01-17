package club.esprit.backend.controllers;

import club.esprit.backend.entities.Club;
import club.esprit.backend.entities.quiz.Test;
import club.esprit.backend.services.IClubService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
//@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/club")
public class ClubRestController {
    private IClubService clubService;

    @Autowired
    public ClubRestController(IClubService clubService) {
        this.clubService = clubService;
    }

    @PostMapping("/add")
    public ResponseEntity<Club> addClub(@RequestParam("name") String name,
                                        @RequestParam("description") String description,
                                        @RequestParam("contactInfo") String contactInfo,
                                        @RequestParam("logo") MultipartFile logo) {
        Club club = new Club();
        club.setName(name);
        club.setDescription(description);
        club.setContactInfo(contactInfo);

        // Handle the logo file
        if (logo != null && !logo.isEmpty()) {
            try {
                // Save the file to a directory
                String logoPath = "C:\\xampp\\htdocs\\img\\"; // Update this path
                Path path = Paths.get(logoPath + logo.getOriginalFilename() );
                Files.write(path, logo.getBytes());

                // Set the logo URL in the club object
                club.setLogo(logo.getOriginalFilename());
            } catch (IOException e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            }
        }

        Club savedClub = clubService.addClub(club);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedClub);
    }

    @PutMapping("/update/{clubId}")
    public ResponseEntity<Club> updateClub(@PathVariable("clubId") Long clubId, @RequestBody Club club) {
        club.setId(clubId);
        Club updatedClub = clubService.updateClub(club);
        if (updatedClub != null) {
            return ResponseEntity.ok(updatedClub);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public void deleteClub(@PathVariable Long id) {
        clubService.deleteClub(id);
    }

    @GetMapping("/get/{id}")
    public Club getClub(@PathVariable Long id) {
        return clubService.getClub(id);
    }

    @GetMapping("/clubs")
    public List<Club> getAllClubs() {
        return clubService.getAllClubs();
    }
    @GetMapping("/getbyuser/{id}")
    public List<Club> getAllClubs(@PathVariable Long id) {
        return clubService.getClubByUser(id);
    }
    @GetMapping("/getbyuserandpresident/{id}")
    public Club findClubsByUserIdAndPresident(@PathVariable Long id) {
        return  clubService.findClubsByUserIdAndPresident(id);
    }
        @PutMapping("/updateclubtest/{clubId}")
        public Club updateClubTest(@PathVariable  Long clubId,@RequestBody Test newTest){
            return clubService.updateClubTest(clubId,newTest);
        }
}
