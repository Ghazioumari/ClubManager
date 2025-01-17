package club.esprit.backend.repository;

import club.esprit.backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);
    Optional<User> findById(Long id);
    @Query("SELECT m.user FROM Membership m WHERE m.club.id = :clubId")
    List<User> findUsersByClubId(@Param("clubId") Long clubId);
    @Query("SELECT m.user FROM Membership m WHERE m.department.id = :departmentId")
    List<User> findUsersByDepartmentId(@Param("departmentId") Long departmentId);
    @Query("SELECT m.user FROM Membership m WHERE m.club.id = :clubId AND m.department IS NULL")
    List<User> findMembersWithoutDepartment(@Param("clubId") Long clubId);

    @Query("SELECT u FROM User u WHERE u.id NOT IN (SELECT m.user.id FROM Membership m WHERE m.club.id = :clubId)")
    List<User> findUsersNotInClub(@Param("clubId") Long clubId);

}
