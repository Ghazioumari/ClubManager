package club.esprit.backend.controllers;

import club.esprit.backend.entities.Membership;
import club.esprit.backend.entities.User;
import club.esprit.backend.services.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/users")
public class UserRestController {

    private UserServiceImp userService;

    @Autowired
    public UserRestController(UserServiceImp userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userService.addUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable("userId") Long userId) {
        User user = userService.getById(userId);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }




    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
    @GetMapping("/userbyclub/{id}")
    public ResponseEntity<List<User>> getusersbyclub(@PathVariable("id") Long id) {
        List<User> users = userService.getUsersByClub(id);
        return ResponseEntity.ok(users);
    }
    @GetMapping("/userbydepartment/{id}")
    public ResponseEntity<List<User>> getusersbydepartment(@PathVariable("id") Long id) {
        List<User> users = userService.findUsersByDepartmentId(id);
        return ResponseEntity.ok(users);
    }
    @GetMapping("/memberwithoutdepartment/{id}")
    public ResponseEntity<List<User>> memberwithoutdepartment(@PathVariable("id") Long id) {
        List<User> users = userService.findMembersWithoutDepartment(id);
        return ResponseEntity.ok(users);
    }


    @PutMapping("/{userId}/update")
    public ResponseEntity<User> updateUser(@PathVariable("userId") Long userId, @RequestBody User user) {
        user.setId(userId);
        User updatedUser = userService.updateUser(user);
        if (updatedUser != null) {
            return ResponseEntity.ok(updatedUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{userId}/delete")
    public ResponseEntity<Void> deleteUser(@PathVariable("userId") Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/{userId}/members")
    public ResponseEntity<List<Membership>> getMembersForUser(@PathVariable Long userId) {
        List<Membership> memberships = userService.getMembersForUser(userId);
        System.out.println(memberships);
        return ResponseEntity.ok(memberships);
    }
@GetMapping("/userNotinClub")
    public ResponseEntity<List<User>> findUsersNotClub(@RequestParam Long id) {
        List<User> users = userService.findUsersNotClub(id);
        return ResponseEntity.ok(users);
    }


    // get by mail
    @GetMapping("/email/{e}")
    public User getUserById(@PathVariable("e") String e) {
        return userService.getByEmail(e);
    }

    @DeleteMapping("deleteUserFromDepartment/{userId}/{clubId}")
    public User deleteuserFromdepartment(@PathVariable("userId")  Long userId,@PathVariable("clubId") Long clubId){
        return userService.deleteuserFromdepartment(userId, clubId);
    }
}
