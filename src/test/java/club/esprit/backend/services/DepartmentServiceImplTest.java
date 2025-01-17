package club.esprit.backend.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import club.esprit.backend.entities.Department;
import club.esprit.backend.entities.Membership;
import club.esprit.backend.entities.User;
import club.esprit.backend.repository.DepartmentRepository;
import club.esprit.backend.repository.MembershipRepository;
import club.esprit.backend.services.DepartmentServiceImpl;
import club.esprit.backend.services.MembershipService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DepartmentServiceImplTest {

    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private MembershipRepository membershipRepository;

    @Mock
    private MembershipService membershipService;

    @InjectMocks
    private DepartmentServiceImpl departmentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddDepartment() {
        Department department = new Department();
        when(departmentRepository.save(department)).thenReturn(department);

        Department result = departmentService.addDepartment(department);

        assertEquals(department, result);
        verify(departmentRepository, times(1)).save(department);
    }

    @Test
    void testCreateDepartment() {
        Department department = new Department();
        Long clubId = 1L;
        Long userId = 2L;
        Membership membership = new Membership();

        when(departmentRepository.save(department)).thenReturn(department);
        when(membershipRepository.findByClub_IdAndAndUserId(clubId, userId)).thenReturn(membership);

        Department result = departmentService.createDepartment(department, clubId, userId);

        assertEquals(department, result);
        assertTrue(membership.isResponsable());
        assertEquals(department, membership.getDepartment());
        verify(membershipService, times(1)).updateMember(membership);
    }

    @Test
    void testUpdateDepartment() {
        Department department = new Department();
        department.setId(1L);
        Department existingDepartment = new Department();
        existingDepartment.setId(1L);

        when(departmentRepository.getById(department.getId())).thenReturn(existingDepartment);
        when(departmentRepository.save(existingDepartment)).thenReturn(existingDepartment);

        Department result = departmentService.updateDepartment(department);

        assertEquals(existingDepartment, result);
        verify(departmentRepository, times(1)).save(existingDepartment);
    }

    @Test
    void testDeleteDepartment() {
        Long departmentId = 1L;
        List<Membership> memberships = new ArrayList<>();
        memberships.add(new Membership());

        when(membershipRepository.findMembershipByDepartment_Id(departmentId)).thenReturn(memberships);

        departmentService.deleteDepartment(departmentId);

        for (Membership membership : memberships) {
            assertNull(membership.getDepartment());
            assertFalse(membership.isResponsable());
            verify(membershipRepository, times(1)).save(membership);
        }
        verify(departmentRepository, times(1)).deleteById(departmentId);
    }

    @Test
    void testGetDepartment() {
        Long departmentId = 1L;
        Department department = new Department();
        department.setId(departmentId);

        when(departmentRepository.findById(departmentId)).thenReturn(Optional.of(department));

        Department result = departmentService.getDepartment(departmentId);

        assertEquals(department, result);
        verify(departmentRepository, times(1)).findById(departmentId);
    }

    @Test
    void testGetAllDepartments() {
        List<Department> departments = List.of(new Department(), new Department());
        when(departmentRepository.findAll()).thenReturn(departments);

        List<Department> result = departmentService.getAllDepartments();

        assertEquals(departments.size(), result.size());
        verify(departmentRepository, times(1)).findAll();
    }

    @Test
    void testFindDepartmentsByClub_Id() {
        Long clubId = 1L;
        List<Department> departments = List.of(new Department(), new Department());

        when(departmentRepository.findDepartmentsByClub_Id(clubId)).thenReturn(departments);

        List<Department> result = departmentService.findDepartmentsByClub_Id(clubId);

        assertEquals(departments, result);
        verify(departmentRepository, times(1)).findDepartmentsByClub_Id(clubId);
    }

    @Test
    void testFindResponsibleUserByDepartmentId() {
        Long departmentId = 1L;
        User user = new User();
        Optional<User> optionalUser = Optional.of(user);

        when(membershipRepository.findResponsibleUserByDepartmentId(departmentId)).thenReturn(optionalUser);

        Optional<User> result = departmentService.findResponsibleUserByDepartmentId(departmentId);

        assertEquals(optionalUser, result);
        verify(membershipRepository, times(1)).findResponsibleUserByDepartmentId(departmentId);
    }
}
