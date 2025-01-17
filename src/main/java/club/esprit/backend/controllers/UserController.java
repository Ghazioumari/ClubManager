package club.esprit.backend.controllers;

import club.esprit.backend.entities.Role;
import club.esprit.backend.entities.User;
import club.esprit.backend.repository.UserRepository;
import club.esprit.backend.services.IUser;
import club.esprit.backend.utils.UserUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final IUser iUser;
    private final UserRepository userRepository;
    private final UserUtil userUtil;

    @PutMapping("/{id}/role")
    public ResponseEntity<?> setNewRole(@PathVariable Long id, @RequestBody Role newRole) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        iUser.setNewRole(user, newRole);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/allUsers")
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(iUser.getAllUsers());
    }

    @GetMapping("/usersByRoleAndEtat/{role}/{etat}")
    public ResponseEntity<?> getUsersByRoleAndEtat(@PathVariable String role, @PathVariable String etat) {
        return ResponseEntity.ok(iUser.getUsersByRoleAndEtat(role, etat));
    }


    @GetMapping("/api/user")
    public ResponseEntity<?> getUser() {
        UserDetails userDetails = userUtil.getAuthenticatedUser();
        if (userDetails != null) {
            return ResponseEntity.ok(userDetails);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
        }
    }


    @PutMapping("/{id}/pending")
    public void setPending(@PathVariable Long id) {
        iUser.setEtatToPending(id);
    }

    @PutMapping("/{id}/accepted")
    public ResponseEntity<?> setAccepted(@PathVariable Long id) {
        boolean isUpdated = iUser.setEtatToAccepted(id);
        if (isUpdated) {
            return ResponseEntity.ok().body("User status updated to accepted and role set to responsable.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/rejected")
    public void setRejected(@PathVariable Long id) {
        iUser.setEtatToRejected(id);
    }


    @GetMapping("/findByEmail/{email}")
    public Optional<User> getUserByEmail(@PathVariable String email){
        return iUser.findUserByEmail(email) ;
    }

    @PutMapping("/{id}/updateProfile")
    public ResponseEntity<?> updateUserProfile(@PathVariable Long id, @RequestBody User user) {
        try {
            iUser.updateUserProfile(id, user);
            return ResponseEntity.ok().body("User profile updated successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating user profile: " + e.getMessage());
        }
    }
}
