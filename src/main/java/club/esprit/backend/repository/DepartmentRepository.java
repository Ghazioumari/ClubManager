package club.esprit.backend.repository;

import club.esprit.backend.entities.Club;
import club.esprit.backend.entities.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    //List<Department> getDepartmentsByClub(Club club) ;
    List<Department> findDepartmentsByClub_Id(Long club_id);
}
