package club.esprit.backend.repository;

import club.esprit.backend.entities.RequestToJoin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RequestToJoinRepository extends JpaRepository<RequestToJoin, Long> {
    List<RequestToJoin> findByUserId(Long userId);
    List<RequestToJoin> findByClub_Id(Long clubId);
}