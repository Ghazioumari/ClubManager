package club.esprit.backend.repository;

import club.esprit.backend.entities.Membership;
import org.springframework.data.jpa.repository.JpaRepository;
import club.esprit.backend.entities.Club;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClubRepository extends JpaRepository<Club, Long> {
    @Query("SELECT m.club FROM Membership m WHERE m.user.id = :userId")
    List<Club> findClubsByUserId(@Param("userId") Long userId);
    @Query("SELECT m.club FROM Membership m WHERE m.user.id = :userId AND m.president = true")
    List<Club> findClubsByUserIdAndPresident(@Param("userId") Long userId);


}
