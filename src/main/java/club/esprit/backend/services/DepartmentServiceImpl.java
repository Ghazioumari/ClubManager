package club.esprit.backend.services;

import club.esprit.backend.entities.Club;
import club.esprit.backend.entities.Department;
import club.esprit.backend.entities.Membership;
import club.esprit.backend.entities.User;
import club.esprit.backend.repository.DepartmentRepository;
import club.esprit.backend.repository.MembershipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.lang.reflect.Field;
@Service
public class DepartmentServiceImpl implements IDepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private MembershipRepository membershipRepository;
    @Autowired
    MembershipService membershipService;


    @Override
    public Department addDepartment(Department department) {
        return departmentRepository.save(department);
    }

    @Override
    public Department createDepartment(Department department, Long club, Long user) {

        Department dep =addDepartment(department);
        Membership membership = membershipRepository.findByClub_IdAndAndUserId(club, user);
        membership.setResponsable(true);
        membership.setDepartment(dep);
        membershipService.updateMember(membership);
        return dep;

    }

    @Override
    public Department updateDepartment(Department department) {

        Department existingDepartment = departmentRepository.getById(department.getId());

        Field[] fields = Department.class.getDeclaredFields();


        for (Field field : fields) {
            try {

                field.setAccessible(true);


                Object value = field.get(department);


                if (value != null) {
                    field.set(existingDepartment, value);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }


        Department updatedDepartment = departmentRepository.save(existingDepartment);

        return updatedDepartment;
    }


    @Override
    public void deleteDepartment(Long id) {

        List<Membership> memberships=membershipRepository.findMembershipByDepartment_Id(id);
        for (Membership membership:memberships){
            membership.setDepartment(null);
            membership.setResponsable(false);
            membershipRepository.save(membership);
        }
        departmentRepository.deleteById(id);
    }

    @Override
    public Department getDepartment(Long id) {
        return departmentRepository.findById(id).orElse(null);
    }

    @Override
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    public List<Department> findDepartmentsByClub_Id(Long club_id){
        return departmentRepository.findDepartmentsByClub_Id(club_id);
    }

    @Override
    public Optional<User> findResponsibleUserByDepartmentId(Long id) {
        return membershipRepository.findResponsibleUserByDepartmentId(id);
    }


}
