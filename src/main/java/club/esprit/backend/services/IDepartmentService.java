package club.esprit.backend.services;

import club.esprit.backend.entities.Club;
import club.esprit.backend.entities.Department;
import club.esprit.backend.entities.Membership;
import club.esprit.backend.entities.User;

import java.util.List;
import java.util.Optional;

public interface IDepartmentService {
    Department addDepartment(Department department);
    Department createDepartment(Department department, Long club, Long user);
    Department updateDepartment(Department department);
    void deleteDepartment(Long id);
    Department getDepartment(Long id);
    List<Department> getAllDepartments();
    List<Department> findDepartmentsByClub_Id(Long club_id);
    Optional<User> findResponsibleUserByDepartmentId(Long id);
}
