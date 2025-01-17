package club.esprit.backend.services;

import club.esprit.backend.entities.Membership;
import club.esprit.backend.entities.User;

import java.util.List;

public interface UserService {
    User addUser(User user);
    User updateUser(User user);
    void deleteUser(Long id);
    User getUser(Long id);
    List<User> getAllUsers();
    User getById(Long id);
    List<Membership> getMembersForUser(Long userId);

    List<User> getUsersByClub(Long id);
    List<User> findUsersNotClub(Long id);
    List<User> findUsersByDepartmentId( Long departmentId);
    List<User> findMembersWithoutDepartment(Long id);

    User deleteuserFromdepartment(Long userId,Long departmentId);
    //
    User getByEmail(String email);
}
