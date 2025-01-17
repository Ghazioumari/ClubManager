package club.esprit.backend.services;

import club.esprit.backend.entities.Club;
import club.esprit.backend.entities.Department;
import club.esprit.backend.entities.Membership;
import club.esprit.backend.entities.User;
import club.esprit.backend.repository.DepartmentRepository;
import club.esprit.backend.repository.MembershipRepository;
import club.esprit.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
//import org.webjars.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.lang.reflect.Field;
@Service
public class MembershipServiceImpl implements MembershipService {

    private final MembershipRepository membershipRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    public MembershipServiceImpl(MembershipRepository membershipRepository) {
        this.membershipRepository = membershipRepository;
    }

    @Override
    public Membership addMember(Membership membership) {
        return membershipRepository.save(membership);
    }

    @Override
    public Membership updateMember(Membership membership) {
        Membership existingMembership = membershipRepository.getById(membership.getId());
        Field[] fields = Membership.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                Object value = field.get(membership);
                if (value != null) {
                    field.set(existingMembership, value);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        // Save the updated Membership object
        Membership updatedMembership = membershipRepository.save(existingMembership);

        return updatedMembership;
    }

    @Override
    public void deleteMember(Long memberId) {
        membershipRepository.deleteById(memberId);
    }

    @Override
    public void deleteMemberByUserId_ClubId(Long userId, Long clubId) {
        Membership m=membershipRepository.findByClub_IdAndAndUserId(clubId,userId);

        membershipRepository.delete(m);

    }

    @Override
    public Membership getMemberById(Long memberId) {
        return membershipRepository.findById(memberId).orElse(null);
    }

    @Override
    public List<Membership> getAllMembers() {
        return membershipRepository.findAll();
    }
    @Override
    public List<Membership> getMembershipByclub(Long id ) {
        return membershipRepository.findByClub_Id(id);
    }

    @Override
    public List<Membership> getMembershipByDepartment(Long id) {
        return membershipRepository.findByDepartment_Id(id);
    }

    @Override
    public Membership getMembershipByUserandClub(Long idUser, Long idClub) {
       return  membershipRepository.findByClub_IdAndAndUserId(idClub,idUser);
    }

    public List<Membership> assignUsersToDepartment(List<Long> userIds, Long departmentId,Long clubId) throws ChangeSetPersister.NotFoundException {

        Department department = departmentRepository.findById(departmentId).orElseThrow(ChangeSetPersister.NotFoundException::new);


        List<Membership> memberships = new ArrayList<>();

        for (Long userId : userIds) {

            User user = userRepository.findById(userId)
                    .orElseThrow(ChangeSetPersister.NotFoundException::new);


            Membership membership = membershipRepository.findByClub_IdAndAndUserId(clubId,user.getId());


            membership.setDepartment(department);


            Membership savedMembership = membershipRepository.save(membership);

            memberships.add(savedMembership);
        }

        return memberships;
    }
}
