package club.esprit.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import club.esprit.backend.entities.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MembershipRepository extends JpaRepository<Membership, Long> {
    List<Membership> findByUserId(Long userId);
    List<Membership> findByClub_Id(Long clubId);
    List<Membership> findByDepartment_Id(Long departmentId);
    Membership findByClub_IdAndAndUserId(Long clubId,Long userId);
    List<Membership> findMembershipByDepartment_Id(Long id);
    @Query("SELECT m.user FROM Membership m WHERE m.department.id = :departmentId AND m.responsable = true")
    Optional<User> findResponsibleUserByDepartmentId(@Param("departmentId") Long departmentId);

   // Membership findByDepartment_IdAndAndUser_Id(Long departmentId, Long userId);



}
