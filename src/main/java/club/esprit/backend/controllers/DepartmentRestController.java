package club.esprit.backend.controllers;

import club.esprit.backend.entities.Club;
import club.esprit.backend.entities.Department;
import club.esprit.backend.entities.Membership;
import club.esprit.backend.entities.User;
import club.esprit.backend.services.IDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/department")
public class DepartmentRestController {
    private IDepartmentService departmentService;

    @Autowired
    public DepartmentRestController(IDepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping("/add")
    public Department addDepartment(@RequestBody Department department) {
        return departmentService.addDepartment(department);
    }
    @PutMapping("/create/{idclub}/{iduser}")
    public Department addCreate(@RequestBody Department department,@PathVariable("idclub") Long idclub
    ,@PathVariable("iduser") Long iduser
    ) {

        return departmentService.createDepartment(department,idclub,iduser);
    }

    @PutMapping("/update")
    public ResponseEntity<Department> updateDepartment(@RequestBody Department department) {
        Department department1  = departmentService.updateDepartment(department);
        return ResponseEntity.ok(department1);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteDepartment(@PathVariable Long id) {
        departmentService.deleteDepartment(id);
    }

    @GetMapping("/get/{id}")
    public Department getDepartment(@PathVariable Long id) {
        return departmentService.getDepartment(id);
    }

    @GetMapping("/getAll")
    public List<Department> getAllDepartments() {
        return departmentService.getAllDepartments();
    }
    @GetMapping("/getbyclub/{id}")
    public List<Department> addClub(@PathVariable Long id) {
     return departmentService.findDepartmentsByClub_Id(id);
    }
    @GetMapping("/responsable/{id}")
    public Optional<User> findresonsable(@PathVariable Long id) {
        //System.out.println(departmentService.findResponsableOfDepartment(id));

        return departmentService.findResponsibleUserByDepartmentId(id);
    }

}
