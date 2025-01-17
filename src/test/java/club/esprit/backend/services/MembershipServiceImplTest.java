package club.esprit.backend.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import club.esprit.backend.entities.Membership;
import club.esprit.backend.entities.Department;
import club.esprit.backend.entities.User;
import club.esprit.backend.repository.DepartmentRepository;
import club.esprit.backend.repository.MembershipRepository;
import club.esprit.backend.repository.UserRepository;
import club.esprit.backend.services.MembershipServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MembershipServiceImplTest {

    @Mock
    private MembershipRepository membershipRepository;
    @Mock
    private DepartmentRepository departmentRepository;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private MembershipServiceImpl membershipService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddMember() {
        Membership membership = new Membership();
        when(membershipRepository.save(membership)).thenReturn(membership);

        Membership result = membershipService.addMember(membership);

        assertEquals(membership, result);
        verify(membershipRepository, times(1)).save(membership);
    }

    @Test
    void testDeleteMember() {
        Long memberId = 1L;

        membershipService.deleteMember(memberId);

        verify(membershipRepository, times(1)).deleteById(memberId);
    }

    @Test
    void testGetAllMembers() {
        membershipService.getAllMembers();

        verify(membershipRepository, times(1)).findAll();
    }

    @Test
    void testGetMembershipByDepartment() {
        Long departmentId = 1L;

        membershipService.getMembershipByDepartment(departmentId);

        verify(membershipRepository, times(1)).findByDepartment_Id(departmentId);
    }


}
